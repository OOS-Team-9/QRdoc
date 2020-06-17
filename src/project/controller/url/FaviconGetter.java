package project.controller.url;

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

/**
 * url에서 favicon의 buffer image을 추출하는 클래스
 */
public class FaviconGetter {

    /**
     * url에서 favion의 buffer image를 추출하는 메소드
     * @param url   url
     * @return      favicon의 buffer image
     * @throws IOException  favicon의 buffer image를 읽을 떄 발생할 수 있는 예외
     */
    public static BufferedImage getFavicon(URL url) throws IOException {
        URL faviconURL=new URL(url.getProtocol() + "://" + url.getHost()+"/favicon.ico");
        List<BufferedImage> images = ICODecoder.read(faviconURL.openStream());
        BufferedImage image = images != null ? (images.size() > 0 ? images.get(0) : null) : null;
        return image;
    }
}
