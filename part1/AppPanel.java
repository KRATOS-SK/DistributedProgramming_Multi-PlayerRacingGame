package com.sreekanth.final_assignment.part1;

import java.awt.Graphics;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;

public class AppPanel extends JPanel implements ActionListener {
    Timer timer;
    ImageIcon[] carImages;
    int carPhase = 0;

    public AppPanel() {
        timer = new Timer(100, this);
        carImages = new ImageIcon[16];

        for (int i = 0; i < carImages.length; i++) {
            carImages[i] = new ImageIcon(Objects.requireNonNull(getClass().getResource("greenCar/" + i + ".png")));
        }

        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            carPhase++;

            if (carPhase == 16) {
                carPhase = 0;
            }

            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = (getWidth() - 50) / 2;
        int y = (getHeight() - 50) / 2;

        carImages[carPhase].paintIcon(this, g, x, y);
    }
}
