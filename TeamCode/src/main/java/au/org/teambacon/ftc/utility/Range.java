package au.org.teambacon.ftc.utility;

public class Range {
    public static final float clip(float number, float min, float max) {
        if (number < min)
            return min;
        else if (number > max)
            return max;

        return number;
    }

    public static final double clip(double number, double min, double max) {
        if (number < min)
            return min;
        else if (number > max)
            return max;

        return number;
    }
}
