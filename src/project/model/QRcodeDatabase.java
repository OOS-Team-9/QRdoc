package project.model;

import java.util.ArrayList;

public class QRcodeDatabase {
    public ArrayList<QRcode> qrList;

    public QRcodeDatabase() {
        qrList = new ArrayList<>();
    }

    void addQRcode(QRcode qr) {
        qrList.add(qr);
    }

    void addQRcode(QRcode qr, int index){
        qrList.add(index,qr);
    }

    boolean isEmpty() {
        return qrList.isEmpty();
    }

    int searchQRcode(QRcode qr) {
        for (int i = 0; i < qrList.size(); i++) {
            if(qrList.get(i).getOrder()==qr.getOrder()){
                return i;
            }
        }
        return -1;
    }

    void deleteQRcode(int index) {
        qrList.remove(index);
    }

    ArrayList<QRcode> getQRList(){
        return qrList;
    }
}
