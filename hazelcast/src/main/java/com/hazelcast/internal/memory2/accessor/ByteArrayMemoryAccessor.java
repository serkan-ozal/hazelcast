package com.hazelcast.internal.memory2.accessor;

import com.hazelcast.internal.memory2.strategy.MemoryAccessStrategy;

public class ByteArrayMemoryAccessor extends AbstractMemoryAccessor {

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

    public byte[] getBaseArray() {
        return baseArray;
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

    }

    private static class PureJavaByteArrayReaderWriter implements ByteArrayReaderWriter {

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

    }

    private static class MemoryAccessStrategyBasedReaderWriter implements ByteArrayReaderWriter {

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

    }

}
