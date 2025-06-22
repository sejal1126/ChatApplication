package ChatApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    private JFrame serverFrame;
    private JTextArea textarea;
    private JScrollPane scroll;
    private JTextField textField;
    private ServerSocket serversocket;
    private InetAddress inet_address;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket socket;
    //---------------------------------------THREAD CREATION---------------------------

    Thread thread = new Thread()
    {
        public void run()
        {
            while(true)
            {
                readMessage();
            }
        }
    };
    //-----------------------------------------------------------------------------------

    Server() {

        serverFrame = new JFrame("Server");
        serverFrame.setSize(500,500);
        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textarea = new JTextArea();
        Font font = new Font("Arial",1,16);
        textarea.setFont(font);
        textarea.setEditable(false);
        scroll= new JScrollPane(textarea);
        serverFrame.add(scroll);

        textField = new JTextField();
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(textField.getText());
               textarea.append(textField.getText()+"\n");
               textField.setText("");
            }
        });
        textField.setEditable(false);
        serverFrame.add(textField, BorderLayout.SOUTH);

        serverFrame.setVisible(true);

    }
    public void sendMessage(String message)
    {
            try{
                dos.writeUTF(message);
                dos.flush();
            } catch (Exception e) {
                System.out.println(e);
            }
    }

    public void setIOStream()
    {  thread.start();
        try{
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

        }
        catch (Exception e) {
            System.out.println(e);
        }

    }
public void readMessage()
{
    try{
        String message =dis.readUTF();
        showMessage("Client : "+message);
    } catch (Exception e) {
        System.out.println(e);
    }
}
   public void showMessage(String message) {
       textarea.append(message + "\n");
   }

    public String ip_address()
    {  String ip_address="";
        try{
            inet_address = InetAddress.getLocalHost();
            ip_address=inet_address.getHostAddress();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ip_address;
    }

    public void waitForClient()
    {
        try{
            serversocket = new ServerSocket(1111);
            textarea.setText("To connect with server please provide ip_address"+ip_address());
            socket=serversocket.accept();
            textarea.setText("Client Connected");
            textarea.append("--------------------------------------------------------------------\n");
            textField.setEditable(true);
        } catch (Exception e) {
           System.out.println(e);
        }
    }
}
