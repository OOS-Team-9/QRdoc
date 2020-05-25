package Information;

/*문서에서 추출할 정보 클래스
* Link 클래스가 이 클래스를 상속한다.*/
public abstract class Information {
    private String text;        //문자열.
    private int textPos;        //문자열의 위치.
    private int order;          //문자열의 순서값.

    /*생성자*/
    public Information(String text, int textPos, int order) {
        this.text = text;
        this.textPos = textPos;
        this.order = order;
    }

    public Information(Information other) {
        this(other.text, other.textPos, other.order);
    }

    public Information() {
        this("", -1, -1);
    }

    /*Getter, Setter 함수*/
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextPos() {
        return this.textPos;
    }

    public void setTextPos(int textPos) {
        this.textPos = textPos;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
