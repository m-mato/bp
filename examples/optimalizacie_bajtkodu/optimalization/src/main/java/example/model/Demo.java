package example.model;

/**
 * Class example.model.Demo.
 * Class to run testing functionality of optimizing application.
 * Optimizing class @examples.model.Triangle.
 * 
 * @author Matej Majdis
 */
public class Demo {
    public static void main(String[] args) {
        
        //Initializing optimizer
        System.out.println("Initializing optimizer...");
        application.ArithmeticOptimizer optimizer = new application.ArithmeticOptimizer();
        
        //Optimizing class example.model.Triangle
        System.out.println("Modifying class Triangle...\n");
        optimizer.optimizeClass(Triangle.class.getName());
        
        //Testing if optimized class runs OK
        System.out.println("------------------------------\nTesting modified class Triangle:");
        Vertex a = new Vertex(0, 0);
        Vertex b = new Vertex(5, 0);
        Vertex c = new Vertex(0, 5);
        
        Triangle t = new Triangle(a, b, c);
        
        System.out.println("Created " + t + ", length: " + t.getLength() + ", area: " + t.getArea());
        
        
    
    }
    
}
