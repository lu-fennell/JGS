package testclasses;

import util.analyzer.HelperClass;
import util.test.C;

public class NSU_FieldAccess {
	//static int f = 0;
	public static void main(String[] args) {

		C b = new C();
		C c = HelperClass.makeHigh(b);
		// c.f = DynamicLabel.makeHigh(c.f);
		c.f = true; // should throw an error, since we access access f through
					// high-sec c and PC = LOW
       // f = 1; // ok
        //if (c == b){
         // f = 2; // NSU Error
        //}
		// System.out.println(c.f);
	
	}
}
