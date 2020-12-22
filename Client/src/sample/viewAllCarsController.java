package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class viewAllCarsController implements Initializable {
    Stage stage;
    @FXML
    public ListView listView;
    List<String> carInfo=new ArrayList<>();

    ClientManage client;

    public void setStage(Stage stage){
        this.stage=stage;
    }
    public void setClient(ClientManage client)
    {
        this.client=client;
    }

    public void setCarsInfoInListView()
    {
        client.info=new ArrayList<>();
        client.sendToServer("carsInfo/e");
        new Thread(()->{
            try {
                Thread.sleep(300);
                carInfo=new ArrayList<>();
                carInfo=client.info;
                Platform.runLater(()->{
                    listView.getItems().clear();
                });
                System.out.println("Carinfo size: "+carInfo.size());
                CreatePane createAPane=new CreatePane();
                for(String msg: carInfo){
                    String[] strings=msg.split("/");
                    AnchorPane pane=new AnchorPane();
                    pane=createAPane.createPane(strings[1],strings[2],strings[3],strings[4],strings[5],strings[6],strings[7],strings[8]);
                    AnchorPane finalPane = pane;
                    Platform.runLater(()->{
                        listView.getItems().add(finalPane);
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
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
    public void refreshButtonPressed(ActionEvent actionEvent){
        setCarsInfoInListView();
    }

    public void buyCarPressed(ActionEvent actionEvent){
        if(listView.getSelectionModel().getSelectedItem()==null){
            createAlert("Select a Car to Buy.");
        }
        else{
            int i=listView.getSelectionModel().getSelectedIndex();
            String inf=carInfo.get(i);
            String[] strings=inf.split("/");
            client.sendToServer("delete/"+strings[1]);
            new Thread(()->{
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            setCarsInfoInListView();
        }
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
