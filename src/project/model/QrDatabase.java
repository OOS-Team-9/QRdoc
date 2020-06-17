package project.model;

import java.util.ArrayList;

/**
 * QR-code 정보를 다 모아놓은 데이터베이스 클래스
 * (나중에 사용할 예정)
 */
public class QrDatabase {
    public ArrayList<Qr> qrList;

    public QrDatabase() {
        qrList = new ArrayList<>();
    }

    public int getSize() {
        return qrList.size();
    }

    public Qr getQRcode(int index) {
        return qrList.get(index);
    }

    public void addQRcode(Qr qr) {
        qrList.add(qr);
    }

    public void addQRcode(Qr qr, int index) {
        qrList.add(index, qr);
    }

    public boolean isEmpty() {
        return qrList.isEmpty();
    }

    public int searchQRcode(Qr target) {
        Qr orignal=null;
        for (int i = 0; i < qrList.size(); i++) {
            orignal=qrList.get(i);
            if (orignal.getPageOrder() == target.getPageOrder()&&
                    orignal.getInfoOrderInOnePage()==target.getInfoOrderInOnePage()) {
                return i;
            }
        }
        return -1;
    }

    public void deleteQRcode(int index) {
        qrList.remove(index);
    }

    public ArrayList<Qr> getQRList() {
        return qrList;
    }
}
