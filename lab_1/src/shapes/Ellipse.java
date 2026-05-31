package shapes;

import java.awt.Graphics2D;

public final class Ellipse extends Shape {
    public Ellipse(int x, int y, int w, int h) { super(x, y, w, h); }
    public void draw(Graphics2D g) { g.drawOval(x, y, w, h); }
}
