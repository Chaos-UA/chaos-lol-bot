package chaos_referral_bot.action;

import chaos_referral_bot.BotController;
import chaos_referral_bot.BotProperties;
import chaos_referral_bot.LoLUtil;
import chaos_referral_bot.resources.Resources;
import org.sikuli.api.*;


public class StartingGameCycleAction extends AbstractBotAction {
    ImageTarget targetAcceptMatch = new ImageTarget(Resources.getUrl(Resources.getAcceptMatchImagePath()));

    public StartingGameCycleAction(BotController botController) {
        super(botController);
    }

    void acceptMatch() {
        ScreenRegion r = findImage(targetAcceptMatch);
        if (r != null) {
            getBotController().getMouse().click(r.getCenter());
        }
    }

    @Override
    public AbstractBotAction executeAndGetNext() throws Exception {
        ImageTarget targetPlay = new ImageTarget(Resources.getUrl(Resources.getLolClientPlayImagePath()));
        //int attempts = 5;
        ImageTarget findMatch = new ImageTarget(Resources.getUrl(Resources.getFindMatchPath()));

        ImageTarget targetConnection = new ImageTarget(Resources.getUrl(Resources.getConnectionErrorImagePath()));
        ImageTarget targetRandomChampion = new ImageTarget(Resources.getUrl(Resources.getRandomChampionImagePath()));
        ImageTarget targetLockChampion = new ImageTarget(Resources.getUrl(Resources.getLockChampionImagePath()));

        long startSearchingTimeMs = System.currentTimeMillis();
        double minPassed;
        int cycleCheckConnection = 0;
        do {
            if (LoLUtil.isLeagueOfLegendsGameClientRunning()) {
                sleep(15_000);
                return botController.getAntiAfkAction();
            }
            acceptMatch();
            ScreenRegion r = findImage(targetPlay);
            if (r != null) {
                getBotController().getMouse().click(r.getCenter());
            }
            acceptMatch();
            r = findImage(findMatch);
            if (r != null) {
                getBotController().getMouse().click(r.getCenter());
            }
            acceptMatch();
            selectChampion(targetRandomChampion);
            LoLUtil.forceKillIExplore();
            acceptMatch();
            r = findImage(targetLockChampion);
            if (r != null) {
                getBotController().getMouse().click(r.getCenter());
            }
            acceptMatch();
            if (cycleCheckConnection++ > 5) {
                cycleCheckConnection = 0;
                r = botController.getDesktopScreenRegion().find(targetConnection);
                if (r != null) {
                    return getBotController().getRestartLoLAction();
                }
            }
            //
            minPassed = (System.currentTimeMillis() - startSearchingTimeMs) / 1000D / 60;
            log(String.format("%s minutes passed of %s to restart", minPassed, BotProperties.getBotProperties().getSearchingTimeoutMin()));
        } while (minPassed < BotProperties.getBotProperties().getSearchingTimeoutMin());
        log("Searching timeout");
        return getBotController().getRestartLoLAction();
    }

    private void selectChampion(ImageTarget targetRandomChampion) {
        // random champ was removed. Do workaround trick
        ScreenRegion r = findImage(targetRandomChampion);
        if (r != null) {
            Screen screen = getBotController().getDesktopScreenRegion().getScreen();
            ScreenLocation screenLocation = new DefaultScreenLocation(
                    screen,
                    (int) Math.round(r.getBounds().getX() + r.getBounds().width / 2D),
                    (int) Math.round(r.getBounds().getY() + r.getBounds().getHeight()) + 60
            );
            for (int i = 0; i < 4; i++) {
                getBotController().getMouse().click(screenLocation);
                screenLocation = new DefaultScreenLocation(
                        screen,
                        screenLocation.getX() + 100,
                        screenLocation.getY()
                );
            }
        }
    }
}
