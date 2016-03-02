package com.hazelcast.internal.memory.impl.strategy;

import com.hazelcast.internal.memory.strategy.MemoryAccessStrategy;
import com.hazelcast.test.HazelcastSerialClassRunner;
import com.hazelcast.test.annotation.QuickTest;

import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@RunWith(HazelcastSerialClassRunner.class)
@Category({QuickTest.class})
public class StandardMemoryAccessStrategyTest extends BaseMemoryAccessStrategyTest {

    @Override
    protected MemoryAccessStrategy createMemoryAccessStrategy() {
        return new StandardMemoryAccessStrategy();
    }

}
