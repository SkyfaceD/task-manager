package com.skyface.taskmanager.ui_test;

import javax.swing.*;
import java.awt.*;

public class JavaMain extends JFrame {

    public JPanel panelContainer;
    public JProgressBar pb;

    public JavaMain() {
        initFrame();
        initPB();
    }

    private void initPB() {
        pb.setBounds(0, panelContainer.getHeight() / 2, panelContainer.getWidth(), 10);
        pb.setIndeterminate(true);
        pb.setBorder(null);
        pb.setForeground(Color.RED);
        pb.setBackground(Color.BLUE);
    }

    private void initFrame() {
        panelContainer.setSize(500, 500);
        panelContainer.setLayout(null);
        setSize(panelContainer.getWidth(), panelContainer.getHeight());
        setContentPane(panelContainer);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new JavaMain();
    }
}
