package project.controller;
import project.model.*;


import java.util.ArrayList;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.text.PDFTextStripper;

public class LinkExtracter {
    private MyDoc doc;
    private int pageNum;
    private ArrayList<Text> textList= new ArrayList<>();       //texts[0]에는 1쪽의 Text객체 담김
    private ArrayList<ArrayList> linkList= new ArrayList<>();  //links[0]에는 1쪽의 링크들의 ArrayList가 담김
    private PDFTextStripper stripper= new PDFTextStripper();
    private Pattern pattern=Pattern.compile("(([^:/?#@]+):)?((//)?([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");

    LinkExtracter(MyDoc doc) throws IOException {
        this.doc=doc;
        this.pageNum=doc.getNumberOfPages();
    }
    void readTexts() throws IOException {
        String buffer=new String();
        for(int i=1;i<=pageNum;i++){
            stripper.setStartPage(i);
            stripper.setEndPage(i);

            textList.add(new Text(i));

            buffer=stripper.getText(doc);
            textList.get(i).setText(buffer);

        }
    }
    void extractLinks(){
        for(int i=0;i<pageNum;i++){
            linkList.add(new ArrayList<String>());
            Matcher matcher =pattern.matcher(textList.get(i).getText());

            for(int j=0;matcher.find();j++){
                linkList.get(i).add(new Link(i,matcher.group()));
            }
        }
    }

    Text getTextAt(int pageNum) {
        if(pageNum > this.pageNum)
            return null;
        return textList.get(pageNum - 1);
    }
    
    ArrayList getLinksAt(int pageNum){
        if(pageNum>this.pageNum)
            return null;
        return linkList.get(pageNum - 1);
    }

}
