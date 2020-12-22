package sample;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CreatePane {
    public CreatePane(){}
    public static String SOURCE_PATH="Resources//";
    public AnchorPane createPane(String regi,String yearmade,String colour1,String colour2,String colour3,String make,String model,String prc){
        AnchorPane anchorPane=new AnchorPane();
        anchorPane.setPrefHeight(170);
        anchorPane.setPrefWidth(390);
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
        yearMade.setLayoutY(93);
        yearMade.setFont(new Font("System Bold",15));
        yearMade.setText("Made In: "+yearmade);

        Label reg=new Label();
        reg.setLayoutX(22);
        reg.setLayoutY(130);
        reg.setFont(new Font("System Bold",15));
        reg.setText("Reg. No.:"+regi);

        Label price=new Label();
        price.setLayoutX(232);
        price.setLayoutY(109);
        price.setFont(new Font("System Bold Italic",13));
        price.setText("Price: "+prc);

        Circle color1=new Circle();
        color1.setLayoutX(270);
        color1.setLayoutY(150);
        color1.setRadius(8);
        if(colour1.equals("null")){
            color1.setFill(Color.WHITE);
        }
        else{
            color1.setFill(Color.valueOf(colour1));
        }
        color1.setStroke(Color.WHITE);

        Circle color2=new Circle();
        color2.setLayoutX(290);
        color2.setLayoutY(150);
        color2.setRadius(8);
        if(colour2.equals("null")){
            color2.setFill(Color.WHITE);
        }
        else{
            color2.setFill(Color.valueOf(colour2));
        }
        color2.setStroke(Color.WHITE);

        Circle color3=new Circle();
        color3.setLayoutX(310);
        color3.setLayoutY(150);
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
        image.setLayoutX(222);
        image.setLayoutY(22);
        image.setPreserveRatio(true);
        image.setPickOnBounds(true);

        Group group=new Group();
        group.getChildren().addAll(carMake,carModel,yearMade,price,reg,color1,color2,color3,image);
        anchorPane.getChildren().addAll(group);
        anchorPane.setPadding(new Insets(0,0,10,0));
        return anchorPane;
    }
}
