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

import com.hazelcast.internal.memory.MemoryIO;
import com.hazelcast.internal.memory.strategy.MemoryAccessStrategy;

public class ByteArrayMemoryAccessor extends AbstractConcurrentMemoryAccessor {

    private final byte[] baseArray;
    private final ByteArrayReaderWriter byteArrayReaderWriter;

    public ByteArrayMemoryAccessor(byte[] baseArray) {
        this(baseArray, false);
    }

    public ByteArrayMemoryAccessor(byte[] baseArray, boolean aligned) {
        super(aligned);
        this.baseArray = baseArray;
        if (memoryAccessStrategy != null) {
            byteArrayReaderWriter = new MemoryAccessStrategyBasedReaderWriter(baseArray, memoryAccessStrategy);
        } else {
            byteArrayReaderWriter = new PureJavaByteArrayReaderWriter(baseArray);
        }
    }

    @Override
    public boolean getBoolean(long index) {
        return byteArrayReaderWriter.getBoolean(index);
    }

    @Override
    public boolean getBooleanVolatile(long index) {
        return byteArrayReaderWriter.getBooleanVolatile(index);
    }

    @Override
    public void putBoolean(long index, boolean x) {
        byteArrayReaderWriter.putBoolean(index, x);
    }

    @Override
    public void putBooleanVolatile(long index, boolean x) {
        byteArrayReaderWriter.putBooleanVolatile(index, x);
    }

    @Override
    public byte getByte(long index) {
        return byteArrayReaderWriter.getByte(index);
    }

    @Override
    public byte getByteVolatile(long index) {
        return byteArrayReaderWriter.getByteVolatile(index);
    }

    @Override
    public void putByte(long index, byte x) {
        byteArrayReaderWriter.putByte(index, x);
    }

    @Override
    public void putByteVolatile(long index, byte x) {
        byteArrayReaderWriter.putByteVolatile(index, x);
    }

    @Override
    public char getChar(long address) {
        return byteArrayReaderWriter.getChar(address);
    }

    @Override
    public char getCharVolatile(long address) {
        return byteArrayReaderWriter.getCharVolatile(address);
    }

    @Override
    public void putChar(long address, char x) {
        byteArrayReaderWriter.putChar(address, x);
    }

    @Override
    public void putCharVolatile(long address, char x) {
        byteArrayReaderWriter.putCharVolatile(address, x);
    }

    @Override
    public short getShort(long address) {
        return byteArrayReaderWriter.getShort(address);
    }

    @Override
    public short getShortVolatile(long address) {
        return byteArrayReaderWriter.getShortVolatile(address);
    }

    @Override
    public void putShort(long address, short x) {
        byteArrayReaderWriter.putShort(address, x);
    }

    @Override
    public void putShortVolatile(long address, short x) {
        byteArrayReaderWriter.putShortVolatile(address, x);
    }

    @Override
    public int getInt(long address) {
        return byteArrayReaderWriter.getInt(address);
    }

    @Override
    public int getIntVolatile(long address) {
        return byteArrayReaderWriter.getIntVolatile(address);
    }

    @Override
    public void putInt(long address, int x) {
        byteArrayReaderWriter.putInt(address, x);
    }

    @Override
    public void putIntVolatile(long address, int x) {
        byteArrayReaderWriter.putIntVolatile(address, x);
    }

    @Override
    public float getFloat(long address) {
        return byteArrayReaderWriter.getFloat(address);
    }

    @Override
    public float getFloatVolatile(long address) {
        return byteArrayReaderWriter.getFloatVolatile(address);
    }

    @Override
    public void putFloat(long address, float x) {
        byteArrayReaderWriter.putFloat(address, x);
    }

    @Override
    public void putFloatVolatile(long address, float x) {
        byteArrayReaderWriter.putFloatVolatile(address, x);
    }

    @Override
    public long getLong(long address) {
        return byteArrayReaderWriter.getLong(address);
    }

    @Override
    public long getLongVolatile(long address) {
        return byteArrayReaderWriter.getLongVolatile(address);
    }

    @Override
    public void putLong(long address, long x) {
        byteArrayReaderWriter.putLong(address, x);
    }

    @Override
    public void putLongVolatile(long address, long x) {
        byteArrayReaderWriter.putLongVolatile(address, x);
    }

    @Override
    public double getDouble(long address) {
        return byteArrayReaderWriter.getDouble(address);
    }

    @Override
    public double getDoubleVolatile(long address) {
        return byteArrayReaderWriter.getDoubleVolatile(address);
    }

    @Override
    public void putDouble(long address, double x) {
        byteArrayReaderWriter.putDouble(address, x);
    }

    @Override
    public void putDoubleVolatile(long address, double x) {
        byteArrayReaderWriter.putDoubleVolatile(address, x);
    }

    @Override
    public boolean compareAndSwapInt(long address, int expected, int x) {
        return byteArrayReaderWriter.compareAndSwapInt(address, expected, x);
    }

    @Override
    public boolean compareAndSwapLong(long address, long expected, long x) {
        return byteArrayReaderWriter.compareAndSwapLong(address, expected, x);
    }

    //////////////////////////////////////////////////////////////////////////////////

    private interface ByteArrayReaderWriter {

        boolean getBoolean(long index);
        boolean getBooleanVolatile(long index);
        void putBoolean(long index, boolean x);
        void putBooleanVolatile(long index, boolean x);

        byte getByte(long index);
        byte getByteVolatile(long index);
        void putByte(long index, byte x);
        void putByteVolatile(long index, byte x);

        char getChar(long index);
        char getCharVolatile(long index);
        void putChar(long index, char x);
        void putCharVolatile(long index, char x);

        short getShort(long index);
        short getShortVolatile(long index);
        void putShort(long index, short x);
        void putShortVolatile(long index, short x);

        int getInt(long index);
        int getIntVolatile(long index);
        void putInt(long index, int x);
        void putIntVolatile(long index, int x);

        float getFloat(long index);
        float getFloatVolatile(long index);
        void putFloat(long index, float x);
        void putFloatVolatile(long index, float x);

        long getLong(long index);
        long getLongVolatile(long index);
        void putLong(long index, long x);
        void putLongVolatile(long index, long x);

        double getDouble(long index);
        double getDoubleVolatile(long index);
        void putDouble(long index, double x);
        void putDoubleVolatile(long index, double x);

        boolean compareAndSwapInt(long address, int expected, int x);
        boolean compareAndSwapLong(long address, long expected, long x);

    }

    private static final class PureJavaByteArrayReaderWriter implements ByteArrayReaderWriter {

        private final byte[] baseArray;

        private PureJavaByteArrayReaderWriter(byte[] baseArray) {
            this.baseArray = baseArray;
        }

        @Override
        public boolean getBoolean(long index) {
            return baseArray[(int) index] != 0 ? true : false;
        }

        @Override
        public boolean getBooleanVolatile(long index) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

        @Override
        public void putBoolean(long index, boolean x) {
            baseArray[(int) index] = x ? (byte) 1 : (byte) 0;
        }

        @Override
        public void putBooleanVolatile(long index, boolean x) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

        @Override
        public byte getByte(long index) {
            return baseArray[(int) index];
        }

        @Override
        public byte getByteVolatile(long index) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

        @Override
        public void putByte(long index, byte x) {
            baseArray[(int) index] = x;
        }

        @Override
        public void putByteVolatile(long index, byte x) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

        @Override
        public char getChar(long index) {
            return MemoryIO.readChar(baseArray, (int) index, BIG_ENDIAN);
        }

        @Override
        public char getCharVolatile(long index) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

        @Override
        public void putChar(long index, char x) {
            MemoryIO.writeChar(baseArray, (int) index, x, BIG_ENDIAN);
        }

        @Override
        public void putCharVolatile(long index, char x) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

        @Override
        public short getShort(long index) {
            return MemoryIO.readShort(baseArray, (int) index, BIG_ENDIAN);
        }

        @Override
        public short getShortVolatile(long index) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

        @Override
        public void putShort(long index, short x) {
            MemoryIO.writeShort(baseArray, (int) index, x, BIG_ENDIAN);
        }

        @Override
        public void putShortVolatile(long index, short x) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

        @Override
        public int getInt(long index) {
            return MemoryIO.readInt(baseArray, (int) index, BIG_ENDIAN);
        }

        @Override
        public int getIntVolatile(long index) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

        @Override
        public void putInt(long index, int x) {
            MemoryIO.writeInt(baseArray, (int) index, x, BIG_ENDIAN);
        }

        @Override
        public void putIntVolatile(long index, int x) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

        @Override
        public float getFloat(long index) {
            return MemoryIO.readFloat(baseArray, (int) index, BIG_ENDIAN);
        }

        @Override
        public float getFloatVolatile(long index) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

        @Override
        public void putFloat(long index, float x) {
            MemoryIO.writeFloat(baseArray, (int) index, x, BIG_ENDIAN);
        }

        @Override
        public void putFloatVolatile(long index, float x) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

        @Override
        public long getLong(long index) {
            return MemoryIO.readLong(baseArray, (int) index, BIG_ENDIAN);
        }

        @Override
        public long getLongVolatile(long index) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

        @Override
        public void putLong(long index, long x) {
            MemoryIO.writeLong(baseArray, (int) index, x, BIG_ENDIAN);
        }

        @Override
        public void putLongVolatile(long index, long x) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

        @Override
        public double getDouble(long index) {
            return MemoryIO.readDouble(baseArray, (int) index, BIG_ENDIAN);
        }

        @Override
        public double getDoubleVolatile(long index) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

        @Override
        public void putDouble(long index, double x) {
            MemoryIO.writeDouble(baseArray, (int) index, x, BIG_ENDIAN);
        }

        @Override
        public void putDoubleVolatile(long index, double x) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

        @Override
        public boolean compareAndSwapInt(long address, int expected, int x) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

        @Override
        public boolean compareAndSwapLong(long address, long expected, long x) {
            throw new UnsupportedOperationException(
                    "Not supported by pure java based version of ByteArrayReaderWriter!");
        }

    }

    private static final class MemoryAccessStrategyBasedReaderWriter implements ByteArrayReaderWriter {

        private static final int BYTE_ARRAY_BASE_OFFSET = MemoryAccessStrategy.ARRAY_BYTE_BASE_OFFSET;

        private final byte[] baseArray;
        private final MemoryAccessStrategy memoryAccessStrategy;

        private MemoryAccessStrategyBasedReaderWriter(byte[] baseArray, MemoryAccessStrategy memoryAccessStrategy) {
            this.baseArray = baseArray;
            this.memoryAccessStrategy = memoryAccessStrategy;
        }

        private int checkAndGetIndex(long index) {
            long targetIndex = BYTE_ARRAY_BASE_OFFSET + index;
            assert (targetIndex > 0 && targetIndex < Integer.MAX_VALUE) : ("Invalid index: " + index);
            return (int) targetIndex;
        }

        @Override
        public boolean getBoolean(long index) {
            return memoryAccessStrategy.getBoolean(baseArray, checkAndGetIndex(index));
        }

        @Override
        public boolean getBooleanVolatile(long index) {
            return memoryAccessStrategy.getBooleanVolatile(baseArray, checkAndGetIndex(index));
        }

        @Override
        public void putBoolean(long index, boolean x) {
            memoryAccessStrategy.putBoolean(baseArray, checkAndGetIndex(index), x);
        }

        @Override
        public void putBooleanVolatile(long index, boolean x) {
            memoryAccessStrategy.putBooleanVolatile(baseArray, checkAndGetIndex(index), x);
        }

        @Override
        public byte getByte(long index) {
            return memoryAccessStrategy.getByte(baseArray, checkAndGetIndex(index));
        }

        @Override
        public byte getByteVolatile(long index) {
            return memoryAccessStrategy.getByteVolatile(baseArray, checkAndGetIndex(index));
        }

        @Override
        public void putByte(long index, byte x) {
            memoryAccessStrategy.putByte(baseArray, checkAndGetIndex(index), x);
        }

        @Override
        public void putByteVolatile(long index, byte x) {
            memoryAccessStrategy.putByteVolatile(baseArray, checkAndGetIndex(index), x);
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

}
