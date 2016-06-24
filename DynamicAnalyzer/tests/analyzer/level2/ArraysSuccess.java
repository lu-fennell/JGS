package analyzer.level2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import utils.logging.L2Logger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ArraysSuccess {
	
	Logger logger = L2Logger.getLogger();
	
	@Before
	public void init() {
		HandleStmt.init();
	}

	@Test
	public void createArrayTest() {

		logger.log(Level.INFO, "CREATE ARRAY SUCCESS TEST STARTED");
		
		HandleStmt hs = new HandleStmt();
		
		hs.addLocal("String[]_a");
		String[] a = new String[] {"asd", "", ""};
		hs.addArrayToObjectMap(a);
		
		assertEquals(3, hs.getNumberOfFieldsInObjectMap(a));
		
		hs.close();

		logger.log(Level.INFO, "CREATE ARRAY SUCCESS TEST FINISHED");
	}
	
	@Test
	public void readArray() {
		
		logger.log(Level.INFO, "READ ARRAY SUCCESS TEST STARTED");
		
		HandleStmt hs = new HandleStmt();
		
		String[] a = new String[] {"asd", "", ""};
		hs.addArrayToObjectMap(a);
		hs.addLocal("String[]_a");
		hs.addLocal("int_i");
		
		assertTrue(hs.containsObjectInObjectMap(a));
		assertEquals(3, hs.getNumberOfFieldsInObjectMap(a));
		
		/*
		 * check ( x >= lpc)
		 * x = Join(i,a, lpc, a_i)
		 */
		int i = 2;
		hs.addLevelOfArrayField(a, Integer.toString(i));
		hs.addLevelOfLocal("int_i");
		hs.addLevelOfLocal("String[]_a");
		hs.addLocal("String_x");
		hs.setLevelOfLocal("String_x");
		@SuppressWarnings("unused")
		String x = a[i];
		
		assertEquals(SecurityLevel.bottom(), hs.getLocalLevel("String_x"));
		
		hs.close();
		
		logger.log(Level.INFO, "READ ARRAY SUCCESS TEST FINISHED");
		
	}
	
	@Test
	public void writeArray() {

		logger.log(Level.INFO, "WRITE ARRAY SUCCESS TEST STARTED");
		
		HandleStmt hs = new HandleStmt();
		
		String[] a = new String[] {"a", "b", "c"};
		assertEquals(3, a.length);
		hs.addArrayToObjectMap(a);
		assertTrue(hs.containsObjectInObjectMap(a));
		assertEquals(3, hs.getNumberOfFieldsInObjectMap(a));
		
		hs.addLocal("String[]_a");
		hs.addLocal("int_i");
		
		/*
		 * check(a_i >= join(gpc, a, i))
		 * level(a_i) = join(gpc,local, i)
		 */
		int i = 2;
		hs.setLevelOfArrayField(a, Integer.toString(i), "String[]_a", "int_i");
		a[i] = "3";
		
		assertEquals(SecurityLevel.bottom(), hs.getFieldLevel(a, Integer.toString(i)));
		
		hs.close();
		
		logger.log(Level.INFO, "WRITE ARRAY SUCCESS TEST FINISHED");
		
	}

}
