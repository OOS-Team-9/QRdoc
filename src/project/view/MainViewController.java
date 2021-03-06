package project.view;

import com.google.zxing.WriterException;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.Main;
import project.controller.FileStream;
import project.controller.extractor.LinkExtractor;
import project.controller.qr_buffimage_generator.QrBuffImageGenerator;
import project.controller.qr_buffimage_generator.VisualQrBuffImageGenerator;
import project.controller.qr_inserter.BlankQRinserter;
import project.controller.qr_inserter.EndQRinserter;
import project.controller.qr_inserter.FootQRinserter;
import project.controller.qr_inserter.IndicesInserter;

import project.model.MyDoc;
import project.model.Page;
import project.model.Qr;
import project.model.information.Link;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    int nowPage = 0;

    Image image[];

    String arg;
    MyDoc myDoc;
    FileStream myDocStream;


    @FXML
    private Button btnOpen;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnConversion;

    @FXML
    private Button btnPrev;

    @FXML
    private Button btnNext;

    @FXML
    private ImageView imgDoc;

    @FXML
    private Label labelDoc;

    @FXML
    private GridPane controlPane;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public ImageView getImgDoc() {
        return imgDoc;
    }

    public GridPane getControlPane() {
        return controlPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnOpen.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) -> {
            resetView();
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter docType = new FileChooser.ExtensionFilter("pdf file", "*.pdf");
            fileChooser.getExtensionFilters().addAll(docType);
            File inputFile;
            inputFile = fileChooser.showOpenDialog(null);
            if (inputFile == null) {
                return;
            }
            setPDFDoc(inputFile);
        });
        btnSave.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) -> {
            if (myDoc == null) {
                showFileInputAlertDialog();
                return;
            }
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(primaryStage.getScene().getWindow());
            if (file != null) {
                String[] temp = file.getName().split("\\.");
                if (!temp[temp.length - 1].equals("pdf")) {
                    File newFile = new File(file.getAbsolutePath() + ".pdf");
                    file = newFile;
                }
                if (myDocStream.saveDoc(file).contains("(요청한 작업은, 사용자가 매핑한 구역이 열려 있는 상태인 파일에서 수행할 수 없습니다)")) {
                    showFileAlreadyOpenAlertDialog();
                } else {
                    System.out.println("saved" + file.getAbsolutePath());
                }

            } else {
                showSelectSaveDialog(myDocStream);
            }
        });
        btnConversion.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) -> {
            try {
                LinkExtractor linkExtractor = new LinkExtractor(myDoc);
                linkExtractor.readTexts();
                linkExtractor.extract();
                linkExtractor.setPos();
                linkExtractor.findBlank();
                linkExtractor.findBlankForQRcode();
                ArrayList<ArrayList<Link>> linkList = linkExtractor.getInfoList();

                //
                //  QR code 생성!
                //
                List<List<Qr>> qrList = new ArrayList<>();
                QrBuffImageGenerator qrBuffImageGenerator = new VisualQrBuffImageGenerator();
                for (int pageOrder = 0; pageOrder < linkList.size(); pageOrder++) {
                    qrList.add(new ArrayList<>());
                    for (int infoOrderPerOnePage = 0; infoOrderPerOnePage < linkList.get(pageOrder).size(); infoOrderPerOnePage++) {
                        try {
                            BufferedImage qrBuffImage = qrBuffImageGenerator.generate(linkList.get(pageOrder).get(infoOrderPerOnePage),
                                    100,
                                    100);
                            qrList.get(pageOrder).add(new Qr(pageOrder, infoOrderPerOnePage, qrBuffImage));
                        } catch (WriterException e) {
                            System.out.println("QrGenerator::generate ERROR: " +
                                    "[ " + linkList.get(pageOrder).get(infoOrderPerOnePage).getText() + " ]을 로 변환할 수 없습니다.");
                        }
                    }
                }

                FootQRinserter footQrInseter = new FootQRinserter();
                BlankQRinserter blankQrInserter = new BlankQRinserter();
                EndQRinserter qrInserter = new EndQRinserter();

                boolean hasToInsertInEndNote = false;

                for (int pageOrder = 0; pageOrder < linkList.size(); pageOrder++) {
                    Page page = linkExtractor.getPageList().get(pageOrder);
                    ArrayList<Integer[]> allBlank = page.getAvailableBlankForQRcode();

                    int footBlankCount = 0;

                    for (int j = 0; j < allBlank.size(); j++) {
                        if (allBlank.get(j)[1] == 0)
                            footBlankCount++;
                    }
                    System.out.println("아래여백개수:" + footBlankCount);
                    if (footBlankCount == 11) {                            //페이지 아래 간격이 충분해서 각주삽입
                        System.out.println("****footnote Insert" + pageOrder);
                        footQrInseter.insert(qrList, myDoc, pageOrder);
                    } else if (allBlank.size() >= linkList.get(pageOrder).size() * 9) {        //큐알코드 한 개당 4개의 큐알코드 여백이 사라지므로
                        System.out.println("****blank Insert" + pageOrder);
                        for (int infoOrder = 0; infoOrder < linkList.get(pageOrder).size(); infoOrder++) {
                            Integer[] blankPos = linkExtractor.findClosestBlank(page, linkList.get(pageOrder).get(infoOrder));
                            System.out.println("선택된 여백 위치:" + blankPos[0] + "," + blankPos[1]);
                            System.out.println("----------------------------------");
                            blankQrInserter.insert(qrList, myDoc, pageOrder, blankPos);

                            page.fillBlank(blankPos[0], blankPos[1]);
                            page.fillBlank(blankPos[0] + 1, blankPos[1]);
                            page.fillBlank(blankPos[0], blankPos[1] + 1);
                            page.fillBlank(blankPos[0] + 1, blankPos[1] + 1);
                            linkExtractor.findBlankForQRcode();
                        }
                    } else {
                        hasToInsertInEndNote = true;
                    }

                }
                if (hasToInsertInEndNote) {
                    //System.out.println("*****미주"+pageOrder);
                    qrInserter.insert(qrList, myDoc, 0);//0 의미없음
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            makeTempPDF();
            showTempPDF();
        });
        btnPrev.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) -> {
            imgDoc.setImage(image[--nowPage]);
            if (nowPage == 0) {
                btnPrev.setVisible(false);
            }
            btnNext.setVisible(true);
        });
        btnNext.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) -> {
            imgDoc.setImage(image[++nowPage]);
            if (nowPage + 1 == image.length) {
                btnNext.setVisible(false);
            }
            btnPrev.setVisible(true);
        });
    }


    private ArrayList<ArrayList<Link>> extractLink(MyDoc myDoc) {
        LinkExtractor linkExtractor = null;
        try {
            linkExtractor = new LinkExtractor(myDoc);
            linkExtractor.readTexts();
            linkExtractor.extract();
            linkExtractor.setPos();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linkExtractor.getInfoList();
    }

    private void setPDF(String s) {

    }


    void resetView() {
        if (myDocStream != null) {
            myDocStream.close();
        }
        nowPage = 0;
        controlPane.setVisible(false);
        imgDoc.setImage(null);
    }


    public void showTempPDF() {
        if (image == null) {
            labelDoc.setText("Please select the document file first.");
            return;
        }
        nowPage = 0;
        controlPane.setVisible(true);
        imgDoc.setImage(image[nowPage]);
    }

    void makeTempPDF() {
        BufferedImage[] temp = myDoc.conversionPdf2Img();
        image = new Image[temp.length];
        for (int i = 0; i < temp.length; i++) {
            image[i] = SwingFXUtils.toFXImage(temp[i], null);
        }
    }

    public void showSelectSaveDialog(FileStream myFileStream) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/selectSave_view.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            SelectSaveViewController controller = loader.getController();
            Stage selectSaveStage = new Stage();
            Scene scene = new Scene(page);
            selectSaveStage.setTitle("SelectSave");
            selectSaveStage.initModality(Modality.WINDOW_MODAL);
            selectSaveStage.initOwner(primaryStage);
            selectSaveStage.setScene(scene);
            controller.setFileStream(myFileStream);
            controller.setDialogStage(selectSaveStage);
            selectSaveStage.showAndWait();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void showFileInputAlertDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/showFileInputAlert_view.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            showFileInputAlertController controller = loader.getController();
            Stage selectSaveStage = new Stage();
            Scene scene = new Scene(page);
            selectSaveStage.setTitle("Alert");
            selectSaveStage.initModality(Modality.WINDOW_MODAL);
            selectSaveStage.initOwner(primaryStage);
            selectSaveStage.setScene(scene);
            controller.setDialogStage(selectSaveStage);
            selectSaveStage.showAndWait();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void showFileAlreadyOpenAlertDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/showFileAlreadyOpenAlert_view.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            showFileAlreadyOpenAlertController controller = loader.getController();
            Stage selectSaveStage = new Stage();
            Scene scene = new Scene(page);
            selectSaveStage.setTitle("Alert");
            selectSaveStage.initModality(Modality.WINDOW_MODAL);
            selectSaveStage.initOwner(primaryStage);
            selectSaveStage.setScene(scene);
            controller.setDialogStage(selectSaveStage);
            selectSaveStage.showAndWait();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setParameter(String arg) throws IOException {
        this.arg = arg;
        resetView();
        setPDFDoc(new File(this.arg));
        try {
            LinkExtractor linkExtractor = new LinkExtractor(myDoc);
            linkExtractor.readTexts();
            linkExtractor.findBlankForQRcode();
            linkExtractor.extract();
            linkExtractor.setPos();
            ArrayList<ArrayList<Link>> linkList = linkExtractor.getInfoList();

            //
            //  QR code 생성!
            //
            List<List<Qr>> qrList = new ArrayList<>();
            QrBuffImageGenerator qrBuffImageGenerator = new VisualQrBuffImageGenerator();
            for (int pageOrder = 0; pageOrder < linkList.size(); pageOrder++) {
                qrList.add(new ArrayList<>());
                for (int infoOrderPerOnePage = 0; infoOrderPerOnePage < linkList.get(pageOrder).size(); infoOrderPerOnePage++) {
                    try {
                        BufferedImage qrBuffImage = qrBuffImageGenerator.generate(linkList.get(pageOrder).get(infoOrderPerOnePage),
                                100,
                                100);
                        qrList.get(pageOrder).add(new Qr(pageOrder, infoOrderPerOnePage, qrBuffImage));
                    } catch (WriterException e) {
                        System.out.println("QrGenerator::generate ERROR: " +
                                "[ " + linkList.get(pageOrder).get(infoOrderPerOnePage).getText() + " ]을 로 변환할 수 없습니다.");
                    }
                }
            }

            FootQRinserter footQrInseter = new FootQRinserter();
            BlankQRinserter blankQrInserter = new BlankQRinserter();
            EndQRinserter qrInserter = new EndQRinserter();

            //boolean hasToInsertInEndNote=false;

            for (int pageOrder = 0; pageOrder < linkList.size(); pageOrder++) {
                Page page = linkExtractor.getPageList().get(pageOrder);
                ArrayList<Integer[]> allBlank = page.getAvailableBlankForQRcode();
                int footBlankCount = 0;

                for (int j = 0; j < allBlank.size(); j++) {
                    if (allBlank.get(j)[1] == 0)
                        footBlankCount++;
                }
                System.out.println("아래여백개수:" + footBlankCount);
                if (footBlankCount == 11) {                            //페이지 아래 간격이 충분해서 각주삽입
                    System.out.println("footnote Insert");
                    footQrInseter.insert(qrList, myDoc, pageOrder);
                } else if (allBlank.size() >= linkList.get(pageOrder).size() * 4) {        //큐알코드 한 개당 4개의 큐알코드 여백이 사라지므로							//페이지 아래 공간 부족해서 여백삽입
                    System.out.println("blank Insert");
                    for (int infoOrder = 0; infoOrder < linkList.get(pageOrder).size(); infoOrder++) {
                        Integer[] blankPos = linkExtractor.findClosestBlank(page, linkList.get(pageOrder).get(infoOrder));
                        blankQrInserter.insert(qrList, myDoc, pageOrder, blankPos);            //삽입가능한 좌표가 큐알코드 개수의 4배이상일 때 여백삽입

                    }
                }
            }
            qrInserter.insert(qrList, myDoc, 0);//0 의미없음
            IndicesInserter.addIndices(myDoc, linkList);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        makeTempPDF();
        showTempPDF();
    }

    private void setPDFDoc(File file) {

        myDocStream = new FileStream(file);
        myDoc = myDocStream.convertToDoc();
        labelDoc.setText(myDocStream.getFileAddress() + myDocStream.getFileName());

    }
}
