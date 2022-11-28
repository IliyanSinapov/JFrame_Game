package main;

import javax.swing.*;
import java.awt.*;

public class GameWindow {
    private JFrame frame;

    public GameWindow(GamePanel panel) {
        frame = new JFrame();
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        panel.setBackground(new Color(43, 43, 43));
        frame.add(panel);

        frame.setVisible(true);
    }
}
