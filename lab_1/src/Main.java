import shapes.*;
import shapes.Rectangle;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static final ShapeList LIST = new ShapeList();
    static {
        LIST.add(new Line(20, 20, 200, 60));
        LIST.add(new Rectangle(40, 80, 160, 100));
        LIST.add(new Ellipse(240, 80, 160, 100));
        LIST.add(new Triangle(40, 220, 160, 120));
        LIST.add(new Diamond(240, 220, 160, 120));
        LIST.add(new Star(440, 100, 140, 140));
    }

    static void main(String[] args) {
        JFrame f = new JFrame("Lab 1 — Shapes");
        f.add(new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                LIST.drawAll((Graphics2D) g);
            }
        });
        f.setSize(640, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
