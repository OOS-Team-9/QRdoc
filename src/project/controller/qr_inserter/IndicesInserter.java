package project.controller.qr_inserter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import project.model.information.Link;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class IndicesInserter {
    /*
     * 링크 오른쪽위에 지수넣는 클래스입니다
     */
    public static boolean addIndices(String filename, ArrayList<ArrayList<Link>> infoList) {

        boolean result = true;
        String text = "";
        File file = new File(filename);
        PDDocument doc = null;
        try {
            doc = PDDocument.load(file);
        } catch (IOException e) {
            result = false;
            System.out.println("DocumentCreatioin-createPDF ERROR : " + e.getMessage());
        }
        int k = 0;
        for (int i = 0; i < infoList.size(); i++) {
            for (int j = 0; j < infoList.get(i).size(); j++) {
                // System.out.println("text" + infoList.get(i).get(j).getText());
                // System.out.println("xPos: " + infoList.get(i).get(j).getxPos());
                // System.out.println("yPos: " + infoList.get(i).get(j).getyPos());
                // System.out.println("fontSize: " + infoList.get(i).get(j).getFontSize());
                PDPage page = doc.getPage(i);
                PDPageContentStream contentStream;
                try {
                    contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true);
                    // Begin the Content stream
                    contentStream.beginText();
                    // Setting the font to the Content stream
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, infoList.get(i).get(j).getFontSize() / 2);
                    // Setting the position for the line
                    contentStream.newLineAtOffset(infoList.get(i).get(j).getxPos(),
                            (infoList.get(i).get(j).getyPos()) + 3);
                    k++;
                    text = k + ")";

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

        try {
            doc.save("File\\resultPDF.pdf");
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
}
