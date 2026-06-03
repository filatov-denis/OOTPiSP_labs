package app;

import spi.ProcessorPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

// Holds all discovered processor plugins and tracks which ones are currently enabled.
public final class Processors {
    private final List<ProcessorPlugin> all = new ArrayList<>();
    private final Map<String, Boolean> enabled = new HashMap<>();

    public void add(ProcessorPlugin p) { all.add(p); enabled.put(p.name(), false); }
    public List<ProcessorPlugin> all() { return all; }
    public boolean isEnabled(String name) { return enabled.getOrDefault(name, false); }
    public void setEnabled(String name, boolean on) { enabled.put(name, on); }

    // Save: apply enabled processors in registered order.
    public byte[] applySave(byte[] data) throws Exception {
        for (ProcessorPlugin p : all) if (isEnabled(p.name())) data = p.onSave(data);
        return data;
    }

    // Load: reverse order so each processor sees the output of the matching save step.
    public byte[] applyLoad(byte[] data) throws Exception {
        for (ListIterator<ProcessorPlugin> it = all.listIterator(all.size()); it.hasPrevious(); ) {
            ProcessorPlugin p = it.previous();
            if (isEnabled(p.name())) data = p.onLoad(data);
        }
        return data;
    }
}
