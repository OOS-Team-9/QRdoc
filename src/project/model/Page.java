package project.model;

import java.util.ArrayList;

/**
 * 한 페이지 정보를 담당하는 클래스
 */
public class Page {
    private String text;
    private int order;    //pdf페이지 숫자
    private boolean[][] blank=new boolean[12][17];
    private ArrayList<Integer[]> availableBlankForQRcode=new ArrayList<Integer[]>();

    public Page(int order) {
        this.order = order;
        for(int x=0;x<12;x++){
            for(int y=0;y<17;y++){
                blank[x][y]=true;
            }
        }
    }

    public String getText() {
        return this.text;
    }

    public int getOrder() {
        return this.order;
    }

    public void setText(String buffer) {
        this.text = buffer;
    }

    public void resetOrder(int order) {
        this.order = order;
    }
    public boolean isThereBlankAt(int x,int y){
        return blank[x][y];
    }
    public void fillBlank(int x,int y){
        blank[x][y]=false;
    }
    public ArrayList<Integer[]> getAvailableBlankForQRcode(){
        return availableBlankForQRcode;
    }
    public void addAvailableBlankForQRcode(int x,int y){
        Integer[] temp=new Integer[2];
        temp[0]=x;
        temp[1]=y;
        availableBlankForQRcode.add(temp);
    }
    public void resetAvailableBlankForQRcode(){
        availableBlankForQRcode=new ArrayList<Integer[]>();
    }
    public void print() {
        System.out.println("\n------[ " + order + " ]번째 Page------");
        System.out.println("text: " + text);
    }
}
