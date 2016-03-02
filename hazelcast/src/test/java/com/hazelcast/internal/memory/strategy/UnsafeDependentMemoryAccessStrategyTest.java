package com.hazelcast.internal.memory.strategy;

import com.hazelcast.internal.memory.impl.strategy.TestIgnoreRuleAccordingToUnsafeAvailability;
import org.junit.ClassRule;

public abstract class UnsafeDependentMemoryAccessStrategyTest {

    @ClassRule
    public static final TestIgnoreRuleAccordingToUnsafeAvailability UNSAFE_AVAILABILITY_RULE
            = new TestIgnoreRuleAccordingToUnsafeAvailability();

}
