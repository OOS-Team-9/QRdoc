package project.controller.qr_inserter;

import project.model.MyDoc;
import project.model.Qr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  QR-code를 문서에 삽입한 다음,
 *  다른 PDF 파일로 저장하는 클래스
 */
public interface QRinserter {
    /**
     * PDF 파일에 QR-code를 삽입한 다음,
     * "[원래 문서 이름]~QR.pdf" 이름으로 저장하는 함수
     * @param myDoc   기존 PDF 파일
     * @param qrCodeList PDF파일에 삽입할 QR-code 모음
     * @throws IOException
     */
    void insert(List<List<Qr>> qrCodeList, MyDoc myDoc, int pageOrder) throws IOException;
    void insert(List<List<Qr>> qrCodeList, MyDoc myDoc, int pageOrder, Integer[] availableBlank)
            throws IOException;// 여백삽입
}

