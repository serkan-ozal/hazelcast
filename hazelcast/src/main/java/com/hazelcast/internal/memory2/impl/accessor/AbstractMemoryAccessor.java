package com.hazelcast.internal.memory2.impl.accessor;

import com.hazelcast.internal.memory2.accessor.MemoryAccessor;
import com.hazelcast.internal.memory2.strategy.MemoryAccessStrategy;

import java.nio.ByteOrder;

abstract class AbstractMemoryAccessor implements MemoryAccessor {

    protected static final boolean BIG_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;

    protected final MemoryAccessStrategy memoryAccessStrategy;

    protected AbstractMemoryAccessor(boolean aligned) {
        if (aligned) {
            memoryAccessStrategy = MemoryAccessStrategy.AMEM;
        } else {
            memoryAccessStrategy = MemoryAccessStrategy.MEM;
        }
    }

}
