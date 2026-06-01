package editor;

import shapes.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public final class Canvas extends JPanel {
    private final ShapeRegistry registry;
    private final List<Shape> shapes = new ArrayList<>();
    private ShapeKind<?> tool;
    private int x0, y0, x1, y1;
    private boolean dragging;

    public Canvas(ShapeRegistry registry) {
        this.registry = registry;
        MouseAdapter m = new MouseAdapter() {
            public void mousePressed(MouseEvent e)  { x0 = x1 = e.getX(); y0 = y1 = e.getY(); dragging = tool != null; repaint(); }
            public void mouseDragged(MouseEvent e)  { x1 = e.getX(); y1 = e.getY(); repaint(); }
            public void mouseReleased(MouseEvent e) {
                if (!dragging) return;
                dragging = false;
                shapes.add(tool.factory().create(x0, y0, x1, y1));
                repaint();
            }
        };
        addMouseListener(m);
        addMouseMotionListener(m);
    }

    public void setTool(ShapeKind<?> t) { tool = t; }
    public void clear() { shapes.clear(); repaint(); }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        shapes.forEach(s -> registry.render(g2, s));
        if (dragging) {
            Shape preview = tool.factory().create(x0, y0, x1, y1);
            registry.render(g2, preview);
        }
    }
}
