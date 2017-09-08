package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LoginController {
    private final String login = "1234";
    private final String password = "1234";

    private List<User> usersList = new ArrayList<>();

    @FXML
    private TabsController tabsController;

    private RegisterController registerController;// = new RegisterController(this);

    @FXML
    TextField txtLogin;
    @FXML
    TextField txtPassword;

    @FXML
    Button btnLogin;

    @FXML
    Button register;

    public void addUser(User u){
        this.usersList.add(u);
    }

    @FXML
    private void CheckLogin(ActionEvent e) {
        boolean logged = false;
        for (User user :
                this.usersList) {
            if (txtLogin.getText().compareTo(user.getfName()) == 0 && txtPassword.getText().compareTo(user.getfPassword()) == 0){
                logged = true;
                break;
            }

        }
        if(logged){
            Stage stage = new Stage();
            Parent root;
            try {
                ((Node) e.getSource()).getScene().getWindow().hide();
                //root = FXMLLoader.load(getClass().getResource("main.fxml"));
                //registerController = new RegisterController(this);
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "main.fxml"
                        )
                );
               // loader.setController(registerController);

                stage.setTitle("Hello World");
                stage.setScene(new Scene(loader.load()));
                stage.show();
            } catch (IOException ex) {
               showStack(ex);
            }
        } else {
            Alert loginAlert = new Alert(Alert.AlertType.ERROR);
            loginAlert.setTitle("Error");
            loginAlert.setContentText("Incorrect login or password.");
            loginAlert.showAndWait();
        }
    }

    @FXML
    private void ShowRegister(ActionEvent e) {

        Stage stage = new Stage();
        Parent root;
        try {
            //((Node) e.getSource()).getScene().getWindow().hide();
            //root = FXMLLoader.load(getClass().getResource("register.fxml"));
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "register.fxml"
                    )
            );
            stage.setTitle("Hello World");

            stage.setScene(new Scene(loader.load()));
            registerController = loader.<RegisterController>getController();
            registerController.initialize(this);
            registerController.setUsersList(usersList);
            stage.show();
        } catch (IOException ex) {
            showStack(ex);
        }
    }


    private void showStack(IOException ex){
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        printWriter.flush();

        String stackTrace = writer.toString();
        System.out.print(stackTrace);
    }

    @FXML
    private void initialize(){
        System.out.print("d");
    }



}
