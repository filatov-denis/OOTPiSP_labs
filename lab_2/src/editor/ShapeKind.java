package editor;

import shapes.Shape;

import java.awt.*;

public record ShapeKind<T extends Shape>(String name, Factory<T> factory, Renderer<T> renderer) {
    public interface Factory<T extends Shape> { T create(int x, int y, int w, int h); }
    public interface Renderer<T extends Shape> { void draw(Graphics2D g, T shape); }

    @SuppressWarnings("unchecked")
    public void render(Graphics2D g, Shape s) { ((Renderer<Shape>) renderer).draw(g, s); }
}
