import java.util.*;

public class Calculator {
    private HashMap<String, Integer> precedence;

    Calculator() {
        precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
        precedence.put("^",3);
        precedence.put("(", 0);
    }

    public void parseExpression(String str) {
        String[] tokens = tokenize(str);
        ArrayList<String> rpnArrayList = RPN(tokens);
        
    }
    // Shunting yard algorithm https://en.wikipedia.org/wiki/Shunting-yard_algorithm
    private ArrayList<String> RPN(String [] tokens)
    {
        ArrayList<String> output = new ArrayList<>(tokens.length);
        Stack<String> opStack = new Stack<>();
        for (String token : tokens) {
            if (Character.isDigit(token.charAt(0))) {
                output.add(token);
            } else if (isOperator(token)) {
                while (opStack.size() != 0 && precedence.get(opStack.peek()) >= precedence.get(token)) {
                    output.add(opStack.pop());
                }
                opStack.push(token);
            }
            if (token.equals("(")) {
                opStack.push(token);
            }
            if (token.equals(")")) {
                while(!opStack.peek().equals("("))
                {
                    output.add(opStack.pop());
                }
                opStack.pop(); //pop "(" from stack
            }
        }
        while(opStack.size() != 0)
        {
            output.add(opStack.pop());
        }
        return output;
    }
    private boolean isOperator(String token)
    {
        return "+-*x^/".contains(token);
    }

    private String[] tokenize(String s) {
        return s.replace(" ", "").split("");
    }
}