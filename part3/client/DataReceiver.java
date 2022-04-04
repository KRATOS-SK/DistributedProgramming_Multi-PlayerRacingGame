package com.sreekanth.final_assignment.part3.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Objects;

class DataReceiver implements Runnable
{
    static byte[] buffer; //Message buffer

    DataReceiver(DatagramSocket s)
    {
        buffer = new byte[1024]; //Set up buffer
    }

    public static String receiveDataPacket()
    {
        try
        {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length); //Initialize packet
            PartThree.socket.receive(packet); //receive data from network into packet
            return new String(packet.getData(), 0, packet.getLength()); //Convert pack data to string
        } catch (IOException e) {}

        return null;
    }

    private static boolean respondToServer(String serverMessage) throws Exception
    {

        switch (serverMessage) {
            case "0" -> {
                if (ServerInitialization.getCarIndex() != 0)
                    ServerInitialization.setCar(0);
                System.err.println("Allocated Blue car");
                ServerInitialization.waitForReceive = true;
                ServerInitialization.getForm();
            }
            case "1" -> {
                if (ServerInitialization.getCarIndex() != 1)
                    ServerInitialization.setCar(1);
                System.err.println("Allocated Green car");
                ServerInitialization.getForm();
            }
            case "restart" -> Objects.requireNonNull(ServerInitialization.getForm()).restart();
            case "exit" -> {
                //Close Application. Server full (Already controlling two cars)
                System.out.println("Server down");
                System.out.println("Server closing");
                Objects.requireNonNull(ServerInitialization.getForm()).dispose();
                System.exit(0);
                return false; //Stop running
            }
            default -> {
                String[] args = serverMessage.split(" "); //Split server message

                //Use array created as details for new car details
                if (args != null && args.length == 5)
                    Objects.requireNonNull(ServerInitialization.getForm()).makeChanges(args);
            }
        }

        return true;  //Continue running
    }

    public static boolean receiveAndRespond() throws Exception
    {
        String serverMessage = receiveDataPacket(); //Receive message from server

        if(!serverMessage.isEmpty()) //Display server message
            System.out.println("Server: " + serverMessage);

        return respondToServer(serverMessage); //return continue value
    }

    @Override
    public void run() {

        try
        {
            while (receiveAndRespond()) {} //receive messages from server and respond indefinitely
        }
        catch (Exception ex)
        {
            System.err.println("Server can no longer receive messages"); //Display error message
        }
    }
}
