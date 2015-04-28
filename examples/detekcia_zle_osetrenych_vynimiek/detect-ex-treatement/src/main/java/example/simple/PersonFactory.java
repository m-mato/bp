package example.simple;

public class PersonFactory {

    public static Person[] CreatePersons(String[] names) {

        Person[] persons = new Person[names.length];
        int counter = 0;

        for (String name : names) {
            try {
                Person p = new Person(name);
                persons[counter] = p;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("No valid person name specified");

            } catch (NullPointerException e) {
                //empty catch block
            } catch (IllegalArgumentException e) {
                //empty catch block
            }

            counter++;
        }

        System.out.println("Persons created...");
        return persons;
    }
}
