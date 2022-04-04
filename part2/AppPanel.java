package com.sreekanth.final_assignment.part2;

import javax.swing.*;
import java.awt.*;
import java.awt.Stroke;
import java.awt.event.*;
import java.io.Serializable;
import java.util.Objects;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AppPanel extends JPanel implements ActionListener, KeyListener, Serializable {
    Timer timer;
    ImageIcon[] blueCarImages;
    ImageIcon[] greenCarImages;

    int[] carPhase;
    int[] carPosition;
    int[] carLane;
    int[] displacementFactor;

    transient Stroke dashedStroke;
    transient Clip[] clips;
    Label carSpeedLabel;

    boolean soundTrigger;

    public AppPanel() {
        carPhase = new int[]{0, 0};
        carLane = new int[]{0, 0};
        carPosition = new int[]{430, 150, 430, 100};
        displacementFactor = new int[]{0, 0};
        soundTrigger = true;
        blueCarImages = new ImageIcon[16];
        greenCarImages = new ImageIcon[16];

        carSpeedLabel = new Label("Car 1 Speed: 0  Car 2 Speed: 0");
        add(carSpeedLabel);

        addKeyListener(this);

        try {
            clips = new Clip[3];
            clips[0] = AudioSystem.getClip();
            //AudioPlayer.getClass().getResource("sounds/swerve.wav")
            clips[0].open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("sounds/swerve.wav"))));

            clips[1] = AudioSystem.getClip();
            clips[1].open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("sounds/crash.wav"))));

            clips[2] = AudioSystem.getClip();
            clips[2].open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("sounds/drive.wav"))));
        } catch (Exception e) {
        }

        for (int i = 0; i < blueCarImages.length; i++) {
            blueCarImages[i] = new ImageIcon(Objects.requireNonNull(getClass().getResource("blueCar/" + i + ".png")));
            greenCarImages[i] = new ImageIcon(Objects.requireNonNull(getClass().getResource("greenCar/" + i + ".png")));
        }

        dashedStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 50, new float[]{30.6f, 0, 0}, 0);

        timer = new Timer(100, this);
        timer.start();
    }

    void handleCarLaneAssign(int car) {
        int carX = carPosition[car == 0 ? 0 : 2];
        int carY = carPosition[car == 0 ? 1 : 3];

        if (carX <= 70 || carX >= 734 || carY <= 118 || carY >= 531) carLane[car] = 2;
        else carLane[car] = 1;
    }

    void handleCarSpeedIncrease(int car) {
        if (displacementFactor[car] < 10) displacementFactor[car] += 1;

        handleUpdateVehicleInfo();
    }

    void handleCarSpeedReduce(int car) {
        if (displacementFactor[car] > -10) displacementFactor[car] -= 1;

        handleUpdateVehicleInfo();
    }

    void handleCarSpeedControl(int car, int lane) {
        int xLim0;
        int xLim1;
        int yLim1;
        int yLim0;
        int carX;
        int carY;
        int disFac;

        int carDirection = this.carPhase[car];

        if (lane == 1) {
            xLim0 = 148;
            xLim1 = 649;
            yLim1 = 450;
            yLim0 = 200;
        } else {
            xLim0 = 97;
            xLim1 = 700;
            yLim1 = 502;
            yLim0 = 144;
        }

        carX = carPosition[car == 0 ? 0 : 2];
        carY = carPosition[car == 0 ? 1 : 3];
        disFac = displacementFactor[car];

        boolean nearTopR = carX > xLim1 - disFac * 5 && carX < xLim1;

        boolean nearlowerR = carY > yLim1 - disFac * 5 && carY < yLim1 && carDirection == 4;

        boolean nearTopL = carY <= yLim0 + disFac * 5 && carY > yLim0 && carDirection == 12;

        boolean nearlowerL = carX <= xLim0 + disFac * 5 && carX > xLim0;

        if ((nearTopR || nearlowerR || nearTopL || nearlowerL) && disFac > 4) handleCarSpeedReduce(car);

    }

    void handleCarLeftTurn(int car) {
        carPhase[car]--;
        if (carPhase[car] == -1) carPhase[car] = 15;
    }

    void handleCarRightTurn(int car) {
        carPhase[car]++;
        if (carPhase[car] == 16) carPhase[car] = 0;
    }

    void handleMoveCar(int car) {
        int xDiff = 0;
        int yDiff = 0;

        switch (carPhase[car]) {
            case 0, 8 -> {
                xDiff = displacementFactor[car];
                if (carPhase[car] == 8) xDiff *= -1;
            }
            case 1, 2, 3, 5, 6, 7, 9, 10, 11, 13, 14, 15 -> {
                xDiff = displacementFactor[car] / 2;
                yDiff = displacementFactor[car] / 2;
                if (carPhase[car] >= 5 && carPhase[car] <= 11) xDiff *= -1;
                if (carPhase[car] >= 9 && carPhase[car] <= 15) yDiff *= -1;
            }
            case 4, 12 -> {
                yDiff = displacementFactor[car];
                if (carPhase[car] == 12) yDiff *= -1;
            }
        }

        handleCollision(car, xDiff, yDiff);
    }

    void handleUpdateVehicleInfo() {
        String carStatus = "Car 1 Speed:" + displacementFactor[0] * 10 + "  ";
        carStatus += "Car 2 Speed:" + displacementFactor[1] * 10;
        carSpeedLabel.setText(carStatus);
    }

    void handleCollision(int car, int xDiff, int yDiff) {
        int xIndex = car == 0 ? 0 : 2;
        int yIndex = car == 0 ? 1 : 3;

        var carX = carPosition[xIndex] + xDiff;
        var carY = carPosition[yIndex] + yDiff;

        if (carX > 118 && carX < 682 && carY > 160 && carY < 487 || carX < 40 || carX > 760 || carY < 90 || carY > 560) {
            displacementFactor[car] = 0;
            handleUpdateVehicleInfo();
            handleSoundTrigger(0);
        } else {
            Rectangle car1 = new Rectangle(carPosition[0], carPosition[1], 30, 20);
            Rectangle car2 = new Rectangle(carPosition[2], carPosition[3], 30, 20);
            if (car1.intersects(car2)) {
                handleSoundTrigger(1);
                timer.stop();

                JOptionPane.showMessageDialog(this, "Game over, cars crashed. Type 'R' on the game UI to restart");
            } else {
                carPosition[xIndex] = carX;
                carPosition[yIndex] = carY;
            }
        }
    }

    void handleSoundTrigger(int index) {
        if (!timer.isRunning()) return;
        if (!soundTrigger) return;

        try {
            int frameLength = clips[index].getFrameLength();
            if (clips[index].getFramePosition() == frameLength) clips[index].setMicrosecondPosition(1);
            clips[index].start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    void resetCars() {
        carPosition = new int[]{430, 150, 430, 100};
        displacementFactor = new int[]{0, 0};
        carPhase[0] = 0;
        carPhase[1] = 0;
        timer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!timer.isRunning() && e.getKeyCode() != KeyEvent.VK_R) return;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                handleCarSpeedIncrease(0);
                break;
            case KeyEvent.VK_DOWN:
                handleCarSpeedReduce(0);
                break;
            case KeyEvent.VK_LEFT:
                handleCarLeftTurn(0);
                break;
            case KeyEvent.VK_RIGHT:
                handleCarRightTurn(0);
                break;
            case KeyEvent.VK_W:
                handleCarSpeedIncrease(1);
                break;
            case KeyEvent.VK_S:
                handleCarSpeedReduce(1);
                break;
            case KeyEvent.VK_A:
                handleCarLeftTurn(1);
                break;
            case KeyEvent.VK_D:
                handleCarRightTurn(1);
                break;
            case KeyEvent.VK_M:
                soundTrigger = !soundTrigger;
                break;
            case KeyEvent.VK_R:
                if (!timer.isRunning()) {
                    resetCars();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Can be further developed to condition the vehicles to move <=> the action keys and held pressed
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Can be further developed to input the players name and display a score board of sort
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() != timer) return;
        for (int i = 0; i < 2; i++) {
            handleCarSpeedControl(i, carLane[i]);
            handleMoveCar(i);
            handleCarLaneAssign(i);

            if (displacementFactor[i] > 0) handleSoundTrigger(2);
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr.create();

        super.paintComponent(g);

        g.setColor(Color.black);
        g.drawRect(50, 100, 750, 500);
        g.setColor(Color.darkGray);
        g.fillRect(50, 100, 750, 500);

        g.setColor(Color.darkGray);
        g.fillRect(100, 150, 650, 400);

        g.setStroke(dashedStroke);
        g.setColor(Color.yellow);
        g.drawRect(100, 150, 650, 400);
        g.setStroke(new BasicStroke());

        g.setColor(Color.white);
        g.fillRoundRect(150, 200, 550, 300, 14, 14);

        g.setColor(Color.white);
        g.drawLine(425, 100, 425, 200);

        blueCarImages[carPhase[0]].paintIcon(this, g, carPosition[0], carPosition[1]);
        greenCarImages[carPhase[1]].paintIcon(this, g, carPosition[2], carPosition[3]);
    }
}
