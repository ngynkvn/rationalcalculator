import java.util.ArrayList;
import java.util.Scanner;
import symbols.*;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final Calculator calc = new Calculator(null);
    private static ArrayList<String> prev = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int option = getInput();
            System.out.println();
            switch (option) {
            case 1:
                try {
                    String exp = getExpression();
                    System.out.println();
                    Rational ans = calc.parseExpression(exp);
                    System.out.printf("Your answer is: %s\n\n", ans);
                    prev.add(String.format("%s = %s", exp, ans));
                    if (prev.size() > 10)
                        prev.remove(0);
                } catch (Exception e) {
                }
                break;
            case 2:
                printHelp();
                break;
            case 3:
                printPrev();
                break;
            case 4:
                in.close();
                return;
            }
        }
    }

    private static void printMenu() {
        System.out.printf("1. %s\n2. %s\n3. %s\n4. %s\n\nInput: ", "Compute new expression", "Help",
                "Review previous expressions and answers", "Quit");
    }

    private static void printHelp() {
        System.out.printf("Just figure it out.\n\n");
    }

    private static void printPrev() {
        int c = 1;
        for (String x : prev)
            System.out.printf("%d. %s\n", c, x);
        System.out.println();
    }

    private static String getExpression() {
        System.out.print("Input expression: ");
        String input = in.nextLine().trim();
        return input;
    }

    private static int getInput() {
        while (true) {
            try {
                int option = in.nextInt();
                in.nextLine();
                if (option > 0 && option < 5)
                    return option;
                else
                    throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid option");
                printMenu();
                in.nextLine();
            }
        }
    }
}