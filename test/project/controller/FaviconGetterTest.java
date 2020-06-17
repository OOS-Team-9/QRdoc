package project.controller;

import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class FaviconGetterTest {

    @Test
    public void getBaseUrl() {
        FaviconGetter faviconGetter=new FaviconGetter();
        try {
            faviconGetter.getFaviconUrl(new URL("https://www.inflearn.com/questions/19302"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

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