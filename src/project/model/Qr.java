package project.model;

import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;

/**
 * QR-code 정보를 담고 있는 클래스
 */
public class Qr {

    private int pageOrder;                  //페이지 순서
    private int infoOrderInOnePage;         //페이지 안에서 정보 순서
    private BufferedImage bufferedImage;    //qr의 buffer image

    /**
     * 생성자
     * @param pageOrder             페이지 순서
     * @param infoOrderInOnePage    페이지 안에서 정보 순서
     * @param bufferedImage         qr의 buffer image
     */
    public Qr(int pageOrder, int infoOrderInOnePage, BufferedImage bufferedImage) {
        this.pageOrder = pageOrder;
        this.infoOrderInOnePage = infoOrderInOnePage;
        this.bufferedImage = bufferedImage;
    }

    //getter
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public int getPageOrder() {
        return pageOrder;
    }

    public int getInfoOrderInOnePage() {
        return infoOrderInOnePage;
    }
}
