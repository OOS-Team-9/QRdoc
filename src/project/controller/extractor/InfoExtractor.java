package project.controller.extractor;

import org.apache.pdfbox.text.PDFTextStripper;
import project.model.*;
import project.model.information.Information;
import project.model.information.Link;
import project.model.information.Page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*특정 패턴을 만족하는 문자열을 문서에서 추출하는 추상 클래스*/
abstract public class InfoExtractor {
    protected MyDoc doc;
    protected int pageNum;
    protected ArrayList<Page> pageList;       //texts[0]에는 1쪽의 Text객체 담김
    protected ArrayList<ArrayList<Information>> infoList;  //links[0]에는 1쪽의 링크들의 ArrayList가 담김
    protected PDFTextStripper stripper;
    protected Pattern pattern;

    public InfoExtractor(MyDoc doc) throws IOException {
        this.doc = doc;
        this.pageNum = doc.getNumberOfPages();
        pageList = new ArrayList<>();
        infoList = new ArrayList<>();
        stripper = new PDFTextStripper();
        pattern = Pattern.compile("");
    }

    public ArrayList<ArrayList<Information>> getInfoList() {
        return this.infoList;
    }

    public void readTexts() throws IOException {
        String buffer = new String();
        for (int i = 0; i < pageNum; i++) {
            // System.out.println("i: "+i);
            stripper.setStartPage(i + 1);
            stripper.setEndPage(i + 1);

            pageList.add(new Page(i));

            buffer = stripper.getText(doc);
            pageList.get(i).setText(buffer);
            //System.out.println("page"+i+" text:"+textList.get(i).getText());
        }
    }

    public void extract() {
        Matcher matcher;
        /*페이지 하나씩 확인*/
        for (int pageOrder = 0; pageOrder < pageNum; pageOrder++) {

            infoList.add(new ArrayList<>());
            matcher = pattern.matcher(pageList.get(pageOrder).getText());

            /*한 페이지 안에 들어 있는 모든 링크를 array에 저장*/
            for (int infoOrder = 0; matcher.find(); infoOrder++) {
                infoList.get(pageOrder).add(new Link(matcher.group(), matcher.start(), infoOrder));
            }
        }
    }
}
