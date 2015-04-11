package application;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

/**
 * Class AccGenerator This class generates get and set methods for field and
 * class declared in constructor
 *
 * If you want to change tested classes (default - package demo.*) you have to
 * change cDIR constant and also constants in class Demo
 *
 * Constant cDIR should be path to directory containing class file of class in
 * class in constructor - CLASS_WITH_FIELDS.
 *
 * @author Matej Majdis
 */
class AccGenerator {

    private static final String cDIR = "target/classes";

    private Class cl;
    private CtClass cc;
    private final String fieldName;
    private final String fieldType;

    public AccGenerator(Class CLASS_WITH_FIELDS, Field field) {
        cl = CLASS_WITH_FIELDS;
        try {
            cc = ClassPool.getDefault().get(CLASS_WITH_FIELDS.getName());
            cc.defrost();
        } catch (NotFoundException ex) {
            Logger.getLogger(AccGenerator.class.getName()).log(Level.SEVERE, "Error while loading class: " + CLASS_WITH_FIELDS.getName(), ex);
        }
        this.fieldName = field.getName();
        this.fieldType = field.getType().getName();
    }

    public void generateGetter() {
        System.out.println("->" + fieldName + " a " + fieldType);

        final String upName = fieldName.toUpperCase();
        final String header = "public static " + fieldType + " get" + upName + "StatGenerated(Object obj)";
        final String bodyRetype = cl.getTypeName() + " ret = (" + /*simpleTypeName*/ cl.getTypeName() + ") obj;";
        final String bodyPrintln = "System.out.println(\"-> Generated get method was called by field: " + cc.getName() + "." + fieldName + "\");";
        final String bodyReturn = "return ret." + fieldName + ";";
        final String body = bodyRetype + "\n" + bodyPrintln + "\n" + bodyReturn;
        final String getTemplate = header + " {\n" + body + "\n}";

        try {
            CtMethod method = CtNewMethod.make(getTemplate, cc);
            cc.addMethod(method);
            System.out.println("Getter for field: " + fieldName + " in class" + cl.getName() + " was successfully created");
        } catch (CannotCompileException ex) {
            Logger.getLogger(AccGenerator.class.getName()).log(Level.SEVERE, "Can not create or add getter for field: " + fieldName, ex);
        }

    }

    public void generateSetter() {
        final String upName = fieldName.toUpperCase();
        final String header = "public static void set" + upName + "StatGenerated(Object obj, " + fieldType + " " + fieldName + ")";
        final String bodyRetype = cl.getTypeName() + " t = (" + cl.getTypeName() + ") obj;";
        final String bodyPrintln = "System.out.println(\"-> Generated set method was called by field: " + cc.getName() + "." + fieldName + "\");";
        final String bodySet = "t." + fieldName + " = " + fieldName + ";";
        final String body = bodyRetype + bodyPrintln + bodySet;
        final String setTemplate = header + " {\n" + body + "\n}";

        try {
            CtMethod method = CtNewMethod.make(setTemplate, cc);
            cc.addMethod(method);
            System.out.println("Setter for field: " + fieldName + " in class" + cl.getName() + "was successfully created");
        } catch (CannotCompileException ex) {
            Logger.getLogger(AccGenerator.class.getName()).log(Level.SEVERE, "Can not create or add setter for field: " + fieldName, ex);
        }

    }

    public void loadAccessors() {
        try {
            cc.writeFile(cDIR);
        } catch (CannotCompileException | IOException ex) {
            Logger.getLogger(AccGenerator.class.getName()).log(Level.SEVERE, "Can not write to class file: " + cl.getName() + ".class", ex);
        }
        cc.rebuildClassFile();
    }

}
