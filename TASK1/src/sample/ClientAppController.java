package sample;

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

import com.google.common.base.Stopwatch;
import javafx.animation.Timeline;
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
    @FXML private Button chooseDirButton;
    @FXML private Label searchDurationLabel;
//    @FXML private TableView<?> outputTable;
//    @FXML private TableColumn<?, ?> folderListColumn;
//    @FXML private TableColumn<?, ?> itemsFoundColumn;

    private void walk(String path) {

        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null)
            return;

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
                //Using google stopwatch (jar file and import needed)
                Stopwatch stopwatch = Stopwatch.createUnstarted();
                stopwatch.start();

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


                //----------------TIMER STOP---------------------------------------
                stopwatch.stop();
                System.out.println("Time elapsed: " + stopwatch);
                //Error when trying to display elapsed time onto GUI
                searchDurationLabel.setText(stopwatch.toString());
            }).start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}