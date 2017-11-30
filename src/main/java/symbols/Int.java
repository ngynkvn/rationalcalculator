package symbols;

public class Int implements Num {
    private int n;

    public Int(String s) {
        try {
            n = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number type, was expecting an integer");
        }
    }

    public Int(int num) {
        n = num;
    }

    public String toString() {
        return Integer.toString(n);
    }
}