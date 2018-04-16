package symbols;

public class Rational {

    private int num;
    private int denom;

    public Rational(int num) {
        this.num = num;
        this.denom = 1;
    }

    public Rational(Rational n)
    {
        this.num = n.getNumerator();
        this.denom = n.getDenominator();
    }

    public Rational(int num, int denom) {
        if (denom == 0) {
            throw new ArithmeticException("Cannot have zero as denominator");
        }
        int gcd = gcd(num, denom);
        this.num = num / gcd;
        this.denom = denom / gcd;
        this.rationalize();
    }

    public Rational(Rational a, Rational b) {
        this.num = a.getNumerator() * b.getDenominator();
        this.denom = a.getDenominator() * b.getNumerator();
        this.rationalize();
    }

    private void rationalize() {
        int gcd = gcd(num, denom);
        this.num /= gcd;
        this.denom /= gcd;
        if (denom < 0) {
            denom *= (-1);
            num *= (-1);
        }
    }

    public Rational plus(Rational other) {
        if (denom == other.getDenominator())
            return new Rational(num + other.getNumerator(), denom);
        else {
            int newDenom = denom * other.getDenominator();
            return new Rational(num * other.getDenominator() + other.getNumerator() * denom, newDenom);
        }
    }

    public Rational minus(Rational other) {
        return this.plus(new Rational(other.getNumerator() * -1, other.getDenominator()));
    }

    public Rational multiply(Rational other) {
        return new Rational(num * other.getNumerator(), denom * other.getDenominator());
    }

    public Rational divide(Rational other) {
        return this.multiply(other.getInverse());
    }

    public Rational exponent(Rational other) {
        if (other.getNumerator() == 0)
            return new Rational(1);
        // else if(other.getNumerator() == 1)
        //     return this.getInverse();

        boolean inv = other.getNumerator() < 0;
        int newNum = (int) Math.pow(num, Math.abs(other.getNumerator()));
        int newDenom = (int) Math.pow(denom, Math.abs(other.getNumerator()));
        if (other.isInteger()) {
            if (inv){
                return new Rational(newDenom, newNum);
            }
            else
                return new Rational(newNum, newDenom);
        } else { 
            if (inv)
                return new Root(new Rational(newDenom,newNum), other.getDenominator());
            else
                return new Root(new Rational(newNum,newDenom), other.getDenominator());
        }
    }

    // public Root root(Rational other){
    //     // return new Root(this,other);
    // }

    public boolean isInteger() {
        return denom == 1;
    }
    public boolean isNegative()
    {
        return num < 0;
    }
    public Rational getInverse() {
        return new Rational(denom, num);
    }

    public int getNumerator() {
        return num;
    }

    public void setNumerator(int n) {
        this.num = n;
    }

    public int getDenominator() {
        return denom;
    }

    public void setDenominator(int n) {
        this.denom = n;
    }

    //Euclid's algorithm
    private int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    public String toString() {
        if (denom != 1)
            return num + "/" + denom;
        return num + "";
    }
}