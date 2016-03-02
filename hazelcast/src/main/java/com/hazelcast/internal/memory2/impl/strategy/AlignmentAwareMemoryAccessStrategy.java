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

package com.hazelcast.internal.memory2.impl.strategy;

import com.hazelcast.internal.memory2.MemoryIO;

import java.nio.ByteOrder;

/**
 * <p>
 * Aligned {@link com.hazelcast.internal.memory.MemoryAccessor} which checks for and handles unaligned memory access
 * by splitting a larger-size memory operation into several smaller-size ones
 * (which have finer-grained alignment requirements).
 * </p><p>
 * A few notes on this implementation:
 * <ul>
 *      <li>
 *        There is no atomicity guarantee for unaligned memory accesses.
 *        In fact, even on platforms which support unaligned memory accesses,
 *        there is no guarantee for atomicity when there is unaligned memory accesses.
 *        On later Intel processors, unaligned access within the cache line is atomic,
 *        but access that straddles cache lines is not.
 *        See http://psy-lob-saw.blogspot.com.tr/2013/07/atomicity-of-unaligned-memory-access-in.html
 *        for more details.
 *      </li>
 *      <li>Unaligned memory access is not supported for CAS operations. </li>
 *      <li>Unaligned memory access is not supported for ordered writes. </li>
 * </ul>
 * </p>
 */
public class AlignmentAwareMemoryAccessStrategy extends StandardMemoryAccessStrategy {

    private static final int OBJECT_REFERENCE_ALIGN = UNSAFE.arrayIndexScale(Object[].class);
    private static final int OBJECT_REFERENCE_MASK = OBJECT_REFERENCE_ALIGN - 1;
    private static final boolean BIG_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;

    public AlignmentAwareMemoryAccessStrategy() {
        if (!AVAILABLE) {
            throw new IllegalStateException(getClass().getName() + " can only be used only when Unsafe is available!");
        }
    }

    private static boolean is2BytesAligned(long value) {
        return (value & 0x01) == 0;
    }

    private static boolean is4BytesAligned(long value) {
        return (value & 0x03) == 0;
    }

    private static boolean is8BytesAligned(long value) {
        return (value & 0x07) == 0;
    }

    private static boolean isReferenceAligned(long offset) {
        return (offset & OBJECT_REFERENCE_MASK) == 0;
    }

    private static void checkReferenceAligned(long offset) {
        if (!isReferenceAligned(offset)) {
            throw new IllegalArgumentException("Memory accesses to references must be "
                    + OBJECT_REFERENCE_ALIGN + "-bytes aligned, but it is " + offset);
        }
    }

    /////////////////////////////////////////////////////////////////////////

    @Override
    public char getChar(long address) {
        if (is2BytesAligned(address)) {
            return super.getChar(address);
        } else {
            return MemoryIO.readChar(address, BIG_ENDIAN);
        }
    }

    @Override
    public char getCharVolatile(long address) {
        if (is2BytesAligned(address)) {
            return super.getCharVolatile(address);
        } else {
            return MemoryIO.readCharVolatile(address, BIG_ENDIAN);
        }
    }

    @Override
    public char getChar(Object o, long offset) {
        if (is2BytesAligned(offset)) {
            return super.getChar(o, offset);
        } else {
            return MemoryIO.readChar(o, offset, BIG_ENDIAN);
        }
    }

    @Override
    public char getCharVolatile(Object o, long offset) {
        if (is2BytesAligned(offset)) {
            return super.getCharVolatile(o, offset);
        } else {
            return MemoryIO.readCharVolatile(o, offset, BIG_ENDIAN);
        }
    }

    /////////////////////////////////////////////////////////////////////////

    @Override
    public void putChar(long address, char x) {
        if (is2BytesAligned(address)) {
            super.putChar(address, x);
        } else {
            MemoryIO.writeChar(address, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putCharVolatile(long address, char x) {
        if (is2BytesAligned(address)) {
            super.putCharVolatile(address, x);
        } else {
            MemoryIO.writeCharVolatile(address, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putChar(Object o, long offset, char x) {
        if (is2BytesAligned(offset)) {
            super.putChar(o, offset, x);
        } else {
            MemoryIO.writeChar(o, offset, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putCharVolatile(Object o, long offset, char x) {
        if (is2BytesAligned(offset)) {
            super.putChar(o, offset, x);
        } else {
            MemoryIO.writeCharVolatile(o, offset, x, BIG_ENDIAN);
        }
    }

    /////////////////////////////////////////////////////////////////////////

    @Override
    public short getShort(long address) {
        if (is2BytesAligned(address)) {
            return super.getShort(address);
        } else {
            return MemoryIO.readShort(address, BIG_ENDIAN);
        }
    }

    @Override
    public short getShortVolatile(long address) {
        if (is2BytesAligned(address)) {
            return super.getShortVolatile(address);
        } else {
            return MemoryIO.readShortVolatile(address, BIG_ENDIAN);
        }
    }

    @Override
    public short getShort(Object o, long offset) {
        if (is2BytesAligned(offset)) {
            return super.getShort(o, offset);
        } else {
            return MemoryIO.readShort(o, offset, BIG_ENDIAN);
        }
    }

    @Override
    public short getShortVolatile(Object o, long offset) {
        if (is2BytesAligned(offset)) {
            return super.getShortVolatile(o, offset);
        } else {
            return MemoryIO.readShortVolatile(o, offset, BIG_ENDIAN);
        }
    }

    /////////////////////////////////////////////////////////////////////////

    @Override
    public void putShort(long address, short x) {
        if (is2BytesAligned(address)) {
            super.putShort(address, x);
        } else {
            MemoryIO.writeShort(address, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putShortVolatile(long address, short x) {
        if (is2BytesAligned(address)) {
            super.putShortVolatile(address, x);
        } else {
            MemoryIO.writeShortVolatile(address, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putShort(Object o, long offset, short x) {
        if (is2BytesAligned(offset)) {
            super.putShort(o, offset, x);
        } else {
            MemoryIO.writeShort(o, offset, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putShortVolatile(Object o, long offset, short x) {
        if (is2BytesAligned(offset)) {
            super.putShortVolatile(o, offset, x);
        } else {
            MemoryIO.writeShortVolatile(o, offset, x, BIG_ENDIAN);
        }
    }

    /////////////////////////////////////////////////////////////////////////

    @Override
    public int getInt(long address) {
        if (is4BytesAligned(address)) {
            return super.getInt(address);
        } else {
            return MemoryIO.readInt(address, BIG_ENDIAN);
        }
    }

    @Override
    public int getIntVolatile(long address) {
        if (is4BytesAligned(address)) {
            return super.getIntVolatile(address);
        } else {
            return MemoryIO.readIntVolatile(address, BIG_ENDIAN);
        }
    }

    @Override
    public int getInt(Object o, long offset) {
        if (is4BytesAligned(offset)) {
            return super.getInt(o, offset);
        } else {
            return MemoryIO.readInt(o, offset, BIG_ENDIAN);
        }
    }

    @Override
    public int getIntVolatile(Object o, long offset) {
        if (is4BytesAligned(offset)) {
            return super.getIntVolatile(o, offset);
        } else {
            return MemoryIO.readIntVolatile(o, offset, BIG_ENDIAN);
        }
    }

    /////////////////////////////////////////////////////////////////////////

    @Override
    public void putInt(long address, int x) {
        if (is4BytesAligned(address)) {
            super.putInt(address, x);
        } else {
            MemoryIO.writeInt(address, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putIntVolatile(long address, int x) {
        if (is4BytesAligned(address)) {
            super.putIntVolatile(address, x);
        } else {
            MemoryIO.writeIntVolatile(address, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putInt(Object o, long offset, int x) {
        if (is4BytesAligned(offset)) {
            super.putInt(o, offset, x);
        } else {
            MemoryIO.writeInt(o, offset, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putIntVolatile(Object o, long offset, int x) {
        if (is4BytesAligned(offset)) {
            super.putIntVolatile(o, offset, x);
        } else {
            MemoryIO.writeIntVolatile(o, offset, x, BIG_ENDIAN);
        }
    }

    /////////////////////////////////////////////////////////////////////////

    @Override
    public float getFloat(long address) {
        if (is4BytesAligned(address)) {
            return super.getFloat(address);
        } else {
            return MemoryIO.readFloat(address, BIG_ENDIAN);
        }
    }

    @Override
    public float getFloatVolatile(long address) {
        if (is4BytesAligned(address)) {
            return super.getFloatVolatile(address);
        } else {
            return MemoryIO.readFloatVolatile(address, BIG_ENDIAN);
        }
    }

    @Override
    public float getFloat(Object o, long offset) {
        if (is4BytesAligned(offset)) {
            return super.getFloat(o, offset);
        } else {
            return MemoryIO.readFloat(o, offset, BIG_ENDIAN);
        }
    }

    @Override
    public float getFloatVolatile(Object o, long offset) {
        if (is4BytesAligned(offset)) {
            return super.getFloatVolatile(o, offset);
        } else {
            return MemoryIO.readFloatVolatile(o, offset, BIG_ENDIAN);
        }
    }

    /////////////////////////////////////////////////////////////////////////

    @Override
    public void putFloat(long address, float x) {
        if (is4BytesAligned(address)) {
            super.putFloat(address, x);
        } else {
            MemoryIO.writeFloat(address, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putFloatVolatile(long address, float x) {
        if (is4BytesAligned(address)) {
            super.putFloatVolatile(address, x);
        } else {
            MemoryIO.writeFloatVolatile(address, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putFloat(Object o, long offset, float x) {
        if (is4BytesAligned(offset)) {
            super.putFloat(o, offset, x);
        } else {
            MemoryIO.writeFloat(o, offset, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putFloatVolatile(Object o, long offset, float x) {
        if (is4BytesAligned(offset)) {
            super.putFloatVolatile(o, offset, x);
        } else {
            MemoryIO.writeFloatVolatile(o, offset, x, BIG_ENDIAN);
        }
    }

    /////////////////////////////////////////////////////////////////////////

    @Override
    public long getLong(long address) {
        if (is8BytesAligned(address)) {
            return super.getLong(address);
        } else {
            return MemoryIO.readLong(address, BIG_ENDIAN);
        }
    }

    @Override
    public long getLongVolatile(long address) {
        if (is8BytesAligned(address)) {
            return super.getLongVolatile(address);
        } else {
            return MemoryIO.readLongVolatile(address, BIG_ENDIAN);
        }
    }

    @Override
    public long getLong(Object o, long offset) {
        if (is8BytesAligned(offset)) {
            return super.getLong(o, offset);
        } else {
            return MemoryIO.readLong(o, offset, BIG_ENDIAN);
        }
    }

    @Override
    public long getLongVolatile(Object o, long offset) {
        if (is8BytesAligned(offset)) {
            return super.getLongVolatile(o, offset);
        } else {
            return MemoryIO.readLongVolatile(o, offset, BIG_ENDIAN);
        }
    }

    /////////////////////////////////////////////////////////////////////////

    @Override
    public void putLong(long address, long x) {
        if (is8BytesAligned(address)) {
            super.putLong(address, x);
        } else {
            MemoryIO.writeLong(address, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putLongVolatile(long address, long x) {
        if (is8BytesAligned(address)) {
            super.putLongVolatile(address, x);
        } else {
            MemoryIO.writeLongVolatile(address, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putLong(Object o, long offset, long x) {
        if (is8BytesAligned(offset)) {
            super.putLong(o, offset, x);
        } else {
            MemoryIO.writeLong(o, offset, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putLongVolatile(Object o, long offset, long x) {
        if (is8BytesAligned(offset)) {
            super.putLongVolatile(o, offset, x);
        } else {
            MemoryIO.writeLongVolatile(o, offset, x, BIG_ENDIAN);
        }
    }

    /////////////////////////////////////////////////////////////////////////

    @Override
    public double getDouble(long address) {
        if (is8BytesAligned(address)) {
            return super.getDouble(address);
        } else {
            return MemoryIO.readDouble(address, BIG_ENDIAN);
        }
    }

    @Override
    public double getDoubleVolatile(long address) {
        if (is8BytesAligned(address)) {
            return super.getDoubleVolatile(address);
        } else {
            return MemoryIO.readDoubleVolatile(address, BIG_ENDIAN);
        }
    }

    @Override
    public double getDouble(Object o, long offset) {
        if (is8BytesAligned(offset)) {
            return super.getDouble(o, offset);
        } else {
            return MemoryIO.readDouble(o, offset, BIG_ENDIAN);
        }
    }

    @Override
    public double getDoubleVolatile(Object o, long offset) {
        if (is8BytesAligned(offset)) {
            return super.getDoubleVolatile(o, offset);
        } else {
            return MemoryIO.readDoubleVolatile(o, offset, BIG_ENDIAN);
        }
    }

    /////////////////////////////////////////////////////////////////////////

    @Override
    public void putDouble(long address, double x) {
        if (is8BytesAligned(address)) {
            super.putDouble(address, x);
        } else {
            MemoryIO.writeDouble(address, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putDoubleVolatile(long address, double x) {
        if (is8BytesAligned(address)) {
            super.putDoubleVolatile(address, x);
        } else {
            MemoryIO.writeDoubleVolatile(address, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putDouble(Object o, long offset, double x) {
        if (is8BytesAligned(offset)) {
            super.putDouble(o, offset, x);
        } else {
            MemoryIO.writeDouble(o, offset, x, BIG_ENDIAN);
        }
    }

    @Override
    public void putDoubleVolatile(Object o, long offset, double x) {
        if (is8BytesAligned(offset)) {
            super.putDoubleVolatile(o, offset, x);
        } else {
            MemoryIO.writeDoubleVolatile(o, offset, x, BIG_ENDIAN);
        }
    }

    /////////////////////////////////////////////////////////////////////////

    @Override
    public Object getObject(Object o, long offset) {
        checkReferenceAligned(offset);
        return super.getObject(o, offset);
    }

    @Override
    public Object getObjectVolatile(Object o, long offset) {
        checkReferenceAligned(offset);
        return super.getObjectVolatile(o, offset);
    }

    /////////////////////////////////////////////////////////////////////////

    @Override
    public void putObject(Object o, long offset, Object x) {
        checkReferenceAligned(offset);
        super.putObject(o, offset, x);
    }

    @Override
    public void putObjectVolatile(Object o, long offset, Object x) {
        checkReferenceAligned(offset);
        super.putObjectVolatile(o, offset, x);
    }

    /////////////////////////////////////////////////////////////////////////


    @Override
    public boolean compareAndSwapInt(long address, int expected, int x) {
        if (is4BytesAligned(address)) {
            return super.compareAndSwapInt(address, expected, x);
        } else {
            throw new IllegalArgumentException("Unaligned memory accesses are not supported for CAS operations. "
                    + "Address must be 4-bytes aligned for integer typed CAS, but it is " + address);
        }
    }

    @Override
    public boolean compareAndSwapInt(Object o, long offset, int expected, int x) {
        if (is4BytesAligned(offset)) {
            return super.compareAndSwapInt(o, offset, expected, x);
        } else {
            throw new IllegalArgumentException("Unaligned memory accesses are not supported for CAS operations. "
                    + "Offset must be 4-bytes aligned for integer typed CAS, but it is " + offset);
        }
    }

    @Override
    public boolean compareAndSwapLong(long address, long expected, long x) {
        if (is4BytesAligned(address)) {
            return super.compareAndSwapLong(address, expected, x);
        } else {
            throw new IllegalArgumentException("Unaligned memory accesses are not supported for CAS operations. "
                    + "Address must be 8-bytes aligned for long typed CAS, but it is " + address);
        }
    }

    @Override
    public boolean compareAndSwapLong(Object o, long offset, long expected, long x) {
        if (is4BytesAligned(offset)) {
            return super.compareAndSwapLong(o, offset, expected, x);
        } else {
            throw new IllegalArgumentException("Unaligned memory accesses are not supported for CAS operations. "
                    + "Offset must be 8-bytes aligned for long typed CAS, but it is " + offset);
        }
    }

    @Override
    public boolean compareAndSwapObject(Object o, long offset, Object expected, Object x) {
        if (isReferenceAligned(offset)) {
            return super.compareAndSwapObject(o, offset, expected, x);
        } else {
            throw new IllegalArgumentException("Unaligned memory accesses are not supported for CAS operations. "
                    + "Offset must be " + OBJECT_REFERENCE_ALIGN + "-bytes "
                    + "aligned for object reference typed CAS, but it is " + offset);
        }
    }

    /////////////////////////////////////////////////////////////////////////


    @Override
    public void putOrderedInt(long address, int x) {
        if (is4BytesAligned(address)) {
            super.putOrderedInt(address, x);
        } else {
            throw new IllegalArgumentException("Unaligned memory accesses are not supported for ordered writes. "
                    + "Address must be 4-bytes aligned for integer typed ordered write, but it is " + address);
        }
    }

    @Override
    public void putOrderedInt(Object o, long offset, int x) {
        if (is4BytesAligned(offset)) {
            super.putOrderedInt(o, offset, x);
        } else {
            throw new IllegalArgumentException("Unaligned memory accesses are not supported for ordered writes. "
                    + "Offset must be 4-bytes aligned for integer typed ordered write, but it is " + offset);
        }
    }

    @Override
    public void putOrderedLong(long address, long x) {
        if (is8BytesAligned(address)) {
            super.putOrderedLong(address, x);
        } else {
            throw new IllegalArgumentException("Unaligned memory accesses are not supported for ordered writes. "
                    + "Address must be 8-bytes aligned for long typed ordered write, but it is " + address);
        }
    }

    @Override
    public void putOrderedLong(Object o, long offset, long x) {
        if (is8BytesAligned(offset)) {
            super.putOrderedLong(o, offset, x);
        } else {
            throw new IllegalArgumentException("Unaligned memory accesses are not supported for ordered writes. "
                    + "Offset must be 8-bytes aligned for long typed ordered write, but it is " + offset);
        }
    }

    @Override
    public void putOrderedObject(Object o, long offset, Object x) {
        if (isReferenceAligned(offset)) {
            super.putOrderedObject(o, offset, x);
        } else {
            throw new IllegalArgumentException("Unaligned memory accesses are not supported for CAS operations. "
                    + "Offset must be " + OBJECT_REFERENCE_ALIGN + "-bytes "
                    + "aligned for object reference typed ordered writes, but it is " + offset);
        }
    }

}
