package project.controller.qr_generator;

import com.google.zxing.WriterException;
import project.model.exception.WidthHeightNegativeException;
import project.model.information.Information;
import java.io.IOException;

/*Visual Qr-code를 만들어서, 이미지 파일로 저장하는 클래스(추가 구현 사항이므로 아직 구현 안함)*/

/**
 * 문자열을 Visual QR-code로 변환하여 이를 이미지 파일로 저장하는 클래스
 */
public class VisualQRgenerator extends QRgenerator {

    /**
     * 생성자
     * @param outputDirectory QR코드 저장 경로
     */
    VisualQRgenerator(String outputDirectory) {
        super(outputDirectory);
    }

    VisualQRgenerator(DefaultQRgenerator other){
        super(other);
    }

    VisualQRgenerator(){
        super();
    }

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
    void generate(Information info, int qrWidth, int qrHeight, String outputFileName)
            throws WriterException, IOException, WidthHeightNegativeException {

    }
}
