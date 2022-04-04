package com.sreekanth.final_assignment.part3.client;

import java.net.*;

public class PartThree {
    static DatagramSocket socket;

    public static String host = "127.0.0.1"; //Address to be used for communication
    public static int port = 7334; //Address to be used for communication

    static DataReceiver dataReceiver; //Class handling receiving messages
    static DataTransmitter dataTransmitter; //Class handling sending messages to the server

    static Thread dataReceiverThread; //Thread dedicated to the message receiver
    static Thread dataTransmitterThread; //Thread dedicated to the message sender

    public static void main(String[] args) throws SocketException {
        if(args.length > 0)
        {
            //Get port and host info if started with parameters
            try
            {
                host = args[0];
                port = Integer.parseInt(args[1]);
            }
            catch(NumberFormatException e)
            {
                System.out.println(e.getMessage());
            }
        }

        socket = new DatagramSocket(); //Set up socket
        System.out.println("Socket set up");

        dataReceiver = new DataReceiver(socket); //Set up class for receiving messages
        dataTransmitter = new DataTransmitter(); //Set up class for sending messages
        dataReceiverThread = new Thread(dataReceiver); //Set up thread handling the message receiver
        dataTransmitterThread = new Thread(dataTransmitter); //Set up thread handling the message sender

        dataReceiverThread.start(); //Start message receiver
        System.out.println("Receiving thread Initialized");

        dataTransmitterThread.start(); //Start message sender
        System.out.println("Sending thread Initialized");
    }
}
