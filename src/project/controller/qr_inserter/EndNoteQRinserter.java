package project.controller.qr_inserter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import project.controller.FileStream;
import project.model.MyDoc;
import project.model.QRcode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 미주로 QR-code 삽입하는 클래스
 */
public class EndNoteQRinserter extends QRinserter{

    /**
     * PDF 파일에 QR-code를 삽입한 다음,
     * "[원래 문서 이름]~QR.pdf" 이름으로 저장하는 함수
     * @param qrCodeList PDF파일에 삽입할 QR-code 모음
     * @throws IOException
     */
    public void insert(ArrayList<ArrayList<QRcode>> qrCodeList, MyDoc myDoc) throws IOException {
        //변수 선언 및 정의
        int i = 1, j, k = 1, l = 0, m = 0;          // i=QRcode파일 카운터,j=i%4,k=i/4
        int total = 0, tempTotal = 0, pageNum = 0;  // total=파일 수, pageNum=추가될 페이지수
        String s, t;

        BufferedImage tempImage;
        String text = "";
        Image image = null;
        int pageOffset=0;

        //문서 열기
        MyDoc doc = null;
        doc = myDoc;

        //QR코드 전체 개수 구하기
        ArrayList<QRcode> qrCodeListOneLine=new ArrayList<>();
        for(ArrayList<QRcode> qrCodeListPerPage: qrCodeList){
            for(QRcode qr: qrCodeListPerPage){
                qrCodeListOneLine.add(qr);
            }
        }
        total=qrCodeListOneLine.size();
        System.out.println("전체 QR코드의 개수: "+total);

        pageNum = (total - 1) / 20 + 1;
        PDPage blankPage = new PDPage();

        PDImageXObject pdImage;
        PDPageContentStream contents;
        while (pageNum > 0) {
            if (pageNum == 1) {
                tempTotal = total;
            } else {
                tempTotal = (((total - 1) / 20 + 2) - pageNum) * 20;
            }
            blankPage = new PDPage();
            doc.addPage(blankPage);
            i = (((total - 1) / 20 + 1) - pageNum) * 20 + 1;
            m = 0;
            try {
                contents = new PDPageContentStream(doc, blankPage); // creating the PDPageContentStream object
                contents.beginText();
                while (i <= tempTotal) {
                    if (tempTotal - i + 1 > 4) {
                        j = 4;
                    } else {
                        j = tempTotal - i + 1;
                    }
                    while (l < j) {
                        s = Integer.toString(i);
                        if (i < 10)
                            text += "  ";
                        text += s + ")                                ";
                        System.out.println(text);
                        i++;
                        l++;
                    }
                    contents.setFont(PDType1Font.TIMES_ROMAN, 15);
                    if (m == 0)
                        contents.newLineAtOffset(25, ((((tempTotal - 1) % 20) / 4) + 1) * 150);
                    else
                        contents.newLineAtOffset(0, -150);
                    contents.showText(text);
                    text = "";
                    l = 0;
                    m++;
                }
                contents.endText();
                i = (((total - 1) / 20 + 1) - pageNum) * 20 + 1;
                m = 0;
                while (i <= tempTotal) {
                    if (tempTotal - i + 1 > 4) {
                        j = 4;
                    } else {
                        j = tempTotal - i + 1;
                    }
                    while (l < j) {
                        tempImage = qrCodeListOneLine.get(l).getImage();
                        pdImage =  LosslessFactory.createFromImage(doc,tempImage);// Creating PDImageXObject object
                        contents.drawImage(pdImage, 140 * (l) + 48, ((((tempTotal - 1) % 20) / 4) + 1 - m) * 150 - 90);
                        i++;
                        l++;
                    }
                    // if (m == 0)
                    l = 0;
                    m++;
                }
                System.out.println("Image inserted");
                contents.close();
            } catch (IOException e) {
                throw new IOException("EndNoteQRinserter::insert ERROR: QR 코드를 삽입할 수 없습니다.");
            }
            pageNum--;
        }

    }
}

