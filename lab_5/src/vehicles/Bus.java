package vehicles;

public final class Bus extends Vehicle {
    private int passengers;

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public String getData() {
        return super.getData() + ", Passengers: " + passengers;
    }
}
