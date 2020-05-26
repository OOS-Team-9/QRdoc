package project.model;

import com.google.zxing.common.BitMatrix;

public class QRcode {

    private int order;
    private BitMatrix bitmatrix;
    private String path;
    private String directory;
    private String fileName;
    private String fileType;
    private int width;
    private int height;

    public QRcode(int order, BitMatrix bitmatrix,
                  String path,
                  String directory,
                  String fileName,
                  String fileType,
                  int width,
                  int height) {
        this.order = order;
        this.bitmatrix = bitmatrix;
        this.path = path;
        this.directory = directory;
        this.fileName = fileName;
        this.fileType = fileType;
        this.width = width;
        this.height = height;
    }

    public QRcode() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BitMatrix getBitmatrix() {
        return bitmatrix;
    }

    public void setBitmatrix(BitMatrix bitmatrix) {
        this.bitmatrix = bitmatrix;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
