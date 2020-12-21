package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXMLS/initialPage.fxml"));
        Parent root=loader.load();
        InitialController controller=loader.getController();
        ClientManage client=new ClientManage();
        if(client.start()){
            System.out.println("Client connected successfully.");
        }
        else{
            System.out.println("Client failed to connect.");
            return;
        }
        controller.setClient(client);
        primaryStage.setTitle("Car Ware House");
        primaryStage.setScene(new Scene(root));
        controller.setStage(primaryStage);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
