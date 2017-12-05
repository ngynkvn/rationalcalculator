import java.util.*;
import symbols.*;

public class Calculator {
    private HashMap<String, Integer> precedence;
    Calculator() {
        precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("x", 2);
        precedence.put("/", 2);
        precedence.put("^", 3);
        precedence.put("rt",3);
        precedence.put("(", 0);
    }

    public void parseExpression(String str) {
        String[] tokens = tokenize(str);
        ArrayList<String> rpnArrayList = RPN(tokens);
        evaluate(rpnArrayList);
    }

    private void evaluate(ArrayList<String> str) {
        Stack<Num> stack = new Stack<>();
        for (String token : str) {
            if (isOperator(token)) {
                Num a = stack.pop();
                Num b = stack.pop();
                stack.push(doOperation(a, b, token));
            } else {
                stack.push(new Int(token));
            }
        }
        System.out.println(stack.size() == 1);
    }

    private Num doOperation(Num a, Num b, String op) {
        return new Int(1);
    }

    // Shunting yard algorithm https://en.wikipedia.org/wiki/Shunting-yard_algorithm
    private ArrayList<String> RPN(String[] tokens) {
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
                while (!opStack.peek().equals("(")) {
                    output.add(opStack.pop());
                }
                opStack.pop(); //pop "(" from stack
            }
        }
        while (opStack.size() != 0) {
            output.add(opStack.pop());
        }
        return output;
    }

    private boolean isOperator(String token) {
        return "+-*x^/".contains(token);
    }

    private String[] tokenize(String s) {
        String [] ops = "+-*x^/()".split("");
        s = s.replace(" ","");
        for(int i = 0; i < ops.length; i++)
        {
            s = s.replace(ops[i], " "+ops[i]+" ");
        }
        return s.split(" ");
    }
}