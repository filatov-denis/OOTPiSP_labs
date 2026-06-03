package airplane;

import vehicles.Vehicle;

public final class Airplane extends Vehicle {
    private double wingspanM;
    private int seats;

    public double getWingspanM() {
        return wingspanM;
    }

    public void setWingspanM(double wingspanM) {
        this.wingspanM = wingspanM;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
