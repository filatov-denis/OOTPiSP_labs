package app;

import vehicles.Vehicle;

import java.util.function.BiConsumer;
import java.util.function.Function;

// One editable field on a vehicle.
public record Field<V extends Vehicle>(String name, Function<V, String> get, BiConsumer<V, String> set) {

    public static <V extends Vehicle> Field<V> str(String name, Function<V, String> get, BiConsumer<V, String> set) {
        return new Field<>(name, get, set);
    }
    public static <V extends Vehicle> Field<V> i(String name, Function<V, Integer> get, BiConsumer<V, Integer> set) {
        return new Field<>(name, v -> String.valueOf(get.apply(v)), (v, s) -> set.accept(v, Integer.parseInt(s)));
    }
    public static <V extends Vehicle> Field<V> d(String name, Function<V, Double> get, BiConsumer<V, Double> set) {
        return new Field<>(name, v -> String.valueOf(get.apply(v)), (v, s) -> set.accept(v, Double.parseDouble(s)));
    }

    @SuppressWarnings("unchecked")
    public String read(Vehicle v) { return get.apply((V) v); }
    @SuppressWarnings("unchecked")
    public void write(Vehicle v, String s) { set.accept((V) v, s); }
}
