package project.controller.qr_inserter;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import project.model.MyDoc;
import project.model.information.Link;

import java.io.IOException;
import java.util.ArrayList;

public class IndicesInserter {
    /*
     * 링크 오른쪽위에 지수넣는 클래스입니다
     */

    public static boolean addIndices(MyDoc myDoc, ArrayList<ArrayList<Link>> infoList) {

        boolean result = true;
        String text = "";
        for (int i = 0; i < infoList.size(); i++) {
            for (int j = 0; j < infoList.get(i).size(); j++) {
                // System.out.println("text" + infoList.get(i).get(j).getText());
                // System.out.println("xPos: " + infoList.get(i).get(j).getxPos());
                // System.out.println("yPos: " + infoList.get(i).get(j).getyPos());
                // System.out.println("fontSize: " + infoList.get(i).get(j).getFontSize());
                PDPage page = myDoc.getPage(i);
                PDPageContentStream contentStream;
                try {
                    contentStream = new PDPageContentStream(myDoc, page, PDPageContentStream.AppendMode.APPEND, true);
                    // Begin the Content stream
                    contentStream.beginText();
                    // Setting the font to the Content stream
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, infoList.get(i).get(j).getFontSize() / 12+6);
                    // Setting the position for the line
                    contentStream.newLineAtOffset(infoList.get(i).get(j).getxPos(),
                            (infoList.get(i).get(j).getyPos()) + infoList.get(i).get(j).getFontSize() / 12+1);
                    // k++;
                    text = infoList.get(i).get(j).getLinkOrder() + ")";

                    // Adding text in the form of string
                    contentStream.showText(text);

                    // Ending the content stream
                    contentStream.endText();
                    contentStream.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
        return result;
    }
}
