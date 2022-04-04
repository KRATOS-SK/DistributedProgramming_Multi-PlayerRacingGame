package com.sreekanth.final_assignment.part3.client;

import javax.swing.*;
import java.awt.event.*;

public class AppForm extends JFrame {
    AppPanel graphicsPanel;

    public AppForm()
    {
        graphicsPanel = new AppPanel();
        graphicsPanel.setFocusable(true);
        graphicsPanel.setSize(WIDTH, HEIGHT);
        this.add(graphicsPanel);
        this.setVisible(true);

        setSize(850, 650); //Set form size 850 pixels by 650 pixels
        setResizable(false); //Set Resizable to false
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Allow app to close on the pressing of "X"
        setVisible(true); //Set visibility of the app the true

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                try {
                    DataTransmitter.sendMessage("exit"); //Close all clients upon exit
                } catch (Exception ex) {}
            }
        });
    }

    public void restart()
    {
        graphicsPanel.resetCars(); //Reset cars to start position with '0' as displacement factor
    }

    public void makeChanges(String[] str)
    {
        graphicsPanel.handleChanges(str); //Change car information
    }
}
