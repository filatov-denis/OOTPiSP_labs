package xmljson;

import app.Json;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import spi.ProcessorPlugin;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Variant 1 — the core conversion. Save: <vehicles><vehicle .../></vehicles> turns into
// {"vehicles":[{...},...]}. Load: the inverse.
public final class XmlJsonPlugin implements ProcessorPlugin {
    private static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<vehicles>\n";
    private static final String XML_TAIL = "</vehicles>\n";
    private static final DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();

    public String name() { return "XML <--> JSON"; }

    public byte[] onSave(byte[] data) throws Exception {
        NodeList items = DBF.newDocumentBuilder().parse(new ByteArrayInputStream(data))
                .getDocumentElement().getElementsByTagName("vehicle");
        List<Object> arr = new ArrayList<>(items.getLength());
        for (int i = 0; i < items.getLength(); i++) {
            NamedNodeMap attrs = ((Element) items.item(i)).getAttributes();
            Map<String, Object> obj = new LinkedHashMap<>();
            for (int j = 0; j < attrs.getLength(); j++) {
                Node a = attrs.item(j);
                obj.put(a.getNodeName(), a.getNodeValue());
            }
            arr.add(obj);
        }
        return Json.write(Map.of("vehicles", arr), false).getBytes(StandardCharsets.UTF_8);
    }

    @SuppressWarnings("unchecked")
    public byte[] onLoad(byte[] data) {
        Map<String, Object> root = (Map<String, Object>) Json.read(new String(data, StandardCharsets.UTF_8));
        List<Object> arr = (List<Object>) root.get("vehicles");
        StringBuilder sb = new StringBuilder(XML_HEAD);
        for (Object o : arr) {
            sb.append("  <vehicle");
            for (Map.Entry<String, Object> e : ((Map<String, Object>) o).entrySet())
                sb.append(' ').append(e.getKey()).append("=\"").append(esc(e.getValue().toString())).append('"');
            sb.append("/>\n");
        }
        return sb.append(XML_TAIL).toString().getBytes(StandardCharsets.UTF_8);
    }

    private static String esc(String s) {
        return s.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;");
    }
}
