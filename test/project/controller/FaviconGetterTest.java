package project.controller;

import org.junit.Test;
import project.controller.url.FaviconGetter;

import java.io.IOException;
import java.net.URL;

public class FaviconGetterTest {

    @Test
    public void getFavicon() {
        FaviconGetter faviconGetter=new FaviconGetter();
        try {
            faviconGetter.getFavicon(new URL("https://www.inflearn.com/questions/19302"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}