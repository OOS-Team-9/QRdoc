package project.controller.extractor;

import project.model.MyDoc;
import project.model.information.Link;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 문서 안에서 Link를 추출하는 클래스
 */
public class LinkExtractor extends Extractor<Link> {
    Pattern sPattern;

    //private ArrayList<Text> textList= new ArrayList<>();       //texts[0]에는 1쪽의 Text객체 담김

    /**
     * 생성자
     * @param doc 파싱할 문서
     * @throws IOException
     */
    public LinkExtractor(MyDoc doc) throws IOException {
        super(doc);
        //링크 패턴 설정

        sPattern=Pattern.compile("\\s*");
        pattern =Pattern.compile("([^\\:/\\?#@\\s]+://.+)(www\\.\\S+)?|(www\\.\\S+)");
        //pattern =Pattern.compile("([^\\:/\\?#@\\s]+://.+)(www\\.)?[^(http)(www)\\s]+|www\\.[^(http)(www)\\s]+");
/*
        pattern = Pattern.compile("(((http(s)?:\\\\/\\\\/)\\\\S+(\\\\.[^(\\\\n|\\\\t|\\\\s,)]+)+)|((http(s)?:\\\\/\\\\/)?\" +\n" +
                "(([a-zA-z\\\\-_]+[0-9]*)|([0-9]*[a-zA-z\\\\-_]+)){2,}(\\\\.[^(\\\\n|\\\\t|\\\\s,)]+)+))+");

 */
}

    /**pattern =Pattern.compile("([^\\:/\\?#@\\s]+://.+)(www\\.)?[^(http)(www)\\s]+|www\\.[^(http)(www)\\s]+");
     * 링크 리스트를 리턴하는 함수
     * @return 문서 안에서 추출한 링크 리스트
     */
    public ArrayList<ArrayList<Link>> getLinkList() {
        return super.getInfoList();
    }

    public void extract(){
        for(int i=0;i<pageNum;i++){
            infoList.add(new ArrayList<Link>());
            Matcher matcher =pattern.matcher(originalPageList.get(i).getText());
            Link tempLk;
            Matcher sMatcher;
            while(matcher.find()) {
                tempLk=new Link(matcher.group(),i,i);
                sMatcher=sPattern.matcher(tempLk.getLink());
                if(sMatcher.matches())
                    continue;
                //System.out.println("test:"+tempLk.getLink());
                infoList.get(i).add(new Link(tempLk.getLink(),i,i));
            }
        }
    }
}
