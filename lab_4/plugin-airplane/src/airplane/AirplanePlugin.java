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
            Field.str("name", Airplane::getName, Airplane::setName),
            Field.i("year", Airplane::getYear, Airplane::setYear),
            Field.i("maxSpeed", Airplane::getMaxSpeed, Airplane::setMaxSpeed),
            Field.d("wingspanM", Airplane::getWingspanM, Airplane::setWingspanM),
            Field.i("seats", Airplane::getSeats, Airplane::setSeats)
        );
        r.register(Airplane.class, new VehicleKind<>("Airplane", Airplane::new, fields));
    }
}
