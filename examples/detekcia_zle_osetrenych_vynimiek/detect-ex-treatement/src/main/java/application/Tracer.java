package application;

import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * Class Tracer This class should be used to trace empty catch blocks for class
 * declared in parameter of method traceCatchBlocks. Other classes in this
 * package are for internal use and should not be accessed.
 *
 * @author Matej Majdis
 */
public class Tracer {

    /**
     * Static method traceCatchBlocks. Traces catch blocks in all methods and
     * constructors for class @classToTrace. After some suspicious catch block
     * was found method logs information about it.
     *
     * @param classToTrace
     */
    public static void traceCatchBlocks(Class classToTrace) {
        final Class CLASS_TO_TRACE = classToTrace;
        int counter = 0;

        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass cc = pool.get(CLASS_TO_TRACE.getName());
            CtMethod[] cms = cc.getDeclaredMethods();
            CatchBlockTracer ct = new CatchBlockTracer();
            System.out.println("- Class " + CLASS_TO_TRACE.getSimpleName() + " -");
            for (CtMethod method : cms) {
                counter += ct.trace(method);
            }
            for (CtConstructor constructor : cc.getDeclaredConstructors()) {
                counter += ct.trace(constructor);
            }
            System.out.println("SUMMARY: " + counter + " suspicious catch blocks found in class " + CLASS_TO_TRACE.getSimpleName());
        } catch (NotFoundException ex) {
            Logger.getLogger(Tracer.class.getName()).log(Level.WARNING, "Error while accessing class" + CLASS_TO_TRACE.getSimpleName(), ex);
        }
    }

}
