package com;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

public class Client {
    static public Socket socket;
    static public ObjectOutputStream oos;
    static public ObjectInputStream ois;
    static public boolean socketException = false;
    public boolean getConnectToServer(){
        socket = null;
        try {
            socket = new Socket("127.0.0.1", 3345);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch(ConnectException e){
            System.out.println("гг");
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            socketException=true;
            return false;
        }
        return true;
    }


    public ObjectOutputStream getOos() {
        return oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public Socket getSocket() {
        return socket;
    }
}
