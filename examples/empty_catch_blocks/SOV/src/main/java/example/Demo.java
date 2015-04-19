package example;

public class Demo {

    public static void main(String[] args) {
        Person p = null;
        int i = 0;
        int j = 0;
        do {
            try {
                String name = args[i]; // ArrayIndexOutOfBoundsException

                p = new Person(name); // NullPointerException, IllegalArgumentException

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("No valid person name specified");
                System.exit(1);

            } catch (NullPointerException e) {
                //j++;

            } catch (IllegalArgumentException e) {

            } catch (Exception e) {
                j++;
            }
            i++;
        } while (p == null);

        System.out.println(p.getName());

        System.out.println("End of program");
    }
}
