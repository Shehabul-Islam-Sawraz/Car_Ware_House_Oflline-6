package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread implements Runnable{
    Socket s;
    Thread t;
    DataInputStream dis;
    DataOutputStream dos;

    public ServerThread(Socket s)
    {
        this.s=s;
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
