package project.model;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

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

        try {
        //순회하며 이미지로 변환 처리
            for (int i=0; i<this.getPages().getCount(); i++) {
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
