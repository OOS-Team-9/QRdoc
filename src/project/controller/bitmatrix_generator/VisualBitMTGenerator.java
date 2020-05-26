package project.controller.bitmatrix_generator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import project.model.information.Information;

import java.io.IOException;

/*Visual Qr-code를 만들어서, 이미지 파일로 저장하는 클래스(추가 구현 사항이므로 아직 구현 안함)*/

/**
 * 문자열을 Visual QR-code로 변환하여 이를 이미지 파일로 저장하는 클래스
 */
public class VisualBitMTGenerator extends BitMTGenerator {

    /**
     * 문자열 정보를 입력 받아, QR코드 이미지 파일을 만들고
     * 이것을 미리 지정한 경로에 저장하는 함수
     * @param info              변환할 문자열 정보
     * @param qrWidth           QR코드 가로 길이
     * @param qrHeight          QR코드 세로 길이
     * @throws WriterException
     * @throws IOException
     */
    @Override
    BitMatrix generate(Information info, int qrWidth, int qrHeight) throws WriterException, IOException {
        assert (qrWidth > 0 && qrHeight > 0);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        return qrCodeWriter.encode (info.getText(), BarcodeFormat.QR_CODE, qrWidth, qrHeight);
    }
}
