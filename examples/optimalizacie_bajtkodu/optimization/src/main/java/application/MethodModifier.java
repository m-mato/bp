package application;

import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;

/**
 * Class MethodModifier. Optimize bytecode instructions for given method.
 *
 * @author Matej Majdis
 */
class MethodModifier {

    private final MethodInfo method;
    private final CodeIterator ci;
    private int counter;

    /**
     * Constructor for class MethodModifier.
     *
     * @param mi defines bytecode for method to optimize
     */
    public MethodModifier(MethodInfo mi) {
        this.method = mi;
        this.ci = method.getCodeAttribute().iterator();
        this.counter = 0;
    }

    /**
     * Method optimize - key method for class @MethodModifier. Provides
     * iterating over bytecode and removing redundant store and load
     * instructions.
     *
     */
    public void optimize() {
        int lastPairIndex = -1;
        int lastPairNextIndex = -1;
        int insideMode = -1;

        boolean is16 = false;

        while (ci.hasNext()) {
            int index = getNextIndex();
            int op = ci.byteAt(index);
            int ni = ci.lookAhead();
            int argDstore = -1;
            int argDload = -1;

            if (insideMode == -1 && (argDstore = InstructionVerifier.isDstore(/*op,*/index, ci)) >= 0
                    && (argDload = InstructionVerifier.isDload(/*ci.byteAt(ni),*/ni, ci)) >= 0 && (argDload == argDstore)) {
                insideMode = argDstore;

                lastPairIndex = index;
                lastPairNextIndex = ni;
                if (ci.byteAt(ni) == 24) {
                    is16 = true;
                }
            } else if (insideMode != -1 && ci.hasNext()
                    && (insideMode == InstructionVerifier.isDstore(/*ci.byteAt(ni),*/ni, ci))) {
                if (InstructionVerifier.isArithmetic(/*op*/ci.byteAt(index))) {
                    removeIndices(lastPairIndex, lastPairNextIndex, is16);
                    insideMode = -1;
                    is16 = false;
                } else {
                    insideMode = -1;
                }
            }
        }
        
    }

    /**
     * Helper method for method @ptimize. Iterating over bytecode.
     *
     * @return
     */
    private int getNextIndex() {
        try {
            return ci.next();
        } catch (BadBytecode ex) {
            Logger.getLogger(MethodModifier.class.getName()).log(Level.SEVERE, "Error while iterating class file", ex);
            return -1;
        }
    }

    /**
     * Helper method for method @optimize. Provides removing of instructions on
     * given indices.
     *
     * @param lastPairIndex index of store instruction
     * @param lastPairNextIndex index of load instruction
     * @param is16 determines if load instruction is 16 bit long
     */
    private void removeIndices(int lastPairIndex, int lastPairNextIndex, boolean is16) {
        System.out.println("\t-> Removing pair from index: " + lastPairIndex);

        int countB = 2;
        if (lastPairNextIndex - lastPairIndex > 1) {
            countB++;
        }
        if (is16) {
            countB++;
        }

        byte[] code = {0x00};
        for (int j = 0; j < countB; j++) {
            ci.write(code, lastPairIndex + j);
        }
        
        counter += 2;
    }
    
    /**
     * Getter for attribute counter.
     * 
     * @return number of deleted instructions 
     */
    public int instructionsDeleted() {
        return this.counter;
    }

}
