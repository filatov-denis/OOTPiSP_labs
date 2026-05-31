package shapes;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public final class ShapeList {
    private final List<Shape> shapes = new ArrayList<>();
    public void add(Shape s) { shapes.add(s); }
    public void drawAll(Graphics2D g) { shapes.forEach(s -> s.draw(g)); }
}
