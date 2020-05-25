package project.controller.qr_generator;

import com.google.zxing.WriterException;
import project.model.exception.WidthHeightNegativeException;
import project.model.information.Information;
import java.io.IOException;


/**
 * 모든 QRgenerator의 공통 분모
 */
public abstract class QRgenerator {
    String outputDirectory;

    /**
     * 생성자
     * @param outputDirectory QR코드 저장 경로
     */
    QRgenerator(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    QRgenerator(QRgenerator other) {
        this(other.outputDirectory);
    }

    QRgenerator() {
        this(".\\");
    }

    /**
     * QR코드 저장 경로를 바꾸는 함수
     * @param outputDirectory QR코드 저장 경로
     */
    public void resetOutputDirectory(String outputDirectory){
        this.outputDirectory=outputDirectory;
    }

    /*QR-code를 만들어서, 이미지 파일로 저장하는 함수*/

    /**
     * 문자열 정보를 입력 받아, QR코드 이미지 파일을 만들고
     * 이것을 미리 지정한 경로에 저장하는 함수
     * @param info              변환할 문자열 정보
     * @param qrWidth           QR코드 가로 길이
     * @param qrHeight          QR코드 세로 길이
     * @param outputFileName    이미지 파일 이름
     * @throws WriterException
     * @throws IOException
     * @throws WidthHeightNegativeException QR코드 규격을 잘못 입력한 경우
     */
    abstract void generate(Information info, int qrWidth, int qrHeight, String outputFileName)
            throws WriterException, IOException, WidthHeightNegativeException;
}
