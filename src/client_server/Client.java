package client_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author enrico.dorigatti
 */
public class Client {

    Socket cSocket;
    String hostname;
    int port;
    private int ID;
    private String userName;
    private BufferedReader inFromServer;
    private PrintWriter outToServer;

    public Client(String hostname, int port)  {
        this.hostname = hostname;
        this.port = port;
        
        try {
            
            //socket con i parametri del server
            this.cSocket = new Socket(this.hostname, this.port);
            
            //creiamo nel costruttore i flussi di I/O
            this.inFromServer = new BufferedReader(new InputStreamReader(this.cSocket.getInputStream()));
            this.outToServer = new PrintWriter(this.cSocket.getOutputStream(), true);
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readWrite() {
        try {

         
            
            //assegnamo al client l'ID comunicato dal server
            this.setID(Integer.parseInt(inFromServer.readLine()));
            
            
            
            
            
            //messaggio al server
            outToServer.println("Ciao server");
            
            //lettura risposta del server
            System.out.println("risposta dal server: " + inFromServer.readLine());
            
            //eventuli altre operazioni
            
            //chiudiamo le risorse
            inFromServer.close();
            outToServer.close();
            this.cSocket.close();

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //getter & setter per l'ID univoco del client
    public int getID() {
        return ID;
    }

    private void setID(int ID) {
        this.ID = ID;
    }

}
