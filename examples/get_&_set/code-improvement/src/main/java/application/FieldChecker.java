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
import javassist.bytecode.DuplicateMemberException;

/**
 * Class FieldChecker Is used to demonstrate replacing direct write / read from
 attributes by get and set methods using Javassist Before every run of this
 class you have to rebuild project in package demo to get its class files to
 default state

 If you want to change classes which are tested (default - package demo.*) you
 have to change 3 constants described below and constant in class AccGenerator

 Constant classWithFields is class with attributes to generate get / set
 methods for

 Constant classRefFields should be class using direct read / write
 operations on attributes declared in classWithFields This direct read /
 write operations will be replaced by generated get and set methods

 Constant cDir should be path to directory containing class file of class in
 classWithFields.
 *
 * @author Matej Majdis
 */
public class FieldChecker {

    private final Class classWithFields;
    private final Class classRefFields;
    private final String cDir;
    
    public FieldChecker(Class classContainingFields, Class clasReferencingFields) {
        this.classWithFields = classContainingFields;
        this.classRefFields = clasReferencingFields;
        this.cDir = "target/classes";
    }
    
    public FieldChecker(Class classContainingFields, Class clasReferencingFields, String classFilesPath) {
        this.classWithFields = classContainingFields;
        this.classRefFields = clasReferencingFields;
        this.cDir = classFilesPath;
    }

    /**
     * Method fixFieldsAccess Generates attributes and replace calls in declared
     * classes
     *
     */
    public void fixFieldsAccess() {
        try {
            loadAccessorsForFields();
        } catch (DuplicateMemberException ex) {
            System.out.println("It seems accessors for attributes of class " + classWithFields.getSimpleName()
                    + " already was generated.\n" + "If you want to generate them again first remove old accessors by rebuilding classes "
                    + classRefFields.getSimpleName() + " and " + classWithFields.getSimpleName());
        } catch (ReplacingFieldAccessorException e) {
            Logger.getLogger(FieldChecker.class.getName()).log(Level.WARNING,
                    "Error while loading methods into class file, check if: {0} is correct !", cDir);
        }

    }
    
    private void loadAccessorsForFields() throws DuplicateMemberException, ReplacingFieldAccessorException {
        Field[] fields = classWithFields.getFields();
        for (Field field : fields) {
                AccGenerator gen = new AccGenerator(classWithFields, field);
                gen.generateGetter();
                gen.generateSetter();
                gen.loadAccessors(cDir);
                replaceFieldAccess(field.getName());
            }
    }

    /**
     * Method replaceFieldAccess This method is helper method for
     * fixFieldsAccess to replace read / write operations of single field
     *
     * @param fieldName
     * @throws ReplacingFieldAccessorException
     */
    private void replaceFieldAccess(String fieldName) throws ReplacingFieldAccessorException {
        CtClass ccRef = getRefCtClass();
        CtClass ccWith = getWithCtClass();

        CodeConverter converter = new CodeConverter();
        ccRef.defrost();
        final String GET_NAME = "get" + fieldName.toUpperCase() + "StatGenerated";
        final String SET_NAME = "set" + fieldName.toUpperCase() + "StatGenerated";

        try {
            converter.replaceFieldRead(ccWith.getField(fieldName), ccWith, GET_NAME);
            converter.replaceFieldWrite(ccWith.getField(fieldName), ccWith, SET_NAME);
            System.out.println("-> Read and write operations replaced for field [" + classWithFields.getSimpleName()
                    + '.' + fieldName + "] in class [" + classRefFields.getName() + "]\n");
            ccRef.instrument(converter);
            ccRef.writeFile(cDir);
        } catch (NotFoundException | CannotCompileException | IOException ex) {
            Logger.getLogger(FieldChecker.class.getName()).log(Level.SEVERE, "Error while replacing accessor of field: " + fieldName, ex);
            throw new ReplacingFieldAccessorException("Error while replacing accessor of field: " + fieldName, ex);
        }

        ccRef.rebuildClassFile();
    }

    private CtClass getRefCtClass() {
        try {
            return ClassPool.getDefault().get(classRefFields.getName());
        } catch (NotFoundException ex) {
            Logger.getLogger(FieldChecker.class.getName()).log(Level.SEVERE, "Can not create CtClass for class" +
                    classRefFields.getSimpleName(), ex);
            return null;
        }        
    }
    
    private CtClass getWithCtClass() {
        try {
            return ClassPool.getDefault().get(classWithFields.getName());
        } catch (NotFoundException ex) {
            Logger.getLogger(FieldChecker.class.getName()).log(Level.SEVERE, "Can not create CtClass for class" +
                    classWithFields.getSimpleName(), ex);
            return null;
        }
    }

}
