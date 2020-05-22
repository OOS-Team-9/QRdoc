package project.controller;
import project.model.*;
import java.util.*;
import org.apche.pdfbox.text.PDFTextStripper;

public class LinkExtracter {
    private MyDoc doc;
    private int pageNum;
    private ArrayList<Text> textList= new ArrayList<>();       //texts[0]에는 1쪽의 Text객체 담김
    private ArrayList<ArrayList> linkList= new ArrayList<>();  //links[0]에는 1쪽의 링크들의 ArrayList가 담김
    private PDFTextStripper.stripper= new PDFTextStripper();
    private Pattern pattern=Pattern.compile("(([^:/?#@]+):)?((//)?([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?")

    LinkExtracter(MyDoc doc){
        this.doc=doc;
        this.pageNum=doc.pageNum;
    }
    void readTexts(){

        for(int i=0;i<pageNum;i++){
            stripper.setStartPage(i);
            stripper.setEndPage(i);

            textList.add(new Text());

            stripper.writeText(doc,textList[i].text);
            textList[i].pageNum=stripper.getStartPage();
        }
    }
    void extractLinks(){

        for(int i=0;i<pageNum;i++){
            linkList.add(new ArrayList<String>());
            Matcher matcher =pattern.matcher(textList[i].text);
            while(matcher.find()){
                linkList[i].add(matcher.group());
            }
        }
    }

    Text getTextAt(int pageNum) {
        if(pageNum>this.pageNum)
            return;
        return textList[pageNum-1];
    }
    ArrList getLinksAt(int pageNum){
        if(pageNum>this.pageNum)
            return;
        return linkList[pageNum-1];
    }

}
