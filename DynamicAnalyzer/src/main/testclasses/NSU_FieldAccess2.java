package main.testclasses;

import main.testclasses.utils.C;
import utils.analyzer.HelperClass;

public class NSU_FieldAccess2 {
	//static int f = 0;
	public static void main(String[] args) {

		C b = new C();
		C c = b;
		c.f = HelperClass.makeHigh(c.f);
		if (HelperClass.makeHigh(42) == 42) {
			c.f = true; // should be ok, c.f is HIGH		
		}
	}
}