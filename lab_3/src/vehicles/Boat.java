package vehicles;
public final class Boat extends Vehicle {
    private double lengthM;

    public double getLengthM() {
        return lengthM;
    }

    public void setLengthM(double lengthM) {
        this.lengthM = lengthM;
    }

    public String getData() {
        return super.getData() + ", Length: " + lengthM;
    }
}
