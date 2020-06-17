package project.controller;

import net.sf.image4j.codec.ico.ICODecoder;
import project.model.information.Link;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FaviconGetter {

    public FaviconGetter() {
    }

    public static URL getFaviconUrl(final URL url) throws MalformedURLException {
        return new URL(url.getProtocol() + "://" + url.getHost()+"/favicon.ico");
    }

    public static BufferedImage getFavicon(URL url) throws IOException {
        URL faviconURL=getFaviconUrl(url);
        List<BufferedImage> images = ICODecoder.read(faviconURL.openStream());
        BufferedImage image = images != null ? (images.size() > 0 ? images.get(0) : null) : null;
        //File outputfile = new File("saved.png");
        //ImageIO.write(image, "png", outputfile);
        return image;
    }
}
