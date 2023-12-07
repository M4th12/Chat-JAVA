package client_server;

import java.net.ServerSocket;

/**
 *
 * @author enrico.dorigatti
 */
public class Client_server {

    public static void main(String[] args) {
        int port = 5500;
        
        Server s = new Server(port);    //ip: 10.10.0.1
        /*
        Client c1 = new Client("localhost", port);
        Client c2 = new Client("127.0.0.1", port);
        
        c1.readWrite();
        c2.readWrite();*/
    }
    
}
