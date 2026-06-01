package app;

import vehicles.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Registry {
    private final Map<String, VehicleKind<?>> byName = new LinkedHashMap<>();
    private final Map<Class<? extends Vehicle>, VehicleKind<?>> byClass = new LinkedHashMap<>();

    public <V extends Vehicle> void register(Class<V> cls, VehicleKind<V> kind) {
        byName.put(kind.name(), kind);
        byClass.put(cls, kind);
    }

    public VehicleKind<?> byName(String name) { return byName.get(name); }
    public VehicleKind<?> of(Vehicle v) { return byClass.get(v.getClass()); }
    public Iterable<VehicleKind<?>> kinds() { return byName.values(); }

    private static <V extends Vehicle> List<Field<V>> common() {
        return List.of(
            Field.str("name", v -> v.name, (v, s) -> v.name = s),
            Field.i("year", v -> v.year, (v, n) -> v.year = n),
            Field.i("maxSpeed", v -> v.maxSpeed, (v, n) -> v.maxSpeed = n)
        );
    }

    private static <V extends Vehicle> List<Field<V>> combine(List<Field<V>> extra) {
        List<Field<V>> all = new ArrayList<>(Registry.<V>common());
        all.addAll(extra);
        return List.copyOf(all);
    }

    public static Registry defaults() {
        Registry r = new Registry();
        r.register(Car.class, new VehicleKind<>("Car", Car::new, combine(List.of(
            Field.i("doors", v -> v.doors, (v, n) -> v.doors = n),
            Field.str("fuel", v -> v.fuel, (v, s) -> v.fuel = s)
        ))));
        r.register(Truck.class, new VehicleKind<>("Truck", Truck::new, combine(List.of(
            Field.i("cargoTons", v -> v.cargoTons, (v, n) -> v.cargoTons = n)
        ))));
        r.register(Motorcycle.class, new VehicleKind<>("Motorcycle", Motorcycle::new, combine(List.of(
            Field.i("engineCc", v -> v.engineCc, (v, n) -> v.engineCc = n)
        ))));
        r.register(Bus.class, new VehicleKind<>("Bus", Bus::new, combine(List.of(
            Field.i("passengers", v -> v.passengers, (v, n) -> v.passengers = n)
        ))));
        r.register(Bicycle.class, new VehicleKind<>("Bicycle", Bicycle::new, combine(List.of(
            Field.i("gears", v -> v.gears, (v, n) -> v.gears = n)
        ))));
        r.register(Boat.class, new VehicleKind<>("Boat", Boat::new, combine(List.of(
            Field.d("lengthM", v -> v.lengthM, (v, x) -> v.lengthM = x)
        ))));
        return r;
    }
}
