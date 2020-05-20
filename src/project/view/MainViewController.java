package project.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project.model.MyDoc;

public class MainViewController implements Initializable {
	int nowPage = 0;

	Image image[];

	MyDoc myDoc;
	
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

	//private MouseEventHandlerBtnN btnNHandler = new MouseEventHandlerBtnN();

	@Override
	public void initialize(URL location, ResourceBundle resources) {


		btnOpen.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) ->{
			myDoc = convertToDoc();
		});
		btnSave.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) ->{

		});
		btnConversion.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) ->{
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


	public MyDoc convertToDoc(){
		FileChooser fileChooser = new FileChooser();
		File inputFile;
		MyDoc returnDoc = null;
		try {
			inputFile = fileChooser.showOpenDialog(null);
			FileInputStream fileInputStream = new FileInputStream(inputFile);
			labelDoc.setText(inputFile.getName());
			returnDoc = new MyDoc(fileInputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		makeTestData();
		return returnDoc;
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
	
	public void setPrimaryStage(Stage primaryStage){
		this.primaryStage = primaryStage;
	}
	void makeTestData(){
		image = new Image[2];
		image[0] = new Image("@../../test_image/resultPDF_1.jpg");
		image[1] = new Image("@../../test_image/resultPDF_2.jpg");
	}
}
