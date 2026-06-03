package adapter;

import spi.FriendCipher;
import spi.ProcessorPlugin;

import java.nio.charset.StandardCharsets;

// Adapter: presents a FriendCipher as a ProcessorPlugin. Translates byte[] <--> String.
public class FriendCipherAdapter implements ProcessorPlugin {
    private final FriendCipher delegate;
    public FriendCipherAdapter(FriendCipher delegate) { this.delegate = delegate; }
    public String name() { return delegate.displayName(); }
    public byte[] onSave(byte[] data) { return delegate.encrypt(new String(data, StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8); }
    public byte[] onLoad(byte[] data) { return delegate.decrypt(new String(data, StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8); }
}
