package chaos_referral_bot.action;

import chaos_referral_bot.BotController;
import org.sikuli.api.DefaultScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;

import java.io.File;

public abstract class AbstractBotAction {
    protected final BotController botController;

    public AbstractBotAction(BotController botController) {
        this.botController = botController;
    }


    /**
     * @return next action
     */
    public abstract AbstractBotAction executeAndGetNext() throws Exception;

    public BotController getBotController() {
        return botController;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public void log(String message) {
        botController.log(message);
    }

    public void logImageFound(ImageTarget imageTarget, ScreenRegion screenRegion) {
        File f = new File(imageTarget.getURL().getFile());
        if (screenRegion == null) {
            log(String.format("Image has not been found: %s", f.getName()));
        }
        else {
            log(String.format("Image has been found: %s [%s]", f.getName(), screenRegion));
        }
    }

    public ScreenRegion findImage(ImageTarget imageTarget) {
        ScreenRegion screenRegion = getBotController().getDesktopScreenRegion().find(imageTarget);
        logImageFound(imageTarget, screenRegion);
        int spaceToAdd = 5;
        if (screenRegion != null) {
            getBotController().display(new DefaultScreenRegion(
                    getBotController().getDesktopScreenRegion(),
                            screenRegion.getUpperLeftCorner().getX() - spaceToAdd,
                            screenRegion.getUpperLeftCorner().getY() - spaceToAdd,
                            screenRegion.getUpperRightCorner().getX() - screenRegion.getUpperLeftCorner().getX() + (spaceToAdd * 2),
                            screenRegion.getLowerLeftCorner().getY() - screenRegion.getUpperLeftCorner().getY() + (spaceToAdd * 2)
                    ),
                    1
            );
        }
        return screenRegion;
    }

    public ScreenRegion findImage(ImageTarget imageTarget, int attempts, int sleepBetweenAttemptsMs) {
        if (attempts < 1) {
            throw new RuntimeException("Attempts should be more than < 1");
        }
        while (attempts-- > 0) {
            ScreenRegion screenRegion = findImage(imageTarget);
            if (screenRegion != null) {
                return screenRegion;
            }
            log(String.format("Attempts left: %s", attempts));
            sleep(sleepBetweenAttemptsMs);
        }
        return null;
    }

    public void sleep(int ms) {
        log("Sleeping for " + ms + " ms");
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
