public class TestCalculator
{
    public static void main(String [] args)
    {
        Calculator calc = new Calculator();
        String exp = "2 + 2";
        calc.parseExpression(exp);
    }
}