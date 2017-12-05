package symbols;

public interface Numeric {
    Numeric plus(Numeric other);
    Numeric minus(Numeric other);
    Numeric multiply(Numeric other);
    Numeric divide(Numeric other);
}