package chaos_referral_bot.action;

import chaos_referral_bot.BotController;
import chaos_referral_bot.BotProperties;
import chaos_referral_bot.resources.Resources;
import org.sikuli.api.DefaultScreenLocation;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;


public class StartingGamePreparationAction extends AbstractBotAction {
    public StartingGamePreparationAction(BotController botController) {
        super(botController);
    }

    @Override
    public AbstractBotAction executeAndGetNext() throws Exception {
        ImageTarget t;
        if (BotProperties.getBotProperties().isTrainTo5LevelElseTo10()) {
            t = new ImageTarget(Resources.getUrl(Resources.get5lvlImage()));
        }
        else {
            t = new ImageTarget(Resources.getUrl(Resources.get10LvlImage()));
        }
        t.setMinScore(0.98D);
        ScreenRegion r = findImage(t);
        if (r != null) {
            log("Training complete!");
            return getBotController().getReferralTrainingCompleteAction();
        }

        ImageTarget targetPlay = new ImageTarget(Resources.getUrl(Resources.getLolClientPlayImagePath()));
        ScreenRegion regionPlay = findImage(targetPlay);
        if (regionPlay == null) {
            return getBotController().getRestartLoLAction();
        }
        getBotController().getMouse().click(regionPlay.getCenter());
        //

        // PREPARE GAME TYPE
        t = new ImageTarget(Resources.getUrl(Resources.getLolClientCloseMessage()));
        t.setMinScore(0.98D);
        r = findImage(t);
        if (r != null) {
            getBotController().getMouse().click(r.getCenter());
        }
        //
        //sleep(1500);
        AbstractBotAction nextAction = null;
        if (BotProperties.getBotProperties().getMode() == BotProperties.Mode.ANTY_AFK_CO_OP) {
            nextAction = playCoop();
        } else if (BotProperties.getBotProperties().getMode() == BotProperties.Mode.DODGE_5x5_FLEX || BotProperties.getBotProperties().getMode() == BotProperties.Mode.DODGE_SOLO_RANKED) {
            nextAction = dodgeRanked();
        } else {
            throw new RuntimeException("Unknown mode:" + BotProperties.getBotProperties().getMode());
        }
        if (nextAction != null) {
            return nextAction;
        }

        if (!confirmAndPlay()) return getBotController().getRestartLoLAction();
        if (!selectRole()) return getBotController().getRestartLoLAction();

        sleep(2000);
        t = new ImageTarget(Resources.getUrl(Resources.getLolLeaverBusterImage()));
        long maxLeaverBusterTimeMs = 1000 * 60 * 22;// currently max leaver buster is 20 min
        long startLeaverBusterTimeMs = System.currentTimeMillis();
        while ((r = findImage(t)) != null
                && maxLeaverBusterTimeMs > System.currentTimeMillis() - startLeaverBusterTimeMs) {
            sleep(1000);
        }

        // XP BOOST
        /*
        t = new ImageTarget(Resources.getUrl(Resources.getLolClient400Rp()));
        t.setMinScore(0.98D);
        r = findImage(t);
        if (r != null) {
            return getBotController().getBuyRpBoostAction();
        }
        */
        //


        return getBotController().getStartingGameCycleAction();
    }

    private boolean confirmAndPlay() {
        ImageTarget t;
        ScreenRegion r;
        t = new ImageTarget(Resources.getUrl(Resources.getMatchMeImagePath()));
        r = findImage(t);
        if (r == null) {
            log("Play button not found. Restarting");
            return false;
        }
        getBotController().getMouse().click(r.getCenter());
        return true;
    }

    private boolean selectRole() {
        ImageTarget t;
        ScreenRegion r;
        t = new ImageTarget(Resources.getUrl(Resources.getPlayPvpRoleSelect()));
        t.setMinScore(0.7);
        r = findImage(t, 4, 1000);
        if (r == null) {
            return false;
        }
        getBotController().getMouse().click(r.getCenter());

        ScreenLocation screenLocation = new DefaultScreenLocation(
                r.getScreen(),
                (int) Math.round(r.getBounds().getX()),
                (int) Math.round(r.getBounds().getY() + 70)
        );

        getBotController().getMouse().click(screenLocation);

        return true;
    }

    private AbstractBotAction dodgeRanked() {
        ImageTarget t;
        ScreenRegion r;
        t = new ImageTarget(Resources.getUrl(Resources.getPlayPvp()));
        t.setMinScore(0.7);
        r = findImage(t, 4, 1000);
        if (r == null) {
            return getBotController().getRestartLoLAction();
        }
        getBotController().getMouse().click(r.getCenter());
        sleep(1500);
        if (BotProperties.getBotProperties().getMode() == BotProperties.Mode.DODGE_SOLO_RANKED) {
            t = new ImageTarget(Resources.getUrl(Resources.getPlayRankedSolo()));
        } else if (BotProperties.getBotProperties().getMode() == BotProperties.Mode.DODGE_5x5_FLEX) {
            t = new ImageTarget(Resources.getUrl(Resources.getPlayRanked5x5Flex()));
        } else {
            throw new RuntimeException("Unknown mode: " + BotProperties.getBotProperties().getMode());
        }
        t.setMinScore(0.7);
        r = findImage(t, 4, 1000);
        if (r == null) {
            return getBotController().getRestartLoLAction();
        }
        getBotController().getMouse().click(r.getCenter());
        return null;
    }

    private AbstractBotAction playCoop() {
        ImageTarget t;
        ScreenRegion r;
        t = new ImageTarget(Resources.getUrl(Resources.getCoopVsAiImagePath()));
        r = findImage(t, 4, 1000);
        if (r == null) {
            return getBotController().getRestartLoLAction();
        }
        getBotController().getMouse().click(r.getCenter());
        sleep(1500);
        t = new ImageTarget(Resources.getUrl(Resources.get5v5ImagePath()));
        r = findImage(t, 4, 1000);
        if (r == null) {
            return getBotController().getRestartLoLAction();
        }
        getBotController().getMouse().click(r.getCenter());
        sleep(1500);
        if (BotProperties.getBotProperties().isCoopExpertGameType()) {
            t = new ImageTarget(Resources.getUrl(Resources.get5v5Intermediate()));
        }
        else {
            t = new ImageTarget(Resources.getUrl(Resources.get5v5Beginner()));
        }

        r = findImage(t, 4, 1000);

        if (r == null) {
            return getBotController().getRestartLoLAction();
        }
        getBotController().getMouse().click(r.getCenter());
        return null;
    }
}
