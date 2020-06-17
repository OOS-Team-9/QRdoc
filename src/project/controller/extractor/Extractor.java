package project.controller.extractor;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import project.model.MyDoc;
import project.model.information.Information;
import project.model.Page;


import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;

/**
 * 특정 패턴을 만족하는 문자열을 문서에서 추출하는 클래스
 */
abstract public class Extractor<T extends Information> {

    protected MyDoc doc;                //파싱할 문서
    protected int pageNum;              //문서 안 페이지 수
    protected ArrayList<Page> pageList; //문서 안 모든 페이지 정보(개행문제 삭제됨)
    protected ArrayList<Page> originalPageList; //문서 안 모든 페이지 정보(개행문자 삭제 안 된 원문)_link추출에서 쓰임

    protected Pattern pattern;           //특정한 패턴

    protected ArrayList<ArrayList<T>> infoList;  //문서 안에서 특정한 패턴을 만족하는 모든 문자열 정보
    protected Stripper stripper;  //문서를 파싱하는 기계
    ArrayList<List<TextPosition>> listTP = new ArrayList<>();    //TextPostion저장 리스트



    //links[0]에는 1쪽의 링크들의 ArrayList가 담김




    /**
     * 생성자
     * @param doc 파싱할 문서
     * @throws IOException
     */
    public Extractor(MyDoc doc) throws IOException {
        this.doc = doc;
        this.pageNum = doc.getNumberOfPages();
        pageList = new ArrayList<>();
        originalPageList=new ArrayList<>();
        infoList = new ArrayList<>();
        stripper = new Stripper();
        pattern = Pattern.compile("");
    }

    /**
     * infoList의 getter 함수
     * @return  특정 패턴을 만족하는 문자열(infoList) ex)링크 리스트
     */
    public ArrayList<ArrayList<T>> getInfoList() {
        return this.infoList;
    }
    public ArrayList<Page> getPageList(){
        return pageList;
    }
    /**
     * 페이지 단위로 문서를 읽고 이를 Page객체에 저장하는 함수
     * @throws IOException
     */
    public void readTexts() throws IOException {

        String buffer = new String();
        stripper.setSortByPosition(true);
        stripper.setStartPage(1);
        stripper.setEndPage(pageNum);




        //페이지 하나씩 확인
        for (int i = 0; i < pageNum; i++) {
            //System.out.println("i: "+i);
            //범위를 페이지 하나로 한정 지음
            stripper.setSortByPosition(true);
            stripper.setStartPage(i + 1);
            stripper.setEndPage(i + 1);
            StringWriter outString = new StringWriter();
            stripper.writeText(doc, outString);
            List<TextPosition> tempL = stripper.getCharactersByArticle().get(0);
            //System.out.println("temp size:" + tempL.size() + "temp:" + tempL.toString());
            listTP.add(tempL);

            pageList.add(new Page(i));
            originalPageList.add(new Page(i));
            buffer = stripper.getText(doc);
            //페이지 안 문자열을 페이지 객체에 저장
            originalPageList.get(i).setText(buffer);
            pageList.get(i).setText(buffer.replace(System.getProperty("line.separator"), ""));
            String temp = pageList.get(i).getText();
            System.out.println("page"+i+"length:"+ temp.length()+" text:"+temp);
        }
        System.out.println("TP size"+listTP.get(0).size()+"TP: "+listTP.get(0).toString());
    }
    //
    public void setPos(){
        String text;
        for(int i=0;i<pageList.size();i++){
            text=pageList.get(i).getText();

            for(int j=0;j<infoList.get(i).size();j++){
                //i번째 페이지의 j번째 info의 위치 정보와 폰트 사이즈 저장
                //System.out.println("test"+infoList.get(i).get(j).getText());
                int infoIndex=text.indexOf(infoList.get(i).get(j).getText())+infoList.get(i).get(j).getText().length()-1;

                //text=text.substring(infoIndex+1,text.length()-1);
                //System.out.println(listTP.get(i).get(infoIndex).toString());
                infoList.get(i).get(j).setxPos(listTP.get(i).get(infoIndex).getEndX());
                infoList.get(i).get(j).setyPos(listTP.get(i).get(infoIndex).getEndY());
                infoList.get(i).get(j).setFontSize(listTP.get(i).get(infoIndex).getFontSize());
                System.out.println(infoList.get(i).get(j).getText().length());
                text = text.replaceFirst(infoList.get(i).get(j).getText(),replaceString(infoList.get(i).get(j).getText().length()));
            }
        }
        //테스트용 출력
        for(int i=0;i<pageList.size();i++) {
            for (int j = 0; j < infoList.get(i).size(); j++) {
                System.out.println("text: " + infoList.get(i).get(j).getText());
                System.out.println("xPos: " + infoList.get(i).get(j).getxPos());
                System.out.println("yPos: " + infoList.get(i).get(j).getyPos());
                System.out.println("fontSize: " + infoList.get(i).get(j).getFontSize());
            }
        }


    }
    public void findBlank() throws IOException {
        for (int i = 0; i < pageList.size(); i++) {
            for (int j = 0; j < listTP.get(i).size(); j++) {
                TextPosition target = listTP.get(i).get(j);
                if (target.getUnicode().equals(" ") | target.getUnicode().equals("\t") | target.getUnicode().equals("\n"))
                    continue;
                int x = (int) target.getEndX();
                int y = (int) target.getEndY();
                pageList.get(i).fillBlank(x / 50, y / 50);
            }
        }

        int pageIndex=0;
        for(PDPage page:doc.getPages()){
            ImageLocationGetter printer=new ImageLocationGetter(pageIndex);
            printer.processPage(page);
            Page temp=printer.getPageWBlankInfo(pageIndex);
            for(int x=0;x<12;x++){
                for(int y=0;y<17;y++){
                    if(temp.isThereBlankAt(x,y)==false)
                        pageList.get(pageIndex).fillBlank(x,y);
                }
            }
            pageIndex++;
        }
        for(int y=16;y>=0;y--){
            for(int x=0;x<12;x++){
                System.out.print(pageList.get(0).isThereBlankAt(x,y)+" ");
            }
            System.out.println(" ");
        }
    }
    public void findBlankForQRcode() throws IOException {
        for(int i=0;i<pageList.size();i++) {
            Page p=pageList.get(i);
            p.resetAvailableBlankForQRcode();
            for (int x = 0; x < 11; x++) {
                for (int y = 0; y < 16; y++) {
                    if (p.isThereBlankAt(x,y)&&p.isThereBlankAt(x+1,y)&&p.isThereBlankAt(x,y+1)&&p.isThereBlankAt(x+1,y+1)) {
                        p.addAvailableBlankForQRcode(x, y);
                        System.out.println(x+","+y);
                    }
                }
            }
        }
        //test


    }
    public Integer[] findClosestBlank(Page page, Information info){
        Integer[] blank=new Integer[2];
        int x=(int)info.getxPos()/50;
        int y=(int)info.getyPos()/50;
        ArrayList<Integer[]> available=page.getAvailableBlankForQRcode();

        int shortestDist=450;            //pdf 대각선길이의 제곱 보다 길게 초기화
        System.out.println("큐알코드 삽입위치 개수"+available.size());
        for(int i=0;i<available.size();i++) {
            int xDist=abs(available.get(i)[0]-x);
            int yDist=abs(available.get(i)[1]-y);

            if(shortestDist>(xDist*xDist)+(yDist*yDist)){
                shortestDist=(xDist*xDist)+(yDist*yDist);
                blank[0]=available.get(i)[0];
                blank[1]=available.get(i)[1];
            }
            //test
            //System.out.println("shortestDist:"+shortestDist+" xDist:"+xDist+" yDist:"+yDist+" 여백좌표:"+blank[0]+","+blank[1]+"링크좌표"+x+","+y);
        }
        return blank;
    }


    String replaceString(int num){
        String temp = "";
        for(int i = 0; i < num; i++){
            temp += (" ");
        }
        return temp;
    }

    /**
     * 문서 안에서 특정 패턴을 만족하는 문자열을 추출하는 함수
     * 실행 순서: 생성자 호출 -> readTexts 호출 -> extract 호출 -> getInfoList 호출
     */
    abstract public void extract();
    /* {
        Matcher matcher;
        //페이지 하나씩 확인
        for (int pageOrder = 0; pageOrder < pageNum; pageOrder++) {
            infoList.add(new ArrayList<>());

            //특정 패턴을 만족하는 문자열 추출
            matcher = pattern.matcher(pageList.get(pageOrder).getText());

            //이 문자열을 배열에 저장
            for (int infoOrder = 0; matcher.find(); infoOrder++) {
                infoList.get(pageOrder).add((T)(new Information(matcher.group(), matcher.start(), infoOrder)));
            }
        }
    }
    */


}


