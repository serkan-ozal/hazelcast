/*
 * Copyright (c) 2008-2016, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.internal.memory.impl.accessor;

public class BaseAddressedNativeMemoryAccessor extends AbstractConcurrentMemoryAccessor {

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

    @Override
    public char getChar(long address) {
        return memoryAccessStrategy.getChar(baseAddress + address);
    }

    @Override
    public char getCharVolatile(long address) {
        return memoryAccessStrategy.getCharVolatile(baseAddress + address);
    }

    @Override
    public void putChar(long address, char x) {
        memoryAccessStrategy.putChar(baseAddress + address, x);
    }

    @Override
    public void putCharVolatile(long address, char x) {
        memoryAccessStrategy.putCharVolatile(baseAddress + address, x);
    }

    @Override
    public short getShort(long address) {
        return memoryAccessStrategy.getShort(baseAddress + address);
    }

    @Override
    public short getShortVolatile(long address) {
        return memoryAccessStrategy.getShortVolatile(baseAddress + address);
    }

    @Override
    public void putShort(long address, short x) {
        memoryAccessStrategy.putShort(baseAddress + address, x);
    }

    @Override
    public void putShortVolatile(long address, short x) {
        memoryAccessStrategy.putShortVolatile(baseAddress + address, x);
    }

    @Override
    public int getInt(long address) {
        return memoryAccessStrategy.getInt(baseAddress + address);
    }

    @Override
    public int getIntVolatile(long address) {
        return memoryAccessStrategy.getIntVolatile(baseAddress + address);
    }

    @Override
    public void putInt(long address, int x) {
        memoryAccessStrategy.putInt(baseAddress + address, x);
    }

    @Override
    public void putIntVolatile(long address, int x) {
        memoryAccessStrategy.putIntVolatile(baseAddress + address, x);
    }

    @Override
    public float getFloat(long address) {
        return memoryAccessStrategy.getFloat(baseAddress + address);
    }

    @Override
    public float getFloatVolatile(long address) {
        return memoryAccessStrategy.getFloatVolatile(baseAddress + address);
    }

    @Override
    public void putFloat(long address, float x) {
        memoryAccessStrategy.putFloat(baseAddress + address, x);
    }

    @Override
    public void putFloatVolatile(long address, float x) {
        memoryAccessStrategy.putFloatVolatile(baseAddress + address, x);
    }

    @Override
    public long getLong(long address) {
        return memoryAccessStrategy.getLong(baseAddress + address);
    }

    @Override
    public long getLongVolatile(long address) {
        return memoryAccessStrategy.getLongVolatile(baseAddress + address);
    }

    @Override
    public void putLong(long address, long x) {
        memoryAccessStrategy.putLong(baseAddress + address, x);
    }

    @Override
    public void putLongVolatile(long address, long x) {
        memoryAccessStrategy.putLongVolatile(baseAddress + address, x);
    }

    @Override
    public double getDouble(long address) {
        return memoryAccessStrategy.getDouble(baseAddress + address);
    }

    @Override
    public double getDoubleVolatile(long address) {
        return memoryAccessStrategy.getDoubleVolatile(baseAddress + address);
    }

    @Override
    public void putDouble(long address, double x) {
        memoryAccessStrategy.putDouble(baseAddress + address, x);
    }

    @Override
    public void putDoubleVolatile(long address, double x) {
        memoryAccessStrategy.putDoubleVolatile(baseAddress + address, x);
    }

    @Override
    public boolean compareAndSwapInt(long address, int expected, int x) {
        return memoryAccessStrategy.compareAndSwapInt(baseAddress + address, expected, x);
    }

    @Override
    public boolean compareAndSwapLong(long address, long expected, long x) {
        return memoryAccessStrategy.compareAndSwapLong(baseAddress + address, expected, x);
    }

}
