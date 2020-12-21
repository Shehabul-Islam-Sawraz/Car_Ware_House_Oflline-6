package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class manufacturerAllCarsController implements Initializable {
    public static String SOURCE_PATH="Resources//";
    List<String> carInfo=new ArrayList<>();
    //public String msg="";
    Stage stage;
    @FXML
    public ListView listView;
    @FXML
    public Button edit,delete;

    ClientManage client;

    private AnchorPane createPane(String regi,String yearmade,String colour1,String colour2,String colour3,String make,String model,String prc){
        AnchorPane anchorPane=new AnchorPane();
        anchorPane.setPrefHeight(170);
        anchorPane.setPrefWidth(320);
        Label carMake=new Label();
        carMake.setLayoutX(22);
        carMake.setLayoutY(17);
        carMake.setFont(new Font("System Bold",15));
        carMake.setText("Car Make: "+make);

        Label carModel=new Label();
        carModel.setLayoutX(22);
        carModel.setLayoutY(52);
        carModel.setFont(new Font("System Bold",15));
        carModel.setText("Car Model: "+model);

        Label yearMade=new Label();
        yearMade.setLayoutX(22);
        yearMade.setLayoutY(87);
        yearMade.setFont(new Font("System Bold",15));
        yearMade.setText("Made In: "+yearmade);

        Label reg=new Label();
        reg.setLayoutX(22);
        reg.setLayoutY(122);
        reg.setFont(new Font("System Bold",15));
        reg.setText("Reg. No.:"+regi);

        Label price=new Label();
        price.setLayoutX(185);
        price.setLayoutY(109);
        price.setFont(new Font("System Bold Italic",13));
        price.setText("Price: "+prc);

        Circle color1=new Circle();
        color1.setLayoutX(217);
        color1.setLayoutY(140);
        color1.setRadius(8);
        if(colour1.equals("null")){
            color1.setFill(Color.WHITE);
        }
        else{
            color1.setFill(Color.valueOf(colour1));
        }
        color1.setStroke(Color.WHITE);

        Circle color2=new Circle();
        color2.setLayoutX(237);
        color2.setLayoutY(140);
        color2.setRadius(8);
        if(colour2.equals("null")){
            color2.setFill(Color.WHITE);
        }
        else{
            color2.setFill(Color.valueOf(colour2));
        }
        color2.setStroke(Color.WHITE);

        Circle color3=new Circle();
        color3.setLayoutX(257);
        color3.setLayoutY(140);
        color3.setRadius(8);
        if(colour3.equals("null")){
            color3.setFill(Color.WHITE);
        }
        else{
            color3.setFill(Color.valueOf(colour3));
        }
        color3.setStroke(Color.WHITE);

        String file_path=SOURCE_PATH+"car.png";
        Image img= null;
        try {
            img = new Image(new FileInputStream(file_path));
        } catch (FileNotFoundException e) {
            System.out.println("Can't file image of the car to show in ImageView.");
        }
        ImageView image=new ImageView(img);
        image.setFitHeight(84);
        image.setFitWidth(137);
        image.setLayoutX(170);
        image.setLayoutY(21);
        image.setPreserveRatio(true);
        image.setPickOnBounds(true);

        Group group=new Group();
        group.getChildren().addAll(carMake,carModel,yearMade,price,reg,color1,color2,color3,image);
        anchorPane.getChildren().addAll(group);
        anchorPane.setPadding(new Insets(0,0,10,0));
        return anchorPane;
    }

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
                Thread.sleep(200);
                carInfo=new ArrayList<>();
                carInfo=client.info;
                Platform.runLater(()->{
                    listView.getItems().clear();
                });
                System.out.println("Carinfo size: "+carInfo.size());
                for(String msg: carInfo){
                    String[] strings=msg.split("/");
                    AnchorPane pane=new AnchorPane();
                    pane=createPane(strings[1],strings[2],strings[3],strings[4],strings[5],strings[6],strings[7],strings[8]);
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
    public void editButtonPressed(ActionEvent actionEvent) throws IOException {
        if(listView.getSelectionModel().getSelectedItem()==null){
            createAlert("Select a Car to edit it's information.");
        }
        else{
            int i=listView.getSelectionModel().getSelectedIndex();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../FXMLS/manufacturerEditCar.fxml"));
            Parent root=loader.load();
            manufacturerEditController controller=loader.getController();
            Stage primaryStage=new Stage();
            controller.setClient(client);
            primaryStage.setTitle("Edit Car Information");
            primaryStage.setScene(new Scene(root));
            controller.setStage(primaryStage);
            controller.setController(this);
            String inf=carInfo.get(i);
            String[] strings=inf.split("/");
            controller.reg.setText(strings[1]);controller.madeYear.setText(strings[2]);
            if(strings[3].equals("null")){
                controller.colour1.setText("");
            }
            else{
                controller.colour1.setText(strings[3]);
            }
            if(strings[4].equals("null")){
                controller.colour2.setText("");
            }
            else{
                controller.colour2.setText(strings[4]);
            }
            if(strings[5].equals("null")){
                controller.colour3.setText("");
            }
            else{
                controller.colour3.setText(strings[5]);
            }
            controller.makeCar.setText(strings[6]);controller.modelCar.setText(strings[7]);controller.price.setText(strings[8]);
            controller.reg.setDisable(true);
            primaryStage.show();
            /*new Thread(()->{
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });*/
            //setCarsInfoInListView();
        }
    }
    public void deleteButtonPressed(ActionEvent actionEvent){
        if(listView.getSelectionModel().getSelectedItem()==null){
            createAlert("Select a Car to delete.");
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

    public void backButtonPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXMLS/manufacturerMenu.fxml"));
        Parent root=loader.load();
        manufacturerMenuController controller=loader.getController();
        controller.setClient(client);
        Stage primaryStage=new Stage();
        primaryStage.setTitle("Manufacturer Menu");
        primaryStage.setScene(new Scene(root));
        controller.setStage(primaryStage);
        primaryStage.show();
        stage.close();
    }

    public void refreshButtonPressed(ActionEvent actionEvent){
        setCarsInfoInListView();
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
