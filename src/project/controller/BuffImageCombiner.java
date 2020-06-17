package project.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.QRCode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class BuffImageCombiner {
    static final float TRANSPARENCY = 0.25f;
    static final float OVERLAY_RATIO = 1f;
    static final int WIDTH = 250;
    static final int HEIGHT = 250;

    public static BufferedImage combine(BufferedImage qr, BufferedImage favicon) throws WriterException, IOException {
        int deltaHeight = qr.getHeight() - favicon.getHeight();
        int deltaWidth  = qr.getWidth() - favicon.getWidth();

        BufferedImage combined = new BufferedImage(qr.getHeight(), qr.getWidth(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D)combined.getGraphics();
        g.drawImage(qr, 0, 0, null);
        //투명도, overlay 설정
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        //favicon을 qr 가운데에 배치
        g.drawImage(favicon, (int)Math.round(deltaWidth/2), (int)Math.round(deltaHeight/2), null);
        return combined;
    }
}
