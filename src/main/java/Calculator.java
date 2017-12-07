import java.util.*;
import symbols.*;

public class Calculator {
    private HashMap<String, Integer> precedence;
    private Rational prev;
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
        prev = new Rational(0);
    }

    public Rational parseExpression(String str) {
        String[] tokens = tokenize(str);
        // for(String x : tokens){
        //     System.out.printf("%s, ",x);
        // }System.out.println();
        ArrayList<String> rpnArrayList = RPN(tokens);
        return evaluate(rpnArrayList);
    }

    private Rational evaluate(ArrayList<String> str) {
        Stack<Rational> stack = new Stack<>();
        for (String token : str) {
            // System.out.printf("[%s], ",token);
            if (isOperator(token)) {
                Rational a = stack.pop();
                Rational b = stack.pop();
                try {
                    stack.push(doOperation(b, a, token));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                stack.push(new Rational(Integer.parseInt((token))));
            }
        }
        // System.out.println(stack.peek());
        return prev = stack.pop();
    }

    private Rational doOperation(Rational a, Rational b, String op) throws Exception {
        System.out.printf("%s %s %s\n",a,op,b);
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
        case "rt":
            if(b.isNegative() && a.getNumerator() % 2 == 0){
                throw new Exception("Can't take the square root of a negative number!");
            }
            return b.exponent(a.getInverse());
        default:
            throw new Exception("Bad this should never happen");
        }
    }

    // Shunting yard algorithm https://en.wikipedia.org/wiki/Shunting-yard_algorithm
    private ArrayList<String> RPN(String[] tokens) {
        ArrayList<String> output = new ArrayList<>(tokens.length);
        Stack<String> opStack = new Stack<>();
        for (String token : tokens) {
            if (token.matches("(-?\\d+)")) { //matches pos / neg number
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
        return "+-*x^/rt".contains(token);
    }

    private String[] tokenize(String s) {
        s = s.replaceAll("ans", prev.toString());
        s = s.replaceAll("[+*x^/()]|(-)(?<!)\\( |rt", " $0 "); //finds operators and adds spacing around them.
        return s.trim().split("(\\s+)");
    }

    public static void main(String[] args) {
        String flagVerbose;
        try {
            flagVerbose = args[0];
        } catch (Exception e) {
            flagVerbose = null;
        }
        Calculator c = new Calculator(flagVerbose);
        String[] exps = { "2 + 3 * 4 - 5 ^ 2", "2 ^ 3 + 4 * 5 - 2", "( 2 + 3 ) * ( 4 - 5 ) ^ 2",
                "1 / 3 + 1 / 4 + 1 / 12", "105 / 1344", "2 + -1 + 2", "10 ^ 3", "8 ^ ( -4 / 3 ) * 4",
                "(6 / 2) rt (5 + 3)", "4 rt 16", "3 rt -8", "3 rt 2", "3 rt 108 * 3 rt 16","3/10","ans/2", "2 rt 2 * 2 rt 2", "(-1 / 5) ^ -1","-4 ^ 3 - ans"};
        String[] ans = {"-11","26","5","2/3","5/64","3","1000","1/4","2","2","-2","3 rt 2","12","3/10","3/20","2", "-5", "-59"};
        //exps not currently supported:
        /**
         * 
        * 108 log 3 [log3 of 108] == 3 + 2 * 3 log 2
        *  3 rt -8 == -2
        *  ans ^ 2 [where ans is 3/10] == 9 / 100
         **/
        int cnt = 0;
        for (String exp : exps) {
            Rational result = c.parseExpression(exp);
            System.out.println("Evaluating: "+exp);
            System.out.printf("[%s] evaluates to %s [%s]\n", exp, result, ans[cnt++].equals(result.toString()) ? "PASS" : "FAIL");
        }
        // c.parseExpression("3/10");
        // System.out.println(c.parseExpression("ans / 2"));
    }
}