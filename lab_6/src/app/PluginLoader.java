package app;

import spi.ProcessorPlugin;
import spi.VehiclePlugin;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Stream;

// Loads VehiclePlugin (hierarchy) and ProcessorPlugin (functional) services from a folder of jars.
public final class PluginLoader {
    public static void loadInto(Path pluginsDir, Registry registry, Processors processors) {
        if (!Files.isDirectory(pluginsDir)) return;

        List<URL> jars = new ArrayList<>();

        try (Stream<Path> s = Files.list(pluginsDir)) {
            s.filter(p -> p.toString().toLowerCase().endsWith(".jar")).forEach(p -> {
                try {
                    jars.add(p.toUri().toURL());
                } catch (Exception e) {
                    System.out.println("An error was occurred: " + e.getMessage());
                }
            });
        } catch (IOException e) { return; }

        if (jars.isEmpty()) return;

        URLClassLoader cl = new URLClassLoader(jars.toArray(URL[]::new), PluginLoader.class.getClassLoader());

        for (VehiclePlugin p : ServiceLoader.load(VehiclePlugin.class, cl)) {
            System.out.println("Loaded plugin: " + p.getClass().getName());
            p.register(registry);
        }

        for (ProcessorPlugin p : ServiceLoader.load(ProcessorPlugin.class, cl)) {
            System.out.println("Loaded processor: " + p.getClass().getName());
            processors.add(p);
        }
    }
}
