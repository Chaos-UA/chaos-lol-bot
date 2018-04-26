import chaos_referral_bot.BotController;
import chaos_referral_bot.BotProperties;
import chaos_referral_bot.resources.Resources;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GeneralTests {

    private BotController botController;

    @Before
    public void beforeEach() {
        botController = new BotController();
    }

    @Test
    public void printBotPropertiesStructure() throws Throwable {
        BotProperties botProperties = new BotProperties();
        botProperties.setCoopExpertGameType(true);
        botProperties.setDefaultPassword("defaultPass");
        botProperties.setFatalErrorSleepMs(10000);
        botProperties.setGameAntiAfkClickTimeoutSec(3);
        botProperties.setGameImagesCheckTimeoutSec(60);
        botProperties.setGameInGameImageNotFoundTimeoutSec(60);
        botProperties.setGameNextFollowerTimeoutSec(120);
        botProperties.setGameShopTimeoutSec(400);
        botProperties.setGameTimeoutMin(40);
        botProperties.setLolDirectory("D:\\games\\LoL");
        botProperties.setSleepBetweenActionsMs(2000);
        botProperties.setTrainTo5LevelElseTo10(false);
        botProperties.setSearchingTimeoutMin(5);
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(botProperties));
    }

    @Test
    public void selectChampion() throws Exception {
        botController.getStartingGameCycleAction().executeAndGetNext();
    }

    @Test
    public void testImage() throws Exception {
        Assert.assertNotNull(Resources.getUrl(Resources.getFindMatchPath()));
        Assert.assertNotNull(Resources.getUrl(Resources.getPlayPvp()));
        Assert.assertNotNull(Resources.getUrl(Resources.getPlayRanked5x5Flex()));
        Assert.assertNotNull(Resources.getUrl(Resources.getPlayRankedSolo()));
    }
}
