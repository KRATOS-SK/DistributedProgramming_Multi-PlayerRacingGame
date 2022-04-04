package com.sreekanth.final_assignment.part2;
import javax.swing.*;

public class PartTwo {
    public static void main(String[] args) {
        AppForm mainForm = new AppForm();

        mainForm.setSize(850, 650);
        mainForm.setResizable(false);
        mainForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var gameStartDialog = new AppDialog(mainForm, true);
        gameStartDialog.setVisible(true);
    }
}
