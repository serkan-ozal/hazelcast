package com.hazelcast.internal.memory2.accessor;

import com.hazelcast.internal.memory2.strategy.MemoryAccessStrategy;

abstract class AbstractMemoryAccessor implements MemoryAccessor {

    protected final MemoryAccessStrategy memoryAccessStrategy;

    protected AbstractMemoryAccessor(boolean aligned) {
        if (aligned) {
            memoryAccessStrategy = MemoryAccessStrategy.AMEM;
        } else {
            memoryAccessStrategy = MemoryAccessStrategy.MEM;
        }
    }

}
