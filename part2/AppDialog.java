package com.sreekanth.final_assignment.part2;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AppDialog extends JDialog {
    public AppDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {
        JPanel panelGreenCarIcon = new JPanel();
        JPanel panelBlueCarIcon = new JPanel();

        JLabel labelControlsCar1 = new JLabel();
        JLabel labelControlsCar2 = new JLabel();
        JLabel labelAccelerateBlueCar = new JLabel();
        JLabel labelDecelerateBlueCar = new JLabel();
        JLabel labelLeftTurnBlueCar = new JLabel();
        JLabel labelRightTurnBlueCar = new JLabel();
        JLabel labelAccelerateGreenCar = new JLabel();
        JLabel labelDecelerateGreenCar = new JLabel();
        JLabel labelLeftTurnGreenCar = new JLabel();
        JLabel labelRightTurnGreenCar = new JLabel();
        JLabel labelInstructionTextBox = new JLabel();
        JLabel labelMuteSound = new JLabel();
        JLabel labelBlueCarIcon = new JLabel();
        JLabel labelGreenCarIcon = new JLabel();

        JScrollPane instructionScrollPane = new JScrollPane();
        JTextArea instructionTextArea = new JTextArea();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        labelControlsCar1.setFont(new java.awt.Font("Serif", Font.BOLD, 11));
        labelControlsCar1.setText("Controls for car 1");

        panelGreenCarIcon.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        labelGreenCarIcon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("greenCar/0.png"))));
        GroupLayout jPanel1Layout = new GroupLayout(panelGreenCarIcon);
        panelGreenCarIcon.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(30, Short.MAX_VALUE)
                    .addComponent(labelGreenCarIcon)
                    .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(labelGreenCarIcon, GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
        );

        panelBlueCarIcon.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        labelBlueCarIcon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("blueCar/0.png"))));
        GroupLayout jPanel2Layout = new GroupLayout(panelBlueCarIcon);
        panelBlueCarIcon.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(26, 26, 26)
                    .addComponent(labelBlueCarIcon)
                    .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(labelBlueCarIcon, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        labelControlsCar2.setFont(new java.awt.Font("Serif", Font.BOLD, 11));
        labelControlsCar2.setText("Controls for car 2");

        labelAccelerateBlueCar.setText("Accelerate  :   Up");

        labelDecelerateBlueCar.setText("Decelerate  :   Down");

        labelLeftTurnBlueCar.setText("Turn Left     :   Left");

        labelRightTurnBlueCar.setText("Turn Right   :   Right");

        labelAccelerateGreenCar.setText("Accelerate  :   W");

        labelDecelerateGreenCar.setText("Decelerate  :   S");

        labelLeftTurnGreenCar.setText("Turn Left      :   A");

        labelRightTurnGreenCar.setText("Turn Right    :   D");

        instructionTextArea.setEditable(false);
        instructionTextArea.setColumns(20);
        instructionTextArea.setFont(new java.awt.Font("Monospaced", 0, 12));
        instructionTextArea.setLineWrap(true);
        instructionTextArea.setRows(5);
        instructionTextArea.setText("Cars in this game have speeds\nranging from 0 - 100.\n\nAccelerate and Deceletate controls " +
                "will alter car speed by 10.\n\nIn the event of hitting the inner grass or boundraries of the race trace, the " +
                "car speed willbe reduced.\n\nIn the event of both cars colliding, the game will end.");
        instructionTextArea.setFocusable(false);
        instructionScrollPane.setViewportView(instructionTextArea);

        labelInstructionTextBox.setFont(new java.awt.Font("Tahoma", 1, 11));
        labelInstructionTextBox.setText("Information");

        labelMuteSound.setText("Mute Sounds  :  M");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(labelControlsCar1)
                                .addComponent(panelBlueCarIcon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelAccelerateBlueCar)
                                .addComponent(labelDecelerateBlueCar)
                                .addComponent(labelLeftTurnBlueCar)
                                .addComponent(labelRightTurnBlueCar))
                            .addGap(72, 72, 72)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(2, 2, 2)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(labelAccelerateGreenCar)
                                        .addComponent(labelDecelerateGreenCar)
                                        .addComponent(labelLeftTurnGreenCar)
                                        .addComponent(labelRightTurnGreenCar)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(2, 2, 2)
                                    .addComponent(labelControlsCar2))
                                .addComponent(panelGreenCarIcon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(10, 10, 10))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(labelMuteSound)
                            .addGap(102, 102, 102)))
                    .addComponent(instructionScrollPane, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelInstructionTextBox)
                    .addGap(133, 133, 133))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(labelInstructionTextBox)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(instructionScrollPane)
                            .addContainerGap())
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(panelBlueCarIcon, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panelGreenCarIcon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(labelControlsCar1)
                                .addComponent(labelControlsCar2))
                            .addGap(27, 27, 27)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(labelAccelerateBlueCar)
                                    .addGap(13, 13, 13)
                                    .addComponent(labelDecelerateBlueCar)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(labelLeftTurnBlueCar)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(labelRightTurnBlueCar))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(labelAccelerateGreenCar)
                                    .addGap(13, 13, 13)
                                    .addComponent(labelDecelerateGreenCar)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(labelLeftTurnGreenCar)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(labelRightTurnGreenCar)))
                            .addGap(18, 18, 18)
                            .addComponent(labelMuteSound)
                            .addGap(16, 16, 16))))
        );

        pack();
    }
}
