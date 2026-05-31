package shapes;

import java.awt.Graphics2D;

public final class Rectangle extends Shape {
    public Rectangle(int x, int y, int w, int h) { super(x, y, w, h); }
    public void draw(Graphics2D g) { g.drawRect(x, y, w, h); }
}
