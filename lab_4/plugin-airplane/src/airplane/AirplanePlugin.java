package airplane;

import app.Field;
import app.Registry;
import app.VehicleKind;
import spi.VehiclePlugin;

import java.util.List;

// Plugin that contributes the Airplane class plus its editable fields.
public final class AirplanePlugin implements VehiclePlugin {
    public void register(Registry r) {
        List<Field<Airplane>> fields = List.of(
            Field.str("name", v -> v.name, (v, s) -> v.name = s),
            Field.i("year", v -> v.year, (v, n) -> v.year = n),
            Field.i("maxSpeed", v -> v.maxSpeed, (v, n) -> v.maxSpeed = n),
            Field.d("wingspanM", v -> v.wingspanM, (v, x) -> v.wingspanM = x),
            Field.i("seats", v -> v.seats, (v, n) -> v.seats = n)
        );
        r.register(Airplane.class, new VehicleKind<>("Airplane", Airplane::new, fields));
    }
}
