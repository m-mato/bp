package demo;

import java.util.Objects;

/**
 * Class Circle - is OK.
 *
 * @author Matej Majdis
 */
public class Circle {

    private double radius;
    private Vertex centre;

    public Circle() {
        this.radius = 1;
        this.centre = new Vertex();
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Vertex getCentre() {
        return centre;
    }

    public void setCentre(Vertex centre) {
        this.centre = centre;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.radius) ^ (Double.doubleToLongBits(this.radius) >>> 32));
        hash = 47 * hash + Objects.hashCode(this.centre);
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
        final Circle other = (Circle) obj;
        if (Double.doubleToLongBits(this.radius) != Double.doubleToLongBits(other.radius)) {
            return false;
        }
        return Objects.equals(this.centre, other.centre);
    }

    @Override
    public String toString() {
        return "Circle {" + "radius is " + radius + centre + '}';
    }

}
