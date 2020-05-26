package project.controller.extractor;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import project.model.MyPath;
import project.model.Page;
import project.model.information.Information;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 특정 패턴을 만족하는 문자열을 문서에서 추출하는 클래스
 */
abstract public class Extractor {

    protected MyPath docFile;                   //파싱할 문서 경로
    protected ArrayList<Page> pageList;         //문서 안 모든 페이지
    protected Pattern pattern;                  //특정한 패턴
    protected ArrayList<ArrayList<Information>> infoList; //문서 안에서 특정한 패턴을 만족하는 모든 문자열 정보
    protected PDFTextStripper stripper;         //문서를 파싱하는 기계

    /**
     * 생성자
     * @param docFile
     * @throws IOException
     */
    public Extractor(MyPath docFile) throws IOException {
        this.docFile = docFile;
        pageList = new ArrayList<>();
        infoList = new ArrayList<>();
        stripper = new PDFTextStripper();
        pattern = Pattern.compile("");
    }

    /**
     * infoList의 getter 함수
     *
     * @return 특정 패턴을 만족하는 문자열(infoList) ex)링크 리스트
     */
    public ArrayList<ArrayList<Information>> getInfoList() {
        return this.infoList;
    }

    /**
     * 페이지 단위로 문서를 읽고 이를 Page객체에 저장하는 함수
     *
     * @throws IOException
     */
    public void readTexts() throws IOException {

        PDDocument doc = null;
        //문서 열기
        try {
            doc = PDDocument.load(new File(docFile.getPathString()));
        } catch (IOException e) {
            throw new IOException("Extractor::readTexts ERROR: [ " + docFile.getPathString() + " ]을 열 수 없습니다.");
        }

        System.out.println("-------------[ "+docFile.getPath()+" ] 읽기 시작-------------");
        String buffer = null;
        //한 페이지씩 글자 뽑아내기
        for (int i = 0; i < doc.getNumberOfPages(); i++) {
            //읽을 페이지를 한 쪽으로 한정함.
            stripper.setStartPage(i + 1);
            stripper.setEndPage(i + 1);
            pageList.add(new Page(i));
            //PDF문서 한 페이지에서 글자 읽기
            try {
                buffer = stripper.getText(doc);
            } catch (IOException e) {
                throw new IOException
                        ("Extractor::readTexts ERROR: [ " + docFile.getPathString() + " ] stripper가 작동하지 않습니다.");
            }
            //한 페이지에서 뽑아낸 문자열을 배열에 저장
            pageList.get(i).setText(buffer);
            pageList.get(i).print();
        }
        //문서 닫기
        try {
            doc.close();
        }catch(IOException e){
            throw new IOException("Extractor::readTexts ERROR: [ " + docFile.getPathString() + " ]을 닫을 수 없습니다.");
        }
    }

    /**
     * 문서 안에서 특정 패턴을 만족하는 문자열을 추출하는 함수
     * 실행 순서: 생성자 호출 -> readTexts 호출 -> extract 호출 -> getInfoList 호출
     */
    public void extract() {
        System.out.println("-------------파싱 시작-------------");
        Matcher matcher;
        //페이지 하나씩 확인
        for (int pageOrder = 0; pageOrder < pageList.size(); pageOrder++) {
            infoList.add(new ArrayList<>());

            //특정 패턴을 만족하는 문자열 추출
            matcher = pattern.matcher(pageList.get(pageOrder).getText());

            //이 문자열을 배열에 저장
            for (int infoOrder = 0; matcher.find(); infoOrder++) {
                infoList.get(pageOrder).add(new Information(matcher.group(), matcher.start(), infoOrder));
                infoList.get(pageOrder).get(infoOrder).print();
            }
        }
    }
}
