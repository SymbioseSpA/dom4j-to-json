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

	@Rule
	public ContiPerfRule rule = ContiPerfRule.createDefaultRule();

	public Dom4jToJsonPerformanceTest() {
		super(false);
	}

	@Test
	@Override
	@PerfTest(threads = 60, duration = 10 * 1000)
	public void shouldSerializeXmlToJson() throws Exception {
		super.shouldSerializeXmlToJson();
	}

}
