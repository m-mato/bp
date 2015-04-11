package demo;

import java.util.Objects;

/**
 * Class Triangle - should use private attributes and get / set methods.
 *
 * @author Matej Majdis
 */
public class Triangle {

    public Vertex a;
    public Vertex b;
    public Vertex c;

    public Triangle() {
        a = new Vertex();
        b = new Vertex();
        c = new Vertex();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.a);
        hash = 79 * hash + Objects.hashCode(this.b);
        hash = 79 * hash + Objects.hashCode(this.c);
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
        if (!Objects.equals(this.a, other.a)) {
            return false;
        }
        if (!Objects.equals(this.b, other.b)) {
            return false;
        }
        return Objects.equals(this.c, other.c);
    }

    /*public boolean isValid() {
        
     }*/
    @Override
    public String toString() {
        return "Vertices of Triangle are {" + a + ", " + b + ", " + c + '}';
    }

}
