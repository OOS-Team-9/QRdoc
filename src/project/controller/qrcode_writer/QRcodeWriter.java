package project.controller.qrcode_writer;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import project.model.QRcode;

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
    public void writeQRcode(QRcode target) throws IOException {
        System.out.println("\n---[ "+target.getPageOrder()+" - "+target.getInfoOrderInOnePage()+"] 번째 QR코드" +
                " [ "+target.getPath().getPathString()+" ]에 저장---");
        //디렉토리 존재하지 않는 경우
        if (!target.getPath().directoryExists()) {
            //디렉토리 생성할 수 없는 경우
            if (!target.getPath().makeDirectory()) {
                throw new IOException("QRcodeWriter::writeQRcode ERROR: [ " +
                        target.getPath().getPathString() + " ] 디렉토리를 만들 수 없습니다.");
            }
        }
        //같은 이름의 파일이 이미 존재하는 경우
        if (target.getPath().fileExists()) {
            //파일을 지울 수 없는 경우
            if (!target.getPath().deleteFile()) {
                throw new IOException("QRcodeWriter::writeQRcode ERROR: [ " +
                        target.getPath().getPathString() + " ]  QR-code 이미지를 삭제할 수 없습니다.");
            }
        }
        //QR-code 저장
        Path targetPath = FileSystems.getDefault().getPath(target.getPath().getPathString());
        MatrixToImageWriter.writeToPath(target.getBitmatrix(), "PNG", targetPath);    //PNG 파일로 저장.
    }

}
