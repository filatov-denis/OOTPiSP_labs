package adapter;

import friend.CaesarCipher;

public final class CaesarAdapterPlugin extends FriendCipherAdapter {
    public CaesarAdapterPlugin() { super(new CaesarCipher()); }
}
