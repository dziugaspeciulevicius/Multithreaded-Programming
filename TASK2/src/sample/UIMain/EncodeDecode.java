package sample.UIMain;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import capstone.encryption.Encryption;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.encryption.Decryption;
import sample.fileio.FileIO;

import javax.swing.*;

public class EncodeDecode {

    @FXML private Button addFilesButton;
    @FXML private Button encodeButton;
    @FXML private Button decodeButton;
    @FXML private TextField filePath;
    @FXML private TextArea infoTextArea;
    @FXML private ProgressBar actionProgressBar;
    @FXML private Text progressPercentage;
    @FXML private AnchorPane anchorid;

    static FileIO fio = new FileIO();

    /*==========================ENCODING==========================*/
    @FXML
    public void EncodeAction(ActionEvent event) throws FileNotFoundException {

        new Thread(() -> {
            String output;
            infoTextArea.appendText("\nAction Selected:   Encrypt File");
            String pw = getUserPassword();

            if (!pw.equals("")) {
                try {
                    actionProgressBar.setProgress(0.30);
                    progressPercentage.setText("30%");
                    Thread.sleep(100);
                    output = Encryption.encrypt(new File(filePath.getText()), pw);
                    actionProgressBar.setProgress(0.60);
                    progressPercentage.setText("60%");
                    Thread.sleep(100);
                    infoTextArea.appendText("\nEncrypting File...");
                    infoTextArea.appendText("\nEncryption Successful");
                    actionProgressBar.setProgress(0.90);
                    progressPercentage.setText("90%");
                    Thread.sleep(100);
                    infoTextArea.appendText("\nOutput File Name:  \n " + output);
                } catch (Exception ex) {
                    infoTextArea.appendText("\nFile Encryption Failed:  \n" + ex);
                }
            } else {
                infoTextArea.appendText("\nNo Password Entered\nFile Encryption Cancelled");
                filePath.setText("");
                cleanup();
            }

        cleanup();
        actionProgressBar.setProgress(1);
        progressPercentage.setText("100%");
        }).start();
    }


    /*==========================DECODING==========================*/
    @FXML
    void DecodeAction(ActionEvent event) {

        new Thread(() -> {

            String output;
            String pw = getUserPassword();
            infoTextArea.appendText("\nAction Selected:    Decrypt File:  " + fio.getEncFile().getName());

            if(!pw.equals("")){
                try {
                    actionProgressBar.setProgress(0.30);
                    progressPercentage.setText("30%");
                    Thread.sleep(100);
                    infoTextArea.appendText("\nDecrypting File...");
//                    output=Decryption.decrypt(new File(filePath.getText()), pw);
                    output = Decryption.decrypt(fio.getEncFile(),pw);
                    actionProgressBar.setProgress(0.60);
                    progressPercentage.setText("60%");
                    Thread.sleep(100);
                    infoTextArea.appendText("\nDecryption Successful");
                    actionProgressBar.setProgress(0.90);
                    progressPercentage.setText("90%");
                    Thread.sleep(100);
                    infoTextArea.appendText("\nOutput File Name:    \n" + output);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                }
            }else{
                infoTextArea.appendText("\nFile Decryption Cancelled");
                filePath.setText("");
                cleanup();
            }
            cleanup();
            actionProgressBar.setProgress(1);
            progressPercentage.setText("100%");
        }).start();
    }


//    FOR SOME REASON MY CUSTOM PASSWORD BOX DID NOT WORK,
//    SO FOUND A WORKAROUND ON THE INTERNET WITH USING SWING INSTREAD
    private static String getUserPassword() {

        JPasswordField pwd = new JPasswordField(15);
        String pw = "";
        int action = JOptionPane.showConfirmDialog(null, pwd, "Enter Password", JOptionPane.OK_CANCEL_OPTION);
        if (action < 0) {
            JOptionPane.showMessageDialog(null, "Cancelling ");
        } else {
            pw = new String(pwd.getPassword());
        }
        return pw;
    }

    /*==========================SELECTING FOLDER PATH TO ENCODE==========================*/
    @FXML
    void addFilesAction(ActionEvent event) {
        Path path = Paths.get(filePath.getText()).toAbsolutePath();

        final FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) anchorid.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if(file != null) {
            System.out.println("Path: " + file.getAbsolutePath());
            filePath.appendText(file.getAbsolutePath());
            infoTextArea.appendText("\n Selected File Path: " + file.toString());
        }

    }

    /*==========================FIELD CLEANUP==========================*/
    private void cleanup() {
        filePath.setText("");
        fio.cleanup();
    }
}
