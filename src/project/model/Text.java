package project.model

public class Text {
    private String text;
    private int pageNum;

    Text(int pgeNum,String buffer){
        this.pageNum=pgeNum;
        this.text= buffer;
    }
    String getText(){
        return this.text;
    }
    int getPageNum(){
        return this.pageNum;}

    void addText(String buffer){
        this.text.concat(buffer);
    }
    void resetPageNum(int pgeNum){
        this.pageNum=pgeNum;
    }
}
