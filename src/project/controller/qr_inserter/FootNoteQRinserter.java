package project.controller.qr_inserter;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import project.model.MyDoc;
import project.model.Page;
import project.model.QRcode;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 각주로 QR-code 삽입하는 클래스
 */
public class FootNoteQRinserter extends QRinserter {
    @Override
    public void insert(ArrayList<ArrayList<QRcode>> qrCodeList, MyDoc myDoc, int pageOrder) throws IOException {

        int QRcount = 0, size = 4;// QRcount=pageOrder이전 페이지까지의 qr코드 수,각주의 size는 최대 4
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
        if (qrCodeList.get(pageOrder).size() < 4) // mainviewcontroller에서 처리하니 필요없음 test용
            size = qrCodeList.get(pageOrder).size();//
        try {
            contents = new PDPageContentStream(myDoc, page, PDPageContentStream.AppendMode.APPEND, true);
            contents.beginText();
            for (int i = 0; i < size; i++) {
                s = Integer.toString(i + QRcount + 1);
                if (i + QRcount + 1 < 10)
                    text += "  ";
                text += s + ")                                ";
                System.out.println(text);
            }
            contents.setFont(PDType1Font.TIMES_ROMAN, 15);
            contents.newLineAtOffset(25, 115);
            contents.showText(text);
            text = "";
            contents.endText();

            for (int i = 0; i < size; i++) {
                tempImage = qrCodeList.get(pageOrder).get(i).getImage();
                qrCodeList.get(pageOrder).get(i).setCheckInserted(false);// 나중의 미주, 여백삽입시 false체크
                pdImage = LosslessFactory.createFromImage(myDoc, tempImage);// Creating PDImageXObject
                contents.drawImage(pdImage, 140 * i + 48, 25);// 여기서 각주 위치 조정 가능
            }

            System.out.println("Image inserted");
            contents.close();
        } catch (

        IOException e) {
            throw new IOException("EndNoteQRinserter::insert ERROR: QR 코드를 삽입할 수 없습니다.");
        }
    }

    @Override
    public void insert(ArrayList<ArrayList<QRcode>> qrCodeList, MyDoc myDoc, int pageOrder, Integer[] availableBlank)
            throws IOException {
        // TODO Auto-generated method stub

    }

}
