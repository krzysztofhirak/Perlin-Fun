import java.util.Random;

public class MyMath {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static int randInt(int min, int max) {
        Random random = new Random();
        return (int) ((random.nextDouble() * (max - min)) + min);
    }
}
