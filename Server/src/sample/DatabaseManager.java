package sample;

import java.sql.*;

public class DatabaseManager {
    Connection connection;
    private static final String DATABASE_PATH = "jdbc:sqlite:src\\sample\\";
    private static final String DATABASE_NAME = "manufacturer_info.db";
    private static final String MANUFACTURER_INFO_TABLE = "manufacturer_info";
    private static final String CAR_DATABASE_NAME = "car_info.db";
    private static final String CAR_INFO_TABLE = "car_info";

    private static final String NAME_COLUMN = "Username";
    private static final String PASSWORD_COLUMN = "Password";

    private static final String REG_COLUMN = "Registration";
    private static final String YEAR_COLUMN = "Year_made";
    private static final String MAKE_COLUMN = "Car_make";
    private static final String MODEL_COLUMN = "Car_model";
    private static final String PRICE_COLUMN = "Price";
    private static final String COLOR1_COLUMN = "Color1";
    private static final String COLOR2_COLUMN = "Color2";
    private static final String COLOR3_COLUMN = "Color3";

    private static final String Find_Manufacturer_By_Name = "SELECT COUNT(*) FROM "+MANUFACTURER_INFO_TABLE+" WHERE "+NAME_COLUMN+" = ?";
    private static final String Check_Pass_By_Name = "SELECT "+PASSWORD_COLUMN+" From "+ MANUFACTURER_INFO_TABLE +" WHERE "+NAME_COLUMN+" = ?";

    private static final String Find_Car_By_Registration = "SELECT COUNT(*) FROM "+CAR_INFO_TABLE+" WHERE "+REG_COLUMN+" = ?";
    private static final String Insert_New_Car = "INSERT INTO "+CAR_INFO_TABLE+" ( " +
            REG_COLUMN+", "+YEAR_COLUMN+", "+COLOR1_COLUMN+", "+COLOR2_COLUMN +", "+COLOR3_COLUMN+", "+MAKE_COLUMN+", "+MODEL_COLUMN+", "+PRICE_COLUMN+") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String Find_Car_By_Make_Model= "SELECT COUNT(*) FROM "+CAR_INFO_TABLE+" WHERE "+MAKE_COLUMN+" = ? AND "+ MODEL_COLUMN+" =?";
    private static final String Find_Reg_By_Make_Model= "SELECT "+REG_COLUMN+" FROM "+ CAR_INFO_TABLE+" WHERE "+MAKE_COLUMN+" = ? AND "+ MODEL_COLUMN+" =?";
    private static final String Update_Car_By_Reg_Num= "UPDATE "+CAR_INFO_TABLE+
            " SET "+YEAR_COLUMN+" = ? "+ " SET "+COLOR1_COLUMN+" = ? "+
            " SET "+COLOR2_COLUMN+" = ? "+ " SET "+COLOR3_COLUMN+" = ? "+
            " SET "+MAKE_COLUMN+" = ? "+ " SET "+MODEL_COLUMN+" = ? "+
            " SET "+PRICE_COLUMN+" = ? "+ " WHERE "+REG_COLUMN+" = ?";
    private static String Delete_Car_By_Reg= "DELETE FROM "+CAR_INFO_TABLE+" WHERE "+REG_COLUMN+" =?";
    private static String Get_Car_Info_By_Reg="SELECT "+YEAR_COLUMN+" "+COLOR1_COLUMN+" "+COLOR2_COLUMN+" "+COLOR3_COLUMN+" "+MAKE_COLUMN+" "+MODEL_COLUMN+" "+PRICE_COLUMN+" FROM "+CAR_INFO_TABLE+" WHERE "+REG_COLUMN+" =?";


    public DatabaseManager(){}
    public void open(){
        try{
            connection= DriverManager.getConnection(DATABASE_PATH+DATABASE_NAME);
            connection= DriverManager.getConnection(DATABASE_PATH+CAR_DATABASE_NAME);
            System.out.println("Database opened successfully.");
        } catch(Exception e) {
            System.out.println("Database can't be opened successfully.");
        }
    }
    public void close()
    {
        if(connection!=null){
            try{
                connection.close();
                System.out.println("Database closed successfully.");
            }catch(SQLException e){
                System.out.println("Database can't be closed successfully.");
            }
        }
    }
    public boolean getManufacturerByName(String name){
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(Find_Manufacturer_By_Name);
            preparedStatement.setString(1,name);
            ResultSet resultSet=preparedStatement.executeQuery();
            int count=resultSet.getInt(1);
            if(count==0){
                return false;
            }
            return true;
        } catch(SQLException e){
            System.out.println("No manufacturer found with this name: "+e.getMessage());
            return false;
        }
    }
    public boolean AccountValidityCheck(String name,String pass){
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(Check_Pass_By_Name);
            preparedStatement.setString(1,name);
            ResultSet resultSet=preparedStatement.executeQuery();
            String password=resultSet.getString(1);
            if(pass.equals(password)){
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Account invalid for the given information: "+e.getMessage());
            return false;
        }
    }
    public boolean AddCarInfoIntoTable(String reg,int yearMade,String color1,String color2,String color3,String carMake,String carModel,int price){
        try {
            PreparedStatement ps = connection.prepareStatement(Insert_New_Car);
            ps.setString(1, reg);
            ps.setInt(2,yearMade);
            ps.setString(3,color1);
            ps.setString(4,color2);
            ps.setString(5,color3);
            ps.setString(6,carMake);
            ps.setString(7,carModel);
            ps.setInt(8,price);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Adding New Car FAILED...\n"+e.getMessage());
            return false;
        }
    }
    public boolean getCarByReg(String reg){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Find_Car_By_Registration);
            preparedStatement.setString(1, reg);
            ResultSet resultSet = preparedStatement.executeQuery();
            int count = resultSet.getInt(1);
            if(count==0){
                System.out.println("No car with this registration number.");
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Can't return any car for the user...\n"+e.getMessage());
            return false;
        }
    }
    public boolean getCarByMakeModel(String make,String model){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Find_Car_By_Make_Model);
            preparedStatement.setString(1, make);
            preparedStatement.setString(2,model);
            ResultSet resultSet = preparedStatement.executeQuery();
            int count = resultSet.getInt(1);
            if(count==0){
                System.out.println("No car with this make and model number.");
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Can't return any car with this make and model for the user...\n"+e.getMessage());
            return false;
        }
    }
    public String getCarInfoByMakeModel(String make,String model){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Find_Reg_By_Make_Model);
            preparedStatement.setString(1, make);
            preparedStatement.setString(2,model);
            ResultSet resultSet = preparedStatement.executeQuery();
            String reg = resultSet.getString(1);
            String message=Car_Info_By_Reg_Num(reg);
            return message;
        } catch (SQLException e) {
            System.out.println("Can't return any car with this make and model for the user...\n"+e.getMessage());
            return "";
        }
    }
    public String Car_Info_By_Reg_Num(String reg){
        try {
            PreparedStatement preparedStatement =connection.prepareStatement(Get_Car_Info_By_Reg);
            preparedStatement.setString(1,reg);
            ResultSet resultSet=preparedStatement.executeQuery();
            String message=reg+"/"+Integer.toString(resultSet.getInt(1))+"/"+resultSet.getString(2)+"/"+resultSet.getString(3)+"/"+resultSet.getString(4)+"/"+resultSet.getString(5)+"/"+resultSet.getString(6)+"/"+Integer.toString(resultSet.getInt(7));
            return message;
        } catch (SQLException e) {
            System.out.println("Can't return any car with this make and model for the user...\n"+e.getMessage());
            return "";
        }
    }
    public boolean updateCarInfoByRegNum(int yearMade,String color1,String color2,String color3,String carMake,String carModel,int price,String reg){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(Update_Car_By_Reg_Num);
            preparedStatement.setInt(1, yearMade);
            preparedStatement.setString(2, color1);
            preparedStatement.setString(3, color2);
            preparedStatement.setString(4, color3);
            preparedStatement.setString(5, carMake);
            preparedStatement.setString(6, carModel);
            preparedStatement.setInt(7, price);
            preparedStatement.setString(8, reg);
            int affectedRows = preparedStatement.executeUpdate();
            return (affectedRows == 1)? true : false;
        }catch (Exception e){
            System.out.println("Failed to update Car Info...\n"+e.getMessage() );
            return false;
        }
    }
    public boolean Delete_Car_Info_By_Reg_Num(String reg){
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(Delete_Car_By_Reg);
            preparedStatement.setString(1,reg);
            preparedStatement.execute();
            System.out.println("Car Info Deleted Successfully");
            return true;
        } catch(SQLException e) {
            System.out.println("Failed to delete the car info..."+e.getMessage());
            return false;
        }
    }
}
