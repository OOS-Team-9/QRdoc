package project.controller.bitmatrix_generator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import project.model.Qr;
import project.model.information.Information;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/*Visual Qr-code를 만들어서, 이미지 파일로 저장하는 클래스(추가 구현 사항이므로 아직 구현 안함)*/

/**
 * 문자열을 Visual QR-code로 변환하여 이를 이미지 파일로 저장하는 클래스
 */
public class VisualQrGenerator implements QrGenerator {


    public static BufferedImage generateQrBuffImage(Information info) throws WriterException {
        HashMap hints = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(info.getText(),
                BarcodeFormat.QR_CODE,
                100,
                100,
                hints);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public static BufferedImage combineBuffImage(BufferedImage qr, BufferedImage background) {
        int deltaHeight = qr.getHeight() - background.getHeight();
        int deltaWidth = qr.getWidth() - background.getWidth();

        BufferedImage combined = new BufferedImage(qr.getHeight(), qr.getWidth(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) combined.getGraphics();
        g.drawImage(qr, 0, 0, null);
        //투명도, overlay 설정
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        //background를 qr 가운데에 배치
        g.drawImage(background, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);
        return combined;
    }

    /**
     * 문자열 정보를 입력 받아, QR코드 이미지 파일을 만들고
     * 이것을 미리 지정한 경로에 저장하는 함수
     *
     * @param info     변환할 문자열 정보
     * @param qrWidth  QR코드 가로 길이
     * @param qrHeight QR코드 세로 길이
     * @throws WriterException
     * @throws IOException
     */
    public Qr generate(BufferedImage background,
            Information info,
            int pageOrder,
            int infoOrderPerPage,
            int qrWidth,
            int qrHeight) throws WriterException {

        System.out.println("\n-----[ " + info.getText() + " ] bitmatrix 생성-----");
        assert (qrWidth > 0 && qrHeight > 0);
        BufferedImage qrBuffImage = generateQrBuffImage(info);

        return new Qr(pageOrder, infoOrderPerPage, bitMatrix, bufferedImage);
    }
}
