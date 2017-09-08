package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class RegisterController {

    @FXML TextField fName;
    @FXML TextField fPassword;
    @FXML TextField fAge;
    @FXML TextField fGender;
    @FXML TextField fAddress;

    LoginController lo = null;
    User user = new User();


    List<User> usersList;
    public void setUsersList(List<User> us){
        this.usersList = us;
    }

    @FXML public void doSave(){
        int error=0;
        if(user.getfName().length() == 0) error++;
        if(user.getfAge().length() == 0) error++;
        if(user.getfGender().length() == 0) error++;
        if(user.getfPassword().length() == 0) error++;
        if(user.getfAddress().length() == 0) error++;
        if(error==0)
            this.usersList.add(user);

    }
    @FXML public void doClear(){
        fName.setText("");
        fPassword.setText("");
        fAge.setText("");
        fGender.setText("");
        fAddress.setText("");
    }
    @FXML public void doCancel(ActionEvent e){
        Stage stage = new Stage();
        Parent root;

            ((Node) e.getSource()).getScene().getWindow().hide();
            /*root = FXMLLoader.load(getClass().getResource("loginForm.fxml"));
            stage.setTitle("Hello World");
            stage.setScene(new Scene(root));
            stage.show();*/

    }

    public void init(){

    }

    public void initialize(LoginController lo){
        this.lo = lo;
        user.fNameProperty().bind(fName.textProperty());
        user.fPasswordProperty().bind(fPassword.textProperty());
        user.fAgeProperty().bind(fAge.textProperty());
        user.fGenderProperty().bind(fGender.textProperty());
        user.fAddressProperty().bind(fAddress.textProperty());


    }


}
