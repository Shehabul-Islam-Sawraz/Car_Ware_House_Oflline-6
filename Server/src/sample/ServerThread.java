package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread implements Runnable{
    Socket s;
    Thread t;
    DataInputStream dis;
    DataOutputStream dos;
    MessageHandler handler;

    public ServerThread(Socket s,MessageHandler handler)
    {
        this.s=s;
        this.handler=handler;
        t=new Thread(this,"Server Thread");
        try {
            dis=new DataInputStream(s.getInputStream());
            dos=new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            System.out.println("Failed to create input & output stream in Server.");
        }
    }

    public boolean start() {
        try{
            t.start();
            return true;
        } catch(Exception e) {
            System.out.println("Failed to start thread in server for the client.");
            return false;
        }
    }

    @Override
    public void run() {
        try {
            while (true){
                String msg=dis.readUTF();
                String[] strings=msg.split("/");
                if(strings[0].equals("login")){
                    int i=handler.handleLogIn(msg);
                    if(i==1){
                        sendToClient("login/Successful");
                    }
                    else if(i==0){
                        sendToClient("login/Invalid_Pass");
                    }
                    else if(i==2){
                        sendToClient("login/No_Manufacturer");
                    }
                }
                else if(strings[0].equals("update")){
                    if(handler.handleUpdate(msg)){
                        sendToClient("update/Successful");
                    }
                    else{
                        sendToClient("update/Unsuccessful");
                    }
                }
                else if(strings[0].equals("addCar")){
                    if(handler.addCarInfo(msg)){
                        sendToClient("addCar/Successful");
                    }
                    else{
                        sendToClient("addCar/Unsuccessful");
                    }
                }
                else if(strings[0].equals("searchReg")){
                    String carInfo=handler.searchCarByReg(msg);
                    if(!carInfo.equals("null")){
                        sendToClient("searchReg/"+carInfo);
                    }
                    else{
                        sendToClient("searchReg/Reg_Not_Found");
                    }
                }
                else if(strings[0].equals("searchModel")){
                    String carInfo=handler.searchCarByMakeModel(msg);
                    if(!carInfo.equals(null)){
                        sendToClient("searchModel/"+carInfo);
                    }
                    else{
                        sendToClient("searchModel/Model_Not_Found");
                    }
                }
                else if(strings[0].equals("delete")){
                    if(handler.deleteCar(msg)){
                        sendToClient("delete/Successful");
                    }
                    else{
                        sendToClient("delete/Unsuccessful");
                    }
                }
                else if(strings[0].equals("carsInfo")){
                    List<String> cars=new ArrayList<>();
                    cars=handler.getCarsInfo();
                    for(String message:cars){
                        sendToClient("cars/"+message);
                    }
                }
            }
        } catch(Exception e) {
            System.out.println("Manufacturer or user has left.");
        } finally {
            try{
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendToClient(String msg){
        try {
            dos.writeUTF(msg);
            System.out.println("Message sent to client side: "+msg);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
