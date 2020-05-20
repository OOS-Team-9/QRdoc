package project;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	private AnchorPane root;
	private Stage primaryStage;

	
	public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("QRdoc");

        initRootLayout();
    }
	

	public void initRootLayout() {
		 try {
			 FXMLLoader loader = new FXMLLoader();
			 loader.setLocation(Main.class.getResource("view/main_view.fxml"));
			root = (AnchorPane)loader.load();
			Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
