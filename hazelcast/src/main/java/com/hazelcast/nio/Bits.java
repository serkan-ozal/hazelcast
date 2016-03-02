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

package com.hazelcast.nio;

import com.hazelcast.internal.memory.MemoryIO;

import java.io.DataInput;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Access and manipulate bits, bytes, primitives ...
 */
public final class Bits {

    /**
     * Byte size in bytes
     */
    public static final int BYTE_SIZE_IN_BYTES = 1;
    /**
     * Boolean size in bytes
     */
    public static final int BOOLEAN_SIZE_IN_BYTES = 1;
    /**
     * Short size in bytes
     */
    public static final int SHORT_SIZE_IN_BYTES = 2;
    /**
     * Char size in bytes
     */
    public static final int CHAR_SIZE_IN_BYTES = 2;
    /**
     * Integer size in bytes
     */
    public static final int INT_SIZE_IN_BYTES = 4;
    /**
     * Float size in bytes
     */
    public static final int FLOAT_SIZE_IN_BYTES = 4;
    /**
     * Long size in bytes
     */
    public static final int LONG_SIZE_IN_BYTES = 8;
    /**
     * Double size in bytes
     */
    public static final int DOUBLE_SIZE_IN_BYTES = 8;
    /**
     * for null arrays, this value writen to stream to represent null array size.
     */
    public static final int NULL_ARRAY_LENGTH = -1;
    /**
     * Length of the data blocks used by the CPU cache sub-system in bytes.
     */
    public static final int CACHE_LINE_LENGTH = 64;

    /**
     * A reusable instance of the UTF-8 charset
     */
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    /**
     * A reusable instance of the ISO Latin-1 charset
     */
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    private Bits() {
    }

    public static char readChar(byte[] buffer, int pos, boolean bigEndian) {
        return MemoryIO.readChar(buffer, pos, bigEndian);
    }

    public static char readCharB(byte[] buffer, int pos) {
        return MemoryIO.readCharB(buffer, pos);
    }

    public static char readCharL(byte[] buffer, int pos) {
        return MemoryIO.readCharL(buffer, pos);
    }

    public static void writeChar(byte[] buffer, int pos, char v, boolean bigEndian) {
        MemoryIO.writeChar(buffer, pos, v, bigEndian);
    }

    public static void writeCharB(byte[] buffer, int pos, char v) {
        MemoryIO.writeCharB(buffer, pos, v);
    }

    public static void writeCharL(byte[] buffer, int pos, char v) {
        MemoryIO.writeCharL(buffer, pos, v);
    }

    public static short readShort(byte[] buffer, int pos, boolean bigEndian) {
        return MemoryIO.readShort(buffer, pos, bigEndian);
    }

    public static short readShortB(byte[] buffer, int pos) {
        return MemoryIO.readShortB(buffer, pos);
    }

    public static short readShortL(byte[] buffer, int pos) {
        return MemoryIO.readShortL(buffer, pos);
    }

    public static void writeShort(byte[] buffer, int pos, short v, boolean bigEndian) {
        MemoryIO.writeShort(buffer, pos, v, bigEndian);
    }

    public static void writeShortB(byte[] buffer, int pos, short v) {
        MemoryIO.writeShortB(buffer, pos, v);
    }

    public static void writeShortL(byte[] buffer, int pos, short v) {
        MemoryIO.writeShortL(buffer, pos, v);
    }

    public static int readInt(byte[] buffer, int pos, boolean bigEndian) {
        return MemoryIO.readInt(buffer, pos, bigEndian);
    }

    public static int readIntB(byte[] buffer, int pos) {
        return MemoryIO.readIntB(buffer, pos);
    }

    public static int readIntL(byte[] buffer, int pos) {
        return MemoryIO.readIntL(buffer, pos);
    }

    public static void writeInt(byte[] buffer, int pos, int v, boolean bigEndian) {
        MemoryIO.writeInt(buffer, pos, v, bigEndian);
    }

    public static void writeIntB(byte[] buffer, int pos, int v) {
        MemoryIO.writeIntB(buffer, pos, v);
    }

    public static void writeIntL(byte[] buffer, int pos, int v) {
        MemoryIO.writeIntL(buffer, pos, v);
    }

    public static long readLong(byte[] buffer, int pos, boolean bigEndian) {
        return MemoryIO.readLong(buffer, pos, bigEndian);
    }

    public static long readLongB(byte[] buffer, int pos) {
        return MemoryIO.readLongB(buffer, pos);
    }

    public static long readLongL(byte[] buffer, int pos) {
        return MemoryIO.readLongL(buffer, pos);
    }

    public static void writeLong(byte[] buffer, int pos, long v, boolean bigEndian) {
        MemoryIO.writeLong(buffer, pos, v, bigEndian);
    }

    public static void writeLongB(byte[] buffer, int pos, long v) {
        MemoryIO.writeLongB(buffer, pos, v);
    }

    public static void writeLongL(byte[] buffer, int pos, long v) {
        MemoryIO.writeLongL(buffer, pos, v);
    }

    public static int writeUtf8Char(byte[] buffer, int pos, int c) {
        return MemoryIO.writeUtf8Char(buffer, pos, c);
    }

    public static int readUtf8Char(byte[] buffer, int pos, char[] dst, int dstPos)
            throws IOException {
        return MemoryIO.readUtf8Char(buffer, pos, dst, dstPos);
    }

    public static char readUtf8Char(DataInput in, byte firstByte)
            throws IOException {
        return MemoryIO.readUtf8Char(in, firstByte);
    }

    /**
     * Sets n-th bit of the byte value
     *
     * @param value byte value
     * @param bit   n-th bit
     * @return value
     */
    public static byte setBit(byte value, int bit) {
        value |= 1 << bit;
        return value;
    }

    /**
     * Clears n-th bit of the byte value
     *
     * @param value byte value
     * @param bit   n-th bit
     * @return value
     */
    public static byte clearBit(byte value, int bit) {
        value &= ~(1 << bit);
        return value;
    }

    /**
     * Inverts n-th bit of the byte value
     *
     * @param value byte value
     * @param bit   n-th bit
     * @return value
     */
    public static byte invertBit(byte value, int bit) {
        value ^= 1 << bit;
        return value;
    }

    /**
     * Sets n-th bit of the integer value
     *
     * @param value integer value
     * @param bit   n-th bit
     * @return value
     */
    public static int setBit(int value, int bit) {
        value |= 1 << bit;
        return value;
    }

    /**
     * Clears n-th bit of the integer value
     *
     * @param value integer value
     * @param bit   n-th bit
     * @return value
     */
    public static int clearBit(int value, int bit) {
        value &= ~(1 << bit);
        return value;
    }

    /**
     * Inverts n-th bit of the integer value
     *
     * @param value integer value
     * @param bit   n-th bit
     * @return value
     */
    public static int invertBit(int value, int bit) {
        value ^= 1 << bit;
        return value;
    }

    /**
     * Returns true if n-th bit of the value is set, false otherwise
     */
    public static boolean isBitSet(int value, int bit) {
        return (value & 1 << bit) != 0;
    }

    /**
     * Combines two short integer values into an integer.
     */
    public static int combineToInt(short x, short y) {
        return ((int) x << 16) | ((int) y & 0xFFFF);
    }

    public static short extractShort(int value, boolean lowerBits) {
        return (short) ((lowerBits) ? value : (value >> 16));
    }

    /**
     * Combines two integer values into a long integer.
     */
    public static long combineToLong(int x, int y) {
        return ((long) x << 32) | ((long) y & 0xFFFFFFFFL);
    }

    public static int extractInt(long value, boolean lowerBits) {
        return (int) ((lowerBits) ? value : (value >> 32));
    }
}
