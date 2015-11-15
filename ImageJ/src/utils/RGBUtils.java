package utils;

/**
 * Created by benjamin on 15.11.2015.
 */
public class RGBUtils {

    public static int clamp(int v) {
        if (v > 255) return 255;
        else if (v < 0) return 0;
        return v;
    }
}
