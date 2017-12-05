package symbols;

public class Rational implements Num {
    private int num;
    private int denom;

    public Rational(int num, int denom) {
        if(denom == 0)
        {
            throw new ArithmeticException("Cannot have zero as denominator");
        }
        int gcd = gcd(num, denom);
        this.num = num / gcd;
        this.denom = denom / gcd;
    }

    public int getNumerator() {
        return num;
    }

    public int getDenominator() {
        return denom;
    }

    //Euclid's algorithm
    private int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    public String toString() {
        return num + "/" + denom;
    }
}