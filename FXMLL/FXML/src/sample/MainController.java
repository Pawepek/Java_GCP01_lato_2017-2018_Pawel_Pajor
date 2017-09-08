package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.lang.management.PlatformLoggingMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainController {

    @FXML
    private TabsController tabsController;// = new TabsController();
    @FXML
    private TabPane tabs;


    @FXML
    public void initialize(){

    }

    public void addStudent(){
        tabsController.addStudent();
    }
    public void removeStudent(){ tabsController.removeStudent(); }

    @FXML
    private void closeProgram(){
        Platform.exit();
    }
    @FXML
    private void about(){
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setTitle("About");
        aboutAlert.setHeaderText("Crawler");
        aboutAlert.setContentText("Author: Piotr Nosek");
        aboutAlert.showAndWait();
    }


}
