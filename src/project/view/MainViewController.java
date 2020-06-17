package project.view;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
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
import project.controller.bitmatrix_generator.DefaultBitMTgenerator;
import project.controller.extractor.LinkExtractor;
import project.controller.qr_inserter.BlankQRinserter;
import project.controller.qr_inserter.EndNoteQRinserter;
import project.controller.qr_inserter.FootNoteQRinserter;
import project.controller.qr_inserter.IndicesInserter;
import project.controller.qrcode_writer.QRcodeWriter;
import project.model.MyDoc;
import project.model.Page;
import project.model.QRcode;
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

	String arg;
	MyDoc myDoc;
	FileStream myDocStream;
	ArrayList<ArrayList<QRcode>> qrCodeObjList = new ArrayList<>();
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
<<<<<<< HEAD
		btnConversion.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) -> {

			try {
				LinkExtractor linkExtractor = new LinkExtractor(myDoc);
				linkExtractor.readTexts();
				linkExtractor.findBlankForQRcode();
				linkExtractor.extract();
				linkExtractor.setPos();
				ArrayList<ArrayList<Link>> linkList = linkExtractor.getInfoList();

				//
				// Default Bit Generator 작동!
				//
				DefaultBitMTgenerator bitMTgenerator = new DefaultBitMTgenerator();
				ArrayList<ArrayList<BitMatrix>> bitMatrixList = new ArrayList<>();
				BitMatrix bitmatrix = null;
				for (int pageOrder = 0; pageOrder < linkList.size(); pageOrder++) {
					bitMatrixList.add(new ArrayList<BitMatrix>());
					for (int infoOrderPerOnePage = 0; infoOrderPerOnePage < linkList.get(pageOrder)
							.size(); infoOrderPerOnePage++) {
						try {
							bitmatrix = bitMTgenerator.generate(linkList.get(pageOrder).get(infoOrderPerOnePage), 100,
									100);
							bitMatrixList.get(pageOrder).add(bitmatrix);
						} catch (WriterException e) {
							System.out.println("BitMTgenerator::generate ERROR: " + "[ "
									+ linkList.get(pageOrder).get(infoOrderPerOnePage).getText()
									+ " ]을 bit matrix로 변환할 수 없습니다.");
						}
					}
				}

				//
				// QR-code 객체 리스트 생성!!
				//

				QRcode qrCodeObj = null;
				for (int pageOrder = 0; pageOrder < linkList.size(); pageOrder++) {
					qrCodeObjList.add(new ArrayList<>());
					for (int infoOrderPerOnePage = 0; infoOrderPerOnePage < linkList.get(pageOrder)
							.size(); infoOrderPerOnePage++) {
						qrCodeObj = new QRcode(pageOrder, infoOrderPerOnePage,
								bitMatrixList.get(pageOrder).get(infoOrderPerOnePage));
						qrCodeObjList.get(pageOrder).add(qrCodeObj);
						qrCodeObj.print();
					}
				}
=======
		btnConversion.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) ->{
			try {
			LinkExtractor linkExtractor = new LinkExtractor(myDoc);
			linkExtractor.readTexts();
			linkExtractor.extract();
			linkExtractor.setPos();
			linkExtractor.findBlank();
			linkExtractor.findBlankForQRcode();
			ArrayList<ArrayList<Link>> linkList = linkExtractor.getInfoList();

			ArrayList<ArrayList<BitMatrix>> bitMatrixList = makeBitMatrixList(linkList); // Default Bit Generator 작동!
			ArrayList<ArrayList<QRcode>> qrCodeObjList = makeQRCodeObjList(linkList,bitMatrixList);	//  QR-code 객체 리스트 생성!!
			writeQRCode(qrCodeObjList);

			FootNoteQRinserter footQrInseter=new FootNoteQRinserter();
			BlankQRinserter blankQrInserter=new BlankQRinserter();
			EndNoteQRinserter qrInserter = new EndNoteQRinserter();
			//boolean hasToInsertInEndNote=false;

			for(int pageOrder=0;pageOrder<linkList.size();pageOrder++){
				Page page=linkExtractor.getPageList().get(pageOrder);
				ArrayList<Integer[]> allBlank =page.getAvailableBlankForQRcode();
				int footBlankCount=0;
>>>>>>> 535240798cb743ba49dd2ba1f5d4a9788cff3c7c

				for(int j=0;j<allBlank.size();j++) {
					if (allBlank.get(j)[1]==0)
						footBlankCount++;
				}
				System.out.println("아래여백개수:"+footBlankCount);
				if (footBlankCount==11) {                            //페이지 아래 간격이 충분해서 각주삽입
					System.out.println("footnote Insert");
					footQrInseter.insert(qrCodeObjList, myDoc, pageOrder);
				}
				else if (allBlank.size()>=linkList.get(pageOrder).size()*4) {        //큐알코드 한 개당 4개의 큐알코드 여백이 사라지므로
					System.out.println("blank Insert");
					for (int infoOrder = 0; infoOrder < linkList.get(pageOrder).size(); infoOrder++) {
						Integer[] blankPos=linkExtractor.findClosestBlank(page, linkList.get(pageOrder).get(infoOrder));
						System.out.println("선택된 여백 위치:"+blankPos[0]+","+blankPos[1]);
						System.out.println("----------------------------------");
						blankQrInserter.insert(qrCodeObjList, myDoc, pageOrder,blankPos);

						page.fillBlank(blankPos[0],blankPos[1]);
						page.fillBlank(blankPos[0]+1,blankPos[1]);
						page.fillBlank(blankPos[0],blankPos[1]+1);
						page.fillBlank(blankPos[0]+1,blankPos[1]+1);
						linkExtractor.findBlankForQRcode();
					}
				}
				qrInserter.insert(qrCodeObjList, myDoc, 0);//0 의미없음
			}

<<<<<<< HEAD
				IndicesInserter.addIndices(myDoc, linkList);

=======
>>>>>>> 535240798cb743ba49dd2ba1f5d4a9788cff3c7c
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

	private void writeQRCode(ArrayList<ArrayList<QRcode>> qrCodeObjList) {
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
	}

	private ArrayList<ArrayList<QRcode>> makeQRCodeObjList(ArrayList<ArrayList<Link>> linkList,
			ArrayList<ArrayList<BitMatrix>> bitMatrixList) {
		ArrayList<ArrayList<QRcode>> qrCodeObjList = new ArrayList<>();

		QRcode qrCodeObj = null;
		for (int pageOrder = 0; pageOrder < linkList.size(); pageOrder++) {
			qrCodeObjList.add(new ArrayList<>());
			for (int infoOrderPerOnePage = 0; infoOrderPerOnePage < linkList.get(pageOrder)
					.size(); infoOrderPerOnePage++) {
				qrCodeObj = new QRcode(pageOrder, infoOrderPerOnePage,
						bitMatrixList.get(pageOrder).get(infoOrderPerOnePage));
				qrCodeObjList.get(pageOrder).add(qrCodeObj);
				qrCodeObj.print();
			}
		}

		return qrCodeObjList;
	}

	private ArrayList<ArrayList<BitMatrix>> makeBitMatrixList(ArrayList<ArrayList<Link>> linkList) {
		ArrayList<ArrayList<BitMatrix>> bitMatrixList = new ArrayList<ArrayList<BitMatrix>>();
		DefaultBitMTgenerator bitMTgenerator = new DefaultBitMTgenerator();
		BitMatrix bitmatrix = null;
		for (int pageOrder = 0; pageOrder < linkList.size(); pageOrder++) {
			bitMatrixList.add(new ArrayList<BitMatrix>());
			for (int infoOrderPerOnePage = 0; infoOrderPerOnePage < linkList.get(pageOrder)
					.size(); infoOrderPerOnePage++) {
				try {
					bitmatrix = bitMTgenerator.generate(linkList.get(pageOrder).get(infoOrderPerOnePage), 100, 100);
					bitMatrixList.get(pageOrder).add(bitmatrix);
				} catch (WriterException e) {
					System.out.println("BitMTgenerator::generate ERROR: " + "[ "
							+ linkList.get(pageOrder).get(infoOrderPerOnePage).getText()
							+ " ]을 bit matrix로 변환할 수 없습니다.");
				}
			}
		}
		return bitMatrixList;
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
<<<<<<< HEAD

		ArrayList<ArrayList<Link>> linkList = extractLink(myDoc);
=======
		try {
		LinkExtractor linkExtractor = new LinkExtractor(myDoc);
		linkExtractor.readTexts();
		linkExtractor.findBlankForQRcode();
		linkExtractor.extract();
		linkExtractor.setPos();
		ArrayList<ArrayList<Link>> linkList = linkExtractor.getInfoList();
>>>>>>> 535240798cb743ba49dd2ba1f5d4a9788cff3c7c
		ArrayList<ArrayList<BitMatrix>> bitMatrixList = makeBitMatrixList(linkList);
		ArrayList<ArrayList<QRcode>> qrCodeObjList = makeQRCodeObjList(linkList, bitMatrixList);
		writeQRCode(qrCodeObjList);
		FootNoteQRinserter footQrInseter=new FootNoteQRinserter();
		BlankQRinserter blankQrInserter=new BlankQRinserter();
		EndNoteQRinserter qrInserter = new EndNoteQRinserter();
<<<<<<< HEAD
		try {
			qrInserter.insert(qrCodeObjList, myDoc, 0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		IndicesInserter.addIndices(myDoc, linkList);
=======

		//boolean hasToInsertInEndNote=false;

		for(int pageOrder=0;pageOrder<linkList.size();pageOrder++){
			Page page=linkExtractor.getPageList().get(pageOrder);
			ArrayList<Integer[]> allBlank =page.getAvailableBlankForQRcode();
			int footBlankCount=0;

			for(int j=0;j<allBlank.size();j++) {
				if (allBlank.get(j)[1]==0)
					footBlankCount++;
			}
			System.out.println("아래여백개수:" +footBlankCount);
			if (footBlankCount==11) {                            //페이지 아래 간격이 충분해서 각주삽입
				System.out.println("footnote Insert");
				footQrInseter.insert(qrCodeObjList, myDoc, pageOrder);
			}
			else if (allBlank.size()>=linkList.get(pageOrder).size()*4) {        //큐알코드 한 개당 4개의 큐알코드 여백이 사라지므로							//페이지 아래 공간 부족해서 여백삽입
				System.out.println("blank Insert");
				for (int infoOrder = 0; infoOrder < linkList.get(pageOrder).size(); infoOrder++) {
					Integer[] blankPos=linkExtractor.findClosestBlank(page, linkList.get(pageOrder).get(infoOrder));
					blankQrInserter.insert(qrCodeObjList, myDoc, pageOrder,blankPos);            //삽입가능한 좌표가 큐알코드 개수의 4배이상일 때 여백삽입

				}
			}
		}

			qrInserter.insert(qrCodeObjList, myDoc, 0);//0 의미없음
			IndicesInserter.addIndices(myDoc,linkList);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

>>>>>>> 535240798cb743ba49dd2ba1f5d4a9788cff3c7c
		makeTempPDF();
		showTempPDF();
	}

	private void setPDFDoc(File file) {

		myDocStream = new FileStream(file);
		myDoc = myDocStream.convertToDoc();
		labelDoc.setText(myDocStream.getFileAddress() + myDocStream.getFileName());

	}
}
