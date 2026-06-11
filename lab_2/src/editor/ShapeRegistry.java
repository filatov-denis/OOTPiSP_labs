package editor;

import shapes.*;
import shapes.Rectangle;
import shapes.Shape;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ShapeRegistry {
    private final Map<String, ShapeKind<?>> byName = new LinkedHashMap<>();
    private final Map<Class<? extends Shape>, ShapeKind<?>> byClass = new LinkedHashMap<>();

    public <T extends Shape> void register(ShapeKind<T> kind, Class<T> cls) {
        byName.put(kind.name(), kind);
        byClass.put(cls, kind);
    }

    public Iterable<ShapeKind<?>> kinds() { return byName.values(); }

    public void render(Graphics2D g, Shape s) { byClass.get(s.getClass()).render(g, s); }

    public static ShapeRegistry defaults() {
        ShapeRegistry r = new ShapeRegistry();

        //Add draw options
        r.register(new ShapeKind<>("Line", Line::new, (g, s) -> g.drawLine(s.x0, s.y0, s.x1, s.y1)), Line.class);
        r.register(new ShapeKind<>("Rectangle", Rectangle::new, (g, s) -> g.drawRect(s.x0, s.y0, s.x1, s.y1)), Rectangle.class);
        r.register(new ShapeKind<>("Ellipse", Ellipse::new, (g, s) -> g.drawOval(s.x0, s.y0, s.x1, s.y1)), Ellipse.class);
        r.register(new ShapeKind<>("Triangle", Triangle::new, (g, s) ->
                g.drawPolygon(new int[] { s.x0, s.x0 + s.x1 / 2, s.x0 + s.x1}, new int[] { s.y0 + s.y1, s.y0, s.y0 + s.y1}, 3)), Triangle.class);
        r.register(new ShapeKind<>("Diamond", Diamond::new, (g, s) -> {
            int cx = s.x0 + s.x1 / 2, cy = s.y0 + s.y1 / 2;
            g.drawPolygon(new int[] { s.x0, cx, s.x0 + s.x1, cx }, new int[] { cy, s.y0, cy, s.y0 + s.y1}, 4);
        }), Diamond.class);
        r.register(new ShapeKind<>("Star", Star::new, (g, s) -> {
            int cx = s.x0 + s.x1 / 2, cy = s.y0 + s.y1 / 2;
            double rOut = Math.min(Math.abs(s.x1), Math.abs(s.y1)) / 2.0, rIn = rOut / 2.5;
            int[] xs = new int[10], ys = new int[10];
            for (int i = 0; i < 10; i++) {
                double a = -Math.PI / 2 + i * Math.PI / 5, rr = (i % 2 == 0) ? rOut : rIn;
                xs[i] = (int) (cx + rr * Math.cos(a));
                ys[i] = (int) (cy + rr * Math.sin(a));
            }
            g.drawPolygon(xs, ys, 10);
        }), Star.class);

        return r;
    }
}
