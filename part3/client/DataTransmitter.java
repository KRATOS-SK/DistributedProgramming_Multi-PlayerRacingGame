package com.sreekanth.final_assignment.part3.client;

import java.net.DatagramPacket;
import java.net.InetAddress;

class DataTransmitter implements Runnable {
    public static boolean sendMessage(String s) throws Exception {
        if (s.isEmpty()) return true; //Stop function if message is empty

        System.out.println("Client: " + s); //Display message to be sent

        byte[] buf = s.getBytes(); //Get bytes from string

        InetAddress address = InetAddress.getByName(PartThree.host); //Get address

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PartThree.port); //Initialize packet

        PartThree.socket.send(packet); //Send packet to destination

        return !s.equals("exit"); //Keep sending messages
    }

    @Override
    public void run() {
        boolean connected = false;

        //Wait for client to successfully send packet
        while (!connected) {
            try {
                sendMessage("Hello"); //Send greeting message
                connected = true; //Set connection status to true
            } catch (Exception e) {
            }
        }

        System.out.println("Ready to receive data from network");

        while (true) {
            try {
                boolean cont = sendMessage(ServerInitialization.getClientMessage()); //Send message to server
                //Close socket if sent message is exit
                if (!cont) break;
                //Pause process for a second
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}

