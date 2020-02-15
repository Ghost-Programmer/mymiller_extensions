package name.mymiller.lang;

import java.math.BigInteger;

public class AdvancedBigInteger {

    public static AdvancedBigInteger ONE = new AdvancedBigInteger(1);
    public static AdvancedBigInteger TWO = new AdvancedBigInteger(2);
    public static AdvancedBigInteger THREE = new AdvancedBigInteger(3);
    public static AdvancedBigInteger FIVE = new AdvancedBigInteger(5);
    public static AdvancedBigInteger ZERO = new AdvancedBigInteger(0);
    private BigInteger integer = BigInteger.ZERO;

    public AdvancedBigInteger(BigInteger value) {
        super();
        this.integer = value;
    }

    public AdvancedBigInteger(Integer value) {
        super();
        this.integer = BigInteger.valueOf(value);
    }

    public AdvancedBigInteger(Long value) {
        super();
        this.integer = BigInteger.valueOf(value);
    }

    public static AdvancedBigInteger[] convert(BigInteger[] array) {
        final AdvancedBigInteger[] newArray = new AdvancedBigInteger[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = new AdvancedBigInteger(array[i]);
        }

        return newArray;
    }

    /**
     * @return
     * @see java.math.BigInteger#abs()
     */
    public AdvancedBigInteger abs() {
        return new AdvancedBigInteger(this.integer.abs());
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#add(java.math.BigInteger)
     */
    public AdvancedBigInteger add(AdvancedBigInteger val) {
        return new AdvancedBigInteger(this.integer.add(val.integer));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#add(java.math.BigInteger)
     */
    public AdvancedBigInteger add(BigInteger val) {
        return new AdvancedBigInteger(this.integer.add(val));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#and(java.math.BigInteger)
     */
    public AdvancedBigInteger and(AdvancedBigInteger val) {
        return new AdvancedBigInteger(this.integer.and(val.integer));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#and(java.math.BigInteger)
     */
    public AdvancedBigInteger and(BigInteger val) {
        return new AdvancedBigInteger(this.integer.and(val));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#andNot(java.math.BigInteger)
     */
    public AdvancedBigInteger andNot(AdvancedBigInteger val) {
        return new AdvancedBigInteger(this.integer.andNot(val.integer));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#andNot(java.math.BigInteger)
     */
    public AdvancedBigInteger andNot(BigInteger val) {
        return new AdvancedBigInteger(this.integer.andNot(val));
    }

    /**
     * @return
     * @see java.math.BigInteger#bitCount()
     */
    public int bitCount() {
        return this.integer.bitCount();
    }

    /**
     * @return
     * @see java.math.BigInteger#bitLength()
     */
    public int bitLength() {
        return this.integer.bitLength();
    }

    /**
     * @return
     * @see java.lang.Number#byteValue()
     */
    public byte byteValue() {
        return this.integer.byteValue();
    }

    /**
     * @return
     * @see java.math.BigInteger#byteValueExact()
     */
    public byte byteValueExact() {
        return this.integer.byteValueExact();
    }

    /**
     * @param n
     * @return
     * @see java.math.BigInteger#clearBit(int)
     */
    public AdvancedBigInteger clearBit(int n) {
        return new AdvancedBigInteger(this.integer.clearBit(n));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#compareTo(java.math.BigInteger)
     */
    public int compareTo(AdvancedBigInteger val) {
        return this.integer.compareTo(val.integer);
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#compareTo(java.math.BigInteger)
     */
    public int compareTo(BigInteger val) {
        return this.integer.compareTo(val);
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#divide(java.math.BigInteger)
     */
    public AdvancedBigInteger divide(AdvancedBigInteger val) {
        return new AdvancedBigInteger(this.integer.divide(val.integer));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#divide(java.math.BigInteger)
     */
    public AdvancedBigInteger divide(BigInteger val) {
        return new AdvancedBigInteger(this.integer.divide(val));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#divideAndRemainder(java.math.BigInteger)
     */
    public AdvancedBigInteger[] divideAndRemainder(AdvancedBigInteger val) {
        return AdvancedBigInteger.convert(this.integer.divideAndRemainder(val.integer));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#divideAndRemainder(java.math.BigInteger)
     */
    public AdvancedBigInteger[] divideAndRemainder(BigInteger val) {
        return AdvancedBigInteger.convert(this.integer.divideAndRemainder(val));
    }

    /**
     * @return
     * @see java.math.BigInteger#doubleValue()
     */
    public double doubleValue() {
        return this.integer.doubleValue();
    }

    /**
     * @param x
     * @return
     * @see java.math.BigInteger#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object x) {
        return this.integer.equals(x);
    }

    /**
     * @param n
     * @return
     * @see java.math.BigInteger#flipBit(int)
     */
    public AdvancedBigInteger flipBit(int n) {
        return new AdvancedBigInteger(this.integer.flipBit(n));
    }

    /**
     * @return
     * @see java.math.BigInteger#floatValue()
     */
    public float floatValue() {
        return this.integer.floatValue();
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#gcd(java.math.BigInteger)
     */
    public AdvancedBigInteger gcd(AdvancedBigInteger val) {
        return new AdvancedBigInteger(this.integer.gcd(val.integer));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#gcd(java.math.BigInteger)
     */
    public AdvancedBigInteger gcd(BigInteger val) {
        return new AdvancedBigInteger(this.integer.gcd(val));
    }

    /**
     * @return
     * @see java.math.BigInteger#getLowestSetBit()
     */
    public int getLowestSetBit() {
        return this.integer.getLowestSetBit();
    }

    /**
     * @return
     * @see java.math.BigInteger#hashCode()
     */
    @Override
    public int hashCode() {
        return this.integer.hashCode();
    }

    /**
     * @return
     * @see java.math.BigInteger#intValue()
     */
    public int intValue() {
        return this.integer.intValue();
    }

    /**
     * @return
     * @see java.math.BigInteger#intValueExact()
     */
    public int intValueExact() {
        return this.integer.intValueExact();
    }

    /**
     * Determine if a number is prime
     *
     * @return boolean indicating if the number is prime.
     */
    public boolean isPrime() {

        if (this.equals(AdvancedBigInteger.TWO) || this.equals(AdvancedBigInteger.THREE)
                || this.equals(AdvancedBigInteger.FIVE)) {
            return true;
        }
        if (this.equals(AdvancedBigInteger.ONE)) {
            return false;
        }

        final String string = this.toString(10);

        final char lastDigit = string.charAt(string.length() - 1);

        if ((lastDigit == '2') || (lastDigit == '4') || (lastDigit == '5') || (lastDigit == '6') || (lastDigit == '8')
                || (lastDigit == '0')) {
            return false;
        }

        AdvancedBigInteger sum = new AdvancedBigInteger(0);

        for (int i = 0; i < string.length(); i++) {
            final long value = string.charAt(i) - '0';
            sum = sum.add(BigInteger.valueOf(value));
        }

        if (sum.remainder(AdvancedBigInteger.THREE).equals(AdvancedBigInteger.ZERO)) {
            return false;
        }

        for (AdvancedBigInteger i = new AdvancedBigInteger(7); i.multiply(i).compareTo(this) < 1; i = i
                .add(AdvancedBigInteger.TWO)) {
            if (AdvancedBigInteger.ZERO.equals(this.mod(i))) {
                return false;
            }
        }
        return true;

    }

    /**
     * @param certainty
     * @return
     * @see java.math.BigInteger#isProbablePrime(int)
     */
    public boolean isProbablePrime(int certainty) {
        return this.integer.isProbablePrime(certainty);
    }

    /**
     * @return
     * @see java.math.BigInteger#longValue()
     */
    public long longValue() {
        return this.integer.longValue();
    }

    /**
     * @return
     * @see java.math.BigInteger#longValueExact()
     */
    public long longValueExact() {
        return this.integer.longValueExact();
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#max(java.math.BigInteger)
     */
    public AdvancedBigInteger max(AdvancedBigInteger val) {
        return new AdvancedBigInteger(this.integer.max(val.integer));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#max(java.math.BigInteger)
     */
    public AdvancedBigInteger max(BigInteger val) {
        return new AdvancedBigInteger(this.integer.max(val));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#min(java.math.BigInteger)
     */
    public AdvancedBigInteger min(AdvancedBigInteger val) {
        return new AdvancedBigInteger(this.integer.min(val.integer));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#min(java.math.BigInteger)
     */
    public AdvancedBigInteger min(BigInteger val) {
        return new AdvancedBigInteger(this.integer.min(val));
    }

    /**
     * @param m
     * @return
     * @see java.math.BigInteger#mod(java.math.BigInteger)
     */
    public AdvancedBigInteger mod(AdvancedBigInteger m) {
        return new AdvancedBigInteger(this.integer.mod(m.integer));
    }

    /**
     * @param m
     * @return
     * @see java.math.BigInteger#mod(java.math.BigInteger)
     */
    public AdvancedBigInteger mod(BigInteger m) {
        return new AdvancedBigInteger(this.integer.mod(m));
    }

    /**
     * @param m
     * @return
     * @see java.math.BigInteger#modInverse(java.math.BigInteger)
     */
    public AdvancedBigInteger modInverse(AdvancedBigInteger m) {
        return new AdvancedBigInteger(this.integer.modInverse(m.integer));
    }

    /**
     * @param m
     * @return
     * @see java.math.BigInteger#modInverse(java.math.BigInteger)
     */
    public AdvancedBigInteger modInverse(BigInteger m) {
        return new AdvancedBigInteger(this.integer.modInverse(m));
    }

    /**
     * @param exponent
     * @param m
     * @return
     * @see java.math.BigInteger#modPow(java.math.BigInteger, java.math.BigInteger)
     */
    public AdvancedBigInteger modPow(AdvancedBigInteger exponent, AdvancedBigInteger m) {
        return new AdvancedBigInteger(this.integer.modPow(exponent.integer, m.integer));
    }

    /**
     * @param exponent
     * @param m
     * @return
     * @see java.math.BigInteger#modPow(java.math.BigInteger, java.math.BigInteger)
     */
    public AdvancedBigInteger modPow(BigInteger exponent, BigInteger m) {
        return new AdvancedBigInteger(this.integer.modPow(exponent, m));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#multiply(java.math.BigInteger)
     */
    public AdvancedBigInteger multiply(AdvancedBigInteger val) {
        return new AdvancedBigInteger(this.integer.multiply(val.integer));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#multiply(java.math.BigInteger)
     */
    public AdvancedBigInteger multiply(BigInteger val) {
        return new AdvancedBigInteger(this.integer.multiply(val));
    }

    /**
     * @return
     * @see java.math.BigInteger#negate()
     */
    public AdvancedBigInteger negate() {
        return new AdvancedBigInteger(this.integer.negate());
    }

    /**
     * @return
     * @see java.math.BigInteger#nextProbablePrime()
     */
    public AdvancedBigInteger nextProbablePrime() {
        return new AdvancedBigInteger(this.integer.nextProbablePrime());
    }

    /**
     * @return
     * @see java.math.BigInteger#not()
     */
    public AdvancedBigInteger not() {
        return new AdvancedBigInteger(this.integer.not());
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#or(java.math.BigInteger)
     */
    public AdvancedBigInteger or(AdvancedBigInteger val) {
        return new AdvancedBigInteger(this.integer.or(val.integer));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#or(java.math.BigInteger)
     */
    public AdvancedBigInteger or(BigInteger val) {
        return new AdvancedBigInteger(this.integer.or(val));
    }

    /**
     * @param exponent
     * @return
     * @see java.math.BigInteger#pow(int)
     */
    public AdvancedBigInteger pow(int exponent) {
        return new AdvancedBigInteger(this.integer.pow(exponent));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#remainder(java.math.BigInteger)
     */
    public AdvancedBigInteger remainder(AdvancedBigInteger val) {
        return new AdvancedBigInteger(this.integer.remainder(val.integer));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#remainder(java.math.BigInteger)
     */
    public AdvancedBigInteger remainder(BigInteger val) {
        return new AdvancedBigInteger(this.integer.remainder(val));
    }

    /**
     * @param n
     * @return
     * @see java.math.BigInteger#setBit(int)
     */
    public AdvancedBigInteger setBit(int n) {
        return new AdvancedBigInteger(this.integer.setBit(n));
    }

    /**
     * @param n
     * @return
     * @see java.math.BigInteger#shiftLeft(int)
     */
    public AdvancedBigInteger shiftLeft(int n) {
        return new AdvancedBigInteger(this.integer.shiftLeft(n));
    }

    /**
     * @param n
     * @return
     * @see java.math.BigInteger#shiftRight(int)
     */
    public AdvancedBigInteger shiftRight(int n) {
        return new AdvancedBigInteger(this.integer.shiftRight(n));
    }

    /**
     * @return
     * @see java.lang.Number#shortValue()
     */
    public short shortValue() {
        return this.integer.shortValue();
    }

    /**
     * @return
     * @see java.math.BigInteger#shortValueExact()
     */
    public short shortValueExact() {
        return this.integer.shortValueExact();
    }

    /**
     * @return
     * @see java.math.BigInteger#signum()
     */
    public int signum() {
        return this.integer.signum();
    }

    /**
     * @return
     * @see java.math.BigInteger#sqrt()
     */
    public AdvancedBigInteger sqrt() {
        return new AdvancedBigInteger(this.integer.sqrt());
    }

    /**
     * @return
     * @see java.math.BigInteger#sqrtAndRemainder()
     */
    public AdvancedBigInteger[] sqrtAndRemainder() {
        return AdvancedBigInteger.convert(this.integer.sqrtAndRemainder());
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#subtract(java.math.BigInteger)
     */
    public AdvancedBigInteger subtract(AdvancedBigInteger val) {
        return new AdvancedBigInteger(this.integer.subtract(val.integer));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#subtract(java.math.BigInteger)
     */
    public AdvancedBigInteger subtract(BigInteger val) {
        return new AdvancedBigInteger(this.integer.subtract(val));
    }

    /**
     * @param n
     * @return
     * @see java.math.BigInteger#testBit(int)
     */
    public boolean testBit(int n) {
        return this.integer.testBit(n);
    }

    /**
     * @return
     * @see java.math.BigInteger#toByteArray()
     */
    public byte[] toByteArray() {
        return this.integer.toByteArray();
    }

    /**
     * @return
     * @see java.math.BigInteger#toString()
     */
    @Override
    public String toString() {
        return this.integer.toString();
    }

    /**
     * @param radix
     * @return
     * @see java.math.BigInteger#toString(int)
     */
    public String toString(int radix) {
        return this.integer.toString(radix);
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#xor(java.math.BigInteger)
     */
    public AdvancedBigInteger xor(AdvancedBigInteger val) {
        return new AdvancedBigInteger(this.integer.xor(val.integer));
    }

    /**
     * @param val
     * @return
     * @see java.math.BigInteger#xor(java.math.BigInteger)
     */
    public AdvancedBigInteger xor(BigInteger val) {
        return new AdvancedBigInteger(this.integer.xor(val));
    }
}
