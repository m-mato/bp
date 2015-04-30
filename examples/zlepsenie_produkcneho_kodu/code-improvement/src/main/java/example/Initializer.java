package example;

/**
 * Class Initializer.
 * Provides initialization of entities Triangle and Circle.
 * 
 * @author Matej Majdis
 */
public class Initializer {
    
    public static Circle createCircle(double radius, Vertex centre) {
        Circle circle = new Circle();
        
        circle.setRadius(radius);
        circle.setCentre(centre);
        
        return circle;
    }
    
    public static Triangle createTriangle(Vertex a, Vertex b, Vertex c) {
        Triangle triangle = new Triangle();
        triangle.a = a;
        triangle.b = b;
        triangle.c = c;
        
        return triangle;
    }
    
}
