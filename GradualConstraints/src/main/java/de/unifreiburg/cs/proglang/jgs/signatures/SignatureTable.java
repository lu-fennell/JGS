package de.unifreiburg.cs.proglang.jgs.signatures;

import scala.Option;
import soot.SootMethod;
import sun.net.dns.ResolverConfiguration;

import java.util.*;

import static de.unifreiburg.cs.proglang.jgs.signatures.MethodSignatures.makeSignature;

/**
 * A table mapping methods to their security signatures.
 */
public class SignatureTable<Level> {

    private final Map<SootMethod, Signature<Level>> signatureMap;

    /**
     * Create a new table from a map.
     */
    public static <Level> SignatureTable<Level> of(Map<SootMethod, Signature<Level>> signatureMap) {
        return new SignatureTable<>(new HashMap<>(signatureMap));
    }

    private SignatureTable(Map<SootMethod, Signature<Level>> signatureMap) {
        this.signatureMap = signatureMap;
    }

    public SignatureTable<Level> extendWith(SootMethod m, Collection<SigConstraint<Level>> constraints, Effects<Level> effects) {
        HashMap<SootMethod, Signature<Level>> freshTable =
                new HashMap<>(this.signatureMap);
        freshTable.put(m, makeSignature(m.getParameterCount(), constraints, effects));
        return of(freshTable);
    }

    @Override
    public String toString() {
        return this.signatureMap.toString();
    }

    public Option<Signature<Level>> get(SootMethod m) {
        Option<Signature<Level>> result = Option.apply(signatureMap.get(m));
        if (result.isEmpty()) {
            List<SigConstraint<Level>> constraints = Collections.emptyList();
            Effects<Level> effects = Effects.emptyEffect();
            result = Option.apply(MethodSignatures.makeSignature(m.getParameterCount(), constraints, effects));
        }
        return result;
    }

}
