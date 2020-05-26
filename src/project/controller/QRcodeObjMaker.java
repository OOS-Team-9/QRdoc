package project.controller;

import com.google.zxing.common.BitMatrix;
import project.model.QRcode;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QRcodeObjMaker {

    /**
     * path 문자열을 '디렉토리'와 '파일명'으로 나눠주는 함수
     * @param targetFile    '디렉토리'와 '파일명'을 저장할 객체
     * @param path          path 문자열
     * @throws Exception
     */
    protected static void parsePath(QRcode targetFile, String path) throws IOException {
        Pattern pattern = Pattern.compile("(.*\\\\)((.*)\\.(\\w*$))");           //특정한 패턴
        Matcher matcher = pattern.matcher(path);

        if (matcher.groupCount() != 4) {
            throw (new IOException("\" " + path + " \"" + "는 유효한 이미지 파일 경로가 아닙니다."));
        } else {
            targetFile.setPath(matcher.group(0));
            targetFile.setDirectory(matcher.group(1));
            targetFile.setFileName(matcher.group(2));
            targetFile.setFileType(matcher.group(4));
        }
    }

    /**
     * QRcode 객체를 만드는 함수
     * @param bitmatrix QR code의 bit matrix
     * @param path      QR code 이미지 저장 경로
     * @param width     QR code 폭
     * @param height    QR code 높이
     * @return          QR code 객체
     * @throws Exception
     */
    protected QRcode makeQRcodeObj(int order, BitMatrix bitmatrix, String path, int width, int height) throws IOException {
        QRcode qr = new QRcode();
        qr.setOrder(order);
        qr.setBitmatrix(bitmatrix);
        parsePath(qr, path);
        qr.setWidth(width);
        qr.setHeight(height);
        return qr;
    }
}
