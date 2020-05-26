package project.controller.extractor;

import org.apache.pdfbox.text.PDFTextStripper;
import project.model.MyDoc;
import project.model.information.Information;
import project.model.Page;


import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 특정 패턴을 만족하는 문자열을 문서에서 추출하는 클래스
 */
abstract public class Extractor<T extends Information> {

    protected MyDoc doc;                //파싱할 문서
    protected int pageNum;              //문서 안 페이지 수
    protected ArrayList<Page> pageList; //문서 안 모든 페이지 정보

    protected Pattern pattern;           //특정한 패턴

    protected ArrayList<ArrayList<T>> infoList;  //문서 안에서 특정한 패턴을 만족하는 모든 문자열 정보
    protected PDFTextStripper stripper;  //문서를 파싱하는 기계



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
        infoList = new ArrayList<>();
        stripper = new PDFTextStripper();
        pattern = Pattern.compile("");
    }

    /**
     * infoList의 getter 함수
     * @return  특정 패턴을 만족하는 문자열(infoList) ex)링크 리스트
     */
    public ArrayList<ArrayList<T>> getInfoList() {
        return this.infoList;
    }

    /**
     * 페이지 단위로 문서를 읽고 이를 Page객체에 저장하는 함수
     * @throws IOException
     */
    public void readTexts() throws IOException {
        String buffer = new String();

        //페이지 하나씩 확인
        for (int i = 0; i < pageNum; i++) {
            System.out.println("i: "+i);
            //범위를 페이지 하나로 한정 지음
            stripper.setStartPage(i + 1);
            stripper.setEndPage(i + 1);

            pageList.add(new Page(i));
            buffer = stripper.getText(doc);
            //페이지 안 문자열을 페이지 객체에 저장
            pageList.get(i).setText(buffer);
            System.out.println("page"+i+" text:\n"+pageList.get(i).getText());
        }
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


