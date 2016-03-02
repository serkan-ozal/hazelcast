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

package com.hazelcast.internal.memory.accessor;

/**
 * <p>
 * Contact point for {@link MemoryAccessor} implementations
 * which support concurrent memory access operations
 * such as volatile read/write, CAS and ordered write.
 * </p>
 *
 * @see com.hazelcast.internal.memory.accessor.MemoryAccessor
 */
public interface ConcurrentMemoryAccessor extends MemoryAccessor {

    /**
     * Reads the boolean value as volatile from given object by its offset.
     *
     * @param address the address where boolean value will be read from
     * @return the read value
     */
    boolean getBooleanVolatile(long address);

    /**
     * Writes the boolean value as volatile to given object by its offset.
     *
     * @param address   the address where boolean value will be read from
     * @param x         the boolean value to be written
     */
    void putBooleanVolatile(long address, boolean x);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Reads the byte value as volatile from given object by its offset.
     *
     * @param address the address where byte value will be read from
     * @return the read value
     */
    byte getByteVolatile(long address);

    /**
     * Writes the byte value as volatile to given object by its offset.
     *
     * @param address   the address where byte value will be written to
     * @param x         the byte value to be written
     */
    void putByteVolatile(long address, byte x);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Reads the char value as volatile from given object by its offset.
     *
     * @param address the address where char value will be read from
     * @return the read value
     */
    char getCharVolatile(long address);

    /**
     * Writes the char value as volatile to given object by its offset.
     *
     * @param address   the address where char value will be written to
     * @param x         the char value to be written
     */
    void putCharVolatile(long address, char x);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Reads the short value as volatile from given object by its offset.
     *
     * @param address the address where short value will be read from
     * @return the read value
     */
    short getShortVolatile(long address);

    /**
     * Writes the short value as volatile to given object by its offset.
     *
     * @param address   the address where short value will be written to
     * @param x         the short value to be written
     */
    void putShortVolatile(long address, short x);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Reads the int value as volatile from given object by its offset.
     *
     * @param address the address where int value will be read from
     * @return the read value
     */
    int getIntVolatile(long address);

    /**
     * Writes the int value as volatile to given object by its offset.
     *
     * @param address   the address where int value will be written to
     * @param x         the int value to be written
     */
    void putIntVolatile(long address, int x);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Reads the float value as volatile from given object by its offset.
     *
     * @param address the address where float value will be read from
     * @return the read value
     */
    float getFloatVolatile(long address);

    /**
     * Writes the float value as volatile to given object by its offset.
     *
     * @param address   the address where float value will be written to
     * @param x         the float value to be written
     */
    void putFloatVolatile(long address, float x);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Reads the long value as volatile from given object by its offset.
     *
     * @param address the address where long value will be read from
     * @return the read value
     */
    long getLongVolatile(long address);

    /**
     * Writes the long value as volatile to given object by its offset.
     *
     * @param address   the address where long value will be written to
     * @param x         the long value to be written
     */
    void putLongVolatile(long address, long x);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Reads the double value as volatile from given object by its offset.
     *
     * @param address the address where double value will be read from
     * @return the read value
     */
    double getDoubleVolatile(long address);

    /**
     * Writes the double value as volatile to given object by its offset.
     *
     * @param address   the address where double value will be written to
     * @param x         the double value to be written
     */
    void putDoubleVolatile(long address, double x);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Compares and swaps int value to specified value atomically at given address
     * if and only if its current value equals to specified expected value.
     *
     * @param address   the address where int value will be written to
     * @param expected  the expected current int value to be set new int value
     * @param x         the int value to be written
     * @return <tt>true</tt> if CAS successful, otherwise <tt>false</tt>
     */
    boolean compareAndSwapInt(long address, int expected, int x);

    /**
     * Compares and swaps long value to specified value atomically at given address
     * if and only if its current value equals to specified expected value.
     *
     * @param address   the address where long value will be written to
     * @param expected  the expected current long value to be set new long value
     * @param x         the long value to be written
     * @return <tt>true</tt> if CAS successful, otherwise <tt>false</tt>
     */
    boolean compareAndSwapLong(long address, long expected, long x);

}
