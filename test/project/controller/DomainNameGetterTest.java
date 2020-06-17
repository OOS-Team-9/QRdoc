package project.controller;

import org.junit.Test;
import project.controller.url.DomainNameGetter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DomainNameGetterTest {

    @Test
    public void getDomainName() {
        List<String> siteList=new ArrayList<>();
        siteList.add("http://www.naver.com");
        siteList.add("http://www.google.com");
        siteList.add("https://www.google.com/search?q=java+%EA%B0%9D%EC%B2%B4+call+by+reference&oq=java+%EA%B0%9D%EC%B2%B4+call+by+reference&aqs=chrome..69i57j69i64l2&sourceid=chrome&ie=UTF-8");
        siteList.add("http://regexr.com/3au3g");
        siteList.add("https://zkim0115.tistory.com/869");
        siteList.add("https://ko.wikipedia.org/wiki/.org");
        try {
            for(int i=0;i<siteList.size();i++){
                URL testUrl=new URL(siteList.get(i));
                String domainName= DomainNameGetter.getDomainName(testUrl);
                System.out.println("domainName = " + domainName);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}