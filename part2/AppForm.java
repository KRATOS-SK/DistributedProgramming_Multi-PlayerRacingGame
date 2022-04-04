package com.sreekanth.final_assignment.part2;

import javax.swing.*;

public class AppForm extends JFrame {
    AppPanel graphicsPanel;

    public AppForm()
    {
        graphicsPanel = new AppPanel();
        graphicsPanel.setFocusable(true);
        graphicsPanel.setSize(WIDTH, HEIGHT);
        this.add(graphicsPanel);
        this.setVisible(true);
    }
}
