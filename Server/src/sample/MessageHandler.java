package sample;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MessageHandler {
    DatabaseManager ds=new DatabaseManager();
    public MessageHandler(){
        ds.open();
    }
    public int handleLogIn(String message){
        String[] strings=message.split("/");
        if(strings[0].equals("login")){
            if(ds.getManufacturerByName(strings[1])){
                if(ds.AccountValidityCheck(strings[1],strings[2])){
                    return 1;
                }
                else{
                    return 0;
                }
            }
            else {
                return 2;
            }
        }
        return 0;
    }
    public boolean handleUpdate(String message){
        String[] strings=message.split("/");
        if(ds.updateCarInfoByRegNum(Integer.parseInt(strings[2]),strings[3],strings[4],strings[5],strings[6],strings[7],Integer.parseInt(strings[8]),strings[1])){
            System.out.println("Car updated Successfully");
            return true;
        }
        else{
            System.out.println("Car info can't be updated.");
            return false;
        }
    }
    public boolean addCarInfo(String message){
        String[] strings=message.split("/");
        if(ds.AddCarInfoIntoTable(strings[1],Integer.parseInt(strings[2]),strings[3],strings[4],strings[5],strings[6],strings[7],Integer.parseInt(strings[8]))){
            System.out.println("Car Added Successfully.");
            return true;
        }
        else {
            System.out.println("Car data can't be added successfully.");
            return false;
        }
    }
    public String searchCarByReg(String message){
        String[] strings=message.split("/");
        if(ds.getCarByReg(strings[1])){
            String info=ds.Car_Info_By_Reg_Num(strings[1]);
            return info;
        }
        else{
            System.out.println("Car not found with this registration number.");
            return "null";
        }
    }
    public String searchCarByMakeModel(String message){
        String[] strings=message.split("/");
        if(ds.getCarByMakeModel(strings[1],strings[2])){
            String info=ds.getCarInfoByMakeModel(strings[1],strings[2]);
            String ans=info.substring(0,info.length()-1);
            return ans;
        }
        else{
            System.out.println("Car not found with this registration number.");
            return "null";
        }
    }
    public boolean deleteCar(String message){
        String[] strings=message.split("/");
        if(ds.Delete_Car_Info_By_Reg_Num(strings[1])){
            System.out.println("Car deleted Successfully.");
            return true;
        }
        else{
            System.out.println("Car can't be deleted successfully.");
            return false;
        }
    }
    public List<String> getCarsInfo(){
        List<String> carsInfo =new ArrayList<>();
        carsInfo= ds.getAllCars();
        return carsInfo;
    }
    public void handleTemporarily(Socket s) {
        ServerThread tempThread = new ServerThread(s, this);
        tempThread.start();
    }
}
