package example.model;

/**
 * Class Vertex.
 * Used by class Triangle.
 * 
 * @author Matej Majdis
 */
class Vertex {
    private final double x;
    private final double y;
    
    /**
     * Constructor for class Vertex2D
     * 
     * @param x
     * @param y
     */
    public Vertex(double x, double y) {
        this.x= x;
        this.y= y;
    }
    
    /**
     * Getter for parameter X
     * 
     * @return double x
     */
    public double getX() {
        return x;
    }
    
    /**
     * Getter for parameter Y
     * 
     * @return double y
     */
    public double getY() {
        return y;
    }
    
    /**
     * Method distance.
     * Computes distance of this Vertex and Vertex defined in parameter
     * 
     * @param z
     * @return double distance of Vertices
     */
    public double distance(Vertex z) {
        if(z==null) {
            return -1;
        }
        
        double dx= Math.pow((this.getX() - z.getX()), 2);
        double dy= Math.pow((this.getY() - z.getY()), 2);
        return Math.sqrt(dx + dy);
    }
    
    /**
     * Method toString
     * 
     * @return String which defines Vertex
     */
    @Override
    public String toString() {
        return "[" + getX() + ", " + getY() + "]";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertex other = (Vertex) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        return Double.doubleToLongBits(this.y) == Double.doubleToLongBits(other.y);
    }   
}
