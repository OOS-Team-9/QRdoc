package project.model;

public class Link {
    private String link;
    private int pageNum;

    public Link(int pgeNum,String address){
        this.pageNum=pgeNum;
        this.link=address;
    }

    public String getLink(){
        return this.link;
    }
    public int getPageNum(){
        return this.pageNum;
    }

    public void resetLink(String address){
        this.link=address;
    }
    public void resetPageNum(int pgeNum){
        this.pageNum=pgeNum;
    }
}
