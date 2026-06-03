package friend;

import spi.FriendCipher;

public final class CaesarCipher implements FriendCipher {
    private static final int SHIFT = 7;
    public String displayName() { return "Caesar"; }

    private static String shift(String s, int by) {
        StringBuilder out = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if      (c >= 'a' && c <= 'z') out.append((char) ('a' + Math.floorMod(c - 'a' + by, 26)));
            else if (c >= 'A' && c <= 'Z') out.append((char) ('A' + Math.floorMod(c - 'A' + by, 26)));
            else out.append(c);
        }
        return out.toString();
    }

    public String encrypt(String text) { return shift(text, SHIFT); }
    public String decrypt(String text) { return shift(text, -SHIFT); }
}
