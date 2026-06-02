package vehicles;

// Common state for every vehicle in the hierarchy. Concrete subclasses add their own public fields.
public abstract class Vehicle {
    public String name = "";
    public int year;
    public int maxSpeed;
}
