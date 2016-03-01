package com.hazelcast.internal.memory2.accessor;

public class DirectNativeMemoryAccessor extends AbstractMemoryAccessor {

    public DirectNativeMemoryAccessor() {
        super(false);
    }

    public DirectNativeMemoryAccessor(boolean aligned) {
        super(aligned);
    }

    @Override
    public boolean getBoolean(long address) {
        return memoryAccessStrategy.getBoolean(address);
    }

    @Override
    public boolean getBooleanVolatile(long address) {
        return memoryAccessStrategy.getBooleanVolatile(address);
    }

    @Override
    public void putBoolean(long address, boolean x) {
        memoryAccessStrategy.putBoolean(address, x);
    }

    @Override
    public void putBooleanVolatile(long address, boolean x) {
        memoryAccessStrategy.putBooleanVolatile(address, x);
    }

    @Override
    public byte getByte(long address) {
        return memoryAccessStrategy.getByte(address);
    }

    @Override
    public byte getByteVolatile(long address) {
        return memoryAccessStrategy.getByteVolatile(address);
    }

    @Override
    public void putByte(long address, byte x) {
        memoryAccessStrategy.putByte(address, x);
    }

    @Override
    public void putByteVolatile(long address, byte x) {
        memoryAccessStrategy.putByteVolatile(address, x);
    }

}
