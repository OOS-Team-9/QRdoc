package project.controller.qr_inserter;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import project.controller.extractor.Extractor;
import project.model.MyDoc;
import project.model.Page;
import project.model.QRcode;
import project.model.information.Link;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 빈 공간에 QR-code 삽입하는 클래스
 */
public class BlankQRinserter extends QRinserter {
    @Override
    public void insert(ArrayList<ArrayList<QRcode>> qrCodeList, MyDoc myDoc, int pageOrder, Page pPage)
            throws IOException {

        int QRcount = 0;// QRcount=pageOrder이전 페이지까지의 qr코드 수
        int size = qrCodeList.get(pageOrder).size();
        String s;
        String text = "";
        BufferedImage tempImage;

        for (int k = 0; k < pageOrder; k++) {
            for (int j = 0; j < qrCodeList.get(k).size(); j++) {
                QRcount++;
            }
        }
        // 문서 열기
        PDPage page = myDoc.getPage(pageOrder);
        PDImageXObject pdImage;
        PDPageContentStream contents;
        try {
            contents = new PDPageContentStream(myDoc, page, PDPageContentStream.AppendMode.APPEND, true);
            contents.beginText();
            contents.setFont(PDType1Font.TIMES_ROMAN, 15);
            int max = pPage.getAvailableBlankForQRcode().size() - 1;
            contents.newLineAtOffset(pPage.getAvailableBlankForQRcode().get(max)[0] * 50 - 25,
                    pPage.getAvailableBlankForQRcode().get(max)[1] * 50 + 50);
            for (int i = 0; i < size; i++) {
                s = Integer.toString(i + QRcount + 1);
                if (i + QRcount + 1 < 10)
                    text += "  ";
                text += s + ")";
                contents.newLineAtOffset(
                        (pPage.getAvailableBlankForQRcode().get(max - i - 1)[0]
                                - pPage.getAvailableBlankForQRcode().get(max - i)[0]) * 50,
                        (pPage.getAvailableBlankForQRcode().get(max - i - 1)[1]
                                - pPage.getAvailableBlankForQRcode().get(max - i)[1]) * 50);
                contents.showText(text);

                text = "";
                // System.out.println(text);
            }

            System.out.println("lalala" + max);
            for (int i = 0; i < max; i++) {
                System.out.println("x:" + pPage.getAvailableBlankForQRcode().get(i)[0]);
                System.out.println("y:" + pPage.getAvailableBlankForQRcode().get(i)[1]);
            }
            System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqq");

            contents.endText();

            for (int i = 0; i < size; i++) {
                tempImage = qrCodeList.get(pageOrder).get(i).getImage();
                pdImage = LosslessFactory.createFromImage(myDoc, tempImage);// Creating PDImageXObject
                contents.drawImage(pdImage, pPage.getAvailableBlankForQRcode().get(max - i)[0] * 50,
                        pPage.getAvailableBlankForQRcode().get(max - i)[1] * 50 - 40);// 여기서 각주 위치 조정 가능
            }

            System.out.println("Image inserted");
            contents.close();
        } catch (

        IOException e) {
            throw new IOException("EndNoteQRinserter::insert ERROR: QR 코드를 삽입할 수 없습니다.");
        }
    }

    @Override
    public void insert(ArrayList<ArrayList<QRcode>> qrCodeList, MyDoc myDoc, int pageOrder) throws IOException {
        // TODO Auto-generated method stub

    }

}
