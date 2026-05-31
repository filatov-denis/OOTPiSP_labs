package shapes;

import java.awt.Graphics2D;

public final class Line extends Shape {
    public Line(int x1, int y1, int x2, int y2) { super(x1, y1, x2, y2); }
    public void draw(Graphics2D g) { g.drawLine(x, y, w, h); }
}
