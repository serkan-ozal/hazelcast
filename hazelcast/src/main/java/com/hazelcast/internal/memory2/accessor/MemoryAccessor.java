package com.hazelcast.internal.memory2.accessor;

/**
 * <p>
 * Abstraction over an address space of readable and writable data (byte, char, int, long, etc ...).
 * {@link MemoryAccessor} is the top (1st) level abstraction for memory accesses and there are other low level abstractions:
 * <ul>
 * <li>{@link com.hazelcast.internal.memory2.strategy.MemoryAccessStrategy} which is 2nd level memory access abstraction</li>
 * <li>{@link com.hazelcast.internal.memory2.MemoryIO} which is 3rd (lowest) level memory access abstraction</li>
 * </ul>
 * </p>
 * <p>
 * The address mentioned doesn't need to be direct memory address
 * and it depends on the {@link MemoryAccessor} implementation itself.
 * For example:
 * <ul>
 * <li>the address might be an offset relative to base address of the {@link MemoryAccessor} implementation.</li>
 * <li>the address might be an offset relative to base byte[] of the {@link MemoryAccessor} implementation</li>
 * </ul>
 * </p>
 *
 * @see com.hazelcast.internal.memory2.impl.accessor.DirectNativeMemoryAccessor
 * @see com.hazelcast.internal.memory2.impl.accessor.BaseAddressedNativeMemoryAccessor
 * @see com.hazelcast.internal.memory2.impl.accessor.ByteArrayMemoryAccessor
 */
public interface MemoryAccessor {

    /**
     * Reads the boolean value from given address.
     *
     * @param address the address where boolean value will be read from
     * @return the read value
     */
    boolean getBoolean(long address);

    /**
     * Reads the boolean value as volatile from given object by its offset.
     *
     * @param address the address where boolean value will be read from
     * @return the read value
     */
    boolean getBooleanVolatile(long address);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Writes the given boolean value to given address.
     *
     * @param address the address where boolean value will be written to
     * @param x       the boolean value to be written
     */
    void putBoolean(long address, boolean x);

    /**
     * Writes the boolean value as volatile to given object by its offset.
     *
     * @param address   the address where boolean value will be read from
     * @param x         the boolean value to be written
     */
    void putBooleanVolatile(long address, boolean x);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Reads the byte value from given address.
     *
     * @param address the address where byte value will be read from
     * @return the read value
     */
    byte getByte(long address);

    /**
     * Reads the byte value as volatile from given object by its offset.
     *
     * @param address the address where byte value will be read from
     * @return the read value
     */
    byte getByteVolatile(long address);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Writes the given byte value to given address.
     *
     * @param address the address where byte value will be written to
     * @param x       the byte value to be written
     */
    void putByte(long address, byte x);

    /**
     * Writes the byte value as volatile to given object by its offset.
     *
     * @param address   the address where byte value will be written to
     * @param x         the byte value to be written
     */
    void putByteVolatile(long address, byte x);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Reads the char value from given address.
     *
     * @param address the address where char value will be read from
     * @return the read value
     */
    char getChar(long address);

    /**
     * Reads the char value as volatile from given object by its offset.
     *
     * @param address the address where char value will be read from
     * @return the read value
     */
    char getCharVolatile(long address);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Writes the given char value to given address.
     *
     * @param address the address where char value will be written to
     * @param x       the char value to be written
     */
    void putChar(long address, char x);

    /**
     * Writes the char value as volatile to given object by its offset.
     *
     * @param address   the address where char value will be written to
     * @param x         the char value to be written
     */
    void putCharVolatile(long address, char x);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Reads the short value from given address.
     *
     * @param address the address where short value will be read from
     * @return the read value
     */
    short getShort(long address);

    /**
     * Reads the short value as volatile from given object by its offset.
     *
     * @param address the address where short value will be read from
     * @return the read value
     */
    short getShortVolatile(long address);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Writes the given short value to given address.
     *
     * @param address the address where short value will be written to
     * @param x       the short value to be written
     */
    void putShort(long address, short x);

    /**
     * Writes the short value as volatile to given object by its offset.
     *
     * @param address   the address where short value will be written to
     * @param x         the short value to be written
     */
    void putShortVolatile(long address, short x);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Reads the int value from given address.
     *
     * @param address the address where int value will be read from
     * @return the read value
     */
    int getInt(long address);

    /**
     * Reads the int value as volatile from given object by its offset.
     *
     * @param address the address where int value will be read from
     * @return the read value
     */
    int getIntVolatile(long address);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Writes the given int value to given address.
     *
     * @param address the address where int value will be written to
     * @param x       the int value to be written
     */
    void putInt(long address, int x);

    /**
     * Writes the int value as volatile to given object by its offset.
     *
     * @param address   the address where int value will be written to
     * @param x         the int value to be written
     */
    void putIntVolatile(long address, int x);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Reads the float value from given address.
     *
     * @param address the address where float value will be read from
     * @return the read value
     */
    float getFloat(long address);

    /**
     * Reads the float value as volatile from given object by its offset.
     *
     * @param address the address where float value will be read from
     * @return the read value
     */
    float getFloatVolatile(long address);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Writes the given float value to given address.
     *
     * @param address the address where float value will be written to
     * @param x       the float value to be written
     */
    void putFloat(long address, float x);

    /**
     * Writes the float value as volatile to given object by its offset.
     *
     * @param address   the address where float value will be written to
     * @param x         the float value to be written
     */
    void putFloatVolatile(long address, float x);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Reads the long value from given address.
     *
     * @param address the address where long value will be read from
     * @return the read value
     */
    long getLong(long address);

    /**
     * Reads the long value as volatile from given object by its offset.
     *
     * @param address the address where long value will be read from
     * @return the read value
     */
    long getLongVolatile(long address);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Writes the given long value to given address.
     *
     * @param address the address where long value will be written to
     * @param x       the long value to be written
     */
    void putLong(long address, long x);

    /**
     * Writes the long value as volatile to given object by its offset.
     *
     * @param address   the address where long value will be written to
     * @param x         the long value to be written
     */
    void putLongVolatile(long address, long x);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Reads the double value from given address.
     *
     * @param address the address where double value will be read from
     * @return the read value
     */
    double getDouble(long address);

    /**
     * Reads the double value as volatile from given object by its offset.
     *
     * @param address the address where double value will be read from
     * @return the read value
     */
    double getDoubleVolatile(long address);

    /////////////////////////////////////////////////////////////////////////

    /**
     * Writes the given double value to given address.
     *
     * @param address the address where double value will be written to
     * @param x       the double value to be written
     */
    void putDouble(long address, double x);

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
