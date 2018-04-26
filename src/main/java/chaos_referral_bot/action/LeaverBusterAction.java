package chaos_referral_bot.action;

import chaos_referral_bot.BotController;
import chaos_referral_bot.BotUtil;

public class LeaverBusterAction extends AbstractBotAction {
    public LeaverBusterAction(BotController botController) {
        super(botController);
    }

    @Override
    public AbstractBotAction executeAndGetNext() throws Exception {
        log(String.format("Mark %s as leaver buster ban", getBotController().getUser()));
        BotUtil.markUserAsLeaverBusterBan();
        return getBotController().getReadUserAction();
    }
}
