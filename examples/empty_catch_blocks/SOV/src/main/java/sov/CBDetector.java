/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sov;

import java.util.Map;
import javassist.CtMethod;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.CannotCompileException;

/**
 *
 * @author mato
 */
class CBDetector {
    
    public static Map<Integer, Integer> getIndices(CtMethod cm) {
        CBIndicesHandler cih= new CBIndicesHandler();
        try {
            cm.instrument(cih);
        } catch (CannotCompileException ex) {
            Logger.getLogger(CBDetector.class.getName()).log(Level.SEVERE, "Can not read from method: " + cm.getName(), ex);
        }
        return cih.getIndices();
    }
}
