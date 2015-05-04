package example.model;

import java.util.Objects;

/**
 * Class Triangle. Test class for optimizing application. Used by method
 * example.model.Demo to test functionality of application. Methods @getArea and
 * @getLength provides arithmetic operations that will be optimized
 *
 * @author Matej Majdis
 */
public class Triangle {

    public static final double EPSILON = 0.001;

    private Vertex vertA;
    private Vertex vertB;
    private Vertex vertC;

    private Triangle sub0;
    private Triangle sub1;
    private Triangle sub2;

    private boolean divided = false;

    /**
     * Constructor for class Triangle
     *
     * @param vertA
     * @param vertB
     * @param vertC
     */
    public Triangle(Vertex vertA, Vertex vertB, Vertex vertC) {
        this.vertA = vertA;
        this.vertB = vertB;
        this.vertC = vertC;
    }

    /**
     * Constructor for class Triangle
     *
     * @param vertA
     * @param vertB
     * @param vertC
     * @param depth
     */
    public Triangle(Vertex vertA, Vertex vertB, Vertex vertC, int depth) {
        this(vertA, vertB, vertC);
        divide(depth);
    }

    /**
     * Getter for property vertA
     *
     * @return Vertex vertA
     */
    public Vertex getVertexA() {
        return vertA;
    }

    /**
     * Getter for property vertB
     *
     * @return Vertex vertB
     */
    public Vertex getVertexB() {
        return vertB;
    }

    /**
     * Getter for property vertC
     *
     * @return Vertex vertC
     */
    public Vertex getVertexC() {
        return vertC;
    }

    /**
     * Method isDivided return if triangle is divided to 3 small subtriangles
     *
     * @return true if triangle is divided, false otherwise
     */
    public boolean isDivided() {
        return divided;
    }

    /**
     * Method getSubTriangle returns subtriangle for this triangle
     *
     * @param i
     * @return null if triangle is not divided, or param if is out of rage,
     * otherwise returns Triangle(i)
     */
    public Triangle getSubTriangle(int i) {
        if (!isDivided()) {
            return null;
        }

        switch (i) {
            case 0:
                return sub0;
            case 1:
                return sub1;
            case 2:
                return sub2;
        }

        return null;
    }

    /**
     * Method divide divides triangle to 3 small subtriangles
     *
     * @return true if triangle was succesfully divided, false if triangle was
     * divided already
     */
    public boolean divide() {
        if (isDivided()) {
            return false;
        }

        Vertex center1 = getCenter(getVertexA(), getVertexB());
        Vertex center2 = getCenter(getVertexB(), getVertexC());
        Vertex center3 = getCenter(getVertexA(), getVertexC());

        sub0 = new Triangle(getVertexA(), center1, center3);
        sub1 = new Triangle(center1, getVertexB(), center2);
        sub2 = new Triangle(center3, center2, getVertexC());

        divided = true;

        return true;
    }

    /**
     * Method divide(int depth) recursively divides triangle to depth in param
     *
     * @param depth
     */
    private void divide(int depth) {
        if (depth <= 0) {
            return;
        }

        divide();
        depth--;
        sub0.divide(depth);
        sub1.divide(depth);
        sub2.divide(depth);
    }

    /**
     * Private method getCenter
     *
     * @param Vertex2D vertX - first point
     * @param Vertex2D vertY - second point
     * @return Vertex the center of the line between vertX and vertY
     */
    private Vertex getCenter(Vertex vertX, Vertex vertY) {
        double cenX = (vertX.getX() + vertY.getX()) / 2;
        double cenY = (vertX.getY() + vertY.getY()) / 2;
        Vertex center = new Vertex(cenX, cenY);

        return center;
    }

    /**
     * Method isEquilateral
     *
     * @return true if triangle is equilateral, false otherwise
     */
    public boolean isEquilateral() {
        double dis1 = vertA.distance(vertB);
        double dis2 = vertA.distance(vertC);
        double dis3 = vertB.distance(vertC);

        return (Math.abs(dis2 - dis1) < EPSILON && Math.abs(dis2 - dis3) < EPSILON);
    }

    /**
     * @return @override
     */
    public double getWidth() {
        double maxW = Math.max(Math.max(vertA.getX(), vertB.getX()), vertC.getX());
        double minW = Math.min(Math.min(vertA.getX(), vertB.getX()), vertC.getX());

        return maxW - minW;
    }

    /**
     * @return @override
     */
    public double getHeight() {
        double maxH = Math.max(Math.max(vertA.getY(), vertB.getY()), vertC.getY());
        double minH = Math.min(Math.min(vertA.getY(), vertB.getY()), vertC.getY());

        return maxH - minH;
    }

    /**
     * @return @override
     */
    public double getLength() {
        double d = vertA.distance(vertB);
        d += vertB.distance(vertC);
        d += vertC.distance(vertA);

        return d;
    }

    /**
     * @return @override
     */
    public double getArea() {
        double d1 = vertA.distance(vertB);
        double d2 = vertB.distance(vertC);
        double d3 = vertC.distance(vertA);
        double s = d1 + d2;
        s += d3;
        s /= 2;
        //double s = (d1 + d2 + d3) / 2;

        return Math.sqrt(s * (s - d1) * (s - d2) * (s - d3));
    }

    /**
     * Method toString
     *
     * @return String which defines triangle and its vertices
     */
    @Override
    public String toString() {
        if (vertA == null || vertB == null || vertC == null) {
            return "INVALID TRIANGLE";
        }

        return "Triangle: vertices=" + vertA + " " + vertB + " " + vertC;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.vertA);
        hash = 89 * hash + Objects.hashCode(this.vertB);
        hash = 89 * hash + Objects.hashCode(this.vertC);
        hash = 89 * hash + Objects.hashCode(this.sub0);
        hash = 89 * hash + Objects.hashCode(this.sub1);
        hash = 89 * hash + Objects.hashCode(this.sub2);
        hash = 89 * hash + (this.divided ? 1 : 0);
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
        final Triangle other = (Triangle) obj;
        if (!Objects.equals(this.vertA, other.vertA)) {
            return false;
        }
        if (!Objects.equals(this.vertB, other.vertB)) {
            return false;
        }
        if (!Objects.equals(this.vertC, other.vertC)) {
            return false;
        }
        if (!Objects.equals(this.sub0, other.sub0)) {
            return false;
        }
        if (!Objects.equals(this.sub1, other.sub1)) {
            return false;
        }
        return Objects.equals(this.sub2, other.sub2);
    }

}
