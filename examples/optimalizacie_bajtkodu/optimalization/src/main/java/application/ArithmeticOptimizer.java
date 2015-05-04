package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.MethodInfo;

/**
 * Class ArithmeticOptimizer. Access class for application to optimize bytecode
 * in package application. After initialization method @optimizeClass provides
 * optimizations on arithmetical expression for class defined in parameter. No
 * other class from this package should be used outside this package.
 *
 * @author Matej Majdis
 */
public class ArithmeticOptimizer {

    private final String filePath;

    /**
     * Constructor for class @ArithmeticOptimizer.
     */
    public ArithmeticOptimizer() {
        this.filePath = "target/classes";
    }

    /**
     * Constructor for class @ArithmeticOptimizer.
     *
     * @param filePath defines path to class files of class to optimize. Use
     * only if class is not part of this project.
     */
    public ArithmeticOptimizer(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Method optimizeClass. Key method for application and class
     *
     * @ArithmeticOptimizer. Only this method should be use to perform
     * optimizations.
     *
     * @param classNameToOpt defines name of Class to optimize
     */
    public void optimizeClass(String classNameToOpt) {
        CtClass ctClass;
        try {
            ctClass = ClassPool.getDefault().getCtClass(classNameToOpt);
        } catch (NotFoundException ex) {
            Logger.getLogger(ArithmeticOptimizer.class.getName()).log(Level.SEVERE, "Can not find class: " + classNameToOpt, ex);
            return;
        }

        CtMethod[] methods = ctClass.getDeclaredMethods();

        int counter = 0;
        for (CtMethod method : methods) {
            ctClass.defrost();
            MethodInfo mi = method.getMethodInfo();
            System.out.println("-> Method: " + mi.getName());
            MethodModifier optimizer = new MethodModifier(mi);
            optimizer.optimize();
            counter += optimizer.instructionsDeleted();
        }

        try {
            ctClass.writeFile(filePath);
        } catch (CannotCompileException | IOException ex) {
            Logger.getLogger(ArithmeticOptimizer.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("\nSUMMARY: Deleted " + counter + " instructions from class" + classNameToOpt + "\n");
    }

}
