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

import com.hazelcast.internal.memory2.impl.UnsafeUtil;
import com.hazelcast.internal.memory2.strategy.MemoryAccessStrategy;
import sun.misc.Unsafe;

import java.nio.ByteOrder;

/**
 * Base class for {@link sun.misc.Unsafe} backed {@link com.hazelcast.internal.memory.MemoryAccessor} implementations.
 */
abstract class UnsafeBasedMemoryAccessStrategy implements MemoryAccessStrategy {

    /**
     * The {@link sun.misc.Unsafe} instance which is available and ready to use.
     */
    protected static final Unsafe UNSAFE = UnsafeUtil.UNSAFE;

    /**
     * If this constant is {@code true}, then {@link sun.misc.Unsafe} refers to a usable {@code Unsafe}
     * instance.
     */
    protected static final boolean AVAILABLE = UNSAFE != null;

    /**
     * <tr>true</tr> if native byte order of the platform is <tt>BIG-ENDIAN</tt>,
     * <tt>false</tt> otherwise (<tt>LITTLE-ENDIAN</tt>).
     */
    protected static final boolean BIG_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;

    /**
     * Returns the state about this kind of {@link UnsafeBasedMemoryAccessStrategy} instances are available or not.
     *
     * @return <tt>true</tt> if this kind of {@link UnsafeBasedMemoryAccessStrategy} instances are available,
     *         otherwise <tt>false</tt>
     */
    public static boolean isAvailable() {
        return AVAILABLE;
    }

}
