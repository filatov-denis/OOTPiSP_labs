package vehicles;
public final class Truck extends Vehicle {
    private int cargoTons;

    public int getCargoTons() {
        return cargoTons;
    }

    public void setCargoTons(int cargoTons) {
        this.cargoTons = cargoTons;
    }

    public String getData() {
        return super.getData() + ", Cargo tons: " + cargoTons;
    }
}
