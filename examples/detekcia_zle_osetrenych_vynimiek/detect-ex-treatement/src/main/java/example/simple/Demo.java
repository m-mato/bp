package example.simple;

/**
 * Class Demo
 *
 * @author Matej Majdis
 */
public class Demo {

    /**
     * This class and method main are testing functionality of class Tracer and
     * its dependencies on simple classes Person and PersonFactory. Detection of
     * all empty catch block should be provided.
     *
     * @param args
     */
    public static void main(String args[]) {

        System.out.println("Started tracing catch block of classes Person and PersonFactory...\n");

        // Tracing class PersonFactory
        // Source code of this class contains 2 empty and 1 non empty catch blocks
        // Tracer should detect 2 suspicious catch blocks on lines: 17 and 19
        application.Tracer.traceCatchBlocks(PersonFactory.class);

        System.out.println();
        
        // Tracing class Person
        // Source code of this class contains no catch blocks
        // Tracer should detect no suspicious catch block.       
        application.Tracer.traceCatchBlocks(Person.class);
    }

}
