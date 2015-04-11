package demo;

/**
 * Main class of test project for application.Demo and application.AccGenerator.
 *
 * @author Matej Majdis
 */
public class Main {

    private static final double xA = 2;
    private static final double yA = 2;
    private static final double xB = 2;
    private static final double yB = 7;
    private static final double xC = 5;
    private static final double yC = 2;

    private static final double RADIUS = 1;
    private static final double xCENTRE = 1;
    private static final double yCENTRE = 1;

    private static Triangle t;
    private static Circle c;

    /**
     * Simple main method creates Triangle and Circle represented by homonymous
     * Classes
     *
     * Class Circle is OK, but class Triangle should use get / set method
     * instead of direct write into attributes
     *
     * Program in package application will generate get / set methods for class
     * Triangle and rewrite all read / write operations in Class Main to use
     * this getters and setters.
     *
     * @param args
     */
    public static void main(String[] args) {
        t = new Triangle();
        c = new Circle();

        // Setting attributes of circle is valid
        c.setRadius(RADIUS);
        c.setCentre(new Vertex("CENTRE", xCENTRE, yCENTRE));

        /* Setting attributes of triangle is direct - shold use
         set methods */
        t.a = new Vertex("A", xA, yA);
        t.b = new Vertex("B", xB, yB);
        t.c = new Vertex("C", xC, yC);

        System.out.println(c + "\n" + t);

    }
}
