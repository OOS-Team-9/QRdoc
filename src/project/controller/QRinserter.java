package project.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class QRinserter {

    public static boolean addPage() {

        boolean result = true;

        File file = new File("tempPDF.pdf");
        int i = 1, j, k = 1, l = 0, m = 0; // i=QRcode파일 카운터,j=i%4,k=i/4
        int total = 0, tempTotal = 0, pageNum = 0;// total=파일 수, pageNum=추가될 페이지수
        String s, t;
        String text = "";
        Image image = null;

        PDDocument doc = null;
        try {
            doc = PDDocument.load(file);
        } catch (IOException e) {
            result = false;
            System.out.println("DocumentCreatioin-createPDF ERROR : " + e.getMessage());
        }

        total = countQRcodeImage();
        System.out.println(total);

        pageNum = (total - 1) / 20 + 1;
        // PDPage page = doc.getPage(0);
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
                        s = Integer.toString(i);
                        t = s + ".jpg";
                        pdImage = PDImageXObject.createFromFile(t, doc);// Creating PDImageXObject object
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
                result = false;
                System.out.println("DocumentCreatioin-createPDF ERROR : " + e.getMessage());
            }
            pageNum--;
        }
        try {
            doc.save("resultPDF.pdf");
        } catch (IOException e) {
            result = false;
            System.out.println("DocumentCreatioin-createPDF ERROR : " + e.getMessage());
        }

        try {
            doc.close();
        } catch (IOException e) {
            result = false;
            System.out.println("DocumentCreatioin-createPDF ERROR : " + e.getMessage());
        }

        return result;

    }

    public static int countQRcodeImage() {

        boolean imageCheck = true;
        int i = 0;
        String s;
        File imagefile;
        Image image = null;
        while (imageCheck) {
            i++;
            s = Integer.toString(i);
            s += ".jpg";
            imagefile = new File(s);
            try {
                image = ImageIO.read(imagefile);
            } catch (IOException e) {
                imageCheck = false;
            }
        }
        return i - 1;
    }
}

