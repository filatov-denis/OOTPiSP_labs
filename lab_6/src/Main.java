import app.AppContext;
import app.MainFrame;
import app.PluginLoader;

import javax.swing.SwingUtilities;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        AppContext ctx = AppContext.get();
        PluginLoader.loadInto(Path.of("lab_6/plugins"), ctx.registry, ctx.processors);
        SwingUtilities.invokeLater(() -> new MainFrame(ctx.registry, ctx.processors).setVisible(true));
    }
}
