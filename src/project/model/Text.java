package project.model;

public class Text {
    private String text;
    private int pageNum;    //pdf페이지 숫자

    public Text(int pgeNum){
        this.pageNum=pgeNum;
    }

    public String getText(){
        return this.text;
    }
    int getPageNum(){
        return this.pageNum;}

    public void setText(String buffer){
        this.text=buffer;
    }
    public void resetPageNum(int pgeNum){
        this.pageNum=pgeNum;
    }
}
