package project.controller.qr_inserter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class QRinserter {
    protected String qrCodeDirectory;

    abstract boolean insert();

    int countQRImage() {
        String fullPath;
        Image qrImage;
        int order;

        for (order = 0; ; order++) {
            fullPath = qrCodeDirectory + order + ".png";
            try {
                qrImage = ImageIO.read(new File(fullPath));
            } catch (IOException e) {
                break;
            }
        }
        return order + 1;
    }
}

