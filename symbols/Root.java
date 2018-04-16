package symbols;

public class Root extends Rational {
    private int nth;
    private int coefficient;
    public Root(int num, int nth) {
        super(num);
        this.nth = nth;
        this.simplify();
    }

    public Root(Rational num, int nth)
    {
        super(num);
        this.nth = nth;
        this.simplify();
    }

    // https://en.wikipedia.org/wiki/Nth_root_algorithm
    private void simplify()
    {
        double epsilon = 0.1;
        double sqN = nthAlgo(this.getNumerator());
        double sqD = nthAlgo(this.getDenominator());
        if(sqN%1 < epsilon && sqD%1 < epsilon) {
            this.setNumerator((int)sqN);
            this.setDenominator((int)sqD);
            this.nth = 1;
            this.coefficient = 1;
        }
    }

    public Rational multiply(Rational other)
    {
        return new Root(super.multiply(other), nth);
    }

    private double nthAlgo(int n)
    {
        double x = 1;
        double epsilon = 0.1;
        double dx = (1.0/nth)*(n/(Math.pow(x,nth-1)) - x);
        while(Math.abs(dx) > epsilon)
        {
            x += dx;
            dx = (1.0/nth)*(n/(Math.pow(x,nth-1)) - x);
        }
        
            return x;
            // this.setNumerator((int)x);
            // this.nth = 1;
            // this.coefficient = 1;
    }

    public boolean isInteger()
    {
        return nth == 1 && this.getDenominator() == 1;
    }

    @Override
    public String toString()
    {
        if (nth == 1)
            return super.toString();
        return nth + " rt " + super.toString();
    }

    public static void main(String [] args)
    {
        System.out.println(new Root(8,3).getNumerator());
        System.out.println(new Root(4,2).getNumerator());
        System.out.println(new Root(16,2).getNumerator());
        System.out.println(new Root(3,2).getNumerator());
        Rational a = new Rational(8);
        Rational b = new Rational(-4,3);
        System.out.println(a.exponent(b));
        a = new Rational(4);
        b = new Rational(1,2);
        System.out.println(new Root(2,3));
        System.out.println(a.exponent(b));
    }
}