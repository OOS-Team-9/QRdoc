package project.controller.extractor;

import project.model.MyPath;
import project.model.information.Information;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * 문서 안에서 Link를 추출하는 클래스
 */
public class LinkExtractor extends Extractor {


    /**
     * 생성자
     * @param docFile       
     * @throws IOException
     */
    public LinkExtractor(MyPath docFile) throws IOException {
        super(docFile);
        //링크 패턴 설정

        sPattern=Pattern.compile("\\s*");
        pattern =Pattern.compile("([^\\:/\\?#@\\s]+://.+)(www\\.\\S+)?|(www\\.\\S+)");
        //pattern =Pattern.compile("([^\\:/\\?#@\\s]+://.+)(www\\.)?[^(http)(www)\\s]+|www\\.[^(http)(www)\\s]+");
/*
        pattern = Pattern.compile("(((http(s)?:\\\\/\\\\/)\\\\S+(\\\\.[^(\\\\n|\\\\t|\\\\s,)]+)+)|((http(s)?:\\\\/\\\\/)?\" +\n" +
                "(([a-zA-z\\\\-_]+[0-9]*)|([0-9]*[a-zA-z\\\\-_]+)){2,}(\\\\.[^(\\\\n|\\\\t|\\\\s,)]+)+))+");

*/

}


    /**
     * 링크 리스트를 리턴하는 함수
     * @return 문서 안에서 추출한 링크 리스트
     */
    public ArrayList<ArrayList<Information>> getLinkList() {
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


