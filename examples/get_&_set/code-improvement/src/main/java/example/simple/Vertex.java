package example.simple;

import java.util.Objects;

/**
 * Class Vertex - is OK.
 *
 * @author Matej Majdis
 */
public class Vertex {

    private String name;
    private double coordX;
    private double coordY;

    public Vertex() {
        this.name = "default";
        this.coordX = 0;
        this.coordY = 0;
    }

    public Vertex(String name, double coordX, double coordY) {
        this.name = name;
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCoordX() {
        return coordX;
    }

    public void setCoordX(double coordX) {
        this.coordX = coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public void setCoordY(double coordY) {
        this.coordY = coordY;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.coordX) ^ (Double.doubleToLongBits(this.coordX) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.coordY) ^ (Double.doubleToLongBits(this.coordY) >>> 32));
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
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (Double.doubleToLongBits(this.coordX) != Double.doubleToLongBits(other.coordX)) {
            return false;
        }
        return Double.doubleToLongBits(this.coordY) == Double.doubleToLongBits(other.coordY);
    }

    @Override
    public String toString() {
        return "Vertex " + name + ": " + "[" + coordX + ", " + coordY + "]";
    }
}
