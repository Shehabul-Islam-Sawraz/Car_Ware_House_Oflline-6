package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class manufacturerEditController implements Initializable {
    ClientManage client;
    Stage stage;
    manufacturerAllCarsController controller;
    @FXML
    public TextField reg,madeYear,colour1,colour2,colour3,makeCar,modelCar,price;
    public void saveButtonPressed(ActionEvent actionEvent){
        String regNo=reg.getText();
        String yearMade=madeYear.getText();
        String color1=colour1.getText(),color2=colour2.getText(),color3=colour3.getText();
        String carMake=makeCar.getText(),carModel=modelCar.getText();
        String prc=price.getText();
        if(colour1.getText().isEmpty() || colour1.getText()==null){
            color1="null";
        }
        else{
            color1=color1.toUpperCase();
        }
        if(colour2.getText().isEmpty() || colour2.getText()==null){
            color2="null";
        }
        else{
            color2=color2.toUpperCase();
        }
        if(colour3.getText().isEmpty() || colour3.getText()==null){
            color3="null";
        }
        else{
            color3=color3.toUpperCase();
        }
        if(reg.getText().isEmpty() || reg.getText()==null || madeYear.getText().isEmpty() || madeYear.getText()==null || makeCar.getText().isEmpty() ||
                makeCar.getText()==null || modelCar.getText().isEmpty() || modelCar.getText()==null || price.getText().isEmpty() || price.getText()==null){
            createAlert("Please enter all the info.");
        }
        if(!validity_check(yearMade,prc)){

        }
        else{
            regNo=regNo.toUpperCase();carMake=carMake.toUpperCase();carModel=carModel.toUpperCase();
            String inf=regNo+"/"+yearMade+"/"+color1+"/"+color2+"/"+color3+"/"+carMake+"/"+carModel+"/"+prc;
            client.sendToServer("update/"+inf);
            new Thread(()->{
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        stage.close();
        return;
    }

    private boolean validity_check(String year,String pc){
        try{
            int made=Integer.parseInt(year);
            int pri=Integer.parseInt(pc);
        }
        catch (Exception e){
            createAlert("Please give a integer value for Made In & Price segment.");
            return false;
        }
        return true;
    }

    public void setClient(ClientManage client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setController(manufacturerAllCarsController controller){
        this.controller=controller;
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
