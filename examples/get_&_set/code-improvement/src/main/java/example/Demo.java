package example;

/**
 * Class Demo. Used to demonstrate functionality of class FieldChecker and its
 * dependencies in package application. Functionality of class FieldCHecker is
 * described in its documentation.
 *
 * Package example contains class Vertex representing Vertex in 2D. Classes
 * Triangle and Circle are using Vertex to construct geometric objects.
 * Attributes of class circle are valid and accessed by get and set methods.
 * Attributes of class Triangle are purposely public and accessed directly by
 * Class Initializer.
 *
 * !!! Using class FiedChecker should be generated get and set methods for
 * attributes in class Triangle. This class should also replace direct read /
 * write operations in Initializer to call generated get and set methods. !!!
 *
 *
 * @author Matej Majdis
 */
public class Demo {

    /**
     * Method main. Method main is used to test functionality of program in
     * package application.
     *
     * @param args
     */
    public static void main(String[] args) {

        /* Creation and initialization of Class FieldChecker responsible for 
         generating accessors in class Triangle and replacing attribute calls
         in class Initializer.*/
        application.FieldChecker fc = new application.FieldChecker(Triangle.class, Initializer.class);

        /* Method @fixFieldsAcces is only method that should be called to 
         provide functionality described above*/
        fc.fixFieldsAccess();

        /* After get and set method was generated and read / write operations
         was replaced modified methods are called to test their functionality*/
        System.out.println("-------------------------------\nTrying to use modified classes: \n");

        Vertex centre = new Vertex("centre", 0, 0);
        Circle c = Initializer.createCircle(5, centre);
        System.out.println("Created: " + c);
        Vertex aVert = new Vertex("A", -1, -1);
        Vertex bVert = new Vertex("B", 1, -1);
        Vertex cVert = new Vertex("C", 1, 1);
        Triangle t = Initializer.createTriangle(aVert, bVert, cVert);
        System.out.println("Created: " + t);

        System.out.println("\nGet and set methods successfully tested.");

    }
}
