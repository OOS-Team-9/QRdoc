package project.controller.qrcode_writer;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import project.model.QRcode;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRcodeWriter extends FileSystemHandler {
    /**
     * QR code를 이미지 파일에 저장하는 함수
     * @param qr                저장할 QR code 객체
     * @throws IOException
     */
    public void writeQRcode(QRcode qr) throws IOException {
        if (!isDirectoryExisted(qr.getDirectory())) {
            if (!makeDirectory(qr.getDirectory())) {
                throw new IOException("파일 경로 \""+qr.getDirectory()+" \"를 생성할 수 없습니다.");
            }
        }
        if (isFileExisted(qr.getDirectory(), qr.getFileName())) {
            if (!deleteFile(qr.getDirectory(), qr.getFileName())) {
                throw new IOException("파일 \""+qr.getDirectory()+qr.getFileName()+" \"을 삭제할 수 없습니다.");
            }
        }
        Path path = FileSystems.getDefault().getPath(qr.getDirectory() + qr.getFileName());
        MatrixToImageWriter.writeToPath(qr.getBitmatrix(), "PNG", path);    //PNG 파일로 저장.
    }

}
