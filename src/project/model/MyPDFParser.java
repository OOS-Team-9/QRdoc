package project.model;

import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.io.ScratchFile;
import org.apache.pdfbox.pdfparser.PDFParser;

import java.io.IOException;
import java.io.InputStream;

public class MyPDFParser extends PDFParser {

    public MyPDFParser(RandomAccessRead source) throws IOException {
        super(source, "", ScratchFile.getMainMemoryOnlyInstance());
    }

    public MyPDFParser(RandomAccessRead source, ScratchFile scratchFile) throws IOException {
        super(source, "", scratchFile);
    }

    public MyPDFParser(RandomAccessRead source, String decryptionPassword) throws IOException {
        super(source, decryptionPassword, ScratchFile.getMainMemoryOnlyInstance());
    }

    public MyPDFParser(RandomAccessRead source, String decryptionPassword, ScratchFile scratchFile) throws IOException {
        super(source, decryptionPassword, (InputStream)null, (String)null, scratchFile);
    }

    public MyPDFParser(RandomAccessRead source, String decryptionPassword, InputStream keyStore, String alias) throws IOException {
        super(source, decryptionPassword, keyStore, alias, ScratchFile.getMainMemoryOnlyInstance());
    }

    public MyPDFParser(RandomAccessRead source, String decryptionPassword, InputStream keyStore, String alias, ScratchFile scratchFile) throws IOException {
        super(source,decryptionPassword,keyStore, alias,scratchFile);
    }

    RandomAccessRead getSource(){
        return super.source;
    }

    public MyDoc getMyDocDocument() throws IOException {
        MyDoc doc = new MyDoc(this.getDocument(), this.source, this.getAccessPermission());
        doc.setEncryptionDictionary(this.getEncryption());
        return doc;
    }


}
