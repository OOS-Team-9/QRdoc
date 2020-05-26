package project.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.controller.FileStream;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectSaveViewController implements Initializable {
    FileStream fileStream;
    Stage selectSaveStage;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancleButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) ->{
            fileStream.saveDoc();
            closeStage();
        });

        cancleButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) ->{
            closeStage();
        });
    }
    void closeStage(){
        selectSaveStage.close();
    }

    public void setFileStream(FileStream fileStream){
        this.fileStream = fileStream;
    }

    public void setDialogStage(Stage selectSaveStage) {
        this.selectSaveStage = selectSaveStage;
    }
}
