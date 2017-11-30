package ca.ece.ubc.cpen221.mp5;

import ca.ece.ubc.cpen221.mp5.YelpDB;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class YelpDBServer {

    private ServerSocket serverSocket;
    private final YelpDB yelpDB;
    public static final int DEFAULT_PORT = 4949;


    //Entry point which simply initializes a YelpDBServer that handles
    //Connections from multiple clients on port DEFAULT_PORT.
    public static void main(String args[]) {
        try {
           // int port = Integer.parseInt(args[0]);
            YelpDBServer server = new YelpDBServer(4949);
            server.serve();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Make a YelpDBServer that listens for connections on port.
     *
     * @param port
     *            port number, requires 0 <= port <= 65535
     */
    public YelpDBServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        String restaurantsFile = ("data/restaurants.json");
        String reviewsFile = ("data/reviews.json");
        String usersFile = ("data/users.json");

        this.yelpDB = new YelpDB(restaurantsFile,  reviewsFile,  usersFile);
    }

    /* Stays in an infinite loop that serves connections
     * by initializing connection handling threads.
     */
    public void serve() throws IOException {
        while (true) {
            //Blocks until user connects.
            final Socket socket = serverSocket.accept();

            //Pass user socket and pointer to database to new thread.
            Thread handler = new Thread(new ConnectionHandler(socket, yelpDB));
            handler.start();
            //this connection is now handled.
        }
    }
}
