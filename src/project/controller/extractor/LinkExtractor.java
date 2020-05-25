package project.controller.extractor;

import project.model.*;
import project.model.information.Information;
import java.util.ArrayList;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * 문서 안에서 Link를 추출하는 클래스
 */
public class LinkExtractor extends InfoExtractor {

    /**
     * 생성자
     * @param doc 파싱할 문서
     * @throws IOException
     */
    public LinkExtractor(MyDoc doc) throws IOException {
        super(doc);
        //링크 패턴 설정
        pattern = Pattern.compile("(((http(s)?:\\\\/\\\\/)\\\\S+(\\\\.[^(\\\\n|\\\\t|\\\\s,)]+)+)|((http(s)?:\\\\/\\\\/)?\" +\n" +
                "(([a-zA-z\\\\-_]+[0-9]*)|([0-9]*[a-zA-z\\\\-_]+)){2,}(\\\\.[^(\\\\n|\\\\t|\\\\s,)]+)+))+");
    }

    /**
     * 링크 리스트를 리턴하는 함수
     * @return 문서 안에서 추출한 링크 리스트
     */
    public ArrayList<ArrayList<Information>> getLinkList() {
        return super.getInfoList();
    }
}
