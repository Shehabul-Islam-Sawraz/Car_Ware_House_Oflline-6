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
import javafx.scene.control.*;
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
import java.util.List;
import java.util.ResourceBundle;

public class searchMakeModelController implements Initializable {
    @FXML
    Button search;
    @FXML
    public TextField carMake,carModel;
    @FXML
    ListView listView;

    ClientManage client;
    Stage stage;
    public static String SOURCE_PATH="Resources//";

    public void searchButtonPressed(ActionEvent actionEvent){
        String make=carMake.getText();
        String model=carModel.getText();
        if(make.equals("") || model.equals("") || make.equals(null) || model.equals(null)){
            createAlert("Enter both Make and Model information.");
        }
        else{
            model=model.toUpperCase();
            make=make.toUpperCase();
            client.sendToServer("searchModel/"+make+"/"+model);
            client.setController2(this);
            if(!listView.getItems().isEmpty()){
                listView.getItems().clear();
            }
            /*while(true){
                if(client.getMesg().equals("")){
                    continue;
                }
                else{
                    if(client.getMesg().equals("Model_Not_Found")){
                        createAlert("Can't Find Any Car With the Given Make and Model Number");
                        carMake.setText("");
                        carModel.setText("");
                        client.mesg="";
                    }
                    else{
                        carMake.setText("");
                        carModel.setText("");
                        while(true){
                            System.out.println("Gari show kortase.");
                            String info=client.getMesg();
                            String[] strings=info.split("/");
                            AnchorPane anchorPane=new AnchorPane();
                            anchorPane=createPane(strings[1],strings[2],strings[3],strings[4],strings[5],strings[6],strings[7],strings[8]);
                            listView.getItems().add(anchorPane);

                            if(client.mesg.equals(info)){
                                client.mesg="";
                                break;
                            }
                            else{
                                continue;
                            }
                        }
                        break;
                    }
                }
            }*/
        }
    }

    public void addToListView(String m){
        String info=m;
        String[] strings=info.split("/");
        AnchorPane anchorPane=new AnchorPane();
        anchorPane=createPane(strings[1],strings[2],strings[3],strings[4],strings[5],strings[6],strings[7],strings[8]);
        listView.getItems().add(anchorPane);
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
        primaryStage.show();
        stage.close();
    }

    private AnchorPane createPane(String regi, String yearmade, String colour1, String colour2, String colour3, String make, String model, String prc){
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

    public void setClient(ClientManage client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void createAlert(String message) {
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
