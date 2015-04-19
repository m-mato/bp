package example;


public class Person {

    private String name;

    public Person(String name) {
        if (name == null) {
            throw new NullPointerException("Name cannot be null");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
