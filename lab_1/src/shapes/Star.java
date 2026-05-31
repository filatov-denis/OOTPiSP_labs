package shapes;

import java.awt.Graphics2D;

public final class Star extends Shape {
    public Star(int x, int y, int w, int h) { super(x, y, w, h); }
    public void draw(Graphics2D g) {
        int cx = x + w / 2, cy = y + h / 2;
        double rOut = Math.min(w, h) / 2.0, rIn = rOut / 2.5;
        int[] xs = new int[10], ys = new int[10];
        for (int i = 0; i < 10; i++) {
            double a = -Math.PI / 2 + i * Math.PI / 5, r = (i % 2 == 0) ? rOut : rIn;
            xs[i] = (int) (cx + r * Math.cos(a));
            ys[i] = (int) (cy + r * Math.sin(a));
        }
        g.drawPolygon(xs, ys, 10);
    }
}
