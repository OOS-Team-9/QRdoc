package project.controller.url;

import java.net.URL;

/**
 * url에서 도메인 이름을 추출하는 클래스
 */
public class DomainNameGetter {
    /**
     * generic top-level domain, gTLD
     */
    public static enum genericTopLevelDomain {
        AREO,
        ARPA,
        ASIA,
        BIZ,
        CAT,
        COM,
        COOP,
        EDU,
        GOV,
        INFO,
        INT,
        JOBS,
        MIL,
        MOBI,
        MUSEUM,
        NAME,
        NET,
        ORG,
        PRO,
        TEL,
        TRAVEL,
        XXX,
        POST,
        GEO,
        CYM
    }
    //generic top-level domain, gTLD 리스트
    private static genericTopLevelDomain[] genericTopLevelDomainList=genericTopLevelDomain.values();

    /**
     * url에서 도메인 이름을 추출하는 메소드
     * @param url url
     * @return    도메인 이름
     */
    public static String getDomainName(final URL url) {
        String host = url.getHost();
        String tempHost = host;
        String domainName = host;
        int dotPos = -1;

        outer:
        while (true) {
            dotPos = tempHost.lastIndexOf(".");
            if (dotPos == -1) {
                domainName = host.substring(0, host.lastIndexOf("."));
                return domainName;
            } else {
                domainName = tempHost.substring(dotPos + 1);
                tempHost = tempHost.substring(0, dotPos);
                if (domainName.length() != 2) {
                    //추출한 도메인 이름이 generic top-level domain(gTLD)인지 확인
                    for(int i=0;i<genericTopLevelDomainList.length;i++){
                        if(domainName.compareToIgnoreCase(genericTopLevelDomainList[i].name())==0){
                            continue outer; //도메인 이름 다시 추출
                        }
                    }
                    return domainName;
                }
            }
        }
    }
}
