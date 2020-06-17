package project.model;

import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;

/**
 * QR-code 정보를 담고 있는 클래스
 */
public class Qr {

    private int pageOrder;              //페이지 순서
    private int infoOrderInOnePage;     //페이지 안에서 정보 순서
    private BitMatrix bitmatrix;        //bit matrix
    private BufferedImage bufferedImage;



    public Qr(int pageOrder, int infoOrderInOnePage, BitMatrix bitmatrix, BufferedImage bufferedImage) {
        this.pageOrder = pageOrder;
        this.infoOrderInOnePage = infoOrderInOnePage;
        this.bitmatrix = bitmatrix;
        this.bufferedImage = bufferedImage;
    }

    public Qr() {
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

    public BitMatrix getBitmatrix() {
        return bitmatrix;
    }

    public void print() {
        System.out.println("\n------[ " + pageOrder + " - " + infoOrderInOnePage + " ]번째 QRcode 객체------");
        System.out.println("pageOrder = " + pageOrder);
        System.out.println("infoOrderInOnePage = " + infoOrderInOnePage);
    }
}
