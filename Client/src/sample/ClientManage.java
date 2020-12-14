package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientManage implements Runnable {
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    Thread t;

    ClientManage(){}

    public boolean start()
    {
        try{
            socket=new Socket("localhost",5000);
            dis=new DataInputStream(socket.getInputStream());
            dos=new DataOutputStream(socket.getOutputStream());
            t=new Thread(this,"Client Manager");
            t.start();
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
            } catch (IOException e) {
                System.out.println("Server Closed!");
                break;
            }
        }
    }
}
