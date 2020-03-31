package sample.fileio;

import java.io.File;
import java.nio.file.Path;

public class FileIO {

    private File encFile, decFile;

     /*===This method sets the Decrypted File===*/
    public void setDecFile(File newFile) {
        this.decFile = newFile;
    }

    /*===This method sets the Decrypted File based on a Path object to the file===*/
    public void setDecFile(Path path) {
        this.decFile = new File(path.toString());
    }

    /*===This method sets the Encrypted File based on the file passed===*/
    public void setEncFile(File newFile) {
        this.encFile = newFile;
    }

    /*===This method sets the Encrypted File using a Path object===*/
    public void setEncFile(Path path) {
        this.encFile = new File(path.toString());
    }

    /*===This method returns the File object corresponding to the Decrypted File===*/
    public File getDecFile() {
        return this.decFile;
    }

    /*===This method returns the File object corresponding to the Encrypted File===*/
    public File getEncFile() {
        return this.encFile;
    }

    /*===This method clears the File objects stored in this class to prevent potential modification from other sources.===*/
    public void cleanup() {
        this.decFile = null;
        this.encFile = null;
    }
}
