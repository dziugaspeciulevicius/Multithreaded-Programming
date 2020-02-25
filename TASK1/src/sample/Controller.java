package sample;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Stopwatch;
import com.google.common.cache.CacheLoader;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import static java.lang.String.valueOf;


public class Controller implements Runnable {

    @FXML private AnchorPane anchorid;
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private TextField itemNameInput;
    @FXML private TextField folderNameInput;
    @FXML private Button searchFileButton;
    @FXML private Button chooseDirButton;
    @FXML private Label searchDurationLabel;
    @FXML private TextField searchDurationField;
    @FXML private TextArea outputArea;
    @FXML private TextArea realTimeOutput;
    @FXML private ProgressBar progressBar;

    //    Files.walk returns a stream that is lazily populated with Path by recursively walking
//    the file tree rooted at a given starting file. The file tree is traversed depth-first.
//    There are two overloaded Files.walk methods;
//    one of them takes the maxDepth parameter, which sets the maximum number of levels of directories to visit.
//
    private void walk(String path) {

        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null) {
            return;
        }

        for (File file : list) {
            if (file.isDirectory()) {
                walk(file.getAbsolutePath());
                System.out.println("Dir:" + file.getAbsoluteFile());
//                realTimeOutput.appendText(String.valueOf(file));
            }
//            else {
//                System.out.println( "File:" + file.getAbsoluteFile() );
//            }
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

                outputArea.clear();

                    Controller filewalker = new Controller();
                    filewalker.walk(folderNameInput.getText());

                    try (Stream<Path> walk = Files.walk(Paths.get(folderNameInput.getText()))) {

                        List<String> result = walk.map(x -> x.toString() + "\n\n")
                                .filter(f -> f.contains(itemNameInput.getText()))
                                .collect(Collectors.toList());

                        result.forEach(System.out::println);
                        outputArea.setText(valueOf(result));



                    } catch (CacheLoader.InvalidCacheLoadException e) {
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                //----------------TIMER STOP---------------------------------------
                stopwatch.stop();
                //Error when trying to display elapsed time onto GUI with a label, works with a text box
                searchDurationField.setText(valueOf(stopwatch));
                System.out.println("Time elapsed: " + stopwatch);

            }).start();

        } catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("File cannot be found");
            alert.setContentText("Please try again!");
            alert.showAndWait();
        }
    }

    @Override
    public void run() {
    }
}