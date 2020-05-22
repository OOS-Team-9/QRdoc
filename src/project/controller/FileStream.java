package project.controller;

import javafx.stage.FileChooser;
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
import project.model.MyDoc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;


public class FileStream {
    private MyDoc doc;
    private String fileName = "";
    private String fileAddress = "";
    private File docOrgFile;



    public FileStream(File file){
        try {
            docOrgFile = file;
            makeFileInfo(file.getAbsolutePath());
            MyDoc document = MyDoc.myLoad(file);
            doc = document;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileStream(MyDoc myDoc){
        this.doc = myDoc;
    }
    public void close(){
        try {
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeFileInfo(String filePath){
        System.out.println("input:" + filePath);
        String[] temp = filePath.split("\\\\");
        this.fileName = temp[temp.length-1];
        for(int i = 0; i < temp.length-1; i++){
            fileAddress = fileAddress  + temp[i]+"\\";
        }
        System.out.println("fileAddress:" + fileAddress);
        System.out.println("fileName:" + fileName);


    }

    public boolean saveDoc(File saveFile){
        try {
            doc.save(saveFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveDoc(){
        try {
            doc.save(fileAddress + fileName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public MyDoc convertToDoc(){
       if(docOrgFile == null){
            System.out.println("cancel select");
        }
        else{
            //String filePath = inputFile.getAbsolutePath();
            return doc;
        }
        return null;
    }


    public boolean saveDoc(String saveName, String saveFileAddress){

        return false;
    }

    public boolean saveDoc(String saveName){

        return false;
    }


    public String getFileName() {
        return fileName;
    }



    public String getFileAddress() {
        return fileAddress;
    }




}
