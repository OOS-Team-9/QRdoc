package QRgenerator;

import Information.Information;
import Exception.WidthHeightNegativeException;
import com.google.zxing.WriterException;

import java.io.IOException;

/*QR-code를 만들어서 이미지 파일로 저장하는 '추상' 클래스*/
public abstract class QRgenerator {
    String outputDirectory;

    /*생성자*/
    QRgenerator(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    QRgenerator(QRgenerator other) {
        this(other.outputDirectory);
    }

    QRgenerator() {
        this(".\\");
    }

    /*중간에 이미지 파일이 저장될 디렉토리를 바꿔야 하는 경우, 이 함수를 호출한다.*/
    void setOutputDirectory(String outputDirectory){
        this.outputDirectory=outputDirectory;
    }

    /*QR-code를 만들어서, 이미지 파일로 저장하는 함수*/
    abstract void generate(Information info, int qrWidth, int qrHeight, String outputFileName)
            throws WriterException, IOException, WidthHeightNegativeException;
}
