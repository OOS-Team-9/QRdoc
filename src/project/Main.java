package project;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.view.MainViewController;

import java.io.IOException;
import java.util.ArrayList;


public class Main extends Application {
	private AnchorPane root;
	private Stage primaryStage;
	private String defalutPath;
	public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("QRdoc");
        if(System.getProperty("inp") != null){
        	defalutPath = System.getProperty("inp");
		}
        initRootLayout();
    }
	

	public void initRootLayout() {
		 try {
		 	FXMLLoader loader = new FXMLLoader();
		 	loader.setLocation(Main.class.getResource("view/main_view.fxml"));
			root = (AnchorPane)loader.load();
			 MainViewController controller = (MainViewController)loader.getController();
			 controller.setPrimaryStage(primaryStage);
			 if(System.getProperty("inp") != null) {
				 controller.setParameter(defalutPath);
			 }
			Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		System.out.println("test");
		String test = System.getProperty("inp");
		System.out.println(test);
		if(args.length > 0){
			System.out.println(args[0]);
		}
		launch(args);
	}
	
	
}
