package shapes;

import java.awt.Graphics2D;

public abstract class Shape {
    protected final int x, y, w, h;
    protected Shape(int x, int y, int w, int h) { this.x = x; this.y = y; this.w = w; this.h = h; }
    public abstract void draw(Graphics2D g);
}
