package project.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.*;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.ResourceCache;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.PDEncryption;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SigningSupport;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class MyDoc extends PDDocument {
    /*
    FileInputStream fileInputStream;
    public MyDoc(FileInputStream fileInputStream){
        this.fileInputStream = fileInputStream;
    }

     */

    public MyDoc(COSDocument doc, RandomAccessRead source, AccessPermission permission){
        super(doc,source,permission);
    }

    public MyDoc(){
        super();
    }


    private static MyDoc myLoad(RandomAccessBufferedFileInputStream raFile, String password, InputStream keyStore, String alias, MemoryUsageSetting memUsageSetting) throws IOException {
        ScratchFile scratchFile = new ScratchFile(memUsageSetting);

        try {
            MyPDFParser parser = new MyPDFParser(raFile, password, keyStore, alias, scratchFile);
            parser.parse();
            return parser.getMyDocDocument();
        } catch (IOException var7) {
            IOUtils.closeQuietly(scratchFile);
            throw var7;
        }
    }
    public static MyDoc myLoad(File file, String password, InputStream keyStore, String alias, MemoryUsageSetting memUsageSetting) throws IOException {
        RandomAccessBufferedFileInputStream raFile = new RandomAccessBufferedFileInputStream(file);

        try {
            return myLoad(raFile, password, keyStore, alias, memUsageSetting);
        } catch (IOException var7) {
            IOUtils.closeQuietly(raFile);
            throw var7;
        }
    }

    public static MyDoc myLoad(File file, String password, MemoryUsageSetting memUsageSetting) throws IOException {
        return myLoad((File)file, password, (InputStream)null, (String)null, memUsageSetting);
    }

    public static MyDoc myLoad(File file) throws IOException {
        return MyDoc.myLoad(file, "", MemoryUsageSetting.setupMainMemoryOnly());
    }
/*
    public MyDoc(File myFile){

    }

    public MyDoc(String myFilePath){
        try{
            super(PDDocument.load(new File(myFilePath)));
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

 */
    public BufferedImage[] conversionPdf2Img() {

        //String[] savedImgList = new String[this.getNumberOfPages()]; //저장된 이미지 경로를 저장하는 List 객체
        BufferedImage[] returnValue = new BufferedImage[this.getPages().getCount()];

        //PDDocument pdfDoc = PDDocument.load(is); //Document 생성
        PDFRenderer pdfRenderer = new PDFRenderer(this);

        String resultImgPath = "src/test_image"; //이미지가 저장될 경로
        try {
            Files.createDirectories(Paths.get(resultImgPath)); //PDF 2 Img에서는 경로가 없는 경우 이미지 파일이 생성이 안되기 때문에 디렉토리를 만들어준다.


        //순회하며 이미지로 변환 처리
            for (int i=0; i<this.getPages().getCount(); i++) {
                String imgFileName = resultImgPath + "/" + i + ".jpg";
                //DPI 설정
                returnValue[i] = pdfRenderer.renderImageWithDPI(i, 300, ImageType.RGB);
                // 이미지로 만든다.
                /*
                FileOutputStream out = new FileOutputStream(imgFileName); //파일로 출력하기위해 파일출력스트림 생성
                ImageIO.write(bim, "png",out); //이미지 출력! , 이미지를 파일출력스트림을 통해 JPG타입으로 출력
                out.close();  //출력스트림 닫기
                savedImgList[i]=i+".jpg";
                */
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        return returnValue;
    }
}
