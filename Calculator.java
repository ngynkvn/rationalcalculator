import java.util.*;

public class Calculator
{

    public void parseExpression(String str)
    {
        String [] tokens = str.replace(" ","").split("") //create array of tokens
        ArrayList<String> output = new ArrayList<>(tokens.length);
        Stack<String> operators = new Stack<>();
        //Shunting yard algorithm https://en.wikipedia.org/wiki/Shunting-yard_algorithm
        for (String token : tokens)
        {
            if (Character.isDigit(token))
            {
                output.add(token);
            } else if () {
            }
        }
    }
    public String result()
    {
        return "ok";
    }
}