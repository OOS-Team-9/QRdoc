package project.model;

/**
 * 한 페이지 정보를 담당하는 클래스
 */
public class Page {
    private String text;
    private int order;    //pdf페이지 숫자

    public Page(int order) {
        this.order = order;
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

    public void print() {
        System.out.println("\n------[ " + order + " ]번째 Page------");
        System.out.println("text: " + text);
    }
}
