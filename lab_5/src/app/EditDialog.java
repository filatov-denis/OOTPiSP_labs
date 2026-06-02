package app;

import vehicles.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

// Generic editor: a JTextField per field.
public final class EditDialog {
    public static boolean edit(Component parent, VehicleKind<?> kind, Vehicle v) {
        Map<Field<Vehicle>, JTextField> inputs = new LinkedHashMap<>();
        JPanel p = new JPanel(new GridLayout(0, 2, 4, 4));
        for (Field<Vehicle> f : kind.fieldsErased()) {
            p.add(new JLabel(f.name()));
            JTextField tf = new JTextField(f.read(v), 16);
            inputs.put(f, tf);
            p.add(tf);
        }
        int ok = JOptionPane.showConfirmDialog(parent, p, "Edit " + kind.name(), JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return false;
        try {
            inputs.forEach((f, tf) -> f.write(v, tf.getText()));
            return true;
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(parent, ex.getMessage(), "Bad input", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
