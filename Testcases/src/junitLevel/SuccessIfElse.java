package junitLevel;

import static security.Definition.*;

public class SuccessIfElse {

    @ParameterSecurity({ "low" })
    public static void main(String[] args) {
    }

    @WriteEffect({ "low", "high" })
    public SuccessIfElse() {
        super();
    }

    @ReturnSecurity("high")
    public int ifReturnExpr() {
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        return conditionHigh ? thenHigh : elseHigh;
    }

    @ReturnSecurity("high")
    public int ifReturnExpr2() {
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        return conditionLow ? thenHigh : elseHigh;
    }

    @ReturnSecurity("high")
    public int ifReturnExpr3() {
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        return conditionHigh ? thenHigh : elseLow;
    }

    @ReturnSecurity("high")
    public int ifReturnExpr4() {
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        return conditionLow ? thenHigh : elseLow;
    }

    @ReturnSecurity("high")
    public int ifReturnExpr5() {
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        return conditionHigh ? thenLow : elseHigh;
    }

    @ReturnSecurity("high")
    public int ifReturnExpr6() {
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        return conditionLow ? thenLow : elseHigh;
    }

    @ReturnSecurity("high")
    public int ifReturnExpr7() {
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        return conditionHigh ? thenLow : elseLow;
    }

    @ReturnSecurity("high")
    public int ifReturnExpr8() {
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        return conditionLow ? thenLow : elseLow;
    }

    @ReturnSecurity("low")
    public int ifReturnExpr9() {
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        return conditionLow ? thenLow : elseLow;
    }

    @ReturnSecurity("high")
    public int ifReturn() {
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            return thenHigh;
        }
        return elseHigh;
    }

    @ReturnSecurity("high")
    public int ifReturn2() {
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            return thenHigh;
        }
        return elseHigh;
    }

    @ReturnSecurity("high")
    public int ifReturn3() {
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            return thenHigh;
        }
        return elseLow;
    }

    @ReturnSecurity("high")
    public int ifReturn4() {
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            return thenHigh;
        }
        return elseLow;
    }

    @ReturnSecurity("high")
    public int ifReturn5() {
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            return thenLow;
        }
        return elseHigh;
    }

    @ReturnSecurity("high")
    public int ifReturn6() {
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            return thenLow;
        }
        return elseHigh;
    }

    @ReturnSecurity("high")
    public int ifReturn7() {
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            return thenLow;
        }
        return elseLow;
    }

    @ReturnSecurity("high")
    public int ifReturn8() {
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            return thenLow;
        }
        return elseLow;
    }

    @ReturnSecurity("low")
    public int ifReturn9() {
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            return thenLow;
        }
        return elseLow;
    }

    @ReturnSecurity("high")
    public int ifElseReturn() {
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            return thenHigh;
        } else {
            return elseHigh;
        }
    }

    @ReturnSecurity("high")
    public int ifElseReturn2() {
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            return thenHigh;
        } else {
            return elseHigh;
        }
    }

    @ReturnSecurity("high")
    public int ifElseReturn3() {
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            return thenHigh;
        } else {
            return elseLow;
        }
    }

    @ReturnSecurity("high")
    public int ifElseReturn4() {
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            return thenHigh;
        } else {
            return elseLow;
        }
    }

    @ReturnSecurity("high")
    public int ifElseReturn5() {
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            return thenLow;
        } else {
            return elseHigh;
        }
    }

    @ReturnSecurity("high")
    public int ifElseReturn6() {
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            return thenLow;
        } else {
            return elseHigh;
        }
    }

    @ReturnSecurity("high")
    public int ifElseReturn7() {
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            return thenLow;
        } else {
            return elseLow;
        }
    }

    @ReturnSecurity("high")
    public int ifElseReturn8() {
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            return thenLow;
        } else {
            return elseLow;
        }
    }

    @ReturnSecurity("low")
    public int ifElseReturn9() {
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            return thenLow;
        } else {
            return elseLow;
        }
    }

    @ReturnSecurity("high")
    public int ifElseAssign() {
        int result = mkHigh(42);
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            result = thenHigh;
        } else {
            result = elseHigh;
        }
        return result;
    }

    @ReturnSecurity("high")
    public int ifElseAssign2() {
        int result = mkHigh(42);
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenHigh;
        } else {
            result = elseHigh;
        }
        return result;
    }

    @ReturnSecurity("high")
    public int ifElseAssign3() {
        int result = mkLow(42);
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            result = thenHigh;
        } else {
            result = elseHigh;
        }
        return result;
    }

    @ReturnSecurity("high")
    public int ifElseAssign4() {
        int result = mkLow(42);
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenHigh;
        } else {
            result = elseHigh;
        }
        return result;
    }

    @ReturnSecurity("high")
    public int ifElseAssign5() {
        int result = mkHigh(42);
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            result = thenHigh;
        } else {
            result = elseLow;
        }
        return result;
    }

    @ReturnSecurity("high")
    public int ifElseAssign6() {
        int result = mkHigh(42);
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenHigh;
        } else {
            result = elseLow;
        }
        return result;
    }

    @ReturnSecurity("high")
    public int ifElseAssign7() {
        int result = mkLow(42);
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            result = thenHigh;
        } else {
            result = elseLow;
        }
        return result;
    }

    @ReturnSecurity("high")
    public int ifElseAssign8() {
        int result = mkLow(42);
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenHigh;
        } else {
            result = elseLow;
        }
        return result;
    }

    @ReturnSecurity("high")
    public int ifElseAssign9() {
        int result = mkHigh(42);
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            result = thenLow;
        } else {
            result = elseHigh;
        }
        return result;
    }

    @ReturnSecurity("high")
    public int ifElseAssign10() {
        int result = mkHigh(42);
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenLow;
        } else {
            result = elseHigh;
        }
        return result;
    }

    @ReturnSecurity("high")
    public int ifElseAssign11() {
        int result = mkLow(42);
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            result = thenLow;
        } else {
            result = elseHigh;
        }
        return result;
    }

    @ReturnSecurity("high")
    public int ifElseAssign12() {
        int result = mkLow(42);
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenLow;
        } else {
            result = elseHigh;
        }
        return result;
    }

    @ReturnSecurity("high")
    public int ifElseAssign13() {
        int result = mkHigh(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            result = thenLow;
        } else {
            result = elseLow;
        }
        return result;
    }

    @ReturnSecurity("high")
    public int ifElseAssign14() {
        int result = mkHigh(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenLow;
        } else {
            result = elseLow;
        }
        return result;
    }

    @ReturnSecurity("high")
    public int ifElseAssign15() {
        int result = mkLow(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            result = thenLow;
        } else {
            result = elseLow;
        }
        return result;
    }

    @ReturnSecurity("high")
    public int ifElseAssign16() {
        int result = mkLow(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenLow;
        } else {
            result = elseLow;
        }
        return result;
    }

    @ReturnSecurity("low")
    public int ifElseAssign17() {
        int result = mkHigh(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenLow;
        } else {
            result = elseLow;
        }
        return result;
    }

    @ReturnSecurity("low")
    public int ifElseAssign18() {
        int result = mkLow(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenLow;
        } else {
            result = elseLow;
        }
        return result;
    }

    @ReturnSecurity("high")
    @WriteEffect({ "high" })
    public int ifElseAssignField() {
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            highField = thenHigh;
        } else {
            highField = elseHigh;
        }
        return highField;
    }

    @ReturnSecurity("high")
    @WriteEffect({ "high" })
    public int ifElseAssignField2() {
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            highField = thenHigh;
        } else {
            highField = elseHigh;
        }
        return highField;
    }

    @ReturnSecurity("high")
    @WriteEffect({ "high" })
    public int ifElseAssignField3() {
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            highField = thenHigh;
        } else {
            highField = elseLow;
        }
        return highField;
    }

    @ReturnSecurity("high")
    @WriteEffect({ "high" })
    public int ifElseAssignField4() {
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            highField = thenHigh;
        } else {
            highField = elseLow;
        }
        return highField;
    }

    @ReturnSecurity("high")
    @WriteEffect({ "high" })
    public int ifElseAssignField5() {
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            highField = thenLow;
        } else {
            highField = elseHigh;
        }
        return highField;
    }

    @ReturnSecurity("high")
    @WriteEffect({ "high" })
    public int ifElseAssignField6() {
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            highField = thenLow;
        } else {
            highField = elseHigh;
        }
        return highField;
    }

    @ReturnSecurity("high")
    @WriteEffect({ "high" })
    public int ifElseAssignField7() {
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            highField = thenLow;
        } else {
            highField = elseLow;
        }
        return highField;
    }

    @ReturnSecurity("high")
    @WriteEffect({ "high" })
    public int ifElseAssignField8() {
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            highField = thenLow;
        } else {
            highField = elseLow;
        }
        return highField;
    }

    @ReturnSecurity("high")
    @WriteEffect({ "low" })
    public int ifElseAssignField9() {
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            lowField = thenLow;
        } else {
            lowField = elseLow;
        }
        return lowField;
    }

    @ReturnSecurity("low")
    @WriteEffect({ "low" })
    public int ifElseAssignField10() {
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            lowField = thenLow;
        } else {
            lowField = elseLow;
        }
        return lowField;
    }

    @ReturnSecurity("high")
    @WriteEffect({ "high" })
    public int ifAssign() {
        int thenHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            highField = thenHigh;
        }
        return highField;
    }

    @ReturnSecurity("high")
    @WriteEffect({ "high" })
    public int ifAssign2() {
        int thenHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            highField = thenHigh;
        }
        return highField;
    }

    @ReturnSecurity("high")
    @WriteEffect({ "high" })
    public int ifAssign3() {
        int thenLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            highField = thenLow;
        }
        return highField;
    }

    @ReturnSecurity("high")
    @WriteEffect({ "high" })
    public int ifAssign4() {
        int thenLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            highField = thenLow;
        }
        return highField;
    }

    @ReturnSecurity("high")
    @WriteEffect({ "low" })
    public int ifAssign5() {
        int thenLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            lowField = thenLow;
        }
        return lowField;
    }

    @ReturnSecurity("low")
    @WriteEffect({ "low" })
    public int ifAssign6() {
        int thenLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            lowField = thenLow;
        }
        return lowField;
    }

    @ReturnSecurity("high")
    public int ifExprAssign() {
        int result = mkHigh(42);
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        result = conditionHigh ? thenHigh : elseHigh;
        return result;
    }

    @ReturnSecurity("high")
    public int ifExprAssign2() {
        int result = mkHigh(42);
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        result = conditionLow ? thenHigh : elseHigh;
        return result;
    }

    @ReturnSecurity("high")
    public int ifExprAssign3() {
        int result = mkLow(42);
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        result = conditionHigh ? thenHigh : elseHigh;
        return result;
    }

    @ReturnSecurity("high")
    public int ifExprAssign4() {
        int result = mkLow(42);
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        result = conditionLow ? thenHigh : elseHigh;
        return result;
    }

    @ReturnSecurity("high")
    public int ifExprAssign5() {
        int result = mkHigh(42);
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        result = conditionHigh ? thenHigh : elseLow;
        return result;
    }

    @ReturnSecurity("high")
    public int ifExprAssign6() {
        int result = mkHigh(42);
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        result = conditionLow ? thenHigh : elseLow;
        return result;
    }

    @ReturnSecurity("high")
    public int ifExprAssign7() {
        int result = mkLow(42);
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        result = conditionHigh ? thenHigh : elseLow;
        return result;
    }

    @ReturnSecurity("high")
    public int ifExprAssign8() {
        int result = mkLow(42);
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        result = conditionLow ? thenHigh : elseLow;
        return result;
    }

    @ReturnSecurity("high")
    public int ifExprAssign9() {
        int result = mkHigh(42);
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        result = conditionHigh ? thenLow : elseHigh;
        return result;
    }

    @ReturnSecurity("high")
    public int ifExprAssign10() {
        int result = mkHigh(42);
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        result = conditionLow ? thenLow : elseHigh;
        return result;
    }

    @ReturnSecurity("high")
    public int ifExprAssign11() {
        int result = mkLow(42);
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        result = conditionHigh ? thenLow : elseHigh;
        return result;
    }

    @ReturnSecurity("high")
    public int ifExprAssign12() {
        int result = mkLow(42);
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        result = conditionLow ? thenLow : elseHigh;
        return result;
    }

    @ReturnSecurity("high")
    public int ifExprAssign13() {
        int result = mkHigh(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        result = conditionHigh ? thenLow : elseLow;
        return result;
    }

    @ReturnSecurity("high")
    public int ifExprAssign14() {
        int result = mkHigh(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        result = conditionLow ? thenLow : elseLow;
        return result;
    }

    @ReturnSecurity("high")
    public int ifExprAssign15() {
        int result = mkLow(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        result = conditionHigh ? thenLow : elseLow;
        return result;
    }

    @ReturnSecurity("high")
    public int ifExprAssign16() {
        int result = mkLow(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        result = conditionLow ? thenLow : elseLow;
        return result;
    }

    @ReturnSecurity("low")
    public int ifExprAssign17() {
        int result = mkHigh(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        result = conditionLow ? thenLow : elseLow;
        return result;
    }

    @ReturnSecurity("low")
    public int ifExprAssign18() {
        int result = mkLow(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        result = conditionLow ? thenLow : elseLow;
        return result;
    }

    @WriteEffect({ "high" })
    public void ifElseAssignLocalField() {
        int result = mkHigh(42);
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            result = thenHigh;
        } else {
            result = elseHigh;
        }
        highField = result;
    }

    @WriteEffect({ "high" })
    public void ifElseAssignLocalField2() {
        int result = mkHigh(42);
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenHigh;
        } else {
            result = elseHigh;
        }
        highField = result;
    }

    @WriteEffect({ "high" })
    public void ifElseAssignLocalField3() {
        int result = mkHigh(42);
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            result = thenHigh;
        } else {
            result = elseLow;
        }
        highField = result;
    }

    @WriteEffect({ "high" })
    public void ifElseAssignLocalField4() {
        int result = mkHigh(42);
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenHigh;
        } else {
            result = elseLow;
        }
        highField = result;
    }

    @WriteEffect({ "high" })
    public void ifElseAssignLocalField5() {
        int result = mkHigh(42);
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            result = thenLow;
        } else {
            result = elseHigh;
        }
        highField = result;
    }

    @WriteEffect({ "high" })
    public void ifElseAssignLocalField6() {
        int result = mkHigh(42);
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenLow;
        } else {
            result = elseHigh;
        }
        highField = result;
    }

    @WriteEffect({ "high" })
    public void ifElseAssignLocalField7() {
        int result = mkHigh(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            result = thenLow;
        } else {
            result = elseLow;
        }
        highField = result;
    }

    @WriteEffect({ "high" })
    public void ifElseAssignLocalField8() {
        int result = mkHigh(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenLow;
        } else {
            result = elseLow;
        }
        highField = result;
    }

    @WriteEffect({ "low" })
    public void ifElseAssignLocalField9() {
        int result = mkHigh(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenLow;
        } else {
            result = elseLow;
        }
        lowField = result;
    }

    @WriteEffect({ "high" })
    public void ifElseAssignLocalField10() {
        int result = mkLow(42);
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            result = thenHigh;
        } else {
            result = elseHigh;
        }
        highField = result;
    }

    @WriteEffect({ "high" })
    public void ifElseAssignLocalField11() {
        int result = mkLow(42);
        int thenHigh = mkHigh(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenHigh;
        } else {
            result = elseHigh;
        }
        highField = result;
    }

    @WriteEffect({ "high" })
    public void ifElseAssignLocalField12() {
        int result = mkLow(42);
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            result = thenHigh;
        } else {
            result = elseLow;
        }
        highField = result;
    }

    @WriteEffect({ "high" })
    public void ifElseAssignLocalField13() {
        int result = mkLow(42);
        int thenHigh = mkHigh(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenHigh;
        } else {
            result = elseLow;
        }
        highField = result;
    }

    @WriteEffect({ "high" })
    public void ifElseAssignLocalField14() {
        int result = mkLow(42);
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            result = thenLow;
        } else {
            result = elseHigh;
        }
        highField = result;
    }

    @WriteEffect({ "high" })
    public void ifElseAssignLocalField15() {
        int result = mkLow(42);
        int thenLow = mkLow(42);
        int elseHigh = mkHigh(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenLow;
        } else {
            result = elseHigh;
        }
        highField = result;
    }

    @WriteEffect({ "high" })
    public void ifElseAssignLocalField16() {
        int result = mkLow(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionHigh = mkHigh(false);
        if (conditionHigh) {
            result = thenLow;
        } else {
            result = elseLow;
        }
        highField = result;
    }

    @WriteEffect({ "high" })
    public void ifElseAssignLocalField17() {
        int result = mkLow(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenLow;
        } else {
            result = elseLow;
        }
        highField = result;
    }

    @WriteEffect({ "low" })
    public void ifElseAssignLocalField18() {
        int result = mkLow(42);
        int thenLow = mkLow(42);
        int elseLow = mkLow(42);
        boolean conditionLow = mkLow(false);
        if (conditionLow) {
            result = thenLow;
        } else {
            result = elseLow;
        }
        lowField = result;
    }

    @ParameterSecurity({ "low", "low" })
    @WriteEffect({ "low", "high" })
    public void ifIf(boolean var1Low, boolean var2Low) {
        int var3Low = mkLow(42);
        int var4Low = mkLow(42);
        if (var1Low) {
            highField = var3Low;
            lowField = var3Low;
        }
        if (var2Low) {
            highField = var4Low;
            lowField = var4Low;
        }
    }

    @ParameterSecurity({ "low", "low" })
    @WriteEffect({ "low", "high" })
    public void ifDoubleCond(boolean var1Low, boolean var2Low) {
        int var3Low = mkLow(42);
        if (var1Low && var2Low) {
            highField = var3Low;
            lowField = var3Low;
        }
        highField = var3Low;
        lowField = var3Low;
    }

    @ParameterSecurity({ "low", "low" })
    @WriteEffect({ "low", "high" })
    public void ifElseDoubleCond(boolean var1Low, boolean var2Low) {
        int var3Low = mkLow(42);
        if (var1Low && var2Low) {
            highField = var3Low;
            lowField = var3Low;
        } else {
            highField = var3Low;
            lowField = var3Low;
        }
        highField = var3Low;
        lowField = var3Low;
    }

    // TODO: Multiple condition values

    @FieldSecurity("low")
    int lowField = mkLow(42);

    @FieldSecurity("high")
    int highField = mkHigh(42);

}
