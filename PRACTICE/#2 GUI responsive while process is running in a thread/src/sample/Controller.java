package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class Controller {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private TextField input;

    @FXML void CalculateSum(ActionEvent event) {
        //Making calculation run in a thread so ui is not stuck
        new Thread(() -> {
            Long inputNumber = Long.parseLong(input.getText());
            Long sum = 0L;
            for (int i = 0; i < inputNumber; i++) {
                sum = sum + inputNumber;
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Sum = " + sum);
            alert.showAndWait();

        }).start();
    }

    @FXML
    void initialize() {
        assert input != null : "fx:id=\"input\" was not injected: check your FXML file 'sample.fxml'.";

    }
}
