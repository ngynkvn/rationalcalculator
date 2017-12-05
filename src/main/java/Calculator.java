import java.util.*;
import symbols.*;

public class Calculator {
    private HashMap<String, Integer> precedence;
    private boolean verbose = false;
    Calculator(String flag) {
        precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("x", 2);
        precedence.put("/", 2);
        precedence.put("^", 3);
        precedence.put("rt", 3);
        precedence.put("(", 0);

        if(flag != null)
            verbose = true;
    }

    public void parseExpression(String str) {
        String[] tokens = tokenize(str);
        // for(String x : tokens)
        //     System.out.printf("[%s], ",x);
        ArrayList<String> rpnArrayList = RPN(tokens);
        evaluate(rpnArrayList);
    }

    private void evaluate(ArrayList<String> str) {
        Stack<Rational> stack = new Stack<>();
        for (String token : str) {
            if (isOperator(token)) {
                Rational a = stack.pop();
                Rational b = stack.pop();
                try {
                    stack.push(doOperation(b, a, token));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                stack.push(new Rational(Integer.parseInt((token)), 1));
            }
        }
        System.out.println(stack.pop());
    }

    private Rational doOperation(Rational a, Rational b, String op) throws Exception {
        // if(verbose)
        //     System.out.println(a + " " + op + " " + b);
        switch (op) {
        case "+":
            return a.plus(b);
        case "-":
            return a.minus(b);
        case "/":
            return a.divide(b);
        case "*":
        case "x":
            return a.multiply(b);
        case "^":
            return a.exponent(b);
        default:
            throw new Exception("Bad");
        }
    }

    // Shunting yard algorithm https://en.wikipedia.org/wiki/Shunting-yard_algorithm
    private ArrayList<String> RPN(String[] tokens) {
        ArrayList<String> output = new ArrayList<>(tokens.length);
        Stack<String> opStack = new Stack<>();
        for (String token : tokens) {
            // System.out.printf("[%s]\n",token);
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
        // if(verbose){
        //     for(String x : output) System.out.print(x + " ");
        //     System.out.println();
        // }
        return output;
    }

    private boolean isOperator(String token) {
        return "+-*x^/".contains(token);
    }

    private String[] tokenize(String s) {
        String ops = "[+*x^/()]";
        s = s.replace(ops+"|(-)(?<!\\( ", " $& ");
        return s.trim().split("(\\s+)");
    }

    public static void main(String [] args)
    {
        String flagVerbose;
        try{
            flagVerbose = args[0];
        } catch (Exception e){
            flagVerbose = null;
        }
        Calculator c = new Calculator(flagVerbose);
        String [] exps = {"2 + 3 * 4 - 5 ^ 2",
                         "2 ^ 3 + 4 * 5 - 2",
                         "( 2 + 3 ) * ( 4 - 5 ) ^ 2 ",
                         "1 / 3 + 1 / 4 + 1 / 12",
                         "105 / 1344","8 ^ ( -4 / 3 ) * 4"};
        for(String exp : exps)
        {
            System.out.printf("[%s] evaluates to ",exp);
            c.parseExpression(exp);
        }
    }
}