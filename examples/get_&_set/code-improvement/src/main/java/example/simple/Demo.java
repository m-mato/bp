package example.simple;

/**
 * Demo class of test project for application.Demo and application.AccGenerator.
 *
 * @author Matej Majdis
 */
public class Demo {

    /**
     * Simple main method creates Triangle and Circle represented by homonymous
     * Classes
     *
     * Class Circle is OK, but class Triangle should use get / set method
     * instead of direct write into attributes
     *
     * Program in package application will generate get / set methods for class
     * Triangle and rewrite all read / write operations in Class Demo to use
     * this getters and setters.
     *
     * @param args
     */
    public static void main(String[] args) {

            (new application.FieldChecker(Triangle.class, Initializer.class)).fixFieldsAccess();
        
        System.out.println(Initializer.createTriangle(new Vertex("a", 1, 2), new Vertex("a", 1, 2), new Vertex("a", 1, 2)).toString());

    }
}
