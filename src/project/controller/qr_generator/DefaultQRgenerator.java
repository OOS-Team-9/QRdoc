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

/*가장 기본적인 QR-code를 만들어서 이미지 파일로 저장하는 클래스*/
public class DefaultQRgenerator extends QRgenerator {
    /*생성자*/
    public DefaultQRgenerator(String outputDirectory) {
        super(outputDirectory);
    }

    public DefaultQRgenerator(DefaultQRgenerator other) {
        super(other);
    }

    public DefaultQRgenerator() {
        super();
    }

    /*QR-code를 만들어서 이미지 파일로 저장하는 함수*/
    public void generate(Information info, int qrWidth, int qrHeight, String outputFileName)
            throws WriterException, IOException, WidthHeightNegativeException {
        /*QR-code 이미지 파일 폭, 높이값 점검*/
        if (qrWidth <= 0) {
            throw new WidthHeightNegativeException("QR-code의 너비값이 0과 같거나 작습니다.");
        } else if (qrHeight <= 0) {
            throw new WidthHeightNegativeException("QR-code의 높이값이 0과 같거나 작습니다.");
        }
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode
                (info.getText(), BarcodeFormat.QR_CODE, qrWidth, qrHeight);
        Path path = FileSystems.getDefault().getPath(outputDirectory + "\\" + outputFileName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);    //PNG 파일에 저장.
    }
}
