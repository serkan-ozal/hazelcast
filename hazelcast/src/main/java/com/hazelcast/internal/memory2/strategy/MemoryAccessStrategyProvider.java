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

package com.hazelcast.internal.memory2.strategy;

import com.hazelcast.internal.memory2.impl.strategy.AlignmentAwareMemoryAccessStrategy;
import com.hazelcast.internal.memory2.impl.strategy.StandardMemoryAccessStrategy;

import java.util.EnumMap;
import java.util.Map;

/**
 * Provides {@link MemoryAccessStrategy} implementations by their {@link MemoryAccessStrategyType}.
 */
public final class MemoryAccessStrategyProvider {

    private static final Map<MemoryAccessStrategyType, MemoryAccessStrategy> MEMORY_ACCESS_STRATEGY_MAP
            = new EnumMap<MemoryAccessStrategyType, MemoryAccessStrategy>(MemoryAccessStrategyType.class);

    static {
        final boolean unalignedAccessAllowed = isUnalignedAccessAllowed();

        if (StandardMemoryAccessStrategy.isAvailable()) {
            StandardMemoryAccessStrategy standardMemoryAccessStrategy = new StandardMemoryAccessStrategy();
            MEMORY_ACCESS_STRATEGY_MAP.put(MemoryAccessStrategyType.STANDARD, standardMemoryAccessStrategy);
            if (unalignedAccessAllowed) {
                MEMORY_ACCESS_STRATEGY_MAP.put(MemoryAccessStrategyType.PLATFORM_AWARE, standardMemoryAccessStrategy);
            }
        }

        if (AlignmentAwareMemoryAccessStrategy.isAvailable()) {
            AlignmentAwareMemoryAccessStrategy alignmentAwareMemoryAccessStrategy = new AlignmentAwareMemoryAccessStrategy();
            MEMORY_ACCESS_STRATEGY_MAP.put(MemoryAccessStrategyType.ALIGNMENT_AWARE, alignmentAwareMemoryAccessStrategy);
            if (!unalignedAccessAllowed) {
                MEMORY_ACCESS_STRATEGY_MAP.put(MemoryAccessStrategyType.PLATFORM_AWARE, alignmentAwareMemoryAccessStrategy);
            }
        }
    }

    private MemoryAccessStrategyProvider() {
    }

    public static boolean isUnalignedAccessAllowed() {
        // we can't use Unsafe to access memory on platforms where unaligned access is not allowed
        // see https://github.com/hazelcast/hazelcast/issues/5518 for details.
        String arch = System.getProperty("os.arch");
        // list of architectures copied from OpenJDK - java.nio.Bits::unaligned
        return arch.equals("i386") || arch.equals("x86") || arch.equals("amd64") || arch.equals("x86_64");
    }

    /**
     * Returns the {@link MemoryAccessStrategy} instance appropriate to the given {@link MemoryAccessStrategyType}.
     */
    public static MemoryAccessStrategy getMemoryAccessStrategy(MemoryAccessStrategyType memoryAccessorType) {
        return MEMORY_ACCESS_STRATEGY_MAP.get(memoryAccessorType);
    }

    /**
     * Returns the default {@link MemoryAccessStrategy} instance.
     */
    public static MemoryAccessStrategy getDefaultMemoryAccessStrategy() {
        return MEMORY_ACCESS_STRATEGY_MAP.get(MemoryAccessStrategyType.PLATFORM_AWARE);
    }

}
