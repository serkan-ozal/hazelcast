/*
 * Copyright (c) 2008-2015, Hazelcast, Inc. All Rights Reserved.
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

package com.hazelcast.internal.serialization.impl;

import com.hazelcast.nio.UnsafeHelper;
import com.hazelcast.util.QuickMath;
import sun.misc.Unsafe;

/**
 *
 */
public final class DataUtil  {

    private static final ByteArrayComparator BYTE_ARRAY_COMPARATOR;

    static {
        if (UnsafeHelper.UNSAFE_AVAILABLE) {
            BYTE_ARRAY_COMPARATOR = new SimpleByteArrayComparator(); //UnsafeBasedByteArrayComparator(UnsafeHelper.UNSAFE);
        } else {
            BYTE_ARRAY_COMPARATOR = new SimpleByteArrayComparator();
        }
    }

    private DataUtil() {
    }

    /**
     *
     *
     * @param data1
     * @param data2
     * @param start
     * @param end
     * @return
     */
    public static boolean equals(byte[] data1, byte[] data2, int start, int end) {
        if (data1 == data2) {
            return true;
        }
        if (data1 == null || data2 == null) {
            return false;
        }
        final int length = data1.length;
        if (data2.length != length) {
            return false;
        }
        return BYTE_ARRAY_COMPARATOR.equals(data1, data2, start, end);
    }

    private static interface ByteArrayComparator {

        boolean equals(byte[] data1, byte[] data2, int start, int end);

    }

    private static class SimpleByteArrayComparator implements ByteArrayComparator {

        @Override
        public boolean equals(byte[] data1, byte[] data2, int start, int end) {
            for (int i = end; i >= start; i--) {
                if (data1[i] != data2[i]) {
                    return false;
                }
            }
            return true;
        }

    }

    private static class UnsafeBasedByteArrayComparator implements ByteArrayComparator {

        private final Unsafe UNSAFE;
        private final int BYTE_ARRAY_BASE_OFFSET;

        private UnsafeBasedByteArrayComparator(Unsafe unsafe) {
            this.UNSAFE = unsafe;
            this.BYTE_ARRAY_BASE_OFFSET = UNSAFE.arrayBaseOffset(byte[].class);
        }

        @Override
        public boolean equals(byte[] data1, byte[] data2, int start, int end) {
            return vectorizedMismatch(data1, data2, start, end);
        }

        private boolean vectorizedMismatch(Object a, Object b,
                                           int start, int end) {
            int finish = end + 1;
            int startWithOffset = BYTE_ARRAY_BASE_OFFSET + start;
            int finishWithOffset = BYTE_ARRAY_BASE_OFFSET + finish;
            int head = QuickMath.modPowerOfTwo(startWithOffset, Long.BYTES);
            int tail = QuickMath.modPowerOfTwo(finishWithOffset, Long.BYTES);
            int startWithOffsetAsLongAligned = head == 0 ? startWithOffset : (startWithOffset + Long.BYTES - tail);
            int finishWithOffsetAsLongAligned = finishWithOffset - tail;

            int i = startWithOffset;

            for (; i < startWithOffsetAsLongAligned; i++) {
                byte av = UNSAFE.getByte(a, i);
                byte bv = UNSAFE.getByte(b, i);
                if (av != bv) {
                    return false;
                }
            }

            for (; i < finishWithOffsetAsLongAligned; i += Long.BYTES) {
                long av = UNSAFE.getLong(a, i);
                long bv = UNSAFE.getLong(b, i);
                if (av != bv) {
                    return false;
                }
            }

            for (; i < finishWithOffset; i++) {
                byte av = UNSAFE.getByte(a, i);
                byte bv = UNSAFE.getByte(b, i);
                if (av != bv) {
                    return false;
                }
            }

            return true;
        }
    }

}
