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
import javassist.bytecode.DuplicateMemberException;
import javassist.tools.reflect.CannotReflectException;

/**
 * Class AccGenerator This class generates get and set methods for field and
 class declared in constructor

 If you want to change tested classes (default - package demo.*) you have to
 change cDir constant and also constants in class Demo

 Constant cDir should be path to directory containing class file of class in
 class in constructor - CLASS_WITH_FIELDS.
 *
 * @author Matej Majdis
 */
class AccGenerator {

    private Class cl;
    private CtClass cc;
    private final String fieldName;
    private final String fieldType;

    public AccGenerator(Class ClassWithFields, Field field) {
        cl = ClassWithFields;
        try {
            cc = ClassPool.getDefault().get(ClassWithFields.getName());
            cc.defrost();
        } catch (NotFoundException ex) {
            Logger.getLogger(AccGenerator.class.getName()).log(Level.SEVERE, "Error while loading class: " + ClassWithFields.getName(), ex);
        }
        this.fieldName = field.getName();
        this.fieldType = field.getType().getName();
    }

    public void generateGetter() throws DuplicateMemberException {        
        String getTemplate = AccCreator.makeGetTemplate(fieldName, fieldType, cl.getTypeName());

        try {
            CtMethod method = CtNewMethod.make(getTemplate, cc);
            cc.addMethod(method);
            System.out.println("+ Getter for field: [" + fieldName + "] in class [" + cl.getName() + "] was successfully created");
        } catch (CannotCompileException ex) {
            if (ex instanceof CannotReflectException) {
                Logger.getLogger(AccGenerator.class.getName()).log(Level.SEVERE, "Can not create or add getter for field: " + fieldName, ex);
            } else {
                throw (DuplicateMemberException) ex;
            }
        }
    }

    public void generateSetter() throws DuplicateMemberException {
        String setTemplate = AccCreator.makeSetTemplate(fieldName, fieldType, cl.getTypeName());
        
        try {
            CtMethod method = CtNewMethod.make(setTemplate, cc);
            cc.addMethod(method);
            System.out.println("+ Setter for field: [" + fieldName + "] in class [" + cl.getName() + "] was successfully created");
        } catch (CannotCompileException ex) {
            Logger.getLogger(AccGenerator.class.getName()).log(Level.SEVERE, "Can not create or add setter for field: " + fieldName, ex);
        }

    }

    public void loadAccessors(String cDir) {
        try {
            cc.writeFile(cDir);
        } catch (CannotCompileException | IOException ex) {
            Logger.getLogger(AccGenerator.class.getName()).log(Level.SEVERE, "Can not write to class file: " + cl.getName() + ".class", ex);
        }
        cc.rebuildClassFile();
    }

}
