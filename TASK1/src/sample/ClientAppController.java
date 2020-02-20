package sample;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ClientAppController implements Runnable {

    @FXML private AnchorPane anchorid;
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private TextField itemNameInput;
    @FXML private TextField folderNameInput;
    @FXML private Button searchFileButton;
    @FXML private Label searchDurationLabel;
    @FXML private Button chooseDirButton;
    @FXML private TableView<?> outputTable;
    @FXML private TableColumn<?, ?> folderListColumn;
    @FXML private TableColumn<?, ?> itemsFoundColumn;
    
    @Override
    public void run() {

    }

    private void walk(String path) {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return;

        for ( File file : list ) {
            if ( file.isDirectory() ) {
                walk( file.getAbsolutePath() );
                //System.out.println( "Dir:" + file.getAbsoluteFile() );
            }
            else {
                //System.out.println( "File:" + file.getAbsoluteFile() );
            }
        }
    }

    public void ChooseDir(javafx.event.ActionEvent event) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) anchorid.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);

        if(file != null) {
            System.out.println("Path: " + file.getAbsolutePath());
            folderNameInput.setText(file.getAbsolutePath());
        }
    }

    public void SearchFile(javafx.event.ActionEvent event) {
        try {
            if (itemNameInput.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No input found for file name");
                alert.setContentText("Please fill file name field and try again!");
                alert.showAndWait();
                return;
            }
            if (folderNameInput.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No input found for directory");
                alert.setContentText("Please fill dir field and try again!");
                alert.showAndWait();
                return;
            }

            //making search run in a thread so UI is not stuck while search is happening
            new Thread(() -> {

                //----------------TIMER START---------------------------------------
                Instant start = Instant.now();
                //long start = System.nanoTime();
                //--------------------------------------------------------------------

                ClientAppController filewalker = new ClientAppController();
                filewalker.walk(folderNameInput.getText());

                try (Stream<Path> walk = Files.walk(Paths.get(folderNameInput.getText()))) {

                    List<String> result = walk.map(x -> x.toString())
                            .filter(f -> f.contains(itemNameInput.getText()))
                            .collect(Collectors.toList());

                    result.forEach(System.out::println);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Instant end = Instant.now();
                Duration timeElapsed = Duration.between(start, end);
                System.out.println("Time passed: " + timeElapsed);

                searchDurationLabel.setText(String.valueOf(timeElapsed));
                //long end = System.nanoTime();
                //long elapsedTime = end - start;
                //System.out.println(elapsedTime / 1000000000);
            }).start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}