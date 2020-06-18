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
    private boolean checkInserted = true;

    public Qr(int pageOrder, int infoOrderInOnePage, BufferedImage bufferedImage) {
        this.pageOrder = pageOrder;
        this.infoOrderInOnePage = infoOrderInOnePage;
        this.bufferedImage = bufferedImage;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public int getPageOrder() {
        return pageOrder;
    }

    public int getInfoOrderInOnePage() {
        return infoOrderInOnePage;
    }

    public boolean getCheckInserted() {
        return checkInserted;
    }

    public void setCheckInserted(boolean checkInserted) {
        this.checkInserted = checkInserted;
    }
}
