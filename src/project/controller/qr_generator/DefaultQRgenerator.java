package project.controller.qr_generator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import project.model.exception.WidthHeightNegativeException;
import project.model.information.Information;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * 문자열을 일반 QR-code로 변환하여 이를 이미지 파일로 저장하는 클래스
 */
public class DefaultQRgenerator extends QRgenerator {

    /**
     * 생성자
     * @param outputDirectory QR코드 저장 경로
     */
    public DefaultQRgenerator(String outputDirectory) {
        super(outputDirectory);
    }

    public DefaultQRgenerator(DefaultQRgenerator other) {
        super(other);
    }

    public DefaultQRgenerator() {
        super();
    }

    /**
     * 문자열 정보를 입력 받아, QR코드 이미지 파일을 만들고
     * 이것을 미리 지정한 경로에 저장하는 함수
     * @param info              변환할 문자열 정보
     * @param qrWidth           QR코드 가로 길이
     * @param qrHeight          QR코드 세로 길이
     * @param outputFileName    이미지 파일 이름
     * @throws WriterException
     * @throws IOException
     * @throws WidthHeightNegativeException QR코드 규격을 잘못 입력한 경우
     */
    public void generate(Information info, int qrWidth, int qrHeight, String outputFileName)
            throws WriterException, IOException, WidthHeightNegativeException {
        //QR코드 규격 점검
        if (qrWidth <= 0) {
            throw new WidthHeightNegativeException("QR-code의 너비값이 0과 같거나 작습니다.");
        } else if (qrHeight <= 0) {
            throw new WidthHeightNegativeException("QR-code의 높이값이 0과 같거나 작습니다.");
        }
        //
        //  QR코드 생성 및 이미지 파일 저장
        //
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode
                (info.getText(), BarcodeFormat.QR_CODE, qrWidth, qrHeight);
        Path path = FileSystems.getDefault().getPath(outputDirectory + "\\" + outputFileName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);    //PNG 파일에 저장.
    }
}
