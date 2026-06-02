import app.MainFrame;
import app.PluginLoader;
import app.Processors;
import app.Registry;

import javax.swing.SwingUtilities;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Registry reg = Registry.defaults();
        Processors procs = new Processors();
        PluginLoader.loadInto(Path.of("lab_5/plugins"), reg, procs);
        SwingUtilities.invokeLater(() -> new MainFrame(reg, procs).setVisible(true));
    }
}
