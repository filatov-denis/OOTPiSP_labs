package spi;

import app.Registry;

// Plugin entry point. ServiceLoader finds implementations via META-INF/services declarations.
public interface VehiclePlugin {
    void register(Registry registry);
}
