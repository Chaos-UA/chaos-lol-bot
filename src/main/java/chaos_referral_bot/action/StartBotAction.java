package chaos_referral_bot.action;


import chaos_referral_bot.BotController;
import chaos_referral_bot.LoLUtil;

public class StartBotAction extends AbstractBotAction {
    public StartBotAction(BotController botController) {
        super(botController);
    }

    @Override
    public AbstractBotAction executeAndGetNext() throws Exception {
        LoLUtil.forceKillExplorer();
        return getBotController().getReadUserAction();
    }
}
