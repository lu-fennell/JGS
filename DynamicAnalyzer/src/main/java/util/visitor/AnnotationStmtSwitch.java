package util.visitor;

import analyzer.level1.JimpleInjector;
import de.unifreiburg.cs.proglang.jgs.instrumentation.Casts;
import soot.*;
import soot.jimple.*;
import util.exceptions.InternalAnalyzerException;
import util.exceptions.NotSupportedStmtException;
import util.logging.L1Logger;
import util.visitor.AnnotationValueSwitch.StmtContext;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AnnotationStmtSwitch implements StmtSwitch {

	private static final Logger logger = Logger.getLogger(AnnotationStmtSwitch.class.getName());

	private Body body;
	private final Casts<?> casts;

	public AnnotationStmtSwitch(Body body, Casts<?> casts) {
		this.body = body;
		this.casts = casts;
	}

	@Override
	public void caseBreakpointStmt(BreakpointStmt stmt) {
		logger.fine("> > > Breakpoint statement identified < < <");
	}

	@Override
	public void caseInvokeStmt(InvokeStmt stmt) {

		AnnotationValueSwitch valueSwitch = new AnnotationValueSwitch(stmt, StmtContext.INVOKE, casts);

		InvokeStmt iStmt = stmt;

		logger.fine("> > > Invoke Statement identified < < <");

		InvokeExpr invokeExpr = iStmt.getInvokeExpr();

		invokeExpr.apply(valueSwitch);

		logger.finer("Method has return type: " + invokeExpr.getType());
	}

	@Override
	public void caseAssignStmt(AssignStmt stmt) {

		logger.fine("case AssignStmt with: "+stmt);

		// Abort, if more then One Element on the LHS
		if (stmt.getDefBoxes().size() != 1) {
			throw new InternalAnalyzerException("Unexpected number of Elements "
					+ "on Left Hand Side "
					+ "of Assign statement: "
					+ stmt.getDefBoxes().size());
		}

		// Switching the Right hand side values using the RHSInstrumentationSwitch
		/*
		RHSInstrumentationSwitch rhsValSwitch = new RHSInstrumentationSwitch(stmt);
		for (ValueBox val : stmt.getRightOp().getUseBoxes()) {
			val.getValue().apply(rhsValSwitch);
		}
		//*/

		// Old Code to assure, that we have it.
		AnnotationValueSwitch rightValueSwitch = new AnnotationValueSwitch(stmt, StmtContext.ASSIGNRIGHT, casts);
	/*	for (int i = 0; i < stmt.getUseBoxes().size(); i++) {
			Value val = stmt.getUseBoxes().get(i).getValue();
			val.apply(rightValueSwitch);
		}
		//*/

		stmt.getRightOp().apply(rightValueSwitch);
		for (ValueBox val : stmt.getRightOp().getUseBoxes()) {
			val.getValue().apply(rightValueSwitch);
		}
		//*/

		Value leftOperand = stmt.getLeftOp();

		rightValueSwitch.getRequiredActionForRHS().ifPresent(action -> {
			switch (action) {
				case NEW_ARRAY:
					JimpleInjector.addArrayToObjectMap((Local) leftOperand, stmt);
					break;
				case NEW_UNDEF_OBJECT:
					break;
				case MAKE_HIGH:
					logger.finest("Make left operand high");
					JimpleInjector.makeLocal((Local) leftOperand, "HIGH", stmt);
					break; // This two cases are treated later
				case MAKE_LOW:
					logger.finest("Make left operand low");
					JimpleInjector.makeLocal((Local) leftOperand, "LOW", stmt);
					break;
				case MAKE_MEDIUM:
					logger.finest("Make left operand medium");
					JimpleInjector.makeLocal((Local) leftOperand, "MEDIUM", stmt);
					break;
				case SET_RETURN_LEVEL: // This will be handeled later (by nico)
					JimpleInjector.setReturnLevelAfterInvokeStmt((Local) leftOperand, stmt);
					break;
				case CAST:    // will also be handled later
					logger.finest("Cast found at " + stmt);
					JimpleInjector.handleCast(stmt);
					break;
				default:
					throw new InternalAnalyzerException("Unexpected action: "
							+ action);
			}
		});
		// TODO: LHS shouldn't be executed in all cases, but currently some cases like handleCast may rely on it.

		LHSInstrumentationSwitch leftValueSwitch = new LHSInstrumentationSwitch(stmt);
		stmt.getLeftOp().apply(leftValueSwitch);
	}

	@Override
	public void caseIdentityStmt(IdentityStmt stmt) {

		AnnotationValueSwitch valueSwitch = new AnnotationValueSwitch(stmt, StmtContext.IDENTITY, casts);

		logger.fine(" > > > Identity statement identified < < <");

		// for all statements i = parameter[0]
		if (stmt.getRightOp() instanceof ParameterRef) {
			if (!body.getMethod().isMain()) {
				int posInArgList = ((ParameterRef) stmt.getRightOp())
						.getIndex();
				JimpleInjector.assignArgumentToLocal(posInArgList, (Local) stmt.getLeftOp());
				//JimpleInjector.assignArgumentToLocal(posInArgList, stmt, (Local) stmt.getLeftOp());
			}
		} else if (stmt.getRightOp() instanceof ThisRef) {
			// TODO im Grunde nicht nötig...
		} else if (stmt.getRightOp() instanceof CaughtExceptionRef) {
			logger.fine("Right operand in IdentityStmt is a CaughtException");
			throw new InternalAnalyzerException("Catching exceptions is not supported");
		} else {
			throw new InternalAnalyzerException(
					"Unexpected type of right value "
							+ stmt.getRightOp().toString() + " in IdentityStmt");
		}
	}

	@Override
	public void caseEnterMonitorStmt(EnterMonitorStmt stmt) {
		logger.fine(" > > > Enter monitor statement identified < < <");
		throw new NotSupportedStmtException("EnterMonitorStmt");
	}

	@Override
	public void caseExitMonitorStmt(ExitMonitorStmt stmt) {
		logger.fine(" > > > Exit monitor statement identified < < <");
		throw new NotSupportedStmtException("ExitMonitorStmt");
	}

	@Override
	public void caseGotoStmt(GotoStmt stmt) {
		logger.fine(" > > > Goto statement identified < < <");
		logger.fine("GOTO: " + stmt.toString());
	}

	/*
	 * If an IfStmt is identified the condition must be checked. At this
	 * procedure a new lpc is added to the lpc stack.
	 */
	@Override
	public void caseIfStmt(IfStmt stmt) {
		logger.fine(" > > > If statement identified < < <");

		logger.finest("Use and def boxes of IfStmt: "
				+ stmt.getUseAndDefBoxes().toString());

		// Check for all values in the condition if they are a constant value
		// or if they are stored in a local. In the second case the local is
		// added
		// to a list for the locals.
		List<ValueBox> valueList = stmt.getUseBoxes();
		ArrayList<Local> localList = new ArrayList<Local>();
		for (ValueBox v : valueList) {
			Value val = v.getValue();
			if (val instanceof Local) {
				localList.add((Local) val);
				logger.fine("New local added to local-list of IfStmt: " + val);
			}
		}

		int localListLength = localList.size();

		Local[] arguments = new Local[localListLength];

		for (int i = 0; i < localListLength; i++) {
			arguments[i] = localList.get(i);
		}

		JimpleInjector.conditionPos = stmt;

		JimpleInjector.checkCondition(stmt, arguments);
	}

	@Override
	public void caseLookupSwitchStmt(LookupSwitchStmt stmt) {
		logger.fine(" > > > Lookup switch statement identified < < <");
		logger.finest("Use and def boxes of SwitchStmt: "
				+ stmt.getUseAndDefBoxes().toString());

		// Check for all values in the condition if they are a constant value
		// or if they are stored in a local. In the second case the local is
		// added
		// to a list for the locals.
		List<ValueBox> valueList = stmt.getUseBoxes();
		ArrayList<Local> localList = new ArrayList<Local>();
		for (ValueBox v : valueList) {
			Value val = v.getValue();
			if (val instanceof Local) {
				localList.add((Local) val);
				logger.fine("New local added to local-list of SwitchStmt: "
						+ val);
			}
		}

		int localListLength = localList.size();

		Local[] arguments = new Local[localListLength];

		for (int i = 0; i < localListLength; i++) {
			arguments[i] = localList.get(i);
		}

		JimpleInjector.checkCondition(stmt, arguments);
	}

	@Override
	public void caseNopStmt(NopStmt stmt) {
		logger.fine(" > > > Nop statement identified < < <");
	}

	@Override
	public void caseRetStmt(RetStmt stmt) {
		logger.fine(" > > > Ret statement identified < < <");
		throw new NotSupportedStmtException("RetStmt");
	}

	@Override
	public void caseReturnStmt(ReturnStmt stmt) {
		logger.fine(" > > > Return statement identified < < <");
		logger.finer("Use boxes: " + stmt.getUseBoxes().toString());
		Value val = stmt.getUseBoxes().get(0).getValue();
		// JimpleInjector.popGlobalPC();
		if (val instanceof Constant) {
			JimpleInjector.returnConstant(stmt);
		} else if (val instanceof Local) {

			JimpleInjector.returnLocal((Local) val, stmt);
		}
	}

	@Override
	public void caseReturnVoidStmt(ReturnVoidStmt stmt) {
		logger.fine(" > > > Return void statement identified < < <");
	}

	/*
	 * If an IfStmt is identified the condition must be checked. At this
	 * procedure a new lpc is added to the lpc stack.
	 */
	@Override
	public void caseTableSwitchStmt(TableSwitchStmt stmt) {
		logger.fine(" > > > Table switch statement identified < < <");
		logger.finest("Use and def boxes of SwitchStmt: "
				+ stmt.getUseAndDefBoxes().toString());

		// Check for all values in the condition if they are a constant value
		// or if they are stored in a local. In the second case the local is
		// added
		// to a list for the locals.
		List<ValueBox> valueList = stmt.getUseBoxes();
		ArrayList<Local> localList = new ArrayList<Local>();
		for (ValueBox v : valueList) {
			Value val = v.getValue();
			if (val instanceof Local) {
				localList.add((Local) val);
				logger.fine("New local added to local-list of SwitchStmt: "
						+ val);
			}
		}

		int localListLength = localList.size();

		Local[] arguments = new Local[localListLength];

		for (int i = 0; i < localListLength; i++) {
			arguments[i] = localList.get(i);
		}

		JimpleInjector.checkCondition(stmt, arguments);

	}

	@Override
	public void caseThrowStmt(ThrowStmt stmt) {
		logger.fine(" > > > Throw statement identified < < <");
	}

	@Override
	public void defaultCase(Object obj) {
		logger.fine(" > > > Default case of statements identified < < <");
		throw new NotSupportedStmtException("DefaultCase");
	}
}