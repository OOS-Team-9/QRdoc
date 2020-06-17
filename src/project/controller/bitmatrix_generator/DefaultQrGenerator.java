package project.controller.bitmatrix_generator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import project.model.Qr;
import project.model.information.Information;

import java.awt.image.BufferedImage;

/**
 * 문자열을 입력 받아 일반 QR-code bit matrix로 만드는 클래스
 */
public class DefaultQrGenerator implements QrGenerator {

    /**
     * QR코드 bit matrix를 만드는 함수
     * @param info              변환할 문자열 정보
     * @param qrWidth           QR코드 가로 길이
     * @param qrHeight          QR코드 세로 길이
     * @return
     * @throws WriterException
     */
    public Qr generate(Information info,
                              int pageOrder,
                              int infoOrderPerPage,
                              int qrWidth,
                              int qrHeight) throws WriterException {
        BitMatrix bitMatrix=new QRCodeWriter().encode(info.getText(), BarcodeFormat.QR_CODE, qrWidth, qrHeight);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return new Qr(pageOrder,infoOrderPerPage,bitMatrix,bufferedImage);
    }
}
