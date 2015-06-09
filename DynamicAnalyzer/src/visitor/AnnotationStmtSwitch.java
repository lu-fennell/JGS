package visitor;

import java.util.List;
import java.util.logging.Logger;

import exceptions.InternalAnalyzerException;
import logging.L1Logger;
import analyzer.level1.JimpleInjector;
import soot.Local;
import soot.SootField;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.jimple.AssignStmt;
import soot.jimple.BreakpointStmt;
import soot.jimple.Constant;
import soot.jimple.DynamicInvokeExpr;
import soot.jimple.EnterMonitorStmt;
import soot.jimple.ExitMonitorStmt;
import soot.jimple.Expr;
import soot.jimple.FieldRef;
import soot.jimple.GotoStmt;
import soot.jimple.IdentityStmt;
import soot.jimple.IfStmt;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.Jimple;
import soot.jimple.LookupSwitchStmt;
import soot.jimple.NopStmt;
import soot.jimple.RetStmt;
import soot.jimple.ReturnStmt;
import soot.jimple.ReturnVoidStmt;
import soot.jimple.SpecialInvokeExpr;
import soot.jimple.StaticInvokeExpr;
import soot.jimple.StmtSwitch;
import soot.jimple.TableSwitchStmt;
import soot.jimple.ThrowStmt;
import soot.jimple.VirtualInvokeExpr;
import soot.jimple.internal.JimpleLocalBox;

public class AnnotationStmtSwitch implements StmtSwitch {
	
	AnnotationValueSwitch valueSwitch = new AnnotationValueSwitch();
	Logger logger = L1Logger.getLogger();

	@Override
	public void caseBreakpointStmt(BreakpointStmt stmt) {
		logger.severe("> > > Breakpoint statement identified < < <"); // TODO Change to fine
		// TODO Auto-generated method stub

	}

	@Override
	public void caseInvokeStmt(InvokeStmt stmt) {
		
		InvokeStmt iStmt = (InvokeStmt) stmt;
		
		logger.fine(" > > > Invoke Statement identified < < <");
		
		InvokeExpr invokeExpr = iStmt.getInvokeExpr();
		
		if (invokeExpr instanceof SpecialInvokeExpr) {
			logger.finer("Invoke expression is of type SpecialInvoke");
		} else if (invokeExpr instanceof VirtualInvokeExpr) {
			logger.finer("Invoke expression is of type VirtualInvoke");
		} else if (invokeExpr instanceof StaticInvokeExpr) {
			logger.finer("Invoke expression is of type StaticInvoke");	
		} else if (invokeExpr instanceof DynamicInvokeExpr) {
			logger.severe("Invoke expression is of type DynamicInvoke");
			// TODO What is that?
		} else {
			logger.severe("Invoke expression is of type" + invokeExpr);	
			// TODO
		}
		
		logger.finer("Method has return type: " + invokeExpr.getType());
		
		logger.finer("Arguments of invoked method: " + invokeExpr.getArgs());
		List<Value> list = invokeExpr.getArgs();
		for (Value e : list) {
			logger.finer("Type of argument: " + e.getType());
		}
	}

	@Override
	public void caseAssignStmt(AssignStmt stmt) {
		
		AssignStmt aStmt = (AssignStmt) stmt;
		
		logger.fine(" > > > Assign statement identified < < <" );
		logger.finer("left side: " + aStmt.getDefBoxes().toString());
		logger.finer("right side: " + aStmt.getUseBoxes().toString());

		int numOfArgs = aStmt.getUseBoxes().size();
		
		logger.finer("Number of arguments :" + numOfArgs);
		
		Value leftValue = aStmt.getDefBoxes().get(0).getValue();


		if(leftValue instanceof Local) {
		logger.finer("Left value is a Local");
			Local left = (Local) leftValue;

			if (numOfArgs == 1) {
				Value rightValue = aStmt.getUseBoxes().get(0).getValue();

				
				if (rightValue instanceof Local) {
			    logger.finer("Local on the right side");

			    Local right = (Local) rightValue;
			    JimpleInjector.assignLocalToLocal(left, right, aStmt);
				} else {
					JimpleInjector.assignConstantToLocal(left, aStmt); 
				    // TODO: kann man hier was mit dem ValueSwitch machen? 
					// ZB rausfinden, welchen Typ die Argumente habe
					} 
			} else if (numOfArgs == 2) {
				logger.finer("Field on the right side");
				
				// TODO
				
				logger.finer("AHJASHGASDJH" + aStmt.getUseBoxes().toString());
				logger.finer(aStmt.getUseBoxes().get(0).toString());
				logger.finer(aStmt.getUseBoxes().get(1).toString());
				logger.finer(aStmt.getUseBoxes().get(1).getValue().toString());
				logger.finer(aStmt.getUseBoxes().get(1).getValue().getType().toString());
				logger.finer(aStmt.getUseBoxes().get(1).getValue().getUseBoxes().toString());
				
				
			} else if (numOfArgs == 3) {
				logger.finer("2 Locals on the right side");
				

				Value rightValue1 = aStmt.getUseBoxes().get(0).getValue();
				
				Value rightValue2 = aStmt.getUseBoxes().get(1).getValue();
				
				if (!(rightValue1 instanceof Local) || !(rightValue2 instanceof Local)) {
					new InternalAnalyzerException("Expected Local as "
							+ "argument but got unknown type instead");
				}
				
				Local right1 = (Local) rightValue1;
				Local right2 = (Local) rightValue2;
				
				JimpleInjector.assignLocalsToLocal(left, right1, right2, aStmt);
			} else {
				System.out.println(numOfArgs);
				new InternalAnalyzerException("unexpected number of "
						+ "arguments in assign statement");
			}
			
		} else if (leftValue instanceof FieldRef) {
			logger.finer("Left value is a field");
			FieldRef field = (FieldRef) leftValue;
			logger.finer("Declaring class " + field.getField().getDeclaringClass());
			
			if(field.getUseBoxes().size() > 0) {
				logger.finer(" Declaring object " + field.getUseBoxes().toString());
				logger.finer("Local " + field.getUseBoxes().get(0).getClass());
				// TODO its unclear how to access r0 
			}
		}
	}

	@Override
	public void caseIdentityStmt(IdentityStmt stmt) {
		
		IdentityStmt iStmt = (IdentityStmt) stmt;
		
		logger.fine(" > > > Identity statement identified < < <");
		// TODO hier sind Parameter und this-Referenzen
		System.out.println("Identity Stmt: "+ stmt.getUseBoxes().toString());	
		System.out.println(stmt.getRightOp().getType());
		stmt.getRightOp().apply(valueSwitch);
	}

	@Override
	public void caseEnterMonitorStmt(EnterMonitorStmt stmt) {
		logger.severe(" > > > Enter monitor statement identified < < <");  // TODO Change to fine
		// TODO Auto-generated method stub

	}

	@Override
	public void caseExitMonitorStmt(ExitMonitorStmt stmt) {
		logger.severe(" > > > Exit monitor statement identified < < <");  // TODO Change to fine
		// TODO Auto-generated method stub

	}

	@Override
	public void caseGotoStmt(GotoStmt stmt) {
		logger.severe(" > > > Goto statement identified < < <");  // TODO Change to fine
		// TODO Auto-generated method stub

	}

	@Override
	public void caseIfStmt(IfStmt stmt) {
		logger.severe(" > > > If statement identified < < <");  // TODO Change to fine
		// TODO Auto-generated method stub

	}

	@Override
	public void caseLookupSwitchStmt(LookupSwitchStmt stmt) {
		logger.severe(" > > > Lookup switch statement identified < < <");  // TODO Change to fine
		// TODO Auto-generated method stub

	}

	@Override
	public void caseNopStmt(NopStmt stmt) {
		logger.severe(" > > > Nop statement identified < < <");  // TODO Change to fine
		// TODO Auto-generated method stub

	}

	@Override
	public void caseRetStmt(RetStmt stmt) {
		logger.severe(" > > > Ret statement identified < < <");  // TODO Change to fine
		// TODO Auto-generated method stub

	}

	@Override
	public void caseReturnStmt(ReturnStmt stmt) {
		
		ReturnStmt rStmt = (ReturnStmt) stmt;
		
		logger.fine(" > > > Return statement identified < < <");
		
		System.out.println(stmt.getUseBoxes().toString());
		Value val = stmt.getUseBoxes().get(0).getValue();
		if (val instanceof Constant) {
			JimpleInjector.returnConstant();
		} else if (val instanceof Local) {
			JimpleInjector.returnLocal((Local) val);
		}

	}

	@Override
	public void caseReturnVoidStmt(ReturnVoidStmt stmt) {
		logger.severe(" > > > Return void statement identified < < <");  // TODO Change to fine
		// TODO Auto-generated method stub

	}

	@Override
	public void caseTableSwitchStmt(TableSwitchStmt stmt) {
		logger.severe(" > > > Table switch statement identified < < <");  // TODO Change to fine
		// TODO Auto-generated method stub

	}

	@Override
	public void caseThrowStmt(ThrowStmt stmt) {
		logger.severe(" > > > Throw statement identified < < <");  // TODO Change to fine
		// TODO Auto-generated method stub

	}

	@Override
	public void defaultCase(Object obj) {
		logger.severe(" > > > Default case of statements identified < < <");  // TODO Change to fine
		// TODO Auto-generated method stub

	}

}
