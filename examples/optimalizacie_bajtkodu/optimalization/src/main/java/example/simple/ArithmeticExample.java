package example.simple;

/**
 * Class ArithmeticExample. Test class for example.simple. Performs simple
 * arithmetic operations to test functionality of program in package application.
 *
 * @author Matej Majdis
 */
public class ArithmeticExample {

    public static void make() {
        double n1 = 1.8;
        double n2 = 2.5;
        double n3 = 4.5;

        double result1 = n1 + n2;
        result1 += n2 + n3;
        result1 += n1 + n3;

        double result2 = n1 + n2;
        result2 += n3;
        result2 /= 2;

        System.out.println(n1 + ", " + result1 + ", " + result2);
    }

}
