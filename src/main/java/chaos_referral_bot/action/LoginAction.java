package chaos_referral_bot.action;


import chaos_referral_bot.BotController;
import chaos_referral_bot.BotProperties;
import chaos_referral_bot.LoLUtil;
import chaos_referral_bot.resources.Resources;
import org.sikuli.api.DefaultScreenLocation;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;

public class LoginAction extends AbstractBotAction {
    public LoginAction(BotController botController) {
        super(botController);
    }

    private void ignorePossibleDataCorruption(ImageTarget possibleDataCorruption) {

        ScreenRegion r = findImage(possibleDataCorruption);
        if (r != null) {
            getBotController().getMouse().click(r.getLowerRightCorner());
        }
    }

    @Override
    public AbstractBotAction executeAndGetNext() throws Exception {
        ImageTarget possibleDataCorruption = new ImageTarget(Resources.getUrl(Resources.getLolClientPossibleCorruptionImage()));


        String userNameImagePath = Resources.getLoginUsernameImagePath();
        ImageTarget imageTarget = new ImageTarget(Resources.getUrl(userNameImagePath));
        imageTarget.setMinScore(0.7D);
        int attempts = 15;
        boolean xpBugFixed = false;
        while (attempts-- > 0) {
            log("attempt left: " + attempts);
            sleep(3000);
            ignorePossibleDataCorruption(possibleDataCorruption);
            ScreenRegion r = findImage(imageTarget);
            if (r != null) {
                attempts = 15;
                if (false && !xpBugFixed) {
                    xpBugFixed = true;
                    log("Kill lol renderer to fix bug on XP and try again");
                    LoLUtil.killLolClientRenderer();
                    continue;
                }
              //  log("found" + userNameImagePath + " " + screenRegion + "\nlogging...");
                ScreenLocation screenLocation = r.getLowerRightCorner().getRelativeScreenLocation(0, 16);
                getBotController().getMouse().doubleClick(screenLocation);
                log("Logging: " + getBotController().getUser());
                getBotController().getKeyboard().type(getBotController().getUser().getName());
                // password
                screenLocation = r.getLowerRightCorner().getRelativeScreenLocation(0, 75);
                getBotController().getMouse().doubleClick(screenLocation);
                getBotController().getKeyboard().type(getBotController().getUser().getPassword());
                // login
                log("finding login button: " + Resources.getLogInButtonImagePath());
                imageTarget = new ImageTarget(Resources.getUrl(Resources.getLogInButtonImagePath()));
                r = findImage(imageTarget);
                if (r == null) {
                   // log("can't find login button");
                    return getBotController().getRestartLoLAction();
                }
                log("Pressing button: " + r);
                getBotController().getMouse().click(r.getCenter());

                //
                int loginWaitAttempts = 5;
                ImageTarget imageTargetLoginError = new ImageTarget(Resources.getUrl(Resources.getLogInErrorImagePath()));
                imageTargetLoginError.setMinScore(0.98D);
                //ImageTarget imageTargetNewSummonerName = new ImageTarget(Resources.getUrl(Resources.getNewSummonerNameButtonImagePath()));
                //imageTargetNewSummonerName.setMinScore(0.99D);

                ImageTarget imagePlay = new ImageTarget(Resources.getUrl(Resources.getLolClientPlayImagePath()));
                ImageTarget targetInvalidLogin = new ImageTarget(Resources.getUrl(Resources.getInvalidLoginImagePath()));
                ImageTarget targetReconnect = new ImageTarget(Resources.getUrl(Resources.getLoLClientReconnectImagePath()));
                ImageTarget targetLeaverBuster = new ImageTarget(Resources.getUrl(Resources.getLoginLeaverBuster()));
                ImageTarget targetNewMasteries = new ImageTarget(Resources.getUrl(Resources.getLolClientNewMasteries()));
                ImageTarget targetLeaverWarning = new ImageTarget(Resources.getUrl(Resources.getLolLeaverWarningImage()));
                ImageTarget findMatch = new ImageTarget(Resources.getUrl(Resources.getFindMatchPath()));
                ImageTarget acceptKeyFragments = new ImageTarget(Resources.getUrl(Resources.getAcceptKeyFragments()));
                acceptKeyFragments.setMinScore(0.7);
                while (loginWaitAttempts-- > 0) {
                    log("Logging waiting attempts left: " + loginWaitAttempts);
                    r = findImage(acceptKeyFragments);
                    if (r != null) {
                        getBotController().getMouse().click(r.getCenter());
                    }
                    r = findImage(findMatch);
                    if (r != null) {
                        return getBotController().getStartingGameCycleAction();
                    }
                    r = findImage(imageTargetLoginError);
                    if (r != null) {
                        log("Login error!");
                        r = findImage(targetInvalidLogin);
                        if (r != null) {
                            log("Invalid login!");
                            return getBotController().getInvalidLoginAction();
                        }
                        r = findImage(targetLeaverBuster);
                        if (r != null) {
                            log("Leaver buster!");
                            return getBotController().getLeaverBusterAction();
                        }
                        return getBotController().getRestartLoLAction();
                    }
                    if (!getBotController().getNewSummonerNameAction().executeAndCheckName()) {
                        return getBotController().getInvalidLoginAction();
                    }
                    /*r = findImage(imageTargetNewSummonerName);
                    if (r != null) {
                        log("New summoner name");
                        return getBotController().getNewSummonerNameAction();
                    }*/
                    r = findImage(targetNewMasteries);
                    if (r != null) {
                        getBotController().getMouse().click(r.getLowerRightCorner());
                    }
                    AbstractBotAction nextAction = checkLeaverWarning(targetLeaverWarning);
                    if (nextAction != null) {
                        return nextAction;
                    }
                    r = findImage(imagePlay);
                    if (r != null) {
                        log("Play button has been found (LoLClient)");
                        nextAction = checkLeaverWarning(targetLeaverWarning);
                        if (nextAction != null) {
                            return nextAction;
                        }
                        r = findImage(targetNewMasteries);
                        if (r != null) {
                            getBotController().getMouse().click(r.getLowerRightCorner());
                        }
                        return getBotController().getStartingGamePreparationAction();
                    }
                    r = findImage(targetReconnect);
                    if (r != null) {
                        getBotController().getMouse().click(r.getCenter());
                        sleep(15_000);
                        return getBotController().getAntiAfkAction();
                    }
                    if (LoLUtil.isGameRunning()) {
                        return getBotController().getAntiAfkAction();
                    }

                    sleep(5000);
                }
                break;
            }
        }
        return getBotController().getRestartLoLAction();
    }

    private AbstractBotAction checkLeaverWarning(ImageTarget leaverWarning) {
        ScreenRegion r = findImage(leaverWarning);
        if (r != null) {
            if (BotProperties.getBotProperties().isStopOnLeaverWarning()) {
                log("Stopping. Since leaver warning detected");
                return getBotController().getLeaverBusterAction();
            }
            getBotController().getMouse().click(r.getCenter());
            getBotController().getKeyboard().type("I Agree");
            DefaultScreenLocation location = new DefaultScreenLocation(
                    getBotController().getDesktopScreenRegion().getScreen(),
                    (r.getLowerLeftCorner().getX() + r.getLowerRightCorner().getX()) / 2,
                    r.getLowerLeftCorner().getY()
            );
            getBotController().getMouse().click(location);
        }
        return null;
    }
}
