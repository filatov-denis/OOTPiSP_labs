package shapes;

import java.awt.Graphics2D;

public final class Triangle extends Shape {
    public Triangle(int x, int y, int w, int h) { super(x, y, w, h); }
    public void draw(Graphics2D g) {
        int[] xs = { x, x + w / 2, x + w };
        int[] ys = { y + h, y, y + h };
        g.drawPolygon(xs, ys, 3);
    }
}
