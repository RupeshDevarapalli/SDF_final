package arbitraryarithmetic;

public class AInteger {
    protected String value;
    protected boolean isNeg;

    public AInteger() {
        this("0");
    }

    public AInteger(String s) {
        if (s.startsWith("-")) {
            isNeg = true;
            value = s.substring(1);
        } else {
            isNeg = false;
            value = s;
        }
        normalize();
    }

    public AInteger(AInteger other) {
        this.value = other.value;
        this.isNeg = other.isNeg;
    }

    public static AInteger parse(String s) {
        return new AInteger(s);
    }
    //this just removes the unnecessary zeroes in front of the number.
    public void normalize() {
        value = value.replaceFirst("^0+(?!$)", "");
        if (value.equals("0")) isNeg = false;
    }

    public boolean isZero() {
        return value.equals("0");
    }
    //comparing which of the 2 inputs is greater.
    private int compare(AInteger other) {
        if (value.length() != other.value.length())
            return value.length() - other.value.length();
        return value.compareTo(other.value);
    }
    //making the input positive(toggling the boolean "isNeg")
    private AInteger makePositive() {
        AInteger pos = new AInteger(this);
        pos.isNeg = false;
        return pos;
    }
    //addition 
    public AInteger add(AInteger other) {
        if (this.isNeg == other.isNeg) { 
            //if both the num are of same sign then add directly and take sign of one of them
            AInteger result = new AInteger(addStrings(this.value, other.value));
            result.isNeg = this.isNeg;
            return result;
        } else {
            if (this.makePositive().compare(other.makePositive()) >= 0) { 
                // iff both num are diff sign, find the diff of abs value and take the sign of greater number
                AInteger result = new AInteger(subtractStrings(this.value, other.value));
                result.isNeg = this.isNeg;
                return result;  
            } else {
                AInteger result = new AInteger(subtractStrings(other.value, this.value));
                result.isNeg = other.isNeg;
                return result;
            }
        }
    }
    //subtraction
    public AInteger subtract(AInteger other) { 
        // change the sign of the second num and add them
        AInteger negOther = new AInteger(other);
        negOther.isNeg = !negOther.isNeg;
        return this.add(negOther);
    }

    public AInteger multiply(AInteger other) {
        // multiply and if diff signs for num then neg, or else positive
        AInteger result = new AInteger(multiplyStrings(this.value, other.value));
        result.isNeg = this.isNeg != other.isNeg;
        return result;
    }

    public AInteger divide(AInteger other) {
        if (other.isZero()) throw new ArithmeticException("Division by zero"); //divison by zero exception
        //same as multiply
        AInteger result = new AInteger(divideStrings(this.value, other.value));
        result.isNeg = this.isNeg != other.isNeg;
        return result;
    }

    private String addStrings(String a, String b) {
        // go through all the digits in a and b from the back, add them(and carry) append unit digit of the sum and carry the tens digit 
        StringBuilder sb = new StringBuilder();
        int carry = 0, i = a.length() - 1, j = b.length() - 1;
        while (i >= 0 || j >= 0 || carry > 0) {
            int sum = carry;
            if (i >= 0) sum += a.charAt(i--) - '0';
            if (j >= 0) sum += b.charAt(j--) - '0';
            sb.append(sum % 10);
            carry = sum / 10;
        }
        return sb.reverse().toString();
    }

    private String subtractStrings(String a, String b) {
        // go throgh all the digits in a and b, from the back, subtract b-dig(and borrow) from a-dig , if less than 0, add 10 and make borrow one, then append the subtraction
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1, borrow = 0;
        while (i >= 0) {
            int digitA = a.charAt(i--) - '0';
            int digitB = (j >= 0) ? b.charAt(j--) - '0' : 0;
            int diff = digitA - digitB - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            sb.append(diff);
        }
        return sb.reverse().toString().replaceFirst("^0+(?!$)", "");
    }

    private String multiplyStrings(String a, String b) {
        //make a int array, with the combined length of both nums, go through all the dis of a and b, multiply them(and add carry), append the unit place and carry the tens place
        int[] result = new int[a.length() + b.length()];
        for (int i = a.length() - 1; i >= 0; i--) {
            for (int j = b.length() - 1; j >= 0; j--) {
                int mul = (a.charAt(i) - '0') * (b.charAt(j) - '0');
                int sum = result[i + j + 1] + mul;
                result[i + j + 1] = sum % 10;
                result[i + j] += sum / 10;
            }
        }
        // this n=bit code here converts the int array to string
        StringBuilder sb = new StringBuilder();
        for (int r : result)
            if (!(sb.length() == 0 && r == 0)) sb.append(r);
        return sb.length() == 0 ? "0" : sb.toString();
    }

    private String divideStrings(String a, String b) {
        StringBuilder result = new StringBuilder();
        AInteger dividend = new AInteger("0");
        AInteger divisor = new AInteger(b);
        for (int i = 0; i < a.length(); i++) {
            dividend = new AInteger(dividend.value + a.charAt(i));
            dividend.normalize();
            // subtract denominator from numerator and add 1 to count,(do this until denominator > numerator) return count
            int count = 0;
            while (dividend.compare(divisor) >= 0) {
                dividend = dividend.subtract(divisor);
                count++;
            }
            result.append(count);
        }
        return result.toString().replaceFirst("^0+(?!$)", "");
    }

    @Override
    // this bit of code, checks for the sign and keeps it accordingly
    public String toString() {
        return (isNeg && !value.equals("0") ? "-" : "") + value;
    }
}
