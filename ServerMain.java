package ChatApplication;

public class ServerMain {

    public static void main(String[] args) {
       Server s= new Server(); // to invoke GUI Method
       s.waitForClient(); // to connect with client
       s.setIOStream(); // for sending data
    }
}
