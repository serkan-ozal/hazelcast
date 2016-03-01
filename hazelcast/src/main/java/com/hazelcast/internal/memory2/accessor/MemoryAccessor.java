package com.hazelcast.internal.memory2.accessor;

public interface MemoryAccessor {

    /////////////////////////////////////////////////////////////////////////

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

}
