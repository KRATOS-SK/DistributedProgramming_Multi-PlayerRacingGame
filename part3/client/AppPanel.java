package com.sreekanth.final_assignment.part3.client;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.Objects;

public class AppPanel extends JPanel implements ActionListener, KeyListener, Serializable {
    Timer timer; //Swing timer that controls car spinning
    ImageIcon[] blueCarImages; //Array of Blue-Car images
    ImageIcon[] greenCarImages; //Array of Green-Car images

    int[] carPhase; //Array holding phase of car (car at specific angles)
    int[] carPosition; //Array holding x and y value of cars
    int[] carLane; //Array holding car lane information (Lane 1 -> inner lane, Lane 2 -> outer lane)
    int[] displacementFactor; //Array holding displacement factor of cars

    transient Stroke dashedStroke;
    transient Clip[] clips;
    Label carSpeedLabel;

    boolean soundTrigger;

    public AppPanel() {
        carPhase = new int[]{0, 0};
        carLane = new int[]{0, 0};
        carPosition = new int[]{430, 150, 430, 100}; //X, Y values of car 1 and 2
        displacementFactor = new int[]{0, 0}; //Displacement factor of car 1 and 2
        soundTrigger = true; // Boolean variable for playing sounds

        //Declaring and Initializing car images arrays
        blueCarImages = new ImageIcon[16];
        greenCarImages = new ImageIcon[16];

        carSpeedLabel = new Label("Car 1 Speed: 0  Car 2 Speed: 0");
        add(carSpeedLabel);

        addKeyListener(this); //Configure key listener

        //Assigning sound clips to 'clips' array according to their indexes
        try {
            //Initializing 'clips' array
            clips = new Clip[3];

            //Swerve sound assign
            clips[0] = AudioSystem.getClip();
            clips[0].open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("sounds/swerve.wav"))));

            //Crash sound assign
            clips[1] = AudioSystem.getClip();
            clips[1].open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("sounds/crash.wav"))));

            //Drive/Acceleration sound assign
            clips[2] = AudioSystem.getClip();
            clips[2].open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("sounds/drive.wav"))));
        } catch (Exception e) {
        }

        //Setting value to true to start playing sounds
        soundTrigger = true;

        //Fill car1Images with images from path directory
        for (int i = 0; i < blueCarImages.length; i++) {
            blueCarImages[i] = new ImageIcon(Objects.requireNonNull(getClass().getResource("blueCar/" + i + ".png")));
            greenCarImages[i] = new ImageIcon(Objects.requireNonNull(getClass().getResource("greenCar/" + i + ".png")));
        }

        //Assigning stroke to 'dashedStroke' variable
        dashedStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 50, new float[]{30.6f, 0, 0}, 0);

        //Starting swing timer
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

        //Racetrack corners
        int xLim0;
        int xLim1;
        int yLim1;
        int yLim0;

        //Car x, y values
        int carX;
        int carY;

        //Displacement factor
        int disFac;

        //index of car images
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

        //Car is close to top right corner
        boolean nearTopR = carX > xLim1 - disFac * 5 && carX < xLim1;

        //Car is close to bottom right corner
        boolean nearlowerR = carY > yLim1 - disFac * 5 && carY < yLim1 && carDirection == 4;

        //Car is close to top left corner
        boolean nearTopL = carY <= yLim0 + disFac * 5 && carY > yLim0 && carDirection == 12;

        //Car is close to bottom left corner
        boolean nearlowerL = carX <= xLim0 + disFac * 5 && carX > xLim0;

        //Reduce car speed if near any corner
        if ((nearTopR || nearlowerR || nearTopL || nearlowerL) && disFac > 4) handleCarSpeedReduce(car);

    }

    void handleCarLeftTurn(int car) {
        //Rotate car 22.5 degrees anti-clockwise (Selecting from pre-rotated images)
        carPhase[car]--;
        if (carPhase[car] == -1) carPhase[car] = 15;
    }

    void handleCarRightTurn(int car) {
        //Rotate car 22.5 degrees clockwise (Selecting from pre-rotated images)
        carPhase[car]++;
        if (carPhase[car] == 16) carPhase[car] = 0;
    }

    void handleCarMovement(int car) {
        int xDiff = 0;
        int yDiff = 0;

        switch (carPhase[car]) {
            case 0, 8 -> {
                xDiff = displacementFactor[car];
                if (carPhase[car] == 8) xDiff *= -1; //Move car left
            }
            case 1, 2, 3, 5, 6, 7, 9, 10, 11, 13, 14, 15 -> {
                xDiff = displacementFactor[car] / 2;
                yDiff = displacementFactor[car] / 2;

                //Move car left
                if (carPhase[car] >= 5 && carPhase[car] <= 11) xDiff *= -1;

                //Move car up
                if (carPhase[car] >= 9 && carPhase[car] <= 15) yDiff *= -1;
            }
            case 4, 12 -> {
                yDiff = displacementFactor[car];
                if (carPhase[car] == 12) yDiff *= -1; //Move car up
            }
        }

        //Compare car position & speed and decide collision status
        handleCollision(car, xDiff, yDiff);
    }

    void handleUpdateVehicleInfo() {
        String carStatus = "Car 1 Speed:" + displacementFactor[0] * 10 + "  ";
        carStatus += "Car 2 Speed:" + displacementFactor[1] * 10;
        carSpeedLabel.setText(carStatus);
    }

    void handleCollision(int car, int xDiff, int yDiff) {
        int xIndex = car == 0 ? 0 : 2; //Current/Old X position of car
        int yIndex = car == 0 ? 1 : 3; //Current/Old Y position of car

        var carX = carPosition[xIndex] + xDiff; //New x position of car
        var carY = carPosition[yIndex] + yDiff; //New y position of car

        //If car intersects the centre or attempts leaving racetrack
        if (carX > 118 && carX < 682 && carY > 160 && carY < 487 || carX < 40 || carX > 760 || carY < 90 || carY > 560) {
            displacementFactor[car] = 0; //Slow car speed down
            handleUpdateVehicleInfo(); //Update vehicle information
            handleSoundTrigger(0); //Play respective sound (Swerve here)
        } else {
            Rectangle car1 = new Rectangle(carPosition[0], carPosition[1], 30, 20);
            Rectangle car2 = new Rectangle(carPosition[2], carPosition[3], 30, 20);

            //Check for collision
            if (car1.intersects(car2)) {
                handleSoundTrigger(1); //Play respective sound (Crash here)
                timer.stop();

                JOptionPane.showMessageDialog(this, "Game over, cars crashed. Type 'R' on the game UI to restart");
            } else {
                //Resumes normal car movement
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
            if (clips[index].getFramePosition() == frameLength) clips[index].setMicrosecondPosition(1); //Loop sound(s)
            clips[index].start(); //Play sound(s)
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    void resetCars() {
        carPosition = new int[]{430, 150, 430, 100}; //Re-Initialize X & Y values of both cars
        displacementFactor = new int[]{0, 0}; //Re-Initialize Displacement factor of both cars
        carPhase = new int[] {0, 0}; //Re-Initialize CarPhase of both cars
        timer.start(); //Restart timer
    }

    public void handleChanges(String[] str)
    {
        int car = Integer.parseInt(str[0]);
        if(car == ServerInitialization.getCarIndex()) return;

        carPhase[car] = Integer.parseInt(str[1]);
        carPosition[car == 0 ? 0 : 2] = Integer.parseInt(str[2]);
        carPosition[car == 0 ? 1 : 3] = Integer.parseInt(str[3]);
        displacementFactor[car] = Integer.parseInt(str[4]);
    }

    public void handleServerTransmission()
    {
        int i = ServerInitialization.getCarIndex();
        var carX = String.valueOf(carPosition[i == 0 ? 0 : 2]); //X position of car
        var carY = String.valueOf(carPosition[i == 0 ? 1 : 3]); //Y position of car

        String car = String.valueOf(i);
        String carDirection = String.valueOf(carPhase[i]);
        String disFactor = String.valueOf(displacementFactor[i]);
        String message = car + " "
                + carDirection + " " + carX + " " + carY + " "
                + disFactor + " ";

        try
        {
            DataTransmitter.sendMessage(message); //Send details to server
        } catch (Exception ex) {}
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try
        {
            if(!timer.isRunning() && e.getKeyCode() != KeyEvent.VK_R) return;
            switch (e.getKeyCode()) {
                //Controls for cars given by server
                case KeyEvent.VK_UP:    handleCarSpeedIncrease(ServerInitialization.getCarIndex()); break;
                case KeyEvent.VK_DOWN:  handleCarSpeedReduce(ServerInitialization.getCarIndex());   break;
                case KeyEvent.VK_LEFT:  handleCarLeftTurn(ServerInitialization.getCarIndex());       break;
                case KeyEvent.VK_RIGHT: handleCarRightTurn(ServerInitialization.getCarIndex());      break;
                case KeyEvent.VK_M: soundTrigger = !soundTrigger; break;

                //Reset game control
                case KeyEvent.VK_R:
                    if(!timer.isRunning())
                    {
                        try
                        {
                            DataTransmitter.sendMessage("restart"); //Send details to server
                        } catch (Exception ex) {}
                        resetCars();
                    }
                    break;
                default:
            }
            handleServerTransmission();
        }
        catch(Exception et)
        {

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Can be further developed to condition the vehicles to move <=> the action keys are held pressed
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
            handleCarMovement(i);
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
        g.drawRect(50, 100, 750, 500); //Outer edge of the racetrack
        g.setColor(Color.darkGray);
        g.fillRect(50, 100, 750, 500); //Outer edge of the racetrack

        g.setColor(Color.darkGray);
        g.fillRect(100, 150, 650, 400); //Racetrack lanes

        g.setStroke(dashedStroke);
        g.setColor(Color.yellow);
        g.drawRect(100, 150, 650, 400); //Lane Separators
        g.setStroke(new BasicStroke());

        g.setColor(Color.white);
        g.fillRoundRect(150, 200, 550, 300, 14, 14); //Inner field

        g.setColor(Color.white);
        g.drawLine(425, 100, 425, 200); //Start Line

        blueCarImages[carPhase[0]].paintIcon(this, g, carPosition[0], carPosition[1]);
        greenCarImages[carPhase[1]].paintIcon(this, g, carPosition[2], carPosition[3]);
    }
}
