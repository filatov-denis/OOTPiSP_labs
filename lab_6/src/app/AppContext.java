package app;

// Singleton: one shared registry + processors instance for the whole UI.
public final class AppContext {
    private AppContext() {}
    private static final class Holder { static final AppContext I = new AppContext(); }
    public static AppContext get() { return Holder.I; }

    public final Registry registry = Registry.defaults();
    public final Processors processors = new Processors();
}
