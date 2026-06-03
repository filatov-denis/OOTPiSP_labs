package spi;

// The Adapter for the friend's interface
public interface FriendCipher {
    String displayName();
    String encrypt(String text);
    String decrypt(String text);
}
