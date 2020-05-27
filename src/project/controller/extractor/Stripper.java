package project.controller.extractor;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.List;

public class Stripper extends PDFTextStripper {
    public Stripper () throws IOException {
        super();
    }
    public List<List<TextPosition>> getCharactersByArticle (){
        return charactersByArticle;
    }
}