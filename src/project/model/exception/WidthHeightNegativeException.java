package project.model.exception;

/*QR-code 이미지의 폭이나 너비가 0과 같거나 작은 값일 때, 발생하는 예외*/
public class WidthHeightNegativeException extends Exception {
    public WidthHeightNegativeException(String msg) {
        super(msg);
    }

    public WidthHeightNegativeException() {
        super("QR-code의 폭 또는 너비가 0과 같거나, 작은 값입니다.");
    }
}
