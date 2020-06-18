package project.controller.qr_buffimage_generator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import project.model.information.Information;
import java.awt.image.BufferedImage;

/**
 * information을 "일반 QR 코드 buffer image"로 바꿔주는 클래스
 */
public class DefaultQrBuffImageGenerator implements QrBuffImageGenerator {

    /**
     * information의 buffer image를 생성하는 메소드
     * @param info          information
     * @param qrWidth       buffer image의 폭
     * @param qrHeight      buffer image의 높이
     * @return              buffer image
     * @throws WriterException bit matrix 생성 중 발생할 수 있는 에러
     */
    private BufferedImage generateQrBuffImage(final Information info,
                                              final int qrWidth,
                                              final int qrHeight) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(info.getText(),
                BarcodeFormat.QR_CODE,
                qrWidth,
                qrHeight);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    /**
     * information을 "일반 qr코드 buffer image"로 바꿔서 리턴하는 메소드
     * @param info     information
     * @param qrWidth  QR코드 가로 길이
     * @param qrHeight QR코드 세로 길이
     * @return         buffer image
     * @throws WriterException  bit matrix 생성 중 발생할 수 있는 에러
     */
    public BufferedImage generate(Information info,
                                  int qrWidth,
                                  int qrHeight) throws WriterException {
        return generateQrBuffImage(info,qrWidth,qrHeight);
    }
}
