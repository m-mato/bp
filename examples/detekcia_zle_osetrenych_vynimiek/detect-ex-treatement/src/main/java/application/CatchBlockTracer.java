package application;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.CtBehavior;
import javassist.CtConstructor;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.analysis.Util;

/**
 * Class CatchBlockTracer. Detects catch blocks for entered method / constructor
 * - uses class CBDetector. Traces if some catch blocks are suspicious - empty.
 * If finds some logs information about it.
 *
 * @author Matej Majdis
 */
public class CatchBlockTracer {

    /**
     * Method trace. Traces empty catch blocks. If empty catch block is found
     * message specifying line number and method is logged.
     *
     * @param cm
     * @return number of empty catch blocks
     */
    public int trace(final CtBehavior cm) {
        int counter = 0;
        Map<Integer, Integer> catchIndices = CBDetector.getIndices(cm);
        for (int index : catchIndices.keySet()) {
            try {
                if (isEmpty(index, cm)) {
                    counter++;
                    int line = catchIndices.get(index);
                    String message = "-> Suspicious catch block found on line: " + line + " in method: " + cm.getLongName();
                    Logger.getLogger(CatchBlockTracer.class.getName()).log(Level.INFO, message);
                }
            } catch (BadBytecode ex) {
                Logger.getLogger(CatchBlockTracer.class.getName()).log(Level.SEVERE, "Can not read intruction from " + cm.getName(), ex);
            }
        }
        return counter;
    }

    /**
     * Private method isEmpty. Finds out if catch block on entered index in
     * method / constructor @cm is empty.
     *
     * @param index
     * @param cm
     * @return true if catch block is empty, false otherwise
     * @throws BadBytecode if @index is invalid
     */
    private boolean isEmpty(int index, CtBehavior cm) throws BadBytecode {
        ClassFile cf;
        String path = cm.getDeclaringClass().getName().replaceAll("\\.", "/");
        try (BufferedInputStream fin = new BufferedInputStream(new FileInputStream(/*"target/classes/example/" + cm.getDeclaringClass().getSimpleName() + ".class"*/"target/classes/" + path + ".class"))) {
            cf = new ClassFile(new DataInputStream(fin));
        } catch (IOException ex) {
            String message = "Error while accessing class file.";
            Logger.getLogger(CatchBlockTracer.class.getName()).log(Level.SEVERE, message, ex);
            System.err.println(message);
            return false;
        }

        CodeAttribute ca;
        if (cm instanceof CtConstructor) {
            ca = cf.getMethod("<init>").getCodeAttribute();
        } else {
            ca = cf.getMethod(cm.getName()).getCodeAttribute();
        }
        CodeIterator ci = ca.iterator();
        ci.move(index);
        ci.next();

        int nextIndex = ci.next();
        int prevIndex = index - 3;
        int afterCatchIndex = Util.getJumpTarget(prevIndex, ci);

        return (nextIndex == afterCatchIndex) || (Util.isJumpInstruction(ci.byteAt(nextIndex)) && Util.getJumpTarget(nextIndex, ci) == afterCatchIndex);
    }
}
