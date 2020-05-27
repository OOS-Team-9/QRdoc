package project.controller.qrcode_writer;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import project.model.QRcode;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * QR code를 파일 시스템에 기록하는 클래스
 */
public class QRcodeWriter {
    /**
     * QR code를 파일 시스템에 기록하는 함수
     * @param target       파일 시스템에 기록할 QR code 객체
     * @throws IOException
     */
    public BufferedImage writeQRcode(QRcode target) throws IOException {
        System.out.println("\n---[ "+target.getPageOrder()+" - "+target.getInfoOrderInOnePage()+"] 번째 QR코드");
        //디렉토리 존재하지 않는 경우
        //QR-code 저장
        BufferedImage temp = MatrixToImageWriter.toBufferedImage(target.getBitmatrix());
        target.setImage(temp);
        return temp;
        //MatrixToImageWriter.writeToPath(target.getBitmatrix(), "PNG", targetPath);    //PNG 파일로 저장.
    }

}
