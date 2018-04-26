package chaos_referral_bot.action;


import chaos_referral_bot.BotController;
import chaos_referral_bot.BotUtil;

public class InvalidLoginAction extends AbstractBotAction {
    public InvalidLoginAction(BotController botController) {
        super(botController);
    }

    @Override
    public AbstractBotAction executeAndGetNext() throws Exception {
        BotUtil.markUserAsIncorrectLogin();
        return getBotController().getReadUserAction();
    }
}
