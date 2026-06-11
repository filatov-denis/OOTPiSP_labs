package vehicles;
public final class Bicycle extends Vehicle {
    private int gears;

    public int getGears() {
        return gears;
    }

    public void setGears(int gears) {
        this.gears = gears;
    }

    public String getData() {
        return super.getData() + ", Gears: " + gears;
    }
}
