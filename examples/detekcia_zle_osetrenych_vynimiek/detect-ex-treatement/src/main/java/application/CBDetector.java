package application;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.CannotCompileException;
import javassist.CtBehavior;

/**
 * Class CBDetector. Provides static method getIndices which detects and returns
 * information about catch blocks represented by Map. Key - index in class file.
 * Value - line number in source code.
 *
 * @author Matej Majdis
 */
class CBDetector {

    /**
     * Method getIndices. Gets information about all catch blocks for method /
     * constructor in parameter.
     *
     * @param cm
     * @return Map, key - class file index of catch block, value - line number
     * in source code .
     */
    public static Map<Integer, Integer> getIndices(CtBehavior cm) {
        CBIndicesHandler cih = new CBIndicesHandler();
        try {
            cm.instrument(cih);
        } catch (CannotCompileException ex) {
            Logger.getLogger(CBDetector.class.getName()).log(Level.SEVERE, "Can not read from method: " + cm.getName(), ex);
        }
        return cih.getIndices();
    }
}
