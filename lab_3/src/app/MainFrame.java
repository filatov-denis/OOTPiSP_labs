package app;

import vehicles.Vehicle;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class MainFrame extends JFrame {
    private final Registry reg = Registry.defaults();
    private final XmlSerializer ser = new XmlSerializer(reg);
    private final DefaultListModel<Vehicle> model = new DefaultListModel<>();
    private final JList<Vehicle> list = new JList<>(model);

    public MainFrame() {
        super("Lab 3 — Vehicles (XML)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 480);
        setLayout(new BorderLayout());

        list.setCellRenderer((l, v, i, sel, foc) -> {
            JLabel lbl = new JLabel(reg.of(v).name() + ": " + v.name);
            if (sel) { lbl.setOpaque(true); lbl.setBackground(l.getSelectionBackground()); lbl.setForeground(l.getSelectionForeground()); }
            return lbl;
        });
        add(new JScrollPane(list), BorderLayout.CENTER);

        JMenuBar bar = new JMenuBar();
        JMenu addMenu = new JMenu("Add");
        for (VehicleKind<?> k : reg.kinds()) {
            JMenuItem mi = new JMenuItem(k.name());
            mi.addActionListener(e -> {
                Vehicle v = k.ctor().get();
                if (EditDialog.edit(this, k, v)) model.addElement(v);
            });
            addMenu.add(mi);
        }
        JMenu act = new JMenu("Item");
        JMenuItem edit = new JMenuItem("Edit");
        edit.addActionListener(e -> {
            Vehicle v = list.getSelectedValue();
            if (v != null) { EditDialog.edit(this, reg.of(v), v); list.repaint(); }
        });
        JMenuItem del = new JMenuItem("Delete");
        del.addActionListener(e -> { int i = list.getSelectedIndex(); if (i >= 0) model.remove(i); });
        act.add(edit); act.add(del);

        JMenu file = new JMenu("File");
        JMenuItem save = new JMenuItem("Save…");
        JMenuItem load = new JMenuItem("Load…");
        save.addActionListener(e -> withChooser(true, p -> Files.write(p, ser.toBytes(toList()))));
        load.addActionListener(e -> withChooser(false, p -> {
            List<Vehicle> loaded = ser.fromBytes(Files.readAllBytes(p));
            model.clear();
            loaded.forEach(model::addElement);
        }));
        file.add(save); file.add(load);

        bar.add(file); bar.add(addMenu); bar.add(act);
        setJMenuBar(bar);
    }

    @FunctionalInterface private interface IOAction { void run(Path p) throws Exception; }

    private void withChooser(boolean save, IOAction act) {
        JFileChooser c = new JFileChooser();
        c.setFileFilter(new FileNameExtensionFilter("XML files (*.xml)", "xml"));
        c.setAcceptAllFileFilterUsed(false);
        int r = save ? c.showSaveDialog(this) : c.showOpenDialog(this);
        if (r != JFileChooser.APPROVE_OPTION) return;

        try {
            Path path = c.getSelectedFile().toPath();

            // Automatically add .xml extension when saving
            if (save && !path.toString().toLowerCase().endsWith(".xml")) {
                path = Path.of(path + ".xml");
            }

            act.run(path);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "IO error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private List<Vehicle> toList() { return java.util.Collections.list(model.elements()); }
}
