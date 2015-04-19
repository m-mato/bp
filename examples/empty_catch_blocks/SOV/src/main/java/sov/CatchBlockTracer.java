/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sov;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.analysis.Util;

/**
 *
 * @author mato
 */
public class CatchBlockTracer {
    
    public CatchBlockTracer() {
    }

    public void trace(final CtMethod cm) {
            Map<Integer, Integer> catchIndices = CBDetector.getIndices(cm);
            for (int index : catchIndices.keySet()) {
                try {
                    if (isEmpty(index, cm)) {
                        int line = catchIndices.get(index);
                        String message = "-> Suspicious catch block found on line: " + line;
                        Logger.getLogger(CatchBlockTracer.class.getName()).log(Level.INFO, message);
                    }
                } catch (BadBytecode ex) {
                    Logger.getLogger(CatchBlockTracer.class.getName()).log(Level.SEVERE, "Can not read intruction from " + cm.getName(), ex);
                }
            }
    }

    private boolean isEmpty(int index, CtMethod cm) throws BadBytecode {
        ClassFile cf;
        try(BufferedInputStream fin = new BufferedInputStream(new FileInputStream("target/classes/example/" + cm.getDeclaringClass().getSimpleName() + ".class"))) {
            cf = new ClassFile(new DataInputStream(fin));
        } catch (IOException ex) {
            String message= "Error while accessing class file.";
            Logger.getLogger(CatchBlockTracer.class.getName()).log(Level.SEVERE, message, ex);
            System.err.println(message);
            return false;
        }
        
        CodeAttribute ca = cf.getMethod(cm.getName()).getCodeAttribute();
        CodeIterator ci = ca.iterator();
        ci.move(index);
        ci.next();

        int nextIndex = ci.next();
        int prevIndex = index - 3;
        int afterCatchIndex = Util.getJumpTarget(prevIndex, ci);
        
        return (nextIndex == afterCatchIndex) || (Util.isJumpInstruction(ci.byteAt(nextIndex)) && Util.getJumpTarget(nextIndex, ci) == afterCatchIndex);
    }
}
