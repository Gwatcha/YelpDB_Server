package ca.ece.ubc.cpen221.mp5;

import ca.ece.ubc.cpen221.mp5.database.YelpDB;

import java.io.IOException;
import java.net.Socket;

/**
 * Connection handler, which runs on a separate thread handling
 * a connection from one clientSocket.
 */
public class ConnectionHandler implements Runnable {

    private final Socket clientSocket;

    public ConnectionHandler(Socket clientSocket, YelpDB database) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            try {
                handle(clientSocket);
            } finally {
                clientSocket.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void handle(Socket socket) {
        System.out.println("Client connected.");

        //Get sockets input stream. //TODO Not sure what sort of data we process.
    }
}
