package app;

import vehicles.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Registry {
    private final Map<String, VehicleKind<?>> byName = new LinkedHashMap<>();
    private final Map<Class<? extends Vehicle>, VehicleKind<?>> byClass = new LinkedHashMap<>();

    //Register vehicle types in program
    public <V extends Vehicle> void register(Class<V> cls, VehicleKind<V> kind) {
        byName.put(kind.name(), kind);
        byClass.put(cls, kind);
    }

    public VehicleKind<?> byName(String name) { return byName.get(name); }
    public VehicleKind<?> of(Vehicle v) { return byClass.get(v.getClass()); }
    public Iterable<VehicleKind<?>> kinds() { return byName.values(); }

    private static <V extends Vehicle> List<Field<V>> common() {
        return List.of(
            Field.str("name", Vehicle::getName, Vehicle::setName),
            Field.i("year", Vehicle::getYear, Vehicle::setYear),
            Field.i("maxSpeed", Vehicle::getMaxSpeed, Vehicle::setMaxSpeed)
        );
    }

    private static <V extends Vehicle> List<Field<V>> combine(List<Field<V>> extra) {
        List<Field<V>> all = new ArrayList<>(Registry.common());
        all.addAll(extra);
        return List.copyOf(all);
    }

    //Register default vehicle types in program
    public static Registry defaults() {
        Registry r = new Registry();
        r.register(Car.class, new VehicleKind<>("Car", Car::new, combine(List.of(
            Field.i("doors", Car::getDoors, Car::setDoors),
            Field.str("fuel", Car::getFuel, Car::setFuel)
        ))));
        r.register(Truck.class, new VehicleKind<>("Truck", Truck::new, combine(List.of(
            Field.i("cargoTons", Truck::getCargoTons, Truck::setCargoTons)
        ))));
        r.register(Motorcycle.class, new VehicleKind<>("Motorcycle", Motorcycle::new, combine(List.of(
            Field.i("engineCc", Motorcycle::getEngineCc, Motorcycle::setEngineCc)
        ))));
        r.register(Bus.class, new VehicleKind<>("Bus", Bus::new, combine(List.of(
            Field.i("passengers", Bus::getPassengers, Bus::setPassengers)
        ))));
        r.register(Bicycle.class, new VehicleKind<>("Bicycle", Bicycle::new, combine(List.of(
            Field.i("gears", Bicycle::getGears, Bicycle::setGears)
        ))));
        r.register(Boat.class, new VehicleKind<>("Boat", Boat::new, combine(List.of(
            Field.d("lengthM", Boat::getLengthM, Boat::setLengthM)
        ))));
        return r;
    }
}
