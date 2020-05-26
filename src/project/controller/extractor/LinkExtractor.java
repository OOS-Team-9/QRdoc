package project.controller.extractor;

import project.model.MyDoc;
import project.model.information.Link;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * 문서 안에서 Link를 추출하는 클래스
 */
public class LinkExtractor extends Extractor<Link> {

    /**
     * 생성자
     * @param doc 파싱할 문서
     * @throws IOException
     */
    public LinkExtractor(MyDoc doc) throws IOException {
        super(doc);
        //링크 패턴 설정
        pattern = Pattern.compile("(((http(s?))\\:\\/\\/)?)([0-9a-zA-Z\\-]+\\.)+[a-zA-Z]{2,6}(\\:[0-9]+)?(\\/\\S*)?");
    }

    /**
     * 링크 리스트를 리턴하는 함수
     * @return 문서 안에서 추출한 링크 리스트
     */
    public ArrayList<ArrayList<Link>> getLinkList() {
        return super.getInfoList();
    }
}
