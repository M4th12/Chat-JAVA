package client_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

    ServerSocket sSocket = null;
    Thread tServer = null;
    int port;
    static int progressiveClientID = 0;
    private HashMap<Integer, Client_Manager> clientList;

    public Server(int port) {
        this.port = port;
        this.clientList = new HashMap<Integer, Client_Manager>();
        try {
            sSocket = new ServerSocket(this.port);
            System.out.println("----server avviato e in attesa di connessione----");
            tServer = new Thread(this);
            
            tServer.start(); //avvio automatico del server
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {

                //crea un socket per ogni client disonibile
                Socket cSocket = sSocket.accept();
                Client_Manager manager = new Client_Manager(cSocket, ++progressiveClientID, this);
                Thread connManager = new Thread(manager);
                connManager.start();
                
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //funzione per aggiungere un client alla lista del server
    public void addClientToList(Client_Manager client)
    {
        this.clientList.put(client.getIdClient(), client);
        System.out.println("Nuovo client connesso! Ho " + this.clientList.size() + " client in lista");
        for(Integer i:this.clientList.keySet()) {
            System.out.println("Client : " + i + "\tIP: " + this.clientList.get(i).toString());
            }
    }
    
    //funzione per rimuovere un client dalla lista del server
    public void removeClientFromList(int ID)
    {
        this.clientList.remove(ID);
        System.out.println("Client disconnesso! Ho " + this.clientList.size() + " client in lista");
        for(Integer i:this.clientList.keySet()) {
            System.out.println("Client : " + i + "\tIP: " + this.clientList.get(i).toString());
            }
    }

}
