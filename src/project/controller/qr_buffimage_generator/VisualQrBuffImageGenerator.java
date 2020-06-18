package project.controller.qr_buffimage_generator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import project.controller.buffimage.BufferedImageModifier;
import project.controller.url.DomainNameGetter;
import project.controller.url.FaviconGetter;
import project.model.information.Information;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * information을 "Visual QR 코드 buffer image"로 바꿔주는 클래스
 */
public class VisualQrBuffImageGenerator implements QrBuffImageGenerator {


    /**
     * information을 "buffer image"로 바꿔서 리턴하는 메소드
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
     * qr buffer image와 icon buffer image를 합치는 메소드
     * @param qr    qr buffer image
     * @param icon  icon buffer image
     * @return      qr + icon buffer image
     */
    public BufferedImage combineBuffImage(BufferedImage qr, BufferedImage icon) {
        BufferedImage combined = new BufferedImage(qr.getWidth(), qr.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) combined.getGraphics();
        //배경색 = white
        g.setPaint(Color.white);
        g.fillRect(0, 0, combined.getWidth(), combined.getHeight());
        //qr 삽입
        g.drawImage(qr, 0, 0, null);
        //icon 주변 흰색으로 설정
        g.setPaint(Color.white);
        g.fillRect(0, 0, icon.getWidth(), icon.getHeight());
        //icon 삽입
        g.drawImage(icon, 0, 0, null);
        return combined;
    }

    /**
     * text를 buffer image에 쓰는 메소드
     * @param bufferedImage    buffer image
     * @param text             text
     * @param x                text 삽입할 x축 위치
     * @param y                text 삽입할 y축 위치
     * @param fontSize         text font size
     */
    public void writeOnCombined(BufferedImage bufferedImage, String text, int x, int y, int fontSize ) {
        Graphics2D g = bufferedImage.createGraphics();
        g.setFont(new Font("Serif", Font.PLAIN, fontSize));
        FontMetrics fm = g.getFontMetrics();
        g.setPaint(Color.white);
        g.fillRect(x, 1, fm.stringWidth(text), fm.getHeight()+3);
        g.setPaint(Color.red);
        g.drawString(text, x, y + fm.getHeight());
        g.dispose();
    }

    /**
     * information을 "Visual qr코드 buffer image"로 바꿔서 리턴하는 메소드
     * @param info     information
     * @param qrWidth  QR코드 가로 길이
     * @param qrHeight QR코드 세로 길이
     * @return         buffer image
     * @throws WriterException  bit matrix 생성 중 발생할 수 있는 에러
     */
    public BufferedImage generate(
            Information info,
            int qrWidth,
            int qrHeight) throws WriterException {
        try {
            //url 확인
            String urlString = info.getText();
            urlString = urlString.startsWith("http") ? urlString : "http://" + urlString;
            URL url = new URL(urlString);
            String domainName = DomainNameGetter.getDomainName(url);
            //buffer image 생성
            BufferedImage qrBuffImage = generateQrBuffImage(info, qrHeight, qrWidth);
            BufferedImage faviconBuffImage = FaviconGetter.getFavicon(url);
            //icon size 20*20 고정
            faviconBuffImage=BufferedImageModifier.resize(faviconBuffImage,20,20);
            //qr과 icon 합치기
            BufferedImage combinedBuffImage = combineBuffImage(qrBuffImage, faviconBuffImage);
            //가장자리 선 넣기
            BufferedImageModifier.applyBorderLine(combinedBuffImage, 1, Color.black);
            //domain name 글자 넣기
            writeOnCombined(combinedBuffImage,  domainName, faviconBuffImage.getWidth() + 3, 0, 10);
            return combinedBuffImage;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
