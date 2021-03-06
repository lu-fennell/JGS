package testclasses;

import de.unifreiburg.cs.proglang.jgs.support.DynamicLabel;

/**
 * Class to test the basics of NSU policy
 * @author Nicolas Müller
 *
 */
public class NSUPolicy {

	public static void main(String[] args) {
		int y = 5;
		int secret = 42;
		
		//y = DynamicLabel.makeLow(5);		// just for clarity
		secret = DynamicLabel.makeHigh(5);
		
		if (secret > 0) {
			y += 1;						// NSU IFCError, Illegal
										// flow to int_i0
		}

		DynamicLabel.makeHigh(y);
		/**
		 * This is necessary, because otherwise the compiler will just
		 * throw away the updates of y inside the if, which will circumvent the
		 * IFCError
		 */

	}

}
