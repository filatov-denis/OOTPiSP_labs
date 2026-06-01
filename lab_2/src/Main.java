import editor.Canvas;
import editor.ShapeKind;
import editor.ShapeRegistry;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        ShapeRegistry registry = ShapeRegistry.defaults();
        Canvas canvas = new Canvas(registry);

        // Toolbar is built from the registry
        JToolBar bar = new JToolBar();
        ButtonGroup group = new ButtonGroup();
        ShapeKind<?> first = null;
        for (ShapeKind<?> k : registry.kinds()) {
            JToggleButton b = new JToggleButton(k.name());
            b.addActionListener(e -> canvas.setTool(k));
            bar.add(b);
            group.add(b);
            if (first == null) { first = k; b.setSelected(true); }
        }
        canvas.setTool(first);
        JButton clear = new JButton("Clear");
        clear.addActionListener(e -> canvas.clear());
        bar.addSeparator();
        bar.add(clear);

        JFrame f = new JFrame("Lab 2 — Editor");
        f.setLayout(new BorderLayout());
        f.add(bar, BorderLayout.NORTH);
        f.add(canvas, BorderLayout.CENTER);
        f.setSize(800, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
