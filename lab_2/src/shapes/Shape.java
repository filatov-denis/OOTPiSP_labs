package shapes;

public abstract class Shape {
    public final int x0, y0, x1, y1;
    protected Shape(int x0, int y, int x1, int y1) { this.x0 = x0; this.y0 = y; this.x1 = x1; this.y1 = y1; }
}
