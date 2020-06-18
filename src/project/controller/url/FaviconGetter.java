package project.controller.url;

import net.sf.image4j.codec.ico.ICODecoder;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * url에서 favicon의 buffer image을 추출하는 클래스
 */
public class FaviconGetter {

    /**
     * url에서 favion의 buffer image를 추출하는 메소드
     *
     * @param url url
     * @return favicon의 buffer image
     * @throws IOException favicon의 buffer image를 읽을 떄 발생할 수 있는 예외
     */
    public static BufferedImage getFavicon(URL url) throws IOException {
        URL faviconURL=null;
        try {
            faviconURL = new URL("https://" + url.getHost() + "/favicon.ico");
        } catch (IOException e) {
            faviconURL = new URL("http://" + url.getHost() + "/favicon.ico");
        }
        List<BufferedImage> images = ICODecoder.read(faviconURL.openStream());
        BufferedImage image = images != null ? (images.size() > 0 ? images.get(0) : null) : null;
        return image;
    }
}
