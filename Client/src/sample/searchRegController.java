package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class searchRegController implements Initializable {
    @FXML
    TextField regNo;
    @FXML
    Button search;
    @FXML
    ListView listView;

    ClientManage client;
    Stage stage;

    public void searchButtonPressed(ActionEvent actionEvent){
        String reg=regNo.getText();
        if(reg.equals("") || reg.equals(null)){
            createAlert("Enter a registration number.");
        }
        else{
            reg=reg.toUpperCase();
            client.sendToServer("searchReg/"+reg);
            while(true){
                if(client.getMesg().equals("")){
                    continue;
                }
                else{
                    if(client.getMesg().equals("Car_Not_Found")){
                        createAlert("Can't Find Any Car With the Registration Number");
                        regNo.setText("");
                        client.mesg="";
                    }
                    else{
                        regNo.setText("");
                        if(!listView.getItems().isEmpty()){
                            listView.getItems().remove(0);
                        }
                        String info=client.getMesg();
                        String[] strings=info.split("/");
                        AnchorPane anchorPane=new AnchorPane();
                        CreatePane pane=new CreatePane();
                        anchorPane=pane.createPane(strings[1],strings[2],strings[3],strings[4],strings[5],strings[6],strings[7],strings[8]);
                        listView.getItems().add(anchorPane);
                        client.mesg="";
                    }
                    break;
                }
            }
        }
    }

    public void backButtonPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXMLS/viewer_Menu.fxml"));
        Parent root=loader.load();
        viewerMenuController controller=loader.getController();
        controller.setClient(client);
        Stage primaryStage=new Stage();
        primaryStage.setTitle("Viewer Menu");
        primaryStage.setScene(new Scene(root));
        controller.setStage(primaryStage);
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Stage is closing");
            client.stop();
            Platform.exit();
        });
        primaryStage.show();
        stage.close();
    }
    public void setClient(ClientManage client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

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
