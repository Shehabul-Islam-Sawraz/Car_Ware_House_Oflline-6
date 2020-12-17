package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InitialController implements Initializable {
    @FXML
    AnchorPane anchorPane;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Button reset,login;

    ClientManage client;
    Stage stage;

    public void onResetPressed(ActionEvent actionEvent){
        username.clear();
        password.clear();
    }
    public void onLoginPressed(ActionEvent actionEvent) throws IOException {
        String name=username.getText();
        String pass=password.getText();
        username.clear();
        password.clear();
        if(name.equalsIgnoreCase("viewer") && pass.equals("")){
            //Viewer er kase jabe.
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXMLS/viewer_Menu.fxml"));
            Parent root=loader.load();
            viewerMenuController controller=loader.getController();
            Stage primaryStage=new Stage();
            controller.setClient(client);
            primaryStage.setTitle("Viewer Menu");
            primaryStage.setScene(new Scene(root));
            controller.setStage(primaryStage);
            stage.close();
            primaryStage.show();
        }
        else{
            client.sendToServer("login/"+name+"/"+pass);
        }
    }
    public void setClient(ClientManage client)
    {
        this.client=client;
    }
    public void setStage(Stage stage) { this.stage=stage; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
