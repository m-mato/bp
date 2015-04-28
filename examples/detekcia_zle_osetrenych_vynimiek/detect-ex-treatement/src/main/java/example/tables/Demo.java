package example.tables;

/**
 *
 * @author Matej Majdis
 */
public class Demo {

    /**
     * This class and method main are testing functionality of class Tracer and
     * its dependencies on classes in package example.tables. This classes was
     * borrowed from Java Development Kit Demos and Samples package. Detection
     * of all empty catch block should be provided.
     *
     * @param args
     */
    public static void main(String args[]) {

        System.out.println("Started tracing catch blocks of classes JDBCAdapter, OldJTable and TableExample...\n");

        // Tracing class JDBCAdapter
        // Source code of this class contains 4 empty and 3 non empty catch blocks
        // Tracer should detect 4 suspicious catch blocks on lines: 79, 81, 116 and 266
        application.Tracer.traceCatchBlocks(JDBCAdapter.class);

        System.out.println();

        // Tracing class OldJTable
        // Source code of this class contains 1 non empty catch block
        // Tracer should detect no suspicious catch block.       
        application.Tracer.traceCatchBlocks(OldJTable.class);

        System.out.println();

        // Tracing class TableExample
        // Source code of this class contains no catch blocks
        // Tracer should detect no suspicious catch block.       
        application.Tracer.traceCatchBlocks(TableExample.class);

        //Classes TableMap and TableSorter also contains no catch blocks - is not necessary to test.
    }
}
