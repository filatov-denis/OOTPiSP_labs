package prettyjson;

import app.Json;
import spi.ProcessorPlugin;

import java.nio.charset.StandardCharsets;

// Companion to XmlJsonPlugin: indents JSON output.
public final class PrettyJsonPlugin implements ProcessorPlugin {
    public String name() { return "Pretty JSON"; }
    public byte[] onSave(byte[] data) { return reformat(data, true); }
    public byte[] onLoad(byte[] data) { return reformat(data, false); }

    private static byte[] reformat(byte[] data, boolean pretty) {
        String s = new String(data, StandardCharsets.UTF_8).stripLeading();
        if (s.isEmpty() || (s.charAt(0) != '{' && s.charAt(0) != '[')) return data;
        return Json.write(Json.read(s), pretty).getBytes(StandardCharsets.UTF_8);
    }
}
