package chaos_referral_bot.action;


import chaos_referral_bot.BotController;
import chaos_referral_bot.BotUtil;
import chaos_referral_bot.LoLUtil;
import chaos_referral_bot.User;
import chaos_referral_bot.resources.Resources;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;

public class ReadUserAction extends AbstractBotAction {

    public ReadUserAction(BotController botController) {
        super(botController);
    }
    private final ImageTarget targetMiniMapTop = new ImageTarget(Resources.getUrl(Resources.getInGameMiniMapTopImagePath()));

    @Override
    public AbstractBotAction executeAndGetNext() throws Exception {
        User user = BotUtil.getFirstUser();
        if (user == null) {
            log("There is no more users to train. Program finished");
            LoLUtil.forceKillLeagueOfLegends();
            System.exit(0);
        }
        log(String.format("%s to train:", user));
        getBotController().setUser(user);
        if (LoLUtil.isGameRunning()) {
            int secsToAltTab = 10;
            System.out.println(String.format("You have %s seconds to alt+tab to the game to continue", secsToAltTab));
            long startedAt = System.currentTimeMillis();
            while (System.currentTimeMillis() - startedAt <= secsToAltTab * 1000 && LoLUtil.isGameRunning()) {
                ScreenRegion r = findImage(targetMiniMapTop);
                if (r != null) {
                    return getBotController().getAntiAfkAction();
                }
                Thread.sleep(1000);
            }
        }
        return getBotController().getRestartLoLAction();
    }


}
