package spi;

// Pre-save / post-load byte stream processor. Variant 1: transformation between XML and JSON.
public interface ProcessorPlugin {
    String name();
    byte[] onSave(byte[] data) throws Exception;
    byte[] onLoad(byte[] data) throws Exception;
}
