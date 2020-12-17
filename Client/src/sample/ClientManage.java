package sample;

import javafx.application.Platform;
import javafx.scene.control.Alert;

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

    private void handleMessage(String message){
        String[] strings=message.split("/");
        switch (strings[0]) {
            case "login":
                if(strings[1].equals("Successful")){
                    //login successful hoile onno screen e jabe
                    //Ekta information alert banaite hbe keita show korbe successfully logged in
                }
                else{
                    if(strings[1].equals("Invalid_Pass")){
                        createAlert("Password is Invalid");
                    }
                    else if(strings[1].equals("No_Manufacturer")){
                        createAlert("No manufacturer or user with this name");
                    }
                }
                break;

            case "update":
                if(strings[1].equals("Successful")){
                    //Updated er screen show korbe
                    info=new ArrayList<>();
                    sendToServer("carsInfo/e");
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
                if(strings[1].equals("Reg_Not_found")){
                    createAlert("Can't Find Any Car With the Registration Number");
                }
                else{
                    //Car Info show korbe oi reg number er
                }
                break;

            case "searchModel":
                if(strings[1].equals("Model_Not_found")){
                    createAlert("Can't Find Any Car With the Given Make and Model Number");
                }
                else{
                    //Car info show korbe oi make and model er
                }
                break;

            case "delete":
                if(strings[1].equals("Successful")){
                    //Ekta information alert show korbe j Deleted successfully
                    info=new ArrayList<>();
                    sendToServer("carsInfo/e");
                }
                else{
                    createAlert("Can't Delete the Car Successfully");
                }
                break;
        }
    }

    private void createAlert(String message) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText(message);
            a.show();
        });
    }
}
