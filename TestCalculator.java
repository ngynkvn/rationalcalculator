public class TestCalculator
{
    public static void main(String [] args)
    {
        Calculator calc = new Calculator();
        String exp = "3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3";
        calc.parseExpression(exp);
    }
}