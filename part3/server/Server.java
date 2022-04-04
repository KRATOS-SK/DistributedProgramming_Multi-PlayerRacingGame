package com.sreekanth.final_assignment.part3.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends Thread {

    public static int port = 7334; //Network port number
    private static final int BUFFER = 1024; //Buffer size
    static byte[] buf = new byte[BUFFER]; //Message buffer
    private static DatagramSocket socket; //Network socket
    private static ArrayList<InetAddress> clientAddresses; //List of client addresses
    private static ArrayList<Integer> clientPorts; //List of client ports
    private static ArrayList<String> existingClients; //List of client ID's

    public static void main(String args[]) {
        try {
            if (args.length > 0) {
                port = Integer.getInteger(args[0]);
            }

            socket = new DatagramSocket(port); //Set up server
            clientAddresses = new ArrayList(); //Set up list of client addresses
            clientPorts = new ArrayList(); //Set up list of client ports
            existingClients = new ArrayList(); //Set up list of ID's referencing clients
            runServer(); //Run server

        } catch (IOException ex) {
            System.out.println(ex.getMessage()); //Print out error message
        }
    }

    private static String[] getClientMessage() throws IOException {
        Arrays.fill(buf, (byte) 0); //Return array in format [Client id, client message]
        DatagramPacket packet = new DatagramPacket(buf, buf.length); //Initialize data packet
        socket.receive(packet); //Receive client message from socket to packet
        InetAddress clientAddress = packet.getAddress(); //Get client address
        int clientPort = packet.getPort(); //Get client Port
        String id = clientAddress.toString() + "," + clientPort; //Client port

        //Check if client is an old one
        if (!existingClients.contains(id)) {
            existingClients.add(id); //Add id to id list
            clientPorts.add(clientPort); //Add port to client port list
            clientAddresses.add(clientAddress); //Add address to address list
            System.out.println(id + " Joined the network"); //Prompt server admin that client has been added

            int intValue = existingClients.indexOf(id);
            String message = String.valueOf(intValue);

            if (intValue < 2) {
                sentToClient(id, message, false); //Send only to specific client
            } else {
                sentToClient(id, "Exit", false); //Prompt client to exit
            }
        }

        var clientMessage = new String(buf).trim();

        if (!clientMessage.isEmpty()) {
            System.out.println("Client: " + clientMessage);
            return new String[]{id, clientMessage};
        } else return new String[0];
    }

    public static void sentToClient(String id, String message, boolean toAll) throws IOException {
        if (message.isEmpty()) return;

        System.out.println("Server: " + message);

        byte[] data = (message).getBytes();
        for (int i = 0; i < existingClients.size(); i++) {
            boolean isClient = existingClients.get(i) == null ? id == null : existingClients.get(i).equals(id);

            if (isClient || toAll) {
                InetAddress cl = clientAddresses.get(i); //If sender ID then reply

                int cp = clientPorts.get(i);

                var packet = new DatagramPacket(data, data.length, cl, cp);

                socket.send(packet);
                break;
            }
        }
    }

    public static boolean getAndRespondToClient() throws IOException {
        String[] clientMessage = getClientMessage();

        if (clientMessage.length == 0) return true;

        if (clientMessage[1].isEmpty()) return true;

        if (clientMessage[1].equals("exit")) {
            //Send only to specific client
            System.out.println("Client Controlling car has left the server");
            System.out.println("Server is now shutting down ");
            sentToClient(existingClients.get(0), clientMessage[1], false);
            sentToClient(existingClients.get(1), clientMessage[1], false);
            return false;
        } else if (clientMessage[1].equals("restart")) {
            //Send only to specific client
            System.out.println("Restarting game");
            sentToClient(existingClients.get(0), clientMessage[1], false);
            sentToClient(existingClients.get(1), clientMessage[1], false);
            return false;
        } else {
            String[] args = clientMessage[1].split(" ");

            if (args.length != 5) return true;

            int i;

            if (args[0].equals("0")) i = 1;
            else i = 0;

            sentToClient(existingClients.get(i), clientMessage[1], false);
        }
        return true;
    }

    public static void runServer() throws IOException {
        System.out.println("Server Running");

        while (true) {
            if (!getAndRespondToClient()) break;
        }
    }
}
