/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sov;

import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 *
 * @author mato
 */
public class Main {
    public static void main(String[] args) throws CannotCompileException { 
        final Class CLASS_TO_TRACE = example.Demo.class;
        
        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass cc = pool.get(CLASS_TO_TRACE.getName());
            CtMethod[] cms= cc.getDeclaredMethods();
            CatchBlockTracer ct= new CatchBlockTracer();
            for(CtMethod method : cms) {
                ct.trace(method);
            }
        } catch (NotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
