package app;

import org.w3c.dom.*;
import vehicles.Vehicle;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// Variant 1 — XML. Each vehicle is a <vehicle> element; the kind goes in "type", every field
// becomes an attribute. Built-in javax.xml does the parsing;
public final class XmlSerializer {
    private static final DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
    private final Registry reg;
    public XmlSerializer(Registry reg) { this.reg = reg; }

    public byte[] toBytes(List<Vehicle> list) {
        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<vehicles>\n");
        for (Vehicle v : list) {
            VehicleKind<?> k = reg.of(v);
            sb.append("  <vehicle type=\"").append(esc(k.name())).append('"');
            for (Field<Vehicle> f : k.fieldsErased())
                sb.append(' ').append(f.name()).append("=\"").append(esc(f.read(v))).append('"');
            sb.append("/>\n");
        }
        return sb.append("</vehicles>\n").toString().getBytes(StandardCharsets.UTF_8);
    }

    public List<Vehicle> fromBytes(byte[] data) throws Exception {
        Document doc = DBF.newDocumentBuilder().parse(new ByteArrayInputStream(data));
        NodeList items = doc.getDocumentElement().getElementsByTagName("vehicle");
        List<Vehicle> out = new ArrayList<>(items.getLength());
        for (int i = 0; i < items.getLength(); i++) {
            Element e = (Element) items.item(i);
            VehicleKind<?> k = reg.byName(e.getAttribute("type"));
            if (k == null) continue;
            Vehicle v = k.ctor().get();
            NamedNodeMap attrs = e.getAttributes();
            for (Field<Vehicle> f : k.fieldsErased()) {
                Node a = attrs.getNamedItem(f.name());
                if (a != null) f.write(v, a.getNodeValue());
            }
            out.add(v);
        }
        return out;
    }

    private static String esc(String s) {
        return s.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;");
    }
}
