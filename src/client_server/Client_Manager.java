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
public class Client_Manager implements Runnable {

    private Socket cSocket;
    private int idClient;
    private Server server;
    private BufferedReader inFromClient;
    private PrintWriter outToClient;

    public Client_Manager(Socket cSocket, int idClient, Server server) {
        try {
            this.cSocket = cSocket;
            this.idClient = idClient;
            //riferimento al server chiamante
            this.server = server;
            
            //creiamo il flusso di IO del client
            this.inFromClient = new BufferedReader(new InputStreamReader(this.cSocket.getInputStream()));
            this.outToClient = new PrintWriter(this.cSocket.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(Client_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
         
        try {
            System.out.println("ho preso in carico il client");
            

            
            //inviamo al client il suo id univoco
            outToClient.println(this.idClient);
            
            this.server.addClientToList(this);
            
            //leggiamo il messaggio inviato dal client
            System.out.println("ho ricevuto dal client il seguente messaggio: " + inFromClient.readLine());
            
            //eventuali altre operazioni
            
            //risposta al client
            outToClient.println("risposta al client: grazie per tutto il pesce");
            
            quitConnection();
            
        } catch (IOException ex) {
            Logger.getLogger(Client_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void writeToClient()
    {
        //ToDo
    }
    
    private void quitConnection()
    {
        try {
            //chiusura delle risortse e della connessione
            this.inFromClient.close();
            this.outToClient.close();
            this.cSocket.close();
            this.server.removeClientFromList(this.idClient);
        } catch (IOException ex) {
            Logger.getLogger(Client_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public String toString()
    {
        //ToDo
        return this.cSocket.getInetAddress().getHostAddress() + "\tHostName: " + this.cSocket.getInetAddress().getHostName();
    }

    public int getIdClient() {
        return idClient;
    }
    

}
