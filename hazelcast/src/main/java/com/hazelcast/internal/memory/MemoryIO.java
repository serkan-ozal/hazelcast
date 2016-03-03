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

package com.hazelcast.internal.memory;

import com.hazelcast.internal.memory.impl.UnsafeUtil;
import sun.misc.Unsafe;

import java.io.DataInput;
import java.io.IOException;
import java.io.UTFDataFormatException;

/**
 * <p>
 * Utility class to read/write data from/to given memory location (by base object/offset or native memory address)
 * by specified byte order (little/big endian).
 * </p>
 * <p>
 * {@link MemoryIO} is the lowest (1st) level abstraction for memory accesses.
 * Architecturally, it is located under both of {@link com.hazelcast.internal.memory.accessor.MemoryAccessor},
 * which is the highest (3st) level memory access abstraction,
 * and {@link com.hazelcast.internal.memory.strategy.MemoryAccessStrategy}
 * which is the middle (2st) level memory access abstraction.
 * </p>
 */
public final class MemoryIO {

    private static final DirectMemoryBasedByteMemoryAccessor DIRECT_MEM_BYTE_MEM_ACCESSOR;
    private static final ByteArrayBasedByteMemoryAccessor BYTE_ARRAY_BYTE_MEM_ACCESSOR;

    static {
        if (UnsafeUtil.UNSAFE_AVAILABLE) {
            DIRECT_MEM_BYTE_MEM_ACCESSOR = new DirectMemoryBasedByteMemoryAccessor(UnsafeUtil.UNSAFE);
        } else {
            DIRECT_MEM_BYTE_MEM_ACCESSOR = null;
        }
        BYTE_ARRAY_BYTE_MEM_ACCESSOR = new ByteArrayBasedByteMemoryAccessor();
    }

    private MemoryIO() {
    }

    //////////////////////////////////////////////////////////////////

    public interface ByteMemoryAccessor<R> {

        byte getByte(R resource, long offset);

        void putByte(R resource, long offset, byte x);

    }

    private static final class DirectMemoryBasedByteMemoryAccessor
            implements ByteMemoryAccessor<Object> {

        private final Unsafe unsafe;

        private DirectMemoryBasedByteMemoryAccessor(Unsafe unsafe) {
            this.unsafe = unsafe;
        }

        @Override
        public byte getByte(Object base, long offset) {
            return unsafe.getByte(base, offset);
        }

        @Override
        public void putByte(Object base, long offset, byte x) {
            unsafe.putByte(base, offset, x);
        }

    }

    private static final class ByteArrayBasedByteMemoryAccessor
            implements ByteMemoryAccessor<byte[]> {

        @Override
        public byte getByte(byte[] array, long offset) {
            return array[(int) offset];
        }

        @Override
        public void putByte(byte[] array, long offset, byte x) {
            array[(int) offset] = x;
        }

    }

    //////////////////////////////////////////////////////////////////

    public static char readChar(long address, boolean bigEndian) {
        return readChar(null, address, bigEndian);
    }

    public static char readChar(byte[] buffer, int pos, boolean bigEndian) {
        return readChar(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, bigEndian);
    }

    public static char readCharB(byte[] buffer, int pos) {
        return readCharB(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos);
    }

    public static char readCharL(byte[] buffer, int pos) {
        return readCharL(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos);
    }

    public static char readChar(Object base, long offset, boolean bigEndian) {
        return readChar(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, bigEndian);
    }

    public static char readCharB(Object base, long offset) {
        return readCharB(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset);
    }

    public static char readCharL(Object base, long offset) {
        return readCharL(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset);
    }

    public static <R> char readChar(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset, boolean bigEndian) {
        if (bigEndian) {
            return readCharB(memoryAccessor, resource, offset);
        } else {
            return readCharL(memoryAccessor, resource, offset);
        }
    }

    public static <R> char readCharB(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset) {
        int byte1 = memoryAccessor.getByte(resource, offset) & 0xFF;
        int byte0 = memoryAccessor.getByte(resource, offset + 1) & 0xFF;
        return (char) ((byte1 << 8) + byte0);
    }

    public static <R> char readCharL(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset) {
        int byte1 = memoryAccessor.getByte(resource, offset) & 0xFF;
        int byte0 = memoryAccessor.getByte(resource, offset + 1) & 0xFF;
        return (char) ((byte0 << 8) + byte1);
    }

    public static void writeChar(long address, char v, boolean bigEndian) {
        writeChar(null, address, v, bigEndian);
    }

    public static void writeChar(byte[] buffer, int pos, char v, boolean bigEndian) {
        writeChar(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, v, bigEndian);
    }

    public static void writeCharB(byte[] buffer, int pos, char v) {
        writeCharB(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, v);
    }

    public static void writeCharL(byte[] buffer, int pos, char v) {
        writeCharL(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, v);
    }

    public static void writeChar(Object base, long offset, char v, boolean bigEndian) {
        writeChar(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, v, bigEndian);
    }

    public static void writeCharB(Object base, long offset, char v) {
        writeCharB(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, v);
    }

    public static void writeCharL(Object base, long offset, char v) {
        writeCharL(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, v);
    }

    public static <R> void writeChar(ByteMemoryAccessor<R> memoryAccessor, R resource,
                                     long offset, char v, boolean bigEndian) {
        if (bigEndian) {
            writeCharB(memoryAccessor, resource, offset, v);
        } else {
            writeCharL(memoryAccessor, resource, offset, v);
        }
    }

    public static <R> void writeCharB(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset, char v) {
        memoryAccessor.putByte(resource, offset, (byte) ((v >>> 8) & 0xFF));
        memoryAccessor.putByte(resource, offset + 1, (byte) ((v) & 0xFF));
    }

    public static <R> void writeCharL(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset, char v) {
        memoryAccessor.putByte(resource, offset, (byte) ((v) & 0xFF));
        memoryAccessor.putByte(resource, offset + 1, (byte) ((v >>> 8) & 0xFF));
    }

    //////////////////////////////////////////////////////////////////

    public static short readShort(long address, boolean bigEndian) {
        return readShort(null, address, bigEndian);
    }

    public static short readShort(byte[] buffer, int pos, boolean bigEndian) {
        return readShort(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, bigEndian);
    }

    public static short readShortB(byte[] buffer, int pos) {
        return readShortB(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos);
    }

    public static short readShortL(byte[] buffer, int pos) {
        return readShortL(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos);
    }

    public static short readShort(Object base, long offset, boolean bigEndian) {
        return readShort(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, bigEndian);
    }

    public static short readShortB(Object base, long offset) {
        return readShortB(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset);
    }

    public static short readShortL(Object base, long offset) {
        return readShortL(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset);
    }

    public static <R> short readShort(ByteMemoryAccessor<R> memoryAccessor, R resource,
                                      long offset, boolean bigEndian) {
        if (bigEndian) {
            return readShortB(memoryAccessor, resource, offset);
        } else {
            return readShortL(memoryAccessor, resource, offset);
        }
    }

    public static <R> short readShortB(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset) {
        int byte1 = memoryAccessor.getByte(resource, offset) & 0xFF;
        int byte0 = memoryAccessor.getByte(resource, offset + 1) & 0xFF;
        return (short) ((byte1 << 8) + byte0);
    }

    public static <R> short readShortL(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset) {
        int byte1 = memoryAccessor.getByte(resource, offset) & 0xFF;
        int byte0 = memoryAccessor.getByte(resource, offset + 1) & 0xFF;
        return (short) ((byte0 << 8) + byte1);
    }

    public static void writeShort(long address, short v, boolean bigEndian) {
        writeShort(null, address, v, bigEndian);
    }

    public static void writeShort(byte[] buffer, int pos, short v, boolean bigEndian) {
        writeShort(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, v, bigEndian);
    }

    public static void writeShortB(byte[] buffer, int pos, short v) {
        writeShortB(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, v);
    }

    public static void writeShortL(byte[] buffer, int pos, short v) {
        writeShortL(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, v);
    }

    public static void writeShort(Object base, long offset, short v, boolean bigEndian) {
        writeShort(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, v, bigEndian);
    }

    public static void writeShortB(Object base, long offset, short v) {
        writeShortB(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, v);
    }

    public static void writeShortL(Object base, long offset, short v) {
        writeShortL(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, v);
    }

    public static <R> void writeShort(ByteMemoryAccessor<R> memoryAccessor, R resource,
                                      long offset, short v, boolean bigEndian) {
        if (bigEndian) {
            writeShortB(memoryAccessor, resource, offset, v);
        } else {
            writeShortL(memoryAccessor, resource, offset, v);
        }
    }

    public static <R> void writeShortB(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset, short v) {
        memoryAccessor.putByte(resource, offset, (byte) ((v >>> 8) & 0xFF));
        memoryAccessor.putByte(resource, offset + 1, (byte) ((v) & 0xFF));
    }

    public static <R> void writeShortL(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset, short v) {
        memoryAccessor.putByte(resource, offset, (byte) ((v) & 0xFF));
        memoryAccessor.putByte(resource, offset + 1, (byte) ((v >>> 8) & 0xFF));
    }

    //////////////////////////////////////////////////////////////////

    public static int readInt(long address, boolean bigEndian) {
        return readInt(null, address, bigEndian);
    }

    public static int readInt(byte[] buffer, int pos, boolean bigEndian) {
        return readInt(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, bigEndian);
    }

    public static int readIntB(byte[] buffer, int pos) {
        return readIntB(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos);
    }

    public static int readIntL(byte[] buffer, int pos) {
        return readIntL(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos);
    }

    public static int readInt(Object base, long offset, boolean bigEndian) {
        return readInt(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, bigEndian);
    }

    public static int readIntB(Object base, long offset) {
        return readIntB(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset);
    }

    public static int readIntL(Object base, long offset) {
        return readIntL(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset);
    }

    public static <R> int readInt(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset, boolean bigEndian) {
        if (bigEndian) {
            return readIntB(memoryAccessor, resource, offset);
        } else {
            return readIntL(memoryAccessor, resource, offset);
        }
    }

    public static <R> int readIntB(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset) {
        int byte3 = (memoryAccessor.getByte(resource, offset) & 0xFF) << 24;
        int byte2 = (memoryAccessor.getByte(resource, offset + 1) & 0xFF) << 16;
        int byte1 = (memoryAccessor.getByte(resource, offset + 2) & 0xFF) << 8;
        int byte0 = memoryAccessor.getByte(resource, offset + 3) & 0xFF;
        return byte3 + byte2 + byte1 + byte0;
    }

    public static <R> int readIntL(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset) {
        int byte3 = memoryAccessor.getByte(resource, offset) & 0xFF;
        int byte2 = (memoryAccessor.getByte(resource, offset + 1) & 0xFF) << 8;
        int byte1 = (memoryAccessor.getByte(resource, offset + 2) & 0xFF) << 16;
        int byte0 = (memoryAccessor.getByte(resource, offset + 3) & 0xFF) << 24;
        return byte3 + byte2 + byte1 + byte0;
    }

    public static void writeInt(long address, int v, boolean bigEndian) {
        writeInt(null, address, v, bigEndian);
    }

    public static void writeInt(byte[] buffer, int pos, int v, boolean bigEndian) {
        writeInt(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, v, bigEndian);
    }

    public static void writeIntB(byte[] buffer, int pos, int v) {
        writeIntB(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, v);
    }

    public static void writeIntL(byte[] buffer, int pos, int v) {
        writeIntL(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, v);
    }

    public static void writeInt(Object base, long offset, int v, boolean bigEndian) {
        writeInt(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, v, bigEndian);
    }

    public static void writeIntB(Object base, long offset, int v) {
        writeIntB(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, v);
    }

    public static void writeIntL(Object base, long offset, int v) {
        writeIntL(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, v);
    }

    public static <R> void writeInt(ByteMemoryAccessor<R> memoryAccessor, R resource,
                                    long offset, int v, boolean bigEndian) {
        if (bigEndian) {
            writeIntB(memoryAccessor, resource, offset, v);
        } else {
            writeIntL(memoryAccessor, resource, offset, v);
        }
    }

    public static <R> void writeIntB(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset, int v) {
        memoryAccessor.putByte(resource, offset, (byte) ((v >>> 24) & 0xFF));
        memoryAccessor.putByte(resource, offset + 1, (byte) ((v >>> 16) & 0xFF));
        memoryAccessor.putByte(resource, offset + 2, (byte) ((v >>> 8) & 0xFF));
        memoryAccessor.putByte(resource, offset + 3, (byte) ((v) & 0xFF));
    }

    public static <R> void writeIntL(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset, int v) {
        memoryAccessor.putByte(resource, offset, (byte) ((v) & 0xFF));
        memoryAccessor.putByte(resource, offset + 1, (byte) ((v >>> 8) & 0xFF));
        memoryAccessor.putByte(resource, offset + 2, (byte) ((v >>> 16) & 0xFF));
        memoryAccessor.putByte(resource, offset + 3, (byte) ((v >>> 24) & 0xFF));
    }

    //////////////////////////////////////////////////////////////////

    public static float readFloat(long address, boolean bigEndian) {
        return readFloat(null, address, bigEndian);
    }

    public static float readFloat(byte[] buffer, int pos, boolean bigEndian) {
        return readFloat(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, bigEndian);
    }

    public static float readFloatB(byte[] buffer, int pos) {
        return readFloatB(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos);
    }

    public static float readFloatL(byte[] buffer, int pos) {
        return readFloatL(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos);
    }

    public static float readFloat(Object base, long offset, boolean bigEndian) {
        return readFloat(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, bigEndian);
    }

    public static float readFloatB(Object base, long offset) {
        return readFloatB(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset);
    }

    public static float readFloatL(Object base, long offset) {
        return readFloatL(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset);
    }

    public static <R> float readFloat(ByteMemoryAccessor<R> memoryAccessor, R resource,
                                      long offset, boolean bigEndian) {
        if (bigEndian) {
            return readFloatB(memoryAccessor, resource, offset);
        } else {
            return readFloatL(memoryAccessor, resource, offset);
        }
    }

    public static <R> float readFloatB(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset) {
        return Float.intBitsToFloat(readIntB(memoryAccessor, resource, offset));
    }

    public static <R> float readFloatL(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset) {
        return Float.intBitsToFloat(readIntL(memoryAccessor, resource, offset));
    }

    public static void writeFloat(long address, float v, boolean bigEndian) {
        writeFloat(null, address, v, bigEndian);
    }

    public static void writeFloat(byte[] buffer, int pos, float v, boolean bigEndian) {
        writeFloat(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, v, bigEndian);
    }

    public static void writeFloatB(byte[] buffer, int pos, float v) {
        writeFloatB(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, v);
    }

    public static void writeFloatL(byte[] buffer, int pos, float v) {
        writeFloatL(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, v);
    }

    public static void writeFloat(Object base, long offset, float v, boolean bigEndian) {
        writeFloat(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, v, bigEndian);
    }

    public static void writeFloatB(Object base, long offset, float v) {
        writeFloatB(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, v);
    }

    public static void writeFloatL(Object base, long offset, float v) {
        writeFloatL(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, v);
    }

    public static <R> void writeFloat(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset,
                                      float v, boolean bigEndian) {
        if (bigEndian) {
            writeFloatB(memoryAccessor, resource, offset, v);
        } else {
            writeFloatL(memoryAccessor, resource, offset, v);
        }
    }

    public static <R> void writeFloatB(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset, float v) {
        writeIntB(memoryAccessor, resource, offset, Float.floatToRawIntBits(v));
    }

    public static <R> void writeFloatL(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset, float v) {
        writeIntL(memoryAccessor, resource, offset, Float.floatToRawIntBits(v));
    }

    //////////////////////////////////////////////////////////////////

    public static long readLong(long address, boolean bigEndian) {
        return readLong(null, address, bigEndian);
    }

    public static long readLong(byte[] buffer, int pos, boolean bigEndian) {
        return readLong(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, bigEndian);
    }

    public static long readLongB(byte[] buffer, int pos) {
        return readLongB(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos);
    }

    public static long readLongL(byte[] buffer, int pos) {
        return readLongL(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos);
    }

    public static long readLong(Object base, long offset, boolean bigEndian) {
        return readLong(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, bigEndian);
    }

    public static long readLongB(Object base, long offset) {
        return readLongB(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset);
    }

    public static long readLongL(Object base, long offset) {
        return readLongL(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset);
    }

    public static <R> long readLong(ByteMemoryAccessor<R> memoryAccessor, R resource,
                                    long offset, boolean bigEndian) {
        if (bigEndian) {
            return readLongB(memoryAccessor, resource, offset);
        } else {
            return readLongL(memoryAccessor, resource, offset);
        }
    }

    public static <R> long readLongB(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset) {
        long byte7 = (long) memoryAccessor.getByte(resource, offset) << 56;
        long byte6 = (long) (memoryAccessor.getByte(resource, offset + 1) & 0xFF) << 48;
        long byte5 = (long) (memoryAccessor.getByte(resource, offset + 2) & 0xFF) << 40;
        long byte4 = (long) (memoryAccessor.getByte(resource, offset + 3) & 0xFF) << 32;
        long byte3 = (long) (memoryAccessor.getByte(resource, offset + 4) & 0xFF) << 24;
        long byte2 = (long) (memoryAccessor.getByte(resource, offset + 5) & 0xFF) << 16;
        long byte1 = (long) (memoryAccessor.getByte(resource, offset + 6) & 0xFF) << 8;
        long byte0 = (long) (memoryAccessor.getByte(resource, offset + 7) & 0xFF);
        return byte7 + byte6 + byte5 + byte4 + byte3 + byte2 + byte1 + byte0;
    }

    public static <R> long readLongL(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset) {
        long byte7 = (long) (memoryAccessor.getByte(resource, offset) & 0xFF);
        long byte6 = (long) (memoryAccessor.getByte(resource, offset + 1) & 0xFF) << 8;
        long byte5 = (long) (memoryAccessor.getByte(resource, offset + 2) & 0xFF) << 16;
        long byte4 = (long) (memoryAccessor.getByte(resource, offset + 3) & 0xFF) << 24;
        long byte3 = (long) (memoryAccessor.getByte(resource, offset + 4) & 0xFF) << 32;
        long byte2 = (long) (memoryAccessor.getByte(resource, offset + 5) & 0xFF) << 40;
        long byte1 = (long) (memoryAccessor.getByte(resource, offset + 6) & 0xFF) << 48;
        long byte0 = (long) (memoryAccessor.getByte(resource, offset + 7) & 0xFF) << 56;
        return byte7 + byte6 + byte5 + byte4 + byte3 + byte2 + byte1 + byte0;
    }

    public static void writeLong(long address, long v, boolean bigEndian) {
        writeLong(null, address, v, bigEndian);
    }

    public static void writeLong(byte[] buffer, int pos, long v, boolean bigEndian) {
        writeLong(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, v, bigEndian);
    }

    public static void writeLongB(byte[] buffer, int pos, long v) {
        writeLongB(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, v);
    }

    public static void writeLongL(byte[] buffer, int pos, long v) {
        writeLongL(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, v);
    }

    public static void writeLong(Object base, long offset, long v, boolean bigEndian) {
        writeLong(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, v, bigEndian);
    }

    public static void writeLongB(Object base, long offset, long v) {
        writeLongB(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, v);
    }

    public static void writeLongL(Object base, long offset, long v) {
        writeLongL(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, v);
    }

    public static <R> void writeLong(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset,
                                     long v, boolean bigEndian) {
        if (bigEndian) {
            writeLongB(memoryAccessor, resource, offset, v);
        } else {
            writeLongL(memoryAccessor, resource, offset, v);
        }
    }

    public static <R> void writeLongB(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset, long v) {
        memoryAccessor.putByte(resource, offset, (byte) (v >>> 56));
        memoryAccessor.putByte(resource, offset + 1, (byte) (v >>> 48));
        memoryAccessor.putByte(resource, offset + 2, (byte) (v >>> 40));
        memoryAccessor.putByte(resource, offset + 3, (byte) (v >>> 32));
        memoryAccessor.putByte(resource, offset + 4, (byte) (v >>> 24));
        memoryAccessor.putByte(resource, offset + 5, (byte) (v >>> 16));
        memoryAccessor.putByte(resource, offset + 6, (byte) (v >>> 8));
        memoryAccessor.putByte(resource, offset + 7, (byte) (v));
    }

    public static <R> void writeLongL(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset, long v) {
        memoryAccessor.putByte(resource, offset, (byte) (v));
        memoryAccessor.putByte(resource, offset + 1, (byte) (v >>> 8));
        memoryAccessor.putByte(resource, offset + 2, (byte) (v >>> 16));
        memoryAccessor.putByte(resource, offset + 3, (byte) (v >>> 24));
        memoryAccessor.putByte(resource, offset + 4, (byte) (v >>> 32));
        memoryAccessor.putByte(resource, offset + 5, (byte) (v >>> 40));
        memoryAccessor.putByte(resource, offset + 6, (byte) (v >>> 48));
        memoryAccessor.putByte(resource, offset + 7, (byte) (v >>> 56));
    }

    //////////////////////////////////////////////////////////////////

    public static double readDouble(long address, boolean bigEndian) {
        return readDouble(null, address, bigEndian);
    }

    public static double readDouble(byte[] buffer, int pos, boolean bigEndian) {
        return readDouble(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, bigEndian);
    }

    public static double readDoubleB(byte[] buffer, int pos) {
        return readDoubleB(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos);
    }

    public static double readDoubleL(byte[] buffer, int pos) {
        return readDoubleL(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos);
    }

    public static double readDouble(Object base, long offset, boolean bigEndian) {
        return readDouble(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, bigEndian);
    }

    public static double readDoubleB(Object base, long offset) {
        return readDoubleB(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset);
    }

    public static double readDoubleL(Object base, long offset) {
        return readDoubleL(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset);
    }

    public static <R> double readDouble(ByteMemoryAccessor<R> memoryAccessor, R resource,
                                        long offset, boolean bigEndian) {
        if (bigEndian) {
            return readDoubleB(memoryAccessor, resource, offset);
        } else {
            return readDoubleL(memoryAccessor, resource, offset);
        }
    }

    public static <R> double readDoubleB(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset) {
        return Double.longBitsToDouble(readLongB(memoryAccessor, resource, offset));
    }

    public static <R> double readDoubleL(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset) {
        return Double.longBitsToDouble(readLongL(memoryAccessor, resource, offset));
    }

    public static void writeDouble(long address, double v, boolean bigEndian) {
        writeDouble(null, address, v, bigEndian);
    }

    public static void writeDouble(byte[] buffer, int pos, double v, boolean bigEndian) {
        writeDouble(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, v, bigEndian);
    }

    public static void writeDoubleB(byte[] buffer, int pos, double v) {
        writeLongB(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, Double.doubleToRawLongBits(v));
    }

    public static void writeDoubleL(byte[] buffer, int pos, double v) {
        writeLongL(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, Double.doubleToRawLongBits(v));
    }

    public static void writeDouble(Object base, long offset, double v, boolean bigEndian) {
        writeDouble(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, v, bigEndian);
    }

    public static void writeDoubleB(Object base, long offset, double v) {
        writeLongB(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, Double.doubleToRawLongBits(v));
    }

    public static void writeDoubleL(Object base, long offset, double v) {
        writeLongL(DIRECT_MEM_BYTE_MEM_ACCESSOR, base, offset, Double.doubleToRawLongBits(v));
    }

    public static <R> void writeDouble(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset,
                                       double v, boolean bigEndian) {
        if (bigEndian) {
            writeDoubleB(memoryAccessor, resource, offset, v);
        } else {
            writeDoubleL(memoryAccessor, resource, offset, v);
        }
    }

    public static <R> void writeDoubleB(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset, double v) {
        writeLongB(memoryAccessor, resource, offset, Double.doubleToRawLongBits(v));
    }

    public static <R> void writeDoubleL(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset, double v) {
        writeLongL(memoryAccessor, resource, offset, Double.doubleToRawLongBits(v));
    }

    //////////////////////////////////////////////////////////////////

    public static int writeUtf8Char(byte[] buffer, int pos, int c) {
        return writeUtf8Char(BYTE_ARRAY_BYTE_MEM_ACCESSOR, buffer, pos, c);
    }

    public static int writeUtf8Char(long address, long offset, int c) {
        return writeUtf8Char(DIRECT_MEM_BYTE_MEM_ACCESSOR, null, address + offset, c);
    }

    public static <R> int writeUtf8Char(ByteMemoryAccessor<R> memoryAccessor, R resource, long offset, int c) {
        if (c <= 0x007F) {
            memoryAccessor.putByte(resource, offset, (byte) c);
            return 1;
        } else if (c > 0x07FF) {
            memoryAccessor.putByte(resource, offset, (byte) (0xE0 | c >> 12 & 0x0F));
            memoryAccessor.putByte(resource, offset + 1, (byte) (0x80 | c >> 6 & 0x3F));
            memoryAccessor.putByte(resource, offset + 2, (byte) (0x80 | c & 0x3F));
            return 3;
        } else {
            memoryAccessor.putByte(resource, offset, (byte) (0xC0 | c >> 6 & 0x1F));
            memoryAccessor.putByte(resource, offset + 1, (byte) (0x80 | c & 0x3F));
            return 2;
        }
    }

    public static char readUtf8Char(DataInput in, byte firstByte)
            throws IOException {
        int b = firstByte & 0xFF;
        switch (b >> 4) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return (char) b;
            case 12:
            case 13:
                int first = (b & 0x1F) << 6;
                int second = in.readByte() & 0x3F;
                return (char) (first | second);
            case 14:
                int first2 = (b & 0x0F) << 12;
                int second2 = (in.readByte() & 0x3F) << 6;
                int third2 = in.readByte() & 0x3F;
                return (char) (first2 | second2 | third2);
            default:
                throw new UTFDataFormatException("Malformed byte sequence");
        }
    }

    public static int readUtf8Char(byte[] buffer, int pos, char[] dst, int dstPos)
            throws IOException {
        int b = buffer[pos] & 0xFF;
        switch (b >> 4) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                dst[dstPos] = (char) b;
                return 1;
            case 12:
            case 13:
                int first = (b & 0x1F) << 6;
                int second = buffer[pos + 1] & 0x3F;
                dst[dstPos] = (char) (first | second);
                return 2;
            case 14:
                int first2 = (b & 0x0F) << 12;
                int second2 = (buffer[pos + 1] & 0x3F) << 6;
                int third2 = buffer[pos + 2] & 0x3F;
                dst[dstPos] = (char) (first2 | second2 | third2);
                return 3;
            default:
                throw new UTFDataFormatException("Malformed byte sequence");
        }
    }

}
