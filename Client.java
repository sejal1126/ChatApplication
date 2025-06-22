package ChatApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private JFrame clientFrame;
    private JTextArea textarea;
    private JScrollPane scroll;
    Socket socket;
    String ip_address;
    private DataInputStream dis;
    private DataOutputStream dos;
    private JTextField textField;
    //---------------------------------------THREAD CREATION---------------------------

    Thread thread=new Thread(){
        public void run()
        {
            while(true)
            {
                readMessage();
            }
        }
    };
    //-----------------------------------------------------------------------------------

    Client() {
         ip_address = JOptionPane.showInputDialog("Enter the IP address");

        if (ip_address != null) {
            connectToServer();
            if (!ip_address.equals("")) {
                clientFrame = new JFrame("Client");
                clientFrame.setSize(500, 500);
                clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                textarea = new JTextArea();
                Font font = new Font("Arial",1,16);
                textarea.setFont(font);
                textarea.setEditable(false);
                scroll = new JScrollPane(textarea);
                clientFrame.add(scroll);

                textField = new JTextField();
                textField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        sendMessage(textField.getText());
                        textarea.append(textField.getText()+"\n");
                        textField.setText("");
                    }
                });
                clientFrame.add(textField, BorderLayout.SOUTH);
                clientFrame.setVisible(true);
            }
        }

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
    public void showMessage(String message)
    {
        textarea.append(message+"\n");

    }
    public void readMessage()
    {
        try{
            String message =dis.readUTF();
            showMessage("Server : "+message);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void setIOStream()
    { thread.start();
        try{
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }
    public void connectToServer()
    {
        try{
            socket = new Socket(ip_address,1111);
        } catch (Exception e) {
            System.out.println(socket);
        }
    }

    public void chatsound()
    {

    }
}
