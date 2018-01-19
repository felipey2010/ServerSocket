package br.ufrr.serversoket;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by FELIPEY on 16/01/2018.
 */

public class MyServer {
    Thread m_objThread;
    ServerSocket m_server;
    String m_strMessage;
    DataDisplay m_dataDisplay;
    Object m_connected;

    public MyServer(){

    }
    public void setEventListener(DataDisplay dataDisplay){
        m_dataDisplay = dataDisplay;
    }

    public void startListening(final String msgServer){
        m_objThread =  new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    m_server = new ServerSocket(2001);
                    Socket connectedSocket = m_server.accept();
                    Message clientmessage = Message.obtain();

                    ObjectInputStream ois = new ObjectInputStream(connectedSocket.getInputStream());
                    clientmessage.obj = ois.readObject();

                    ObjectOutputStream oos = new ObjectOutputStream(connectedSocket.getOutputStream());
                    oos.writeObject(msgServer);

                    mHandler.sendMessage(clientmessage);
                    oos.close();
                    ois.close();
                    m_server.close();
                } catch (IOException e) {
                    Message msg = Message.obtain();
                    msg.obj = e.getMessage();
                    mHandler.sendMessage(msg);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        m_objThread.start();
    }
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message status) {
            m_dataDisplay.Display(status.obj.toString());
        }
    };
}
