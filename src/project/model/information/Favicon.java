package project.model.information;

public class Favicon {
    String url;
    String fileType;
    int width;
    int height;

    public Favicon(String url, String fileType, int width, int height) {
        this.url = url;
        this.fileType = fileType;
        this.width = width;
        this.height = height;
    }
}
