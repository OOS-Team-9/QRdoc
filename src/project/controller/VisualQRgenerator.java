package QRgenerator;

import Exception.WidthHeightNegativeException;
import Information.Information;
import com.google.zxing.WriterException;

import java.io.IOException;

/*Visual Qr-code를 만들어서, 이미지 파일로 저장하는 클래스(추가 구현 사항이므로 아직 구현 안함)*/
public class VisualQRgenerator extends QRgenerator {

    /*생성자*/
    VisualQRgenerator(String outputDirectory) {
        super(outputDirectory);
    }

    VisualQRgenerator(DefaultQRgenerator other){
        super(other);
    }

    VisualQRgenerator(){
        super();
    }

    @Override
    void generate(Information info, int qrWidth, int qrHeight, String outputFileName)
            throws WriterException, IOException, WidthHeightNegativeException {

    }
}
