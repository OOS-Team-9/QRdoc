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
    private ArrayList<ArrayList<Link>> linkList= new ArrayList<>();  //links[0]에는 1쪽의 링크들의 ArrayList가 담김
    private PDFTextStripper stripper= new PDFTextStripper();
    private Pattern pattern=Pattern.compile("(([^\\:/\\?#@\\s]+):)?((//)?([^/\\?#\\s]]+))?[^\\?#\\s]*(\\?([^#\\s]+))?(#\\S+)?");
    private Pattern sPattern=Pattern.compile("\\s*");

    public LinkExtracter(MyDoc doc) throws IOException {
        this.doc=doc;
        this.pageNum=doc.getNumberOfPages();
    }
    public void readTexts() throws IOException {
        String buffer=new String();
        for(int i=0;i<pageNum;i++){
           // System.out.println("i: "+i);
            stripper.setStartPage(i+1);
            stripper.setEndPage(i+1);

            textList.add(new Text(i));

            buffer=stripper.getText(doc);
            textList.get(i).setText(buffer);
            //System.out.println("page"+i+" text:"+textList.get(i).getText());
        }
    }
    public void extractLinks(){
        for(int i=0;i<pageNum;i++){
            linkList.add(new ArrayList<Link>());
            Matcher matcher =pattern.matcher(textList.get(i).getText());
            Link tempLk;
            Matcher sMatcher;
            while(matcher.find()) {
                tempLk=new Link(i, matcher.group());
                sMatcher=sPattern.matcher(tempLk.getLink());
                if(sMatcher.matches())
                    continue;

                linkList.get(i).add(new Link(i, tempLk.getLink()));
            }
            //System.out.println(linkList.get(i).size());
            /*
            for(int j=0;j<linkList.get(i).size();j++){
                System.out.println("page"+i+" linkNum"+j+": "+linkList.get(i).get(j).getLink());
            }
             */
        }
    }

    public Text getTextAt(int pageNum) {
        if(pageNum > this.pageNum)
            return null;
        return textList.get(pageNum - 1);
    }

    public ArrayList<Link> getLinksAt(int pageNum){
        if(pageNum>this.pageNum)
            return null;
        return linkList.get(pageNum - 1);
    }
    public ArrayList<Text> getTextList(){
        return textList;
    }
    public ArrayList<ArrayList<Link>> getLinkList(){
        return linkList;
    }

}
