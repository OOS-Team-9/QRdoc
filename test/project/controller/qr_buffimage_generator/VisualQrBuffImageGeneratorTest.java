package project.controller.qr_buffimage_generator;

import com.google.zxing.WriterException;
import org.junit.Test;
import project.model.information.Information;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VisualQrBuffImageGeneratorTest {

    @Test
    public void combineBuffImage() {

        try {
            Information info=new Information("https://ko-kr.classting.com/schools/1eee6580-0020-11e2-9713-b199e434548f/notices",0,0);
            VisualQrBuffImageGenerator visualQrBuffImageGenerator=new VisualQrBuffImageGenerator();
            BufferedImage visualQRBuffImage= null;
            visualQRBuffImage = visualQrBuffImageGenerator.generate(info,100,100);
            System.out.println("visualQRBuffImage.getWidth() = " + visualQRBuffImage.getWidth());
            File outputfile = new File("combined.png");
            ImageIO.write(visualQRBuffImage, "png", outputfile);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void generate() {
    }
}