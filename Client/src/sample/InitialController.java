package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class InitialController {
    @FXML
    AnchorPane anchorPane;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Button reset,login;

    ClientManage client;

    public void onResetPressed(ActionEvent actionEvent){
        username.clear();
        password.clear();
    }
    public void onLoginPresses(ActionEvent actionEvent){

    }
    public void setClient(ClientManage client)
    {
        this.client=client;
    }
}
