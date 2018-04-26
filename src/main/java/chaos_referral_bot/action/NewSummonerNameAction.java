package chaos_referral_bot.action;


import chaos_referral_bot.BotController;
import chaos_referral_bot.resources.Resources;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;

public class NewSummonerNameAction extends AbstractBotAction {
    public NewSummonerNameAction(BotController botController) {
        super(botController);
    }

    @Override
    public AbstractBotAction executeAndGetNext() throws Exception {
        ImageTarget t = new ImageTarget(Resources.getUrl(Resources.getNewSummonerNameButtonImagePath()));
        t.setMinScore(0.99D);
        ScreenRegion r = findImage(t);
        if (r == null) {
            //return getBotController().getInvalidLoginAction();
        }
        else {
            getBotController().getKeyboard().type(getBotController().getUser().getName());
            getBotController().getMouse().click(r.getCenter());
        }
        //sleep(20_000);
        int attempts = 15;
        while (attempts-- > 0) {
            t = new ImageTarget(Resources.getUrl(Resources.getNewSummonerNameError()));
            r = findImage(t);
            if (r != null) {
                return getBotController().getInvalidLoginAction();
            }
            t = new ImageTarget(Resources.getUrl(Resources.getChooseSummonerIconImagePath()));
            r = findImage(t);
            if (r != null) {
                break;
            }
            System.out.println("Attempts left: " + attempts);
            sleep(3000);
        }
        if (r == null) {
            //return getBotController().getInvalidLoginAction();
        }
        else {
            getBotController().getMouse().click(r.getCenter());
        }

        t = new ImageTarget(Resources.getUrl(Resources.getChooseSummonerIconButtonImagePath()));
        r = findImage(t, 5, 1000);
        if (r == null) {
            //return getBotController().getInvalidLoginAction();
        }
        else {
            getBotController().getMouse().click(r.getCenter());
        }
        //sleep(4000);
        t = new ImageTarget(Resources.getUrl(Resources.getNewSummonerExpertImagePath()));
        r = findImage(t, 5, 1000);
        if (r == null) {
            //return getBotController().getInvalidLoginAction();
        }
        else {
            getBotController().getMouse().click(r.getCenter());
        }
        t = new ImageTarget(Resources.getUrl(Resources.getNewSummonerExpertConfirmImagePath()));
        r = findImage(t, 5, 1000);
        if (r == null) {
            //return getBotController().getInvalidLoginAction();
        }
        else {
            getBotController().getMouse().click(r.getCenter());
        }

        //sleep(4000);
        t = new ImageTarget(Resources.getUrl(Resources.getNewSummonerLearnBasicsButton()));
        r = findImage(t, 5, 1000);
        if (r == null) {
            return getBotController().getInvalidLoginAction();
        }
        getBotController().getMouse().click(r.getCenter());
        r = findImage(t, 5, 1000);
        if (r != null) {// mb not occur, so just click if occur
            getBotController().getMouse().click(r.getCenter());
        }
        sleep(5000);
        return getBotController().getStartingGamePreparationAction();
    }

    /**
     * @return false if name already exists
     */
    public boolean executeAndCheckName() {
        ImageTarget t = new ImageTarget(Resources.getUrl(Resources.getNewSummonerNameButtonImagePath()));
        t.setMinScore(0.99D);
        ScreenRegion r = findImage(t);
        if (r == null) {
            //return getBotController().getInvalidLoginAction();
        }
        else {
            getBotController().getKeyboard().type(getBotController().getUser().getName());
            getBotController().getMouse().click(r.getCenter());
        }
        //sleep(20_000);
       // int attempts = 1;
        //while (attempts-- > 0) {
        t = new ImageTarget(Resources.getUrl(Resources.getNewSummonerNameError()));
        t.setMinScore(0.98D);
        r = findImage(t);
        if (r != null) {
            return false;
        }
        t = new ImageTarget(Resources.getUrl(Resources.getChooseSummonerIconImagePath()));
        t.setMinScore(0.98D);
        r = findImage(t);
           /* if (r != null) {
                break;
            }*/
           // System.out.println("Attempts left: " + attempts);
            sleep(3000);
        //}
        if (r == null) {
            //return getBotController().getInvalidLoginAction();
        }
        else {
            getBotController().getMouse().click(r.getCenter());
            t = new ImageTarget(Resources.getUrl(Resources.getChooseSummonerIconButtonImagePath()));
            t.setMinScore(0.98D);
            r = findImage(t);
            if (r == null) {
                //return getBotController().getInvalidLoginAction();
            }
            else {
                getBotController().getMouse().click(r.getCenter());
                sleep(500);
            }
        }


        //sleep(4000);
        t = new ImageTarget(Resources.getUrl(Resources.getNewSummonerExpertImagePath()));
        t.setMinScore(0.98D);
        r = findImage(t);
        if (r == null) {
            //return getBotController().getInvalidLoginAction();
        }
        else {
            getBotController().getMouse().click(r.getCenter());
        }
        t = new ImageTarget(Resources.getUrl(Resources.getNewSummonerExpertConfirmImagePath()));
        t.setMinScore(0.98D);
        r = findImage(t);
        if (r == null) {
            //return getBotController().getInvalidLoginAction();
        }
        else {
            getBotController().getMouse().click(r.getCenter());
        }

        //sleep(4000);
        t = new ImageTarget(Resources.getUrl(Resources.getNewSummonerLearnBasicsButton()));
        t.setMinScore(0.98D);
        r = findImage(t);
        if (r == null) {
            //return getBotController().getInvalidLoginAction();
        }
        else {
            getBotController().getMouse().click(r.getCenter());
        }

        r = findImage(t);
        if (r != null) {// mb not occur, so just click if occur
            getBotController().getMouse().click(r.getCenter());
        }
        return true;
    }
}
