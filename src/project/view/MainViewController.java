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
import project.controller.qr_buffimage_generator.QrBuffImageGenerator;
import project.controller.qr_buffimage_generator.VisualQrBuffImageGenerator;
import project.controller.extractor.LinkExtractor;
import project.controller.qr_inserter.EndQrInserter;
import project.controller.qr_inserter.IndicesInserter;
import project.controller.qr_inserter.QrInserter;
import project.model.MyDoc;
import project.model.Qr;
import project.model.information.Link;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
	int nowPage = 0;

	Image image[];

	MyDoc myDoc;
	FileStream myDocStream;
	ArrayList<ArrayList<Qr>> qrList = new ArrayList<>();
	
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

	public void setPrimaryStage(Stage primaryStage){
		this.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnOpen.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) ->{
			resetView();
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter docType = new FileChooser.ExtensionFilter("pdf file", "*.pdf");
			fileChooser.getExtensionFilters().addAll(docType);
			File inputFile;
			inputFile = fileChooser.showOpenDialog(null);

			if(inputFile == null){
				return;
			}

			myDocStream = new FileStream(inputFile);
			myDoc = myDocStream.convertToDoc();
			labelDoc.setText(myDocStream.getFileAddress() + myDocStream.getFileName());
		});
		btnSave.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) ->{
			if(myDoc == null){
				showFileInputAlertDialog();
				return;
			}
			FileChooser fileChooser = new FileChooser();
			File file = fileChooser.showSaveDialog(primaryStage.getScene().getWindow());
			if (file != null) {
				String[] temp = file.getName().split("\\.");
				if(!temp[temp.length-1].equals("pdf")){
					File newFile = new File(file.getAbsolutePath()+".pdf");
					file = newFile;
				}
				if(!myDocStream.saveDoc(file).equals("(요청한 작업은, 사용자가 매핑한 구역이 열려 있는 상태인 파일에서 수행할 수 없습니다)")){
					showFileAlreadyOpenAlertDialog();
				}
				else{
					System.out.println("saved"+ file.getAbsolutePath());
				}

			}
			else{
				showSelectSaveDialog(myDocStream);
			}
		});
		btnConversion.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) ->{
			try {
				LinkExtractor linkExtractor=new LinkExtractor(myDoc);
				linkExtractor.readTexts();
				linkExtractor.findBlankForQRcode();
				linkExtractor.extract();
				linkExtractor.setPos();
				ArrayList<ArrayList<Link>> linkList = linkExtractor.getInfoList();

				//
				//  QR code 생성!
				//
				QrBuffImageGenerator qrBuffImageGenerator = new VisualQrBuffImageGenerator();
				for (int pageOrder = 0; pageOrder < linkList.size(); pageOrder++) {
					qrList.add(new ArrayList<>());
					for (int infoOrderPerOnePage = 0; infoOrderPerOnePage < linkList.get(pageOrder).size(); infoOrderPerOnePage++) {
						try {
							BufferedImage qrBuffImage = qrBuffImageGenerator.generate(linkList.get(pageOrder).get(infoOrderPerOnePage),
									100,
									100);
							qrList.get(pageOrder).add(new Qr(pageOrder,infoOrderPerOnePage,qrBuffImage));
						} catch (WriterException e) {
							System.out.println("QrGenerator::generate ERROR: " +
									"[ " + linkList.get(pageOrder).get(infoOrderPerOnePage).getText() + " ]을 로 변환할 수 없습니다.");
						}
					}
				}

				QrInserter qrInserter = new EndQrInserter();
				try {
					qrInserter.insert(qrList,myDoc);
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}

				IndicesInserter.addIndices(myDoc,linkList);

			} catch (IOException e) {
				e.printStackTrace();
			}

			makeTempPDF();
			showTempPDF();
		});
		btnPrev.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) ->{
			imgDoc.setImage(image[--nowPage]);
			if(nowPage == 0){
				btnPrev.setVisible(false);
			}
			btnNext.setVisible(true);
		});
		btnNext.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) ->{
			imgDoc.setImage(image[++nowPage]);
			if(nowPage+1 == image.length){
				btnNext.setVisible(false);
			}
			btnPrev.setVisible(true);
		});
	}


	void resetView(){
		if(myDocStream != null) {
			myDocStream.close();
		}
		nowPage = 0;
		controlPane.setVisible(false);
		imgDoc.setImage(null);
	}


	public void showTempPDF(){
		if(image == null){
			labelDoc.setText("Please select the document file first.");
			return ;
		}
		nowPage = 0;
		controlPane.setVisible(true);
		imgDoc.setImage(image[nowPage]);
	}

	void makeTempPDF(){
		BufferedImage[] temp = myDoc.conversionPdf2Img();
		image = new Image[temp.length];
		for(int i = 0; i < temp.length; i++){
			image[i] = SwingFXUtils.toFXImage(temp[i],null);
		}
	}

	public void showSelectSaveDialog(FileStream myFileStream){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/selectSave_view.fxml"));
			AnchorPane page = (AnchorPane)loader.load();
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

	public void showFileInputAlertDialog(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/showFileInputAlert_view.fxml"));
			AnchorPane page = (AnchorPane)loader.load();
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


	public void showFileAlreadyOpenAlertDialog(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/showFileAlreadyOpenAlert_view.fxml"));
			AnchorPane page = (AnchorPane)loader.load();
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
}
