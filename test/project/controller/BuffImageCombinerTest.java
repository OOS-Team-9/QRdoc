package project.controller;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

public class BuffImageCombinerTest {

    @Test
    public void combine() {
        try {
            FaviconGetter faviconGetter = new FaviconGetter();
            BufferedImage favicon = faviconGetter.getFavicon(new URL("https://www.inflearn.com/questions/19302"));
            BufferedImage qr = null;
            try {
                qr = ImageIO.read(new File("C:\\Users\\joe10\\Desktop\\qr.jpg"));
            } catch (IOException e) {
            }
            BuffImageCombiner.combine(qr,favicon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}