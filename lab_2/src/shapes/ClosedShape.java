package shapes;

public abstract class ClosedShape extends Shape { public ClosedShape(int x0, int y0, int x1, int y1) { super(Math.min(x0, x1), Math.min(y0, y1), Math.abs(x1 - x0), Math.abs(y1 - y0)); } }
