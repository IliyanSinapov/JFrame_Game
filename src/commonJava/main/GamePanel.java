package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    public ArrayList<Rectangle> rects = new ArrayList<>();

    public GamePanel() {

        mouseInputs = new MouseInputs(this);

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < rects.size(); i++) {
            g.setColor(rects.get(i).color);
            g.fillRect(rects.get(i).x, rects.get(i).y, rects.get(i).w, rects.get(i).h);
        }
    }

    public void addRects(int x, int y, int w, int h, Color color) {
        rects.add(new GamePanel.Rectangle(x * w, y * h, w, h, color));
    }

    public static class Rectangle {
        int x, y, w, h;
        Color color;

        public Rectangle(int x, int y, int w, int h, Color color) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.color = color;
        }

        public void draw(Graphics g) {
            g.setColor(color);
            g.fillRect(x, y, w, h);
        }
    }
}
