package application;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.Handler;

/**
 * Class CBIndicesHandler. Handles information about catch blocks in method /
 * constructor specified in CBDetector. Exactly handles bytecode indices as keys
 * and their line number in source code as values in Map.
 *
 * @author Matej Majdis
 */
class CBIndicesHandler extends ExprEditor {

    Map<Integer, Integer> indices;

    /**
     * Constructor for class CBIndicesHandler. Provides initialization of Map.
     */
    public CBIndicesHandler() {
        this.indices = new HashMap<>();
    }

    /**
     * Method edit. Is used by instrument method in class CBDector to find catch
     * blocks.
     *
     * @param h
     * @throws CannotCompileException
     */
    @Override
    public void edit(Handler h) throws CannotCompileException {
        indices.put(h.indexOfBytecode(), h.getLineNumber());
    }

    /**
     * Getter for attribute indices. Returns map of catch blocks in declared
     * method / constructor represented by Map. Key is index in class file,
     * value is line number in source code.
     *
     * @return Map of indices and line numbers of catch blocks for specified
     * method / constructor
     */
    public Map<Integer, Integer> getIndices() {
        return Collections.unmodifiableMap(indices);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.indices);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CBIndicesHandler other = (CBIndicesHandler) obj;
        return Objects.equals(this.indices, other.indices);
    }

    @Override
    public String toString() {
        return "CBIndicesHandler {" + " indices= " + indices + " }";
    }
}
