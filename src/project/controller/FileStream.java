package project.controller;

import project.model.MyDoc;

import java.io.File;
import java.io.IOException;


public class FileStream {
    private MyDoc doc;
    private String fileName = "";
    private String fileAddress = "";
    private File docOrgFile;

    public FileStream(File file) {
        try {
            docOrgFile = file;
            makeFileInfo(file.getAbsolutePath());
            MyDoc document = MyDoc.myLoad(file);
            doc = document;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileStream(MyDoc myDoc) {
        this.doc = myDoc;
    }

    public void close() {
        try {
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeFileInfo(String filePath) {
        System.out.println("input:" + filePath);
        String[] temp = filePath.split("\\\\");
        this.fileName = temp[temp.length - 1];
        for (int i = 0; i < temp.length - 1; i++) {
            fileAddress = fileAddress + temp[i] + "\\";
        }
        System.out.println("fileAddress:" + fileAddress);
        System.out.println("fileName:" + fileName);


    }

    public boolean saveDoc(File saveFile) {
        try {
            doc.save(saveFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveDoc() {
        try {
            doc.save(fileAddress + fileName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public MyDoc convertToDoc() {
        if (docOrgFile == null) {
            System.out.println("cancel select");
        } else {
            //String filePath = inputFile.getAbsolutePath();
            return doc;
        }
        return null;
    }


    public boolean saveDoc(String saveName, String saveFileAddress) {

        return false;
    }

    public boolean saveDoc(String saveName) {

        return false;
    }


    public String getFileName() {
        return fileName;
    }


    public String getFileAddress() {
        return fileAddress;
    }

    public MyDoc getMyDoc(){return doc;}


}
