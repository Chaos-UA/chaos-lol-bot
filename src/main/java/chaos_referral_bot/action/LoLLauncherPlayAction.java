package chaos_referral_bot.action;


import chaos_referral_bot.BotController;
import chaos_referral_bot.resources.Resources;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;

public class LoLLauncherPlayAction extends AbstractBotAction {
    public LoLLauncherPlayAction(BotController botController) {
        super(botController);
    }

    @Override
    public AbstractBotAction executeAndGetNext() throws Exception {
        if (true) {
            return getBotController().getLoginAction();
        }

        String imagePath = Resources.getLolLauncherPlayImagePath();
        ImageTarget imageTarget = new ImageTarget(Resources.getUrl(Resources.getLolCrashDoNotSendBtn()));
        ScreenRegion screenRegion = findImage(imageTarget);
        if (screenRegion != null) {
            getBotController().getMouse().click(screenRegion.getCenter());
        }
        imageTarget = new ImageTarget(Resources.getUrl(imagePath));
        ImageTarget possibleDataCorruption = new ImageTarget(Resources.getUrl(Resources.getLolClientPossibleCorruptionImage()));
        int attempts = 15;
        while (attempts-- > 0) {
            log("attempts left: " + attempts);
            ignorePossibleDataCorruption(possibleDataCorruption);
            sleep(3000);
            screenRegion = findImage(imageTarget);
            if (screenRegion != null) {
                ignorePossibleDataCorruption(possibleDataCorruption);
                getBotController().getMouse().click(screenRegion.getCenter());
                return getBotController().getLoginAction();
            }
        }
        return getBotController().getRestartLoLAction();
    }

    private void ignorePossibleDataCorruption(ImageTarget possibleDataCorruption) {
        ScreenRegion r = findImage(possibleDataCorruption);
        if (r != null) {
            getBotController().getMouse().click(r.getLowerRightCorner());
        }
    }
}
