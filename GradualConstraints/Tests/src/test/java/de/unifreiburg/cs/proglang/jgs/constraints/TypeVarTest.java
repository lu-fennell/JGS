package de.unifreiburg.cs.proglang.jgs.constraints;

import de.unifreiburg.cs.proglang.jgs.instrumentation.Var;
import de.unifreiburg.cs.proglang.jgs.jimpleutils.Vars;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import de.unifreiburg.cs.proglang.jgs.constraints.TypeVars.TypeVar;
import soot.IntType;
import soot.jimple.Jimple;

public class TypeVarTest {
    
    TypeVars tvars;
    
    @Before
    public void setUp() {
        tvars = new TypeVars();
    }

    @Test
    public void testCreate() {
        Var<?> vx, vx2, vy;
        vx  = Vars.fromLocal(Jimple.v().newLocal("x", IntType.v()));
        vx2 = Vars.fromLocal(Jimple.v().newLocal("x2", IntType.v()));
        vy  = Vars.fromLocal(Jimple.v().newLocal("y", IntType.v()));

        TypeVar x =  tvars.testParam(vx, "");
        TypeVar x2 = tvars.testParam(vx2, "");
        TypeVar y =  tvars.testParam(vy, "");
        
        assertNotEquals(x, x2);
        assertNotEquals(x, y);
        assertNotEquals(x2, y);
        assertEquals(x, x);
    }
    

}
