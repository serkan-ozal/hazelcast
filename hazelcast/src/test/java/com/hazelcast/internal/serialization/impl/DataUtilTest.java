package com.hazelcast.internal.serialization.impl;


import com.hazelcast.nio.serialization.Data;
import com.hazelcast.test.HazelcastSerialClassRunner;
import com.hazelcast.test.annotation.ParallelTest;
import com.hazelcast.test.annotation.QuickTest;
import com.hazelcast.util.ConcurrentReferenceHashMap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

@RunWith(HazelcastSerialClassRunner.class)
@Category({QuickTest.class, ParallelTest.class})
public class DataUtilTest {

    private final Random random = new Random();

    @Test
    public void equalsReturnsTrueWhenByteArraysAreSame() {
        final int LENGTH = 100;
        byte[] array1 = new byte[LENGTH];
        byte[] array2 = array1;

        random.nextBytes(array1);

        assertTrue(DataUtil.equals(array1, array2, 0, LENGTH - 1));
    }

    @Test
    public void equalsReturnsTrueWhenByteArraysHaveSameContentWhenLengthIsMultiplyOfLongSize() {
        final int LENGTH = Long.BYTES * 10;
        byte[] array1 = new byte[LENGTH];
        byte[] array2 = new byte[LENGTH];

        random.nextBytes(array1);
        System.arraycopy(array1, 0, array2, 0, LENGTH);

        assertTrue(DataUtil.equals(array1, array2, 0, LENGTH - 1));
    }

    @Test
    public void equalsReturnsTrueWhenByteArraysHaveSameContentWhenLengthIsNotMultiplyOfLongSize() {
        final int LENGTH = Long.BYTES * 10 + (1 + random.nextInt(Long.BYTES - 1));
        byte[] array1 = new byte[LENGTH];
        byte[] array2 = new byte[LENGTH];

        random.nextBytes(array1);
        System.arraycopy(array1, 0, array2, 0, LENGTH);

        assertTrue(DataUtil.equals(array1, array2, 0, LENGTH - 1));
    }

    @Test
    public void equalsReturnsTrueWhenByteArraysHaveDifferentContentWhenLengthIsMultiplyOfLongSize() {
        final int LENGTH = Long.BYTES * 10;
        byte[] array1 = new byte[LENGTH];
        byte[] array2 = new byte[LENGTH];

        random.nextBytes(array1);
        System.arraycopy(array1, 0, array2, 0, LENGTH);
        int randomIndexToChange = random.nextInt(LENGTH);
        array2[randomIndexToChange] = (byte) (array2[randomIndexToChange] + 1);

        assertFalse(DataUtil.equals(array1, array2, 0, LENGTH - 1));
    }

    @Test
    public void equalsReturnsTrueWhenByteArraysHaveDifferentContentWhenLengthIsNotMultiplyOfLongSize() {
        final int LENGTH = Long.BYTES * 10 + (1 + random.nextInt(Long.BYTES - 1));
        byte[] array1 = new byte[LENGTH];
        byte[] array2 = new byte[LENGTH];

        random.nextBytes(array1);
        System.arraycopy(array1, 0, array2, 0, LENGTH);
        int randomIndexToChange = random.nextInt(LENGTH);
        array2[randomIndexToChange] = (byte) (array2[randomIndexToChange] + 1);

        assertFalse(DataUtil.equals(array1, array2, 0, LENGTH - 1));
    }

    @Test
    public void test() {
        final int LENGTH = Long.BYTES * 100;
        byte[] array1 = new byte[LENGTH];
        byte[] array2 = new byte[LENGTH];
        random.nextBytes(array1);
        System.arraycopy(array1, 0, array2, 0, LENGTH);

        for (int i = 0; i < 100000; i++) {
            assertTrue(DataUtil.equals(array1, array2, 0, LENGTH - 1));
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            assertTrue(DataUtil.equals(array1, array2, 0, LENGTH - 1));
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void test2() {
        Map<Data, Data> map = new ConcurrentReferenceHashMap<Data, Data>();
        Data data = null;

        final int LENGTH = Long.BYTES * 100;
        for (int i = 0; i < 10000; i++) {
            byte[] key = new byte[LENGTH];
            byte[] value = new byte[LENGTH];
            random.nextBytes(key);
            random.nextBytes(value);
            Data keyData = new HeapData(key);
            Data valueData = new HeapData(value);
            map.put(keyData, valueData);
            if (data == null) {
                data = keyData;
            }
        }

        for (int i = 0; i < 100000; i++) {
            assertNotNull(map.get(data));
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            assertNotNull(map.get(data));
        }
        System.out.println(System.currentTimeMillis() - start);
    }

} 
