package symbols;

public class Rational implements Number
{
    private Number num;
    private Number denom;
    Rational(int num, int denom)
    {
        int gcd = gcd(num,denom);
        this.num = new Integer(num/gcd);
        this.denom = new Integer(denom/gcd);
    }
    public int getNumerator()
    {
        return num;
    }
    public int getDenominator()
    {
        return denom;
    }

    //Euclid's algorithm
    private int gcd(int a, int b)
    {
        if(b == 0)
            return a;
        return gcd(b,a%b);
    }
}