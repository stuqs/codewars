package BinaryCalculator;


public class BinaryCalculator {
    public static String calculate(final String n1, final String n2, final String o) {

        int i1 = Integer.parseInt(n1, 2);
        int i2 = Integer.parseInt(n2, 2);

        int result = switch (o) {
            case "add" -> i1 + i2;
            case "subtract" -> i1 - i2;
            case "multiply" -> i1 * i2;
            case "divide" -> i1 / i2;
            default -> throw new IllegalArgumentException();

        };
        return Integer.toBinaryString(result);
    }
}