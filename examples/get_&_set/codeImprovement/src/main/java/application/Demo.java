package application;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CodeConverter;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * Class Demo Is used to demonstrate replacing direct write / read from
 * attributes by get and set methods using Javassist 
 * Before every run of this class you have to rebuild project in package demo
 * to get its class files to default state
 *
 * If you want to change classes which are tested (default - package demo.*) you
 * have to change 3 constants described below and constant in class AccGenerator
 *
 * Constant CLASS_WITH_FIELDS is class with attributes to generate get / set
 * methods for
 *
 * Constant CLASS_REF_FIELDS should be class using direct read / write
 * operations on attributes declared in CLASS_WITH_FIELDS This direct read /
 * write operations will be replaced by generated get and set methods
 *
 * Constant cDIR should be path to directory containing class file of class in
 * CLASS_WITH_FIELDS.
 *
 * @author Matej Majdis
 */
public class Demo {

    private static final Class CLASS_WITH_FIELDS = demo.Triangle.class;
    private static final Class CLASS_REF_FIELDS = demo.Main.class;
    private static final String cDIR = "target/classes";

    /**
     * Method main Generates attributes and replace calls in declared classes
     *
     * @param args
     * @throws ReplacingFieldAccessorException
     */
    public static void main(String[] args) throws ReplacingFieldAccessorException {
        Field[] fields = CLASS_WITH_FIELDS.getFields();
        for (Field field : fields) {
            AccGenerator gen = new AccGenerator(CLASS_WITH_FIELDS, field);
            gen.generateGetter();
            gen.generateSetter();
            gen.loadAccessors();
            replaceFieldAccess(field.getName());
        }

    }

    /**
     * Method replaceFieldAccess This method is helper method for main to
     * replace read / write operations of single field
     *
     * @param fieldName
     * @throws ReplacingFieldAccessorException
     */
    private static void replaceFieldAccess(String fieldName) throws ReplacingFieldAccessorException {
        CtClass ccRef;
        CtClass ccWith;
        try {
            ccRef = ClassPool.getDefault().get(CLASS_REF_FIELDS.getName());
            ccWith = ClassPool.getDefault().get(CLASS_WITH_FIELDS.getName());
        } catch (NotFoundException e) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, "Error while loading classes", e);
            throw new ReplacingFieldAccessorException("Error while loading classes", e);
        }

        CodeConverter converter = new CodeConverter();
        ccRef.defrost();
        final String GET_NAME = "get" + fieldName.toUpperCase() + "StatGenerated";
        final String SET_NAME = "set" + fieldName.toUpperCase() + "StatGenerated";

        try {
            converter.replaceFieldRead(ccWith.getField(fieldName), ccWith, GET_NAME);
            converter.replaceFieldWrite(ccWith.getField(fieldName), ccWith, SET_NAME);
            ccRef.instrument(converter);
            ccRef.writeFile(cDIR);
        } catch (NotFoundException | CannotCompileException | IOException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, "Error while replacing accessor of field: " + fieldName, ex);
            throw new ReplacingFieldAccessorException("Error while replacing accessor of field: " + fieldName, ex);
        }

        ccRef.rebuildClassFile();
    }

}
