package com.hazelcast.internal.memory.strategy;

import com.hazelcast.internal.memory.impl.strategy.AlignmentAwareMemoryAccessStrategy;
import com.hazelcast.internal.memory.impl.strategy.StandardMemoryAccessStrategy;
import com.hazelcast.test.HazelcastSerialClassRunner;
import com.hazelcast.test.annotation.QuickTest;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(HazelcastSerialClassRunner.class)
@Category({QuickTest.class})
public class MemoryAccessStrategyProviderTest extends UnsafeDependentMemoryAccessStrategyTest {

    @Test
    public void test_getMemoryAccessStrategy_default() {
        assertNotNull(MemoryAccessStrategyProvider.getDefaultMemoryAccessStrategy());
    }

    private void checkStandardMemoryAccessStrategyAvailable() {
        MemoryAccessStrategy memoryAccessStrategy
                = MemoryAccessStrategyProvider.getMemoryAccessStrategy(MemoryAccessStrategyType.STANDARD);
        if (StandardMemoryAccessStrategy.isAvailable()) {
            assertNotNull(memoryAccessStrategy);
            assertTrue(memoryAccessStrategy instanceof StandardMemoryAccessStrategy);
        }
    }

    private void checkAlignmentAwareMemoryAccessStrategyAvailable() {
        MemoryAccessStrategy memoryAccessStrategy
                = MemoryAccessStrategyProvider.getMemoryAccessStrategy(MemoryAccessStrategyType.ALIGNMENT_AWARE);
        if (AlignmentAwareMemoryAccessStrategy.isAvailable()) {
            assertNotNull(memoryAccessStrategy);
            assertTrue(memoryAccessStrategy instanceof AlignmentAwareMemoryAccessStrategy);
        }
    }

    @Test
    public void test_getMemoryAccessStrategy_standard() {
        checkStandardMemoryAccessStrategyAvailable();
    }

    @Test
    public void test_getMemoryAccessStrategy_alignmentAware() {
        checkAlignmentAwareMemoryAccessStrategyAvailable();
    }

    @Test
    public void test_getMemoryAccessStrategy_platformAware() {
        if (MemoryAccessStrategyProvider.isUnalignedAccessAllowed()) {
            checkStandardMemoryAccessStrategyAvailable();
        } else {
            checkAlignmentAwareMemoryAccessStrategyAvailable();
        }
    }

}
