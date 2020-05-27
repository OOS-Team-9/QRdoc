package project;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import project.controller.bitmatrix_generator.DefaultBitMTgenerator;
import project.controller.extractor.LinkExtractor;
import project.controller.qr_inserter.EndNoteQRinserter;
import project.controller.qrcode_writer.QRcodeWriter;
import project.model.QRcode;
import project.model.information.Information;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Testmain {
    /*
    public static void main(String[] args) {
        //경로 설정
        MyPath docFile = null;
        MyPath qrDirectory = null;
        try {
            docFile = new MyPath(Paths.get(".\\example\\test.pdf"));
            qrDirectory = new MyPath(Paths.get
                    (docFile.getDirectory() + "\\" + docFile.getFileName().replace(".pdf", "") + "~qr\\"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //
        //  Link Extractor 작동!
        //
        LinkExtractor linkExtractor = null;
        try {
            linkExtractor = new LinkExtractor(docFile);
            linkExtractor.readTexts();
            linkExtractor.extract();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        ArrayList<ArrayList<Information>> linkList = linkExtractor.getLinkList();

        //
        //  Default Bit Generator 작동!
        //
        DefaultBitMTgenerator bitMTgenerator = new DefaultBitMTgenerator();
        ArrayList<ArrayList<BitMatrix>> bitMatrixList = new ArrayList<>();
        BitMatrix bitmatrix = null;
        for (int pageOrder = 0; pageOrder < linkList.size(); pageOrder++) {
            bitMatrixList.add(new ArrayList<BitMatrix>());
            for (int infoOrderPerOnePage = 0; infoOrderPerOnePage < linkList.get(pageOrder).size(); infoOrderPerOnePage++) {
                try {
                    bitmatrix = bitMTgenerator.generate(linkList.get(pageOrder).get(infoOrderPerOnePage), 100, 100);
                    bitMatrixList.get(pageOrder).add(bitmatrix);
                } catch (WriterException e) {
                    System.out.println("BitMTgenerator::generate ERROR: " +
                            "[ " + linkList.get(pageOrder).get(infoOrderPerOnePage).getText() + " ]을 bit matrix로 변환할 수 없습니다.");
                }
            }
        }

        //
        //  QR-code 객체 리스트 생성!!
        //
        ArrayList<ArrayList<QRcode>> qrCodeObjList = new ArrayList<>();
        QRcode qrCodeObj = null;
        for (int pageOrder = 0; pageOrder < linkList.size(); pageOrder++) {
            qrCodeObjList.add(new ArrayList<>());
            for (int infoOrderPerOnePage = 0; infoOrderPerOnePage < linkList.get(pageOrder).size(); infoOrderPerOnePage++) {
                try {
                    qrCodeObj = new QRcode(
                            pageOrder,
                            infoOrderPerOnePage,
                            bitMatrixList.get(pageOrder).get(infoOrderPerOnePage),
                            new MyPath(qrDirectory.getPathString() + "\\" + pageOrder + infoOrderPerOnePage + ".png"));
                    qrCodeObjList.get(pageOrder).add(qrCodeObj);
                    qrCodeObj.print();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        //
        //  QR-code Writer 작동!
        //
        QRcodeWriter qrCodeWriter = new QRcodeWriter();
        for (ArrayList<QRcode> qrCodeObjListPerPage : qrCodeObjList) {
            for (QRcode obj : qrCodeObjListPerPage) {
                try {
                    qrCodeWriter.writeQRcode(obj);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println("QR-code 파일 생성 완료!");

        //
        //  QR-code Inserter 작동!
        //
        EndNoteQRinserter qrInserter = new EndNoteQRinserter();
        try {
            qrInserter.insert(docFile, qrCodeObjList);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

     */
}

