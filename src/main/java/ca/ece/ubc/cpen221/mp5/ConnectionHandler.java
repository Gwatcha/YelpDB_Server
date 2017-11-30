package ca.ece.ubc.cpen221.mp5;

import ca.ece.ubc.cpen221.mp5.YelpDB;

import java.io.*;
import java.net.Socket;

/**
 * Connection handler, which runs on a separate thread handling
 * a connection from one clientSocket.
 */
public class ConnectionHandler implements Runnable {

    private final Socket clientSocket;
    private final YelpDB database;

    public ConnectionHandler(Socket clientSocket, YelpDB database) {
        this.clientSocket = clientSocket;
        this.database = database;
    }

    @Override
    public void run() {
        try {
            try {
                handle();
            } finally {
                clientSocket.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void handle() throws IOException {
        System.out.println("Client " + clientSocket.toString() + " connected.");

        //Create reader and writer.
        BufferedReader in = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                clientSocket.getOutputStream()));

        //As long as the client has not closed the socket..
        while (!clientSocket.isClosed()) {
            // each request is a single line containing a string
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                System.out.println(clientSocket.toString() + " requested: " + line);
                try {
                    String response = executeRequest(line);
                    if (!response.equals("")) {
                        out.print(response + "\n");
                        out.flush(); //send to client
                    }
                } catch (IllegalArgumentException e) {
                    // complain about ill-formatted request
                    out.print("ERR: ILLEGAL_REQUEST\n");
                    out.flush();
                }
            }
        }

        //Client is gone, start cleaning up.
        out.close();
        in.close();
    }

    /**
     * Parses a request line and uses the database to execute it.
     *
     * @param request
     * @return the string to send to the user if elicited, otherwise, an empty string.
     * @throws IllegalArgumentException if invalid or improperly formatted request.
     */
    private String executeRequest(String request) throws IllegalArgumentException {
        //Response is assumed to be empty in case this command
        //Does not elicit a respons.
        String response = "";
        String command;
        String details;

        //Split the request into the command and details.
        String[] arr = request.split(" ");

        if (arr.length == 2) {
            command = arr[0].toUpperCase();
            details = arr[1];
        } else if (arr.length == 1) {
            command = arr[0].toUpperCase();
        } else {
            throw new IllegalArgumentException();
        }


        switch (command) {
            case "GETRESTAURANT": {
                return response;

            }
            case "ADDUSER": {
                return response;

            }
            case "ADDRESTAURANT": {
                return response;

            }
            case "ADDREVIEW": {
                return response;

            }
            case "PING": {
                response = "I'm alive!";
                return response;
            }
            default:
                throw new IllegalArgumentException();
        }
    }
}
