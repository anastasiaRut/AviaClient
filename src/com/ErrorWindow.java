package com;

import javax.swing.*;
import java.awt.*;

public class ErrorWindow extends JFrame {
    private Font font;
    private JFrame frame;
    private JTextArea text;

    public ErrorWindow() {
        font = new Font("Verdana", Font.PLAIN, 11);
        frame = new JFrame("Ошибка");
        text = new JTextArea("Нет подключения к серверу!");
        frame.add(text);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(320, 170));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });


    }


}
