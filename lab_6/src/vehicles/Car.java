package vehicles;
public final class Car extends Vehicle {
    private int doors;
    private String fuel = "";

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getData() {
        return super.getData() + ", Doors: " + doors + ", Fuel: " + fuel;
    }
}
