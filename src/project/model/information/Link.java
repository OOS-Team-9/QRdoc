package project.model.information;

/**
 * 링크 정보를 담당하는 클래스
 */
public class Link extends Information {

    /*생성자*/
    public Link(String link, int linkPos, int linkOrder) {
        super(link, linkPos, linkOrder);
    }

    public Link(Link other) {
        super(other);
    }

    public Link() {
        super();
    }

    /*Getter, Setter 함수*/
    public String getLink() {
        return super.getText();
    }

    public void setLink(String link) {
        super.setText(link);
    }

    public int getLinkPos() {
        return super.getTextPos();
    }

    public void setLinkPos(int linkPos) {
        super.setTextPos(linkPos);
    }

    public int getLinkOrder() {
        return super.getOrder();
    }

    public void setLinkOrder(int linkOrder) {
        super.setOrder(linkOrder);
    }

    public void print() {
        super.print();
    }

}