package arbitraryarithmetic;

public class AFloat {
    private AInteger rawValue;
    private int scale;
        public AFloat() {
        this.rawValue = new AInteger("0");
        this.scale = 1;
    }

    public AFloat(String s) {
        if (!s.contains(".")) {
            rawValue = new AInteger(s);
            scale = 0;
        } else {
            boolean isNegative = s.startsWith("-");
            String[] parts = s.replace("-", "").split("\\.");
            rawValue = new AInteger(parts[0] + parts[1]);
            if (isNegative) rawValue.isNeg = true;
            scale = parts[1].length();
        }
    }

    public AFloat(AInteger raw, int scale) {
        this.rawValue = new AInteger(raw);
        this.scale = scale;
    }

    public static AFloat parse(String s) {
        return new AFloat(s);
    }

    public AFloat add(AFloat other) {
        int maxScale = Math.max(this.scale, other.scale);
        AInteger scaledThis = scaleUp(this.rawValue, this.scale, maxScale);
        AInteger scaledOther = scaleUp(other.rawValue, other.scale, maxScale);
        AInteger sum = scaledThis.add(scaledOther);
        return new AFloat(sum, maxScale);
    }

    public AFloat subtract(AFloat other) {
        int maxScale = Math.max(this.scale, other.scale);
        AInteger scaledThis = scaleUp(this.rawValue, this.scale, maxScale);
        AInteger scaledOther = scaleUp(other.rawValue, other.scale, maxScale);
        AInteger diff = scaledThis.subtract(scaledOther);
        return new AFloat(diff, maxScale);
    }

    public AFloat multiply(AFloat other) {
        AInteger product = this.rawValue.multiply(other.rawValue);
        return new AFloat(product, this.scale + other.scale);
    }

    public AFloat divide(AFloat other) {
        AInteger scaledDividend = scaleUp(this.rawValue, this.scale, this.scale + 30);
        AInteger result = scaledDividend.divide(other.rawValue);
        return new AFloat(result, 30);
    }

    private AInteger scaleUp(AInteger num, int currentScale, int targetScale) {
        int zerosToAdd = targetScale - currentScale;
        String newVal = num.value + "0".repeat(zerosToAdd);
        AInteger result = new AInteger(newVal);
        result.isNeg = num.isNeg;
        return result;
    }

    @Override
    public String toString() {
        String raw = rawValue.value;
        boolean neg = rawValue.isNeg;
        if (scale == 0) return (neg ? "-" : "") + raw;

        while (raw.length() <= scale) raw = "0" + raw;

        String intPart = raw.substring(0, raw.length() - scale);
        String fracPart = raw.substring(raw.length() - scale);

        return (neg ? "-" : "") + intPart + "." + fracPart.replaceFirst("0+$", "");
    }
}
