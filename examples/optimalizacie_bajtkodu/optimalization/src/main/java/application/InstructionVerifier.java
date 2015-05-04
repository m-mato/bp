package application;

import javassist.bytecode.CodeIterator;

/**
 * Class InsturctionVerifier. Tool to classify given instruction.
 *
 * @author Matej Majdis
 */
class InstructionVerifier {

    /**
     * Method isDestore. Determines if instruction on @index is store
     * instruction.
     *
     * @param index of instruction
     * @param ci iterator for code attribute if given index
     * @return -1 if given instruction is not store instruction, value of stored
     * byte otherwise
     */
    public static int isDstore(/*int op, */int index, CodeIterator ci) {
        int op = ci.byteAt(index);
        switch (op) {
            case 57:
                return ci.byteAt(index + 1);
            case 71:
            case 72:
            case 73:
            case 74:
                return op - 71;
        }
        return -1;
    }

    /**
     * Method isDload. Determines if instruction on @index is load instruction.
     *
     * @param index of instruction
     * @param ci iterator for code attribute if given index
     * @return -1 if given instruction is not load instruction, value of loaded
     * byte otherwise
     */
    public static int isDload(/*int op, */int index, CodeIterator ci) {
        int op = ci.byteAt(index);
        switch (op) {
            case 24:
                return ci.byteAt(index + 1);
            case 38:
            case 39:
            case 40:
            case 41:
                return op - 38;
        }
        return -1;
    }

    /**
     * Method isArithmetic. Determines if instruction @op is some of double
     * arithmetic instructions.
     *
     * @param op instruction opcode
     * @return true if instruction is basic double arithmetic ins
     */
    public static boolean isArithmetic(int op) {
        return (op == 99 || op == 103 || op == 107
                || op == 111 || op == 115 || op == 119);
    }

}
