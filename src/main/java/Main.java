import java.util.Scanner;

public class Main
{
    private static final Scanner in = new Scanner(System.in);
    private static final Calculator calc = new Calculator(null);
    public static void main(String [] args)
    {
        while(true)
        {
            printMenu();
            int option = getInput();
            switch(option)
            {
                case 1:
                calc.parseExpression(getExpression());
                break;
                case 2:
                break;
                case 3:
                break;
                case 4:
                in.close();
                return;
            }
        }
    }
    private static void printMenu()
    {
        System.out.println("1. Compute new expression\n2. Help\n3. Review previous expressions and answers\n4. Quit");
    }
    private static String getExpression()
    {
        System.out.print("Input expression: ");
        String input = in.nextLine();
        return input;
    }
    private static int getInput()
    {
        while(true)
        {
            try {
                int option = in.nextInt();
                in.nextLine();
                if(option > 0 && option < 5)
                    return option;
                else
                    throw new Exception();
            } catch(Exception e) {
                System.out.println("Invalid option");
            }
        }
    }
}