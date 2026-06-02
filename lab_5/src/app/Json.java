package app;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Handles objects, arrays and strings — the only shapes our vehicle payloads need.
public final class Json {
    public static String write(Object v, boolean pretty) {
        StringBuilder sb = new StringBuilder();
        write(sb, v, pretty ? 0 : -1);
        return sb.toString();
    }

    public static Object read(String s) {
        int[] pos = { 0 };
        skip(s, pos);
        return readValue(s, pos);
    }

    private static void write(StringBuilder sb, Object v, int indent) {
        if (v instanceof Map<?, ?> m) {
            sb.append('{');
            int i = 0;
            for (Map.Entry<?, ?> e : m.entrySet()) {
                if (i++ > 0) sb.append(',');
                nl(sb, indent + 1);
                writeStr(sb, e.getKey().toString());
                sb.append(':');
                if (indent >= 0) sb.append(' ');
                write(sb, e.getValue(), indent < 0 ? -1 : indent + 1);
            }
            if (!m.isEmpty()) nl(sb, indent);
            sb.append('}');
        } else if (v instanceof List<?> l) {
            sb.append('[');
            int i = 0;
            for (Object o : l) {
                if (i++ > 0) sb.append(',');
                nl(sb, indent + 1);
                write(sb, o, indent < 0 ? -1 : indent + 1);
            }
            if (!l.isEmpty()) nl(sb, indent);
            sb.append(']');
        } else {
            writeStr(sb, v.toString());
        }
    }

    private static void nl(StringBuilder sb, int indent) {
        if (indent < 0) return;
        sb.append('\n');
        for (int i = 0; i < indent; i++) sb.append("  ");
    }

    private static void writeStr(StringBuilder sb, String s) {
        sb.append('"');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"'  -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default   -> sb.append(c);
            }
        }
        sb.append('"');
    }

    private static Object readValue(String s, int[] pos) {
        skip(s, pos);
        char c = s.charAt(pos[0]);
        return switch (c) {
            case '{' -> readObject(s, pos);
            case '[' -> readArray(s, pos);
            case '"' -> readString(s, pos);
            default  -> throw new IllegalArgumentException("Unexpected '" + c + "' at " + pos[0]);
        };
    }

    private static Map<String, Object> readObject(String s, int[] pos) {
        pos[0]++;
        Map<String, Object> m = new LinkedHashMap<>();
        skip(s, pos);
        if (s.charAt(pos[0]) == '}') { pos[0]++; return m; }
        while (true) {
            skip(s, pos);
            String key = readString(s, pos);
            skip(s, pos);
            if (s.charAt(pos[0]++) != ':') throw new IllegalArgumentException("Expected ':'");
            m.put(key, readValue(s, pos));
            skip(s, pos);
            char c = s.charAt(pos[0]++);
            if (c == ',') continue;
            if (c == '}') return m;
            throw new IllegalArgumentException("Expected ',' or '}'");
        }
    }

    private static List<Object> readArray(String s, int[] pos) {
        pos[0]++;
        List<Object> l = new ArrayList<>();
        skip(s, pos);
        if (s.charAt(pos[0]) == ']') { pos[0]++; return l; }
        while (true) {
            l.add(readValue(s, pos));
            skip(s, pos);
            char c = s.charAt(pos[0]++);
            if (c == ',') continue;
            if (c == ']') return l;
            throw new IllegalArgumentException("Expected ',' or ']'");
        }
    }

    private static String readString(String s, int[] pos) {
        if (s.charAt(pos[0]++) != '"') throw new IllegalArgumentException("Expected '\"'");
        StringBuilder sb = new StringBuilder();
        while (true) {
            char c = s.charAt(pos[0]++);
            if (c == '"') return sb.toString();
            if (c == '\\') {
                char e = s.charAt(pos[0]++);
                sb.append(switch (e) {
                    case '"'  -> '"';
                    case '\\' -> '\\';
                    case '/'  -> '/';
                    case 'n'  -> '\n';
                    case 'r'  -> '\r';
                    case 't'  -> '\t';
                    default   -> throw new IllegalArgumentException("Bad escape");
                });
            } else sb.append(c);
        }
    }

    private static void skip(String s, int[] pos) {
        while (pos[0] < s.length() && Character.isWhitespace(s.charAt(pos[0]))) pos[0]++;
    }
}
