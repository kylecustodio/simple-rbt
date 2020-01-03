import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class RBTDriver {
    public static boolean STRING = true;
    public static boolean INTEGER = false;
    public static void main(String[] args) {
        Scanner in;
        if (args.length != 2) {
            System.out.print("Error Incorrect Arguments:" + Arrays.toString(args));
            System.exit(0);
        }

        try {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
            File outputFile = new File(args[1]);
            PrintWriter out = new PrintWriter(outputFile);

            String operation = in.nextLine();
            boolean type = true;

            RedBlackTree<Integer> integerRedBlackTree = new RedBlackTree<>();
            RedBlackTree<String> stringRedBlackTree = new RedBlackTree<>();
            if (operation.equals("Integer")) {
                type = INTEGER;
            } else if (operation.equals("String")) {
                type = STRING;
            } else {
                out.println("Only works for objects Integers and Strings");
                out.close();
                System.exit(0);
            }

            while (in.hasNextLine()) {
                operation = in.nextLine();

                if (operation.startsWith("Insert:")) {
                    try {
                        if (type == INTEGER) {
                            int num = Integer.parseInt(operation.substring(7));
                            out.println(integerRedBlackTree.insert(num) ? "True" : "False");
                        } else {
                            String str = operation.substring(7);
                            out.println(stringRedBlackTree.insert(str) ? "True" : "False");
                        }
                    } catch (Exception e) {
                        out.println(e.getMessage());
                    }
                } else if (operation.startsWith("Contains:")) {
                    try {
                        if (type == INTEGER) {
                            int num = Integer.parseInt(operation.substring(9));
                            out.println(integerRedBlackTree.contains(num) ? "True" : "False");
                        } else {
                            String str = operation.substring(9);
                            out.println(stringRedBlackTree.contains(str) ? "True" : "False");
                        }
                    } catch (Exception e) {
                        out.println(e.getMessage());
                    }
                } else if (operation.equals("PrintTree")) {
                    if (type == INTEGER) {
                        out.println(integerRedBlackTree.toString());
                    } else {
                        out.println(stringRedBlackTree.toString());
                    }
                } else {
                    out.println("Error in line:" + operation);
                }
            }

            out.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

    }
}
