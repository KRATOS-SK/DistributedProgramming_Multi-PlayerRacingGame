package com.sreekanth.final_assignment.part1;

import javax.swing.*;

public class PartOne {

    public static void main(String[] args) {
        AppForm mainForm = new AppForm();

        mainForm.setSize(200, 200);
        mainForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainForm.setResizable(false);
        mainForm.setVisible(true);
    }
}