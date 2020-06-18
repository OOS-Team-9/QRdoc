package project.controller.qr_inserter;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import project.model.MyDoc;
import project.model.Qr;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 빈 공간에 QR-code 삽입하는 클래스
 */
public class BlankQRinserter implements QRinserter {
    @Override
    public void insert(List<List<Qr>> qrCodeList, MyDoc myDoc, int pageOrder, Integer[] availableBlank)
            throws IOException {

        int QRcount = 0;// QRcount=pageOrder이전 페이지까지의 qr코드 수
        int k = 0, j = 0;
        int size = qrCodeList.get(pageOrder).size();
        String s;
        String text = "";
        BufferedImage tempImage;

        for (k = 0; k < pageOrder; k++) {
            for (int i = 0; i < qrCodeList.get(k).size(); i++) {
                QRcount++;
            }
        }
        for (j = 0; j < size; j++) {
            if (!qrCodeList.get(pageOrder).get(j).getCheckInserted())
                QRcount++;
            else
                break;
        }

        // 문서 열기
        PDPage page = myDoc.getPage(pageOrder);
        PDImageXObject pdImage;
        PDPageContentStream contents;
        try {
            contents = new PDPageContentStream(myDoc, page, PDPageContentStream.AppendMode.APPEND, true);
            contents.beginText();
            contents.setFont(PDType1Font.TIMES_ROMAN, 15);
            contents.newLineAtOffset(25 + 50*availableBlank[0], 90 + 50*availableBlank[1]);
            s = Integer.toString(QRcount + 1);
            if (QRcount + 1 < 10)
                text += "  ";
            text += s + ")";
            contents.showText(text);
            contents.endText();
            tempImage = qrCodeList.get(pageOrder).get(j).getBufferedImage();
            pdImage = LosslessFactory.createFromImage(myDoc, tempImage);// Creating PDImageXObject
            contents.drawImage(pdImage, 45 + 50*availableBlank[0], 20 + 50*availableBlank[1]);// 여기서 각주 위치 조정 가능
            System.out.println("Image inserted");
            contents.close();
            qrCodeList.get(pageOrder).get(j).setCheckInserted(false);
        } catch (

        IOException e) {
            throw new IOException("EndNoteQRinserter::insert ERROR: QR 코드를 삽입할 수 없습니다.");
        }
    }

    @Override
    public void insert(List<List<Qr>> qrCodeList, MyDoc myDoc, int pageOrder) throws IOException {
        // TODO Auto-generated method stub

    }

}
