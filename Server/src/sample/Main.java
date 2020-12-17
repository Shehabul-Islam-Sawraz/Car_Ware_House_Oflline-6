package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {
            ServerSocket socket=new ServerSocket(5000);
            System.out.println("Server created successfully.");
            MessageHandler handler=new MessageHandler();
            while (true){
                Socket s=socket.accept();
                System.out.println("Server initialized successfully for the client.");
                handler.handleTemporarily(s);
            }
        } catch (IOException e) {
            System.out.println("Server can't be initialized.");
        }
    }
}
