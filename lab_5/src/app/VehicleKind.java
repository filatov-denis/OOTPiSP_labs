package app;

import vehicles.Vehicle;

import java.util.List;
import java.util.function.Supplier;

public record VehicleKind<V extends Vehicle>(String name, Supplier<V> ctor, List<Field<V>> fields) {
    @SuppressWarnings("unchecked")
    public List<Field<Vehicle>> fieldsErased() { return (List<Field<Vehicle>>) (List<?>) fields; }
}
