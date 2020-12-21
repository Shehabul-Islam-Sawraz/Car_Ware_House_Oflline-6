package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        if((name.equals("") || name.equals(null)) && (pass.equals("") || pass.equals(null))){
            createAlert("Please enter your info to login.");
        }
        else{
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
                while (true){
                    if(client.mesg.equals("")){
                        continue;
                    }
                    else{
                        if(client.mesg.equals("login_successful")){
                            FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXMLS/manufacturerMenu.fxml"));
                            Parent root=loader.load();
                            manufacturerMenuController controller=loader.getController();
                            Stage primaryStage=new Stage();
                            controller.setClient(client);
                            primaryStage.setTitle("Manufacturer Menu");
                            primaryStage.setScene(new Scene(root));
                            controller.setStage(primaryStage);
                            stage.close();
                            primaryStage.show();
                            client.mesg="";
                            break;
                        }
                        else if(client.mesg.equals("invalid_pass")){
                            createAlert("Password is Invalid");
                            client.mesg="";
                            break;
                        }
                        else if(client.mesg.equals("no_manufacturer")){
                            createAlert("No manufacturer or user with this name");
                            client.mesg="";
                            break;
                        }
                    }
                }
            }
        }
    }
    public void setClient(ClientManage client)
    {
        this.client=client;
    }
    public void setStage(Stage stage) { this.stage=stage; }

    private void createAlert(String message) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText(message);
            a.show();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
