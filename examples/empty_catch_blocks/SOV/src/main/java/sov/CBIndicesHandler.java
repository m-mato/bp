/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sov;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.Handler;

/**
 *
 * @author mato
 */
class CBIndicesHandler extends ExprEditor {

    //Set<Integer> indices;
    Map<Integer, Integer> indices;

    public CBIndicesHandler() {
        this.indices= new HashMap<>();
    }

    @Override
    public void edit(Handler h) throws CannotCompileException {
        indices.put(h.indexOfBytecode(), h.getLineNumber());
    }

    public Map<Integer, Integer> getIndices() {
        /*int[] in = new int[this.indices.size()];
        int i = 0;
        for (Integer val : this.indices) {
            in[i++] = val;
        }
        return in;*/
        return Collections.unmodifiableMap(indices);
    }

}
