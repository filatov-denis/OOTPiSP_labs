package vehicles;

public abstract class Vehicle {
    private String name = "";
    private int year;
    private int maxSpeed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getData() {
        return "Name: " + name + ", Year: " + year + ", Max speed: " + maxSpeed;
    }
}
