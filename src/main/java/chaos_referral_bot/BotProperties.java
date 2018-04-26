package chaos_referral_bot;


import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;

public class BotProperties {
    private static BotProperties BOT_PROPERTIES;

    static {
        /*
        // loading
        ObjectMapper mapper = new ObjectMapper();
       // BOT_PROPERTIES = ;
        try {
            BOT_PROPERTIES = mapper.readValue(new File(Path.PROPERTIES_FILE), BotProperties.class);

        } catch (IOException e) {
            System.out.println(String.format("Can`t load properties from %s", Path.PROPERTIES_FILE));
            e.printStackTrace();
        }
        try {
            System.out.println("Using following properties:\n" +
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(BOT_PROPERTIES)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        // shut down
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    LoLUtil.startExplorerIfNotRunning();
                    System.out.println("Saving properties...");
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writerWithDefaultPrettyPrinter().writeValue(new File(Path.PROPERTIES_FILE), BOT_PROPERTIES);
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        */
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    LoLUtil.startExplorerIfNotRunning();
                    Thread.sleep(2000);
                    LoLUtil.startExplorerIfNotRunning();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static BotProperties getBotProperties() {
        if (BOT_PROPERTIES == null) {
            try {
                BOT_PROPERTIES = new ObjectMapper().readValue(new File(Path.PROPERTIES_FILE), BotProperties.class);
                System.out.println("Using following properties:\n" +
                        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(BOT_PROPERTIES)
                );
            } catch (Throwable e) {
                System.out.println(String.format("Can`t load properties from %s", Path.PROPERTIES_FILE));
                e.printStackTrace();
                System.exit(1);
            }
        }
        return BOT_PROPERTIES;
    }

    private String defaultPassword = "123qwe";
    private int fatalErrorSleepMs = 2000;
    private int sleepBetweenActionsMs = 1000;
    private int searchingTimeoutMin = 7;
    private int gameTimeoutMin = 60;
    private int gameAntiAfkClickTimeoutSec = 2;
    private int gameImagesCheckTimeoutSec = 30;
    private int gameInGameImageNotFoundTimeoutSec = 400;
    private boolean coopExpertGameType = true;
    private boolean trainTo5LevelElseTo10 = false;
    private String lolDirectory = "D:/Games/LoL-referrals/";
    private int gameNextFollowerTimeoutSec = 180;
    private int gameShopTimeoutSec = 300;
    private boolean stopOnLeaverWarning = false;

    public BotProperties() {

    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public int getFatalErrorSleepMs() {
        return fatalErrorSleepMs;
    }

    public void setFatalErrorSleepMs(int fatalErrorSleepMs) {
        this.fatalErrorSleepMs = fatalErrorSleepMs;
    }

    public int getSleepBetweenActionsMs() {
        return sleepBetweenActionsMs;
    }

    public void setSleepBetweenActionsMs(int sleepBetweenActionsMs) {
        this.sleepBetweenActionsMs = sleepBetweenActionsMs;
    }

    public int getSearchingTimeoutMin() {
        return searchingTimeoutMin;
    }

    public void setSearchingTimeoutMin(int searchingTimeoutMin) {
        this.searchingTimeoutMin = searchingTimeoutMin;
    }

    public int getGameTimeoutMin() {
        return gameTimeoutMin;
    }

    public void setGameTimeoutMin(int gameTimeoutMin) {
        this.gameTimeoutMin = gameTimeoutMin;
    }

    public int getGameAntiAfkClickTimeoutSec() {
        return gameAntiAfkClickTimeoutSec;
    }

    public void setGameAntiAfkClickTimeoutSec(int gameAntiAfkClickTimeoutSec) {
        this.gameAntiAfkClickTimeoutSec = gameAntiAfkClickTimeoutSec;
    }

    public int getGameImagesCheckTimeoutSec() {
        return gameImagesCheckTimeoutSec;
    }

    public void setGameImagesCheckTimeoutSec(int gameImagesCheckTimeoutSec) {
        this.gameImagesCheckTimeoutSec = gameImagesCheckTimeoutSec;
    }

    public int getGameInGameImageNotFoundTimeoutSec() {
        return gameInGameImageNotFoundTimeoutSec;
    }

    public void setGameInGameImageNotFoundTimeoutSec(int gameInGameImageNotFoundTimeoutSec) {
        this.gameInGameImageNotFoundTimeoutSec = gameInGameImageNotFoundTimeoutSec;
    }

    public boolean isCoopExpertGameType() {
        return coopExpertGameType;
    }

    public void setCoopExpertGameType(boolean coopExpertGameType) {
        this.coopExpertGameType = coopExpertGameType;
    }

    public String getLolDirectory() {
        return lolDirectory;
    }

    public void setLolDirectory(String lolDirectory) {
        this.lolDirectory = lolDirectory;
    }

    public int getGameNextFollowerTimeoutSec() {
        return gameNextFollowerTimeoutSec;
    }

    public void setGameNextFollowerTimeoutSec(int gameNextFollowerTimeoutSec) {
        this.gameNextFollowerTimeoutSec = gameNextFollowerTimeoutSec;
    }

    public int getGameShopTimeoutSec() {
        return gameShopTimeoutSec;
    }

    public void setGameShopTimeoutSec(int gameShopTimeoutSec) {
        this.gameShopTimeoutSec = gameShopTimeoutSec;
    }

    public boolean isTrainTo5LevelElseTo10() {
        return trainTo5LevelElseTo10;
    }

    public void setTrainTo5LevelElseTo10(boolean trainTo5LevelElseTo10) {
        this.trainTo5LevelElseTo10 = trainTo5LevelElseTo10;
    }

    public boolean isStopOnLeaverWarning() {
        return stopOnLeaverWarning;
    }

    public void setStopOnLeaverWarning(boolean stopOnLeaverWarning) {
        this.stopOnLeaverWarning = stopOnLeaverWarning;
    }
}
