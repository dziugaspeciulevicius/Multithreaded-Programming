package sample.UIMain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        /*================================OPENING INTRO WINDOW================================*/
        Parent encodeDecodeWindow = FXMLLoader.load(getClass().getResource("encodeDecode.fxml"));
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        window.setScene(new Scene(encodeDecodeWindow));
        window.setResizable(false);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
