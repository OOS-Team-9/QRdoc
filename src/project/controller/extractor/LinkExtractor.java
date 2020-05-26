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
        pattern = Pattern.compile("(((http(s?))\\:\\/\\/)?)([0-9a-zA-Z\\-]+\\.)+[a-zA-Z]{2,6}(\\:[0-9]+)?(\\/\\S*)?");
    }

    /**
     * 링크 리스트를 리턴하는 함수
     * @return 문서 안에서 추출한 링크 리스트
     */
    public ArrayList<ArrayList<Information>> getLinkList() {
        return super.getInfoList();
    }
}
