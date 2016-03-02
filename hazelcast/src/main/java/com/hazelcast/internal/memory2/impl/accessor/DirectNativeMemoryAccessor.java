package com.hazelcast.internal.memory2.impl.accessor;

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

    @Override
    public char getChar(long address) {
        return memoryAccessStrategy.getChar(address);
    }

    @Override
    public char getCharVolatile(long address) {
        return memoryAccessStrategy.getCharVolatile(address);
    }

    @Override
    public void putChar(long address, char x) {
        memoryAccessStrategy.putChar(address, x);
    }

    @Override
    public void putCharVolatile(long address, char x) {
        memoryAccessStrategy.putCharVolatile(address, x);
    }

    @Override
    public short getShort(long address) {
        return memoryAccessStrategy.getShort(address);
    }

    @Override
    public short getShortVolatile(long address) {
        return memoryAccessStrategy.getShortVolatile(address);
    }

    @Override
    public void putShort(long address, short x) {
        memoryAccessStrategy.putShort(address, x);
    }

    @Override
    public void putShortVolatile(long address, short x) {
        memoryAccessStrategy.putShortVolatile(address, x);
    }

    @Override
    public int getInt(long address) {
        return memoryAccessStrategy.getInt(address);
    }

    @Override
    public int getIntVolatile(long address) {
        return memoryAccessStrategy.getIntVolatile(address);
    }

    @Override
    public void putInt(long address, int x) {
        memoryAccessStrategy.putInt(address, x);
    }

    @Override
    public void putIntVolatile(long address, int x) {
        memoryAccessStrategy.putIntVolatile(address, x);
    }

    @Override
    public float getFloat(long address) {
        return memoryAccessStrategy.getFloat(address);
    }

    @Override
    public float getFloatVolatile(long address) {
        return memoryAccessStrategy.getFloatVolatile(address);
    }

    @Override
    public void putFloat(long address, float x) {
        memoryAccessStrategy.putFloat(address, x);
    }

    @Override
    public void putFloatVolatile(long address, float x) {
        memoryAccessStrategy.putFloatVolatile(address, x);
    }

    @Override
    public long getLong(long address) {
        return memoryAccessStrategy.getLong(address);
    }

    @Override
    public long getLongVolatile(long address) {
        return memoryAccessStrategy.getLongVolatile(address);
    }

    @Override
    public void putLong(long address, long x) {
        memoryAccessStrategy.putLong(address, x);
    }

    @Override
    public void putLongVolatile(long address, long x) {
        memoryAccessStrategy.putLongVolatile(address, x);
    }

    @Override
    public double getDouble(long address) {
        return memoryAccessStrategy.getDouble(address);
    }

    @Override
    public double getDoubleVolatile(long address) {
        return memoryAccessStrategy.getDoubleVolatile(address);
    }

    @Override
    public void putDouble(long address, double x) {
        memoryAccessStrategy.putDouble(address, x);
    }

    @Override
    public void putDoubleVolatile(long address, double x) {
        memoryAccessStrategy.putDoubleVolatile(address, x);
    }

    @Override
    public boolean compareAndSwapInt(long address, int expected, int x) {
        return memoryAccessStrategy.compareAndSwapInt(address, expected, x);
    }

    @Override
    public boolean compareAndSwapLong(long address, long expected, long x) {
        return memoryAccessStrategy.compareAndSwapLong(address, expected, x);
    }

}
