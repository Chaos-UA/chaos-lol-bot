package chaos_referral_bot;

import java.util.Random;

public class CommonUtil {
    public static int getRandom(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
