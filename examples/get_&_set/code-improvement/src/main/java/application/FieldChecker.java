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
 * Class FieldChecker. Tool to generate access methods and replacing direct read
 * / write operations on attributes by this generated methods. Working with two
 * classes - class C1: Containing attributes - class C2 using attributes of
 * class C1. Calling method FieldChecker.fixFieldsAccess will replace direct
 * read / write operations on attributes of Class C1 in CLass C2 by generated
 * get and set methods.
 *
 * This only class and method @fixFieldsAccess should be used to generate
 * accessors in C1 and replace references in C2. Any other class in this package
 * should not be used directly by classes outside it.
 *
 * @author Matej Majdis
 */
public class FieldChecker {

    private final Class classWithFields;
    private final Class classRefFields;
    private final String cDir;

    /**
     * Constructor for Class FieldChecker. By default sets path for class fies
     * of class C2 to "target/classes".
     *
     * @param classContainingFields - C1 : is class containing attributes to
     * generate get and set methods for
     * @param clasReferencingFields - C2 : is class to replace references to
     * attributes of class C1
     */
    public FieldChecker(Class classContainingFields, Class clasReferencingFields) {
        this.classWithFields = classContainingFields;
        this.classRefFields = clasReferencingFields;
        this.cDir = "target/classes";
    }

    /**
     * Constructor for Class FieldChecker
     *
     *
     * @param classContainingFields - C1 : is class containing attributes to
     * generate get and set methods for
     * @param clasReferencingFields - C2 : is class to replace references to
     * attributes of class C1
     * @param classFilesPath - is path to folder containing class files of Class
     * C2, only use if C2 is not Class of this project
     */
    public FieldChecker(Class classContainingFields, Class clasReferencingFields, String classFilesPath) {
        this.classWithFields = classContainingFields;
        this.classRefFields = clasReferencingFields;
        this.cDir = classFilesPath;
    }

    /**
     * Method fixFieldsAccess. Generates attributes using other classes in this
     * package and replaces calls in declared classes using method
     *
     * @replaceFieldAccess This method changes class file of class C2 -
     * generates get and set methods in it. If will be called repeatedly will do
     * nothing. In case of need to call it repeatedly classes must be rebuilt
     * first.
     *
     */
    public void fixFieldsAccess() {
        try {
            loadAccessorsForFields();
        } catch (DuplicateMemberException ex) {
            System.out.println("It seems accessors for attributes of class " + classWithFields.getSimpleName()
                    + " already was generated.\n" + "If you want to generate them again first remove old accessors by rebuilding classes "
                    + classRefFields.getSimpleName() + " and " + classWithFields.getSimpleName() + "\n");
        } catch (ReplacingFieldAccessorException e) {
            Logger.getLogger(FieldChecker.class.getName()).log(Level.WARNING,
                    "Error while loading methods into class file, check if: {0} is correct !", cDir);
        }

    }

    /**
     * Method loadAccessorsForFields. Helper method for @fixFieldsAccess.
     *
     * @throws DuplicateMemberException
     * @throws ReplacingFieldAccessorException
     */
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
     * Method replaceFieldAccess. This method is used by method @fixFieldsAccess
     * to replace read / write operations of single field
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

    /**
     * Method getRefCtClass. Helper method for @replaceFieldAccess. Converts
     * Class to CtClass object.
     *
     * @return CtClass object representing class C2
     */
    private CtClass getRefCtClass() {
        try {
            return ClassPool.getDefault().get(classRefFields.getName());
        } catch (NotFoundException ex) {
            Logger.getLogger(FieldChecker.class.getName()).log(Level.SEVERE, "Can not create CtClass for class"
                    + classRefFields.getSimpleName(), ex);
            return null;
        }
    }

    /**
     * Method getRefCtClass. Helper method for @replaceFieldAccess. Converts
     * Class to CtClass object.
     *
     * @return CtClass object representing class C1
     */
    private CtClass getWithCtClass() {
        try {
            return ClassPool.getDefault().get(classWithFields.getName());
        } catch (NotFoundException ex) {
            Logger.getLogger(FieldChecker.class.getName()).log(Level.SEVERE, "Can not create CtClass for class"
                    + classWithFields.getSimpleName(), ex);
            return null;
        }
    }

}
