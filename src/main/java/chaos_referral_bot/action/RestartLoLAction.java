package chaos_referral_bot.action;

import chaos_referral_bot.BotController;
import chaos_referral_bot.LoLUtil;

public class RestartLoLAction extends AbstractBotAction {
    public RestartLoLAction(BotController botController) {
        super(botController);
    }

    @Override
    public AbstractBotAction executeAndGetNext() throws Exception {
        LoLUtil.restartLeagueOfLegends();
        return getBotController().getLoLLauncherPlayAction();
    }
}
