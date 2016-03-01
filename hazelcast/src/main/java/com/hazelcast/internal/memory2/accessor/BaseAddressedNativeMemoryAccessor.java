package com.hazelcast.internal.memory2.accessor;

public class BaseAddressedNativeMemoryAccessor extends AbstractMemoryAccessor {

    private final long baseAddress;

    public BaseAddressedNativeMemoryAccessor(long baseAddress) {
        this(baseAddress, false);
    }

    public BaseAddressedNativeMemoryAccessor(long baseAddress, boolean aligned) {
        super(aligned);
        this.baseAddress = baseAddress;
    }

    public long getBaseAddress() {
        return baseAddress;
    }

    @Override
    public boolean getBoolean(long address) {
        return memoryAccessStrategy.getBoolean(baseAddress + address);
    }

    @Override
    public boolean getBooleanVolatile(long address) {
        return memoryAccessStrategy.getBooleanVolatile(baseAddress + address);
    }

    @Override
    public void putBoolean(long address, boolean x) {
        memoryAccessStrategy.putBoolean(baseAddress + address, x);
    }

    @Override
    public void putBooleanVolatile(long address, boolean x) {
        memoryAccessStrategy.putBooleanVolatile(baseAddress + address, x);
    }

    @Override
    public byte getByte(long address) {
        return memoryAccessStrategy.getByte(baseAddress + address);
    }

    @Override
    public byte getByteVolatile(long address) {
        return memoryAccessStrategy.getByteVolatile(baseAddress + address);
    }

    @Override
    public void putByte(long address, byte x) {
        memoryAccessStrategy.putByte(baseAddress + address, x);
    }

    @Override
    public void putByteVolatile(long address, byte x) {
        memoryAccessStrategy.putByteVolatile(baseAddress + address, x);
    }

}
