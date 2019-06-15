package de.unifreiburg.cs.proglang.jgs.signatures;

import de.unifreiburg.cs.proglang.jgs.constraints.CTypes;
import de.unifreiburg.cs.proglang.jgs.constraints.Constraint;
import de.unifreiburg.cs.proglang.jgs.constraints.ConstraintKind;
import de.unifreiburg.cs.proglang.jgs.constraints.Constraints;
import de.unifreiburg.cs.proglang.jgs.signatures.Symbol;
import static de.unifreiburg.cs.proglang.jgs.signatures.Symbols.*;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class SigConstraint<Level> {

    public final Symbol<Level> lhs;
    public final Symbol<Level> rhs;
    public final ConstraintKind kind;

    SigConstraint(Symbol<Level> lhs,
                  Symbol<Level> rhs, ConstraintKind kind) {
        super();
        this.lhs = lhs;
        this.rhs = rhs;
        this.kind = kind;
    }

    public Constraint<Level> toTypingConstraint(Map<Symbol<Level>, CTypes.CType<Level>> tvarMapping) {
        return Constraints.make(kind, symbolToCType(tvarMapping, lhs),
                                symbolToCType(tvarMapping, rhs));
    }

    public List<Symbol<Level>> symbols() {
        return asList(lhs, rhs);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", lhs.toString(), kind.toString(), rhs.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SigConstraint<?> that = (SigConstraint<?>) o;

        if (lhs != null ? !lhs.equals(that.lhs) : that.lhs != null)
            return false;
        if (rhs != null ? !rhs.equals(that.rhs) : that.rhs != null)
            return false;
        return kind == that.kind;

    }

    @Override
    public int hashCode() {
        int result = lhs != null ? lhs.hashCode() : 0;
        result = 31 * result + (rhs != null ? rhs.hashCode() : 0);
        result = 31 * result + (kind != null ? kind.hashCode() : 0);
        return result;
    }

}
