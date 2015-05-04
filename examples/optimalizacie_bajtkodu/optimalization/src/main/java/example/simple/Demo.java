package example.simple;

/**
 * Class example.simple.Demo.
 * Class to run testing functionality of optimizing application.
 * Optimizing simple class @examples.simple.ArithmeticExample.
 * 
 * @author Matej Majdis
 */
public class Demo {

    public static void main(String[] args) {

        //Running test method before optimalization
        System.out.println("Test results before optimalization: ");
        ArithmeticExample.make();

        //Initializing optimizer
        System.out.println("\nInitializing optimizer...");
        application.ArithmeticOptimizer optimizer = new application.ArithmeticOptimizer();

        //Optimizing test class
        System.out.println("Modifying class ArithmeticExample...\n");
        optimizer.optimizeClass(ArithmeticExample.class.getName());

        //Running test optimized method again to check it was not brokenn
        System.out.println("Test results after optimalization: ");
        ArithmeticExample.make();

    }

}
