package sample;

import javafx.application.Platform;
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

public class viewerMenuController implements Initializable {
    @FXML
    Button viewAll,searchReg,searchModel;
    Stage stage;
    ClientManage client;
    public void viewAllPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../FXMLS/viewAllCars.fxml"));
        Parent root=loader.load();
        viewAllCarsController controller=loader.getController();
        Stage primaryStage=new Stage();
        controller.setClient(client);
        primaryStage.setTitle("All Cars Information");
        primaryStage.setScene(new Scene(root));
        controller.setStage(primaryStage);
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Stage is closing");
            client.stop();
            Platform.exit();
        });
        stage.close();
        controller.setCarsInfoInListView();
        primaryStage.show();
    }

    public void searchRegPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../FXMLS/search_Reg.fxml"));
        Parent root=loader.load();
        searchRegController controller=loader.getController();
        Stage primaryStage=new Stage();
        controller.setClient(client);
        primaryStage.setTitle("Search By Registration");
        primaryStage.setScene(new Scene(root));
        controller.setStage(primaryStage);
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Stage is closing");
            client.stop();
            Platform.exit();
        });
        stage.close();
        primaryStage.show();
    }

    public void searchModelPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../FXMLS/searchMakeModel.fxml"));
        Parent root=loader.load();
        searchMakeModelController controller=loader.getController();
        Stage primaryStage=new Stage();
        controller.setClient(client);
        primaryStage.setTitle("Search By Make & Model");
        primaryStage.setScene(new Scene(root));
        controller.setStage(primaryStage);
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Stage is closing");
            client.stop();
            Platform.exit();
        });
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
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Stage is closing");
            client.stop();
            Platform.exit();
        });
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
