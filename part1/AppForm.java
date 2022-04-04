package com.sreekanth.final_assignment.part1;

import javax.swing.*;

public class AppForm extends JFrame {
    AppPanel graphicsPanel;

    public AppForm()
    {
        graphicsPanel = new AppPanel();
        graphicsPanel.setSize(WIDTH, HEIGHT);
        this.add(graphicsPanel);
    }
}
