package com.MB.hmt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class HTMViewController {
    @FXML
    private Button txtButton;
    public static ArrayList<String> QA = new ArrayList<>();


    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File txt = fileChooser.showOpenDialog(txtButton.getScene().getWindow());

        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(txt));
            while ((line = reader.readLine()) != null) {
                QA.add(line);
            }
            System.out.println("OPENED FILE");
            System.out.println(QA);
            FXMLLoader loader = new FXMLLoader(HTM.class.getResource("HTM-test.fxml"));
            Stage stage = (Stage) txtButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<String> getQA() {
        return QA;
    }

}
