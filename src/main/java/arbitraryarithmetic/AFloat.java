package arbitraryarithmetic;

public class AFloat {
    private AInteger rawValue;
    private int decidigs;
        public AFloat() {
        this.rawValue = new AInteger("0");
        this.decidigs = 1;
    }



    public AFloat(String s) {
        // here we take out the "." from the string and perfomr operations, for that we split the string at "."and combine them again
        if (!s.contains(".")) {
            // case for ".", make it an integer an move on
            rawValue = new AInteger(s);
            decidigs = 0;
        } else {
            
            boolean isNegative = s.startsWith("-");
            String[] parts = s.replace("-", "").split("\\.");
            rawValue = new AInteger(parts[0] + parts[1]);
            if (isNegative) rawValue.isNeg = true;
            decidigs = parts[1].length();
        }
    }



    public AFloat(AInteger raw, int decidigs) {
        this.rawValue = new AInteger(raw);
        this.decidigs = decidigs;
    }
    public static AFloat parse(String s) {
        return new AFloat(s);
    }

    public AFloat add(AFloat other) {
        // 
        int maxdecidigs = Math.max(this.decidigs, other.decidigs);
        AInteger newThis = addZeroes(this.rawValue, this.decidigs, maxdecidigs);
        AInteger newOther = addZeroes(other.rawValue, other.decidigs, maxdecidigs);
        AInteger sum = newThis.add(newOther);
        return new AFloat(sum, maxdecidigs);
    }



    public AFloat subtract(AFloat other) {
        int maxdecidigs = Math.max(this.decidigs, other.decidigs);
        AInteger newThis = addZeroes(this.rawValue, this.decidigs, maxdecidigs);
        AInteger newOther = addZeroes(other.rawValue, other.decidigs, maxdecidigs);
        AInteger diff = newThis.subtract(newOther);
        return new AFloat(diff, maxdecidigs);
    }
    public AFloat multiply(AFloat other) {
        AInteger product = this.rawValue.multiply(other.rawValue);
        return new AFloat(product, this.decidigs + other.decidigs);
    }



    public AFloat divide(AFloat other) {
        AInteger newDividend = addZeroes(this.rawValue, this.decidigs, this.decidigs + 30);
        AInteger result = newDividend.divide(other.rawValue);
        return new AFloat(result, 30);
    }


    private AInteger addZeroes(AInteger num, int currentdigs, int targetdigs) {
        int zerosToAdd = targetdigs - currentdigs;
        String newVal = num.value + "0".repeat(zerosToAdd);
        AInteger result = new AInteger(newVal);
        result.isNeg = num.isNeg;
        return result;
    }



    @Override
    public String toString() {
        String raw = rawValue.value;
        boolean neg = rawValue.isNeg;
        if (decidigs == 0) return (neg ? "-" : "") + raw;

        while (raw.length() <= decidigs) raw = "0" + raw;

        String intPart = raw.substring(0, raw.length() - decidigs);
        String fracPart = raw.substring(raw.length() - decidigs);

        return (neg ? "-" : "") + intPart + "." + fracPart.replaceFirst("0+$", "");
    }
}
