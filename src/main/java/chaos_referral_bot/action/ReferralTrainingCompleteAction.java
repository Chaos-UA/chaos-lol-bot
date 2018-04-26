package chaos_referral_bot.action;


import chaos_referral_bot.BotController;
import chaos_referral_bot.BotUtil;

public class ReferralTrainingCompleteAction extends AbstractBotAction {
    public ReferralTrainingCompleteAction(BotController botController) {
        super(botController);
    }

    @Override
    public AbstractBotAction executeAndGetNext() throws Exception {
        log("Referral training complete: " + getBotController().getUser());
        BotUtil.markUserAsTrained();
        return getBotController().getReadUserAction();
    }
}
