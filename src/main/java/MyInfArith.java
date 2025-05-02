import arbitraryarithmetic.AInteger;
import arbitraryarithmetic.AFloat;

public class MyInfArith {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.println("Usage: java MyInfArith <int|float> <add|sub|mul|div> <num1> <num2>");
            return;
        }

        String type = args[0];
        String op = args[1];
        String num1 = args[2];
        String num2 = args[3];

        try {
            if (type.equals("int")) {
                AInteger a = new AInteger(num1);
                AInteger b = new AInteger(num2);
                AInteger result = switch (op) {
                    case "add" -> a.add(b);
                    case "sub" -> a.subtract(b);
                    case "mul" -> a.multiply(b);
                    case "div" -> a.divide(b);
                    default -> throw new IllegalArgumentException("Invalid operation: " + op);
                };
                System.out.println(result);
            } else if (type.equals("float")) {
                AFloat a = new AFloat(num1);
                AFloat b = new AFloat(num2);
                AFloat result = switch (op) {
                    case "add" -> a.add(b);
                    case "sub" -> a.subtract(b);
                    case "mul" -> a.multiply(b);
                    case "div" -> a.divide(b);
                    default -> throw new IllegalArgumentException("Invalid operation: " + op);
                };
                System.out.println(result);
            } else {
                System.err.println("Invalid type: " + type);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
