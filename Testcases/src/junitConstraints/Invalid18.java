package junitConstraints;

import security.Definition.FieldSecurity;

public class Invalid18 {

	@FieldSecurity({"low", "high"})
	public static int[] field;

	public static void main(String[] args) {}

}
