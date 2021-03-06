package testclasses;

import testclasses.utils.C;
import de.unifreiburg.cs.proglang.jgs.support.DynamicLabel;

/**
 * Working example from readme. Since two exceptions are to be thrown, also see NSUPolicy2
 * @author Nicolas Müller
 *
 */
public class NSUPolicy3 {
	public static void main(String[] args) {
		C o1 = new C();
		C o2 = new C();
		
		o1.f = true;
		o2.f = false;
		
		// o1, o2, o1.f and o2.f are all LOW
		
		boolean secret = DynamicLabel.makeHigh(true);
		C o;
		if (secret) {
			o = o1;
		} else {
			o = o2;
		}
		
		// o is high.
		// o1, o2, o1.f and o2.f are still LOW
		
		System.out.println(o1.f); // Okay
		// System.out.println(o.f);  // Not okay! Leaks information! -> Test in NSUPolicy2
		// System.out.println(o1.f);  // Not okay! Leaks information! -> Test in NSUPolicy2
		
		o1.f = false;
		o.f = true; 			// not ok.
		//System.out.println(o1.f);  // Not okay! Leaks information! -> Test in NSUPolicy2

		
	}
}
