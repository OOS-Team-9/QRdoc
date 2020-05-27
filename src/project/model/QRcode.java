package project.model;

import com.google.zxing.common.BitMatrix;

/**
 * QR-code 정보를 담고 있는 클래스
 */
public class QRcode {

    private int pageOrder;              //페이지 순서
    private int infoOrderInOnePage;     //페이지 안에서 정보 순서
    private BitMatrix bitmatrix;        //bit matrix
    private MyPath path;                //QR-code 이미지 파일 저장 경로

    public QRcode(int pageOrder,
                  int infoOrderInOnePage,
                  BitMatrix bitmatrix,
                  MyPath path) {
        this.pageOrder = pageOrder;
        this.infoOrderInOnePage = infoOrderInOnePage;
        this.bitmatrix = bitmatrix;
        this.path = path;
    }

    public QRcode() {
    }

    public int getPageOrder() {
        return pageOrder;
    }

    public void setPageOrder(int pageOrder) {
        this.pageOrder = pageOrder;
    }

    public int getInfoOrderInOnePage() {
        return infoOrderInOnePage;
    }

    public void setInfoOrderInOnePage(int infoOrderInOnePage) {
        this.infoOrderInOnePage = infoOrderInOnePage;
    }

    public int getWidth() {
        return bitmatrix.getWidth();
    }

    public int getHeight() {
        return bitmatrix.getHeight();
    }

    public BitMatrix getBitmatrix() {
        return bitmatrix;
    }

    public void setBitmatrix(BitMatrix bitmatrix) {
        this.bitmatrix = bitmatrix;
    }

    public MyPath getPath() {
        return path;
    }

    public void setPath(MyPath path) {
        this.path = path;
    }

    public void print() {
        System.out.println("\n------[ " + pageOrder + " - " + infoOrderInOnePage + " ]번째 QRcode 객체------");
        System.out.println("path: " + path.getPathString());
        System.out.println("directory: " + path.getDirectory());
        System.out.println("fileName: " + path.getFileName());
        System.out.println("fileType: " + path.getFileType());
        System.out.println("width: " + getWidth());
        System.out.println("height: " + getHeight());
    }
}
