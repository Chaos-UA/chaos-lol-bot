package chaos_referral_bot;


public class Main {
    public static void main(String[] args) throws Exception {
        System.out.print("java.library.path: ");
        System.out.println(System.getProperty("java.library.path"));
      // new AntiAfkAction(new BotController()).executeAndGetNext();
        new BotController().start();

    }
}
