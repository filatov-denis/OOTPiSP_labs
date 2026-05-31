package shapes;

import java.awt.Graphics2D;

public final class Diamond extends Shape {
    public Diamond(int x, int y, int w, int h) { super(x, y, w, h); }
    public void draw(Graphics2D g) {
        int cx = x + w / 2, cy = y + h / 2;
        g.drawPolygon(new int[] { x, cx, x + w, cx }, new int[] { cy, y, cy, y + h }, 4);
    }
}
