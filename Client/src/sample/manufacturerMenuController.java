package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class manufacturerMenuController implements Initializable {
    @FXML
    Button viewAll,addCar;
    Stage stage;
    ClientManage client;

    public void viewAllButtonPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../FXMLS/manufacturerAllCars.fxml"));
        Parent root=loader.load();
        manufacturerAllCarsController controller=loader.getController();
        Stage primaryStage=new Stage();
        controller.setClient(client);
        client.setController(controller);
        primaryStage.setTitle("All Cars Information");
        primaryStage.setScene(new Scene(root));
        controller.setStage(primaryStage);
        stage.close();
        controller.setCarsInfoInListView();
        primaryStage.show();
    }

    public void addButtonPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../FXMLS/addCarManufacturer.fxml"));
        Parent root=loader.load();
        addCarController controller=loader.getController();
        Stage primaryStage=new Stage();
        controller.setClient(client);
        primaryStage.setTitle("Edit Car Information");
        primaryStage.setScene(new Scene(root));
        controller.setStage(primaryStage);
        stage.close();
        primaryStage.show();
    }

    public void logOutPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXMLS/initialPage.fxml"));
        Parent root=loader.load();
        InitialController controller=loader.getController();
        controller.setClient(client);
        Stage primaryStage=new Stage();
        primaryStage.setTitle("Car Ware House");
        primaryStage.setScene(new Scene(root));
        controller.setStage(primaryStage);
        primaryStage.show();
        stage.close();
    }

    public void setStage(Stage stage){
        this.stage=stage;
    }
    public void setClient(ClientManage client)
    {
        this.client=client;
        System.out.println("Size of array in menuController is: "+client.info.size());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
