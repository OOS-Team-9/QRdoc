package project.controller.bitmatrix_generator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import project.model.information.Information;

/**
 * 문자열을 입력 받아 일반 QR-code bit matrix로 만드는 클래스
 */
public class DefaultBitMTgenerator extends BitMTgenerator {

    /**
     * QR코드 bit matrix를 만드는 함수
     * @param info              변환할 문자열 정보
     * @param qrWidth           QR코드 가로 길이
     * @param qrHeight          QR코드 세로 길이
     * @return
     * @throws WriterException
     */
    @Override
    public BitMatrix generate(Information info, int qrWidth, int qrHeight) throws WriterException {

        System.out.println("\n-----[ " + info.getText() + " ] bitmatrix 생성-----");
        assert (qrWidth > 0 && qrHeight > 0);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        return qrCodeWriter.encode(info.getText(), BarcodeFormat.QR_CODE, qrWidth, qrHeight);
    }
}
