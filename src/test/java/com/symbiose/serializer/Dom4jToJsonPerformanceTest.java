package com.symbiose.serializer;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author pcollaog
 *
 */
public class Dom4jToJsonPerformanceTest extends Dom4jToJsonTest {

	private static final int WARM_UP = 5 * 1000;

	private static final int DURATION = 10 * 1000;

	private static final int THREADS = 60;

	@Rule
	public ContiPerfRule rule = ContiPerfRule.createDefaultRule();

	/**
	 * 
	 */
	public Dom4jToJsonPerformanceTest() {
		super(false);
	}

	/**
	 * @Required(average = 50, max = 500)
	 */
	@Test
	@Override
	@PerfTest(threads = THREADS, duration = DURATION, warmUp = WARM_UP)
	public void shouldSerialize_case1() throws Exception {
		super.shouldSerialize_case1();
	}

	/**
	 * @Required(average = 700)
	 */
	@Test
	@Override
	@PerfTest(threads = THREADS, duration = DURATION, warmUp = WARM_UP)
	public void shouldSerialize_case3() throws Exception {
		super.shouldSerialize_case3();
	}
}
