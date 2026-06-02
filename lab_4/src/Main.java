import app.MainFrame;
import app.PluginLoader;
import app.Registry;

import javax.swing.SwingUtilities;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Registry reg = Registry.defaults();
        PluginLoader.loadInto(Path.of("lab_4/plugins"), reg);
        SwingUtilities.invokeLater(() -> new MainFrame(reg).setVisible(true));
    }
}
