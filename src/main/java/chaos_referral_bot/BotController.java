package chaos_referral_bot;


import chaos_referral_bot.action.*;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.Region;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;


public class BotController {
    //
    private final AntiAfkAction antiAfkAction = new AntiAfkAction(this);
    private final ReadUserAction readUserAction = new ReadUserAction(this);
    private final StartingGameCycleAction startingGameCycleAction = new StartingGameCycleAction(this);
    private final InvalidLoginAction invalidLoginAction = new InvalidLoginAction(this);
    private final ReferralTrainingCompleteAction referralTrainingCompleteAction = new ReferralTrainingCompleteAction(this);
    private final RestartLoLAction restartLoLAction = new RestartLoLAction(this);
    private final LoLLauncherPlayAction loLLauncherPlayAction = new LoLLauncherPlayAction(this);
    private final LoginAction loginAction = new LoginAction(this);
    private final NewSummonerNameAction newSummonerNameAction = new NewSummonerNameAction(this);
    private final LeaverBusterAction leaverBusterAction = new LeaverBusterAction(this);
    private final StartBotAction startBotAction = new StartBotAction(this);
    private final BuyRpBoostAction buyRpBoostAction = new BuyRpBoostAction(this);
    private final StartingGamePreparationAction startingGamePreparationAction = new StartingGamePreparationAction(this);

    //
    private final DesktopScreenRegion desktopScreenRegion = new DesktopScreenRegion();
    private final Mouse mouse = new DesktopMouse();

    private final Keyboard keyboard = new DesktopKeyboard();
    private User user;

    public void display(Region region, int sec) {
        DesktopCanvas canvas = new DesktopCanvas();
        canvas.addBox(region);
        display(canvas, sec);
    }

    public void display(final Canvas canvas, final double sec) {
        new Thread() {
            @Override
            public void run() {
                canvas.display(sec);
            }
        }.start();
    }

    public void start() {
        AbstractBotAction action = getStartBotAction();
        while (true) {
            try {

                while (true) {
                    log("Next action: " + action.getName());
                    AbstractBotAction currentAction = action;
                    action = action.executeAndGetNext();
                    log("Sleep for " + BotProperties.getBotProperties().getSleepBetweenActionsMs() + "ms");
                    DesktopCanvas canvas = new DesktopCanvas();
                    canvas.addLabel(getDesktopScreenRegion(), String.format("%s -> %s", currentAction.getName(), action.getName()));
                    display(canvas, BotProperties.getBotProperties().getSleepBetweenActionsMs() / 1000D);
                    Thread.sleep(BotProperties.getBotProperties().getSleepBetweenActionsMs());
                }
            }
            catch (Exception ex) {
                System.err.println("Fatal error: ");
                action = getRestartLoLAction();
                ex.printStackTrace();
            }
            try {
                Thread.sleep(BotProperties.getBotProperties().getFatalErrorSleepMs());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public AntiAfkAction getAntiAfkAction() {
        return antiAfkAction;
    }

    public ReadUserAction getReadUserAction() {
        return readUserAction;
    }

    public StartingGameCycleAction getStartingGameCycleAction() {
        return startingGameCycleAction;
    }

    public InvalidLoginAction getInvalidLoginAction() {
        return invalidLoginAction;
    }

    public ReferralTrainingCompleteAction getReferralTrainingCompleteAction() {
        return referralTrainingCompleteAction;
    }

    public RestartLoLAction getRestartLoLAction() {
        return restartLoLAction;
    }

    public LoLLauncherPlayAction getLoLLauncherPlayAction() {
        return loLLauncherPlayAction;
    }

    public LoginAction getLoginAction() {
        return loginAction;
    }

    public NewSummonerNameAction getNewSummonerNameAction() {
        return newSummonerNameAction;
    }

    public LeaverBusterAction getLeaverBusterAction() {
        return leaverBusterAction;
    }

    public StartBotAction getStartBotAction() {
        return startBotAction;
    }

    public BuyRpBoostAction getBuyRpBoostAction() {
        return buyRpBoostAction;
    }

    public StartingGamePreparationAction getStartingGamePreparationAction() {
        return startingGamePreparationAction;
    }

    public DesktopScreenRegion getDesktopScreenRegion() {
        return desktopScreenRegion;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void log(String message) {
        System.out.println(message);
    }
}
