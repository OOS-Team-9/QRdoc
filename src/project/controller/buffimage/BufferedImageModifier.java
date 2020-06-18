package project.controller.buffimage;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * buffer image를 다루는 클래스
 */
public class BufferedImageModifier {

    /**
     * buffer image 크기 조절
     * @param original  기존 buffer image
     * @param newWidth  new width
     * @param newHeight new height
     * @return          크기 조절된 buffer image
     */
    static public BufferedImage resize(BufferedImage original, final int newWidth, final int newHeight) {
        Image resizedImage = original.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedBuffImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedBuffImage.createGraphics();
        g.drawImage(resizedImage, 0, 0, null);
        g.dispose();
        return resizedBuffImage;
    }

    /**
     * buffer image에 가장자리 선 추가하는 메소드
     * @param bufferedImage 기존 buffer image
     * @param borderSize    가장자리 선의 두께
     * @param color         가장자리 선의 색깔
     */
    static public void applyBorderLine(BufferedImage bufferedImage, final int borderSize, final Color color) {
        Graphics2D g = bufferedImage.createGraphics();
        g.setPaint(color);
        //세로 선
        g.fillRect(0, 0, bufferedImage.getWidth(), borderSize);
        g.fillRect(0, bufferedImage.getHeight() - borderSize, bufferedImage.getWidth(), borderSize);
        //가로 선
        g.fillRect(0, 0, borderSize, bufferedImage.getHeight());
        g.fillRect(bufferedImage.getWidth() - borderSize, 0, borderSize, bufferedImage.getHeight());
    }
}
