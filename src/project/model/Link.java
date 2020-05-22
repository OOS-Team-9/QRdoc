package project.model;

public class Link {
    private String link;
    private int pageNum;

    public Link(int pgeNum,String address){
        this.pageNum=pgeNum;
        this.link=address;
    }

    String getLink(){
        return this.link;
    }
    int getPageNum(){
        return this.pageNum;
    }

    void resetLink(String address){
        this.link=address;
    }
    void resetPageNum(int pgeNum){
        this.pageNum=pgeNum;
    }
}
