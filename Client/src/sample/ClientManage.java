package sample;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import javax.swing.plaf.TableHeaderUI;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientManage implements Runnable {
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    Thread t;
    public List<String> info=new ArrayList<>();
    String mesg="";
    manufacturerAllCarsController controller;
    searchMakeModelController controller2;

    ClientManage(){}

    public boolean start()
    {
        try{
            socket=new Socket("localhost",5000);
            dis=new DataInputStream(socket.getInputStream());
            dos=new DataOutputStream(socket.getOutputStream());
            t=new Thread(this,"Client Manager");
            t.start();
            sendToServer("carsInfo/e");
            return true;
        } catch(Exception e) {
            System.out.println("Client failed to connect to the Server.");
            return false;
        }
    }
    public boolean stop() {
        try{
            dis.close();
            dos.close();
            socket.close();
            return true;
        } catch(Exception e) {
            System.out.println("Failed to close the client Thread.");
            return false;
        }
    }

    public boolean sendToServer(String message) {
        try {
            dos.writeUTF(message);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = dis.readUTF();
                System.out.println("Message received: " + msg);
                String[] string=msg.split("/");
                if(string[0].equals("cars")){
                    info.add(msg);
                    System.out.println("Info added successfully.");
                    System.out.println("Size of list: "+info.size());
                }
                else{
                    handleMessage(msg);
                }
            } catch (IOException e) {
                System.out.println("Server Closed!");
                break;
            }
        }
    }

    public void handleMessage(String message){
        String[] strings=message.split("/");
        switch (strings[0]) {
            case "login":
                if(strings[1].equals("Successful")){
                    //login successful hoile onno screen e jabe
                    //Ekta information alert banaite hbe keita show korbe successfully logged in
                    mesg="login_successful";
                    Platform.runLater(() -> {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText("Successfully logged in as Manufacturer");
                        a.show();
                    });
                }
                else{
                    if(strings[1].equals("Invalid_Pass")){
                        //createAlert("Password is Invalid");
                        mesg="invalid_pass";
                    }
                    else if(strings[1].equals("No_Manufacturer")){
                        //createAlert("No manufacturer or user with this name");
                        mesg="no_manufacturer";
                    }
                }
                break;

            case "update":
                if(strings[1].equals("Successful")){
                    //Updated er screen show korbe
                    info=new ArrayList<>();
                    /*sendToServer("carsInfo/e");
                    new Thread(()->{
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });*/
                    controller.setCarsInfoInListView();
                }
                else if(strings[1].equals("Unsuccessful")){
                    createAlert("Update Unsuccessful");
                }
                break;

            case "addCar":
                if(strings[1].equals("Successful")){
                    //Add korar screen show korbe
                    info=new ArrayList<>();
                    sendToServer("carsInfo/e");
                }
                else if(strings[1].equals("Unsuccessful")){
                    createAlert("Can't Add the Car Successfully");
                }
                break;

            case "searchReg":
                if(strings[1].equals("Reg_Not_Found")){
                    //createAlert("Can't Find Any Car With the Registration Number");
                    mesg="Car_Not_Found";
                    System.out.println("Mesg set to: "+mesg);
                }
                else{
                    //Car Info show korbe oi reg number er
                    mesg=message;
                    System.out.println("Mesg set to: "+mesg);
                }
                break;

            case "searchModel":
                if(strings[1].equals("Model_Not_Found")){
                    //createAlert("Can't Find Any Car With the Given Make and Model Number");
                    mesg="Model_Not_Found";
                    controller2.createAlert("Can't Find Any Car With the Given Make and Model Number");
                    controller2.carMake.setText("");
                    controller2.carModel.setText("");
                    mesg="";
                }
                else{
                    controller2.carMake.setText("");
                    controller2.carModel.setText("");
                    //Car info show korbe oi make and model er
                    mesg=message;
                    Platform.runLater(()->{
                        controller2.addToListView(message);
                    });
                    mesg="";
                }
                break;

            case "delete":
                if(strings[1].equals("Successful")){
                    //Ekta information alert show korbe j Deleted successfully
                    info=new ArrayList<>();
                    //sendToServer("carsInfo/e");
                }
                else{
                    createAlert("Can't Delete the Car Successfully");
                }
                break;
        }
    }

    public void setController(manufacturerAllCarsController controller){
        this.controller=controller;
    }
    public void setController2(searchMakeModelController controller){
        this.controller2=controller;
    }

    public String getMesg(){
        return mesg;
    }

    private void createAlert(String message) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText(message);
            a.show();
        });
    }
}
