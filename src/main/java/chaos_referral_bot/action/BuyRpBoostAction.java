package chaos_referral_bot.action;


import chaos_referral_bot.BotController;
import chaos_referral_bot.resources.Resources;
import org.sikuli.api.DefaultScreenLocation;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;

public class BuyRpBoostAction extends AbstractBotAction {
    public BuyRpBoostAction(BotController botController) {
        super(botController);
    }

    @Override
    public AbstractBotAction executeAndGetNext() throws Exception {
        ImageTarget t = new ImageTarget(Resources.getUrl(Resources.getLolClientShop()));
        ScreenRegion r = findImage(t);
        if (r == null) {
            return getBotController().getStartingGameCycleAction();
        }
        getBotController().getMouse().click(r.getCenter());
        t = new ImageTarget(Resources.getUrl(Resources.getLolClientShopBoost()));
        r = findImage(t, 20, 3000);
        if (r == null) {
            return getBotController().getStartingGameCycleAction();
        }
        getBotController().getMouse().click(r.getCenter());
        t = new ImageTarget(Resources.getUrl(Resources.getLolClientShopFilterExperience()));
        r = findImage(t, 15, 3000);
        if (r == null) {
            return getBotController().getStartingGameCycleAction();
        }
        getBotController().getMouse().click(getLeftCenter(r));
        t = new ImageTarget(Resources.getUrl(Resources.getLolClientShopFilterPriceRp()));
        r = findImage(t, 2, 1000);
        if (r == null) {
            return getBotController().getStartingGameCycleAction();
        }
        getBotController().getMouse().click(getLeftCenter(r));
        t = new ImageTarget(Resources.getUrl(Resources.getLolClientShopUnlockXpBoost()));
        r = findImage(t, 5, 2000);
        if (r == null) {
            return getBotController().getStartingGameCycleAction();
        }
        getBotController().getMouse().click(getLowerCenter(r));
        t = new ImageTarget(Resources.getUrl(Resources.getLolClientShopUnlockXpBoostConfirm()));
        r = findImage(t, 15, 3000);
        if (r == null) {
            return getBotController().getStartingGameCycleAction();
        }
        getBotController().getMouse().click(getLowerCenter(r));
        log("Experience boost have been bought");
        return getBotController().getStartingGameCycleAction();
    }

    public static ScreenLocation getLeftCenter(ScreenRegion screenRegion) {
        return new DefaultScreenLocation(
                screenRegion.getScreen(),
                screenRegion.getLowerLeftCorner().getX(),
                Math.round((screenRegion.getLowerLeftCorner().getY() + screenRegion.getUpperLeftCorner().getY()) / 2)
        );
    }

    public static ScreenLocation getLowerCenter(ScreenRegion screenRegion) {
        return new DefaultScreenLocation(
                screenRegion.getScreen(),
                Math.round((screenRegion.getLowerLeftCorner().getX() + screenRegion.getUpperLeftCorner().getX()) / 2),
                screenRegion.getLowerLeftCorner().getY()
        );
    }
}
