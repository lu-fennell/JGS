package analyzer.level2;

import static org.junit.Assert.assertEquals;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import utils.exceptions.IllegalFlowException;
import utils.logging.L2Logger;

public class CheckThatNotHigh {

	Logger LOGGER = L2Logger.getLogger();
	
	@Before
	public void init() {
		HandleStmtForTests.init();
	}
	
	@Test
	public void successTest() {
		
		LOGGER.info("CheckThatNotHigh-successTest started");
		
		HandleStmtForTests hs = new HandleStmtForTests();
		
		hs.addLocal("String_low");
		
		hs.checkThatNotHigh("String_low");
		
		hs.close();
		
		LOGGER.info("CheckThatNotHigh-successTest finished");
		
	}
	
	@Test(expected = IllegalFlowException.class)
	public void failTest() {
		
		LOGGER.info("CheckThatNotHigh-failTest started");
		
		HandleStmtForTests hs = new HandleStmtForTests();
		
		hs.addLocal("String_high");
		
		hs.setLevelOfLocal("String_high", SecurityLevel.HIGH);
		
		assertEquals(SecurityLevel.HIGH, hs.getLocalLevel("String_high"));
		
		hs.checkThatNotHigh("String_high");
		
		hs.close();
		
		LOGGER.info("CheckThatNotHigh-failTest finished");
			
	}
}