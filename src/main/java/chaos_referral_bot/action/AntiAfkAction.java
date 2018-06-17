package chaos_referral_bot.action;


import chaos_referral_bot.BotController;
import chaos_referral_bot.BotProperties;
import chaos_referral_bot.CommonUtil;
import chaos_referral_bot.LoLUtil;
import chaos_referral_bot.resources.Resources;
import org.sikuli.api.DefaultScreenLocation;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.visual.DesktopCanvas;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AntiAfkAction extends AbstractBotAction {
    public AntiAfkAction(BotController botController) {
        super(botController);
    }

    void type(int vk_key, int sleepMs) {
        getBotController().getKeyboard().keyDown(vk_key);
        sleep(sleepMs);
        getBotController().getKeyboard().keyUp(vk_key);
    }

    private void follow(int offset, int followCode, boolean skipRightClick) {
        int x = getBotController().getDesktopScreenRegion().getCenter().getX() - 200 + CommonUtil.getRandom(-offset, offset);
        int y = getBotController().getDesktopScreenRegion().getCenter().getY() + 100 + CommonUtil.getRandom(-offset, offset);
        getBotController().getKeyboard().keyDown(followCode);
        sleep(100);
        getBotController().getKeyboard().keyUp(followCode);
        getBotController().getKeyboard().keyDown(followCode);
        getBotController().getKeyboard().keyUp(followCode);

        log(String.format("Clicking on [x: %s, y: %s]", x, y));
        if (!skipRightClick) {
            getBotController().getMouse().rightClick(new DefaultScreenLocation(
                    getBotController().getDesktopScreenRegion().getScreen(), x, y)
            );
        }
        getBotController().getKeyboard().keyDown(KeyEvent.VK_A);
        sleep(100);
        getBotController().getKeyboard().keyUp(KeyEvent.VK_A);
        getBotController().getKeyboard().type("aaa");
        getBotController().getMouse().click(new DefaultScreenLocation(
                getBotController().getDesktopScreenRegion().getScreen(), x, y)
        );
    }

    @Override
    public AbstractBotAction executeAndGetNext() throws Exception {
        log("Killing lol utils except game");
        LoLUtil.killAllExceptLeagueOfLegendsGameClient();
       // sleep(BotProperties.getGAME_LOADING_SLEEP_SEC() * 1000);
      //  ImageTarget targetInGame = new ImageTarget(Resources.getUrl(Resources.getInGameImagePath()));
        ImageTarget targetInGameAfkDisc = new ImageTarget(Resources.getUrl(Resources.getInGameAfkDisconnectedImagePath()));
        ImageTarget targetInGameAfkWarning = new ImageTarget(Resources.getUrl(Resources.getInGameAfkWarningButtonImagePath()));
       // ImageTarget targetInGameContinue = new ImageTarget(Resources.getUrl(Resources.getInGameContinueImagePath()));
        ImageTarget targetInGameAttemptingToReconnect = new ImageTarget(Resources.getUrl(Resources.getInGameAttemptingToReconnectImagePath()));
       // ImageTarget targetMiniMapAllyTower = new ImageTarget(Resources.getUrl(Resources.getInGameMiniMapAllyTowerImagePath()));
        ImageTarget targetMiniMapTop = new ImageTarget(Resources.getUrl(Resources.getInGameMiniMapTopImagePath()));
        ImageTarget targetLearnSkill = new ImageTarget(Resources.getUrl(Resources.getInGameLearnSkill()));
        ImageTarget targetBuy = new ImageTarget(Resources.getUrl(Resources.getMagazineBuyImage()));
        ImageTarget magazineClose = new ImageTarget(Resources.getUrl(Resources.getMagazineCloseImage()));
        ImageTarget gameStartError = new ImageTarget(Resources.getUrl(Resources.getGameStartError()));

        ScreenRegion shopRecommended = null;
        ScreenRegion regionMagazineClose = null;

        //
        boolean wasInMagazine = false;
        long gameStartTimeMs = System.currentTimeMillis();
        long imagesCheckStartTimeMs = System.currentTimeMillis() - 1000 * 1000;
        long inGameImageStartTimeMs = System.currentTimeMillis();
        long startFollowMs = 0;
        long lastShopping = System.currentTimeMillis();
        double gameMinPassed;
        Rectangle rectangleMiniMap = null;
        int follow = KeyEvent.VK_F2;
        boolean gameStarted = false;
        long gameStartErrorCheckedAtMs = 0;
        final long gameStartErrorCheckIntervalMs = 5000;
     //   boolean makeTp = false;
        do {
            if (!LoLUtil.isLeagueOfLegendsGameClientRunning()) {
                return getBotController().getRestartLoLAction();
            }
            if (!gameStarted && System.currentTimeMillis() - gameStartErrorCheckedAtMs > gameStartErrorCheckIntervalMs) {
                if (findImage(gameStartError) != null) {
                    log("Game starting problem detected. Restarting");
                    return getBotController().getRestartLoLAction();
                }
                gameStartErrorCheckedAtMs = System.currentTimeMillis();
            }
            double lastShoppingPassed = (System.currentTimeMillis() - lastShopping) / 1000D;
            lastShopping = buyItems(shopRecommended, regionMagazineClose, lastShopping, lastShoppingPassed);
            if (gameStarted) {
                if (regionMagazineClose != null) {
                    getBotController().getMouse().click(regionMagazineClose.getCenter());
                }
                double followPassedSec = (System.currentTimeMillis() - startFollowMs) / 1000D;
                if (followPassedSec > BotProperties.getBotProperties().getGameNextFollowerTimeoutSec()) {
                    startFollowMs = System.currentTimeMillis();
                    switch (follow) {
                        case KeyEvent.VK_F2:
                            follow = KeyEvent.VK_F3;
                            break;
                        case KeyEvent.VK_F3:
                            follow = KeyEvent.VK_F4;
                            break;
                        case KeyEvent.VK_F4:
                            follow = KeyEvent.VK_F5;
                            break;
                        case KeyEvent.VK_F5:
                            follow = KeyEvent.VK_F2;
                            break;
                        default:
                            follow = KeyEvent.VK_F2;
                    }
                }
                int offset = 10;


              //  getBotController().getKeyboard().type("qwerfd");
                follow(offset, follow, false);
                type(KeyEvent.VK_Q, 50);
                type(KeyEvent.VK_W, 50);
                type(KeyEvent.VK_E, 50);
                type(KeyEvent.VK_R, 50);
                type(KeyEvent.VK_F, 50);
                type(KeyEvent.VK_D, 50);
                follow(offset, follow, true);
               /* getBotController().getMouse().move(new DefaultScreenLocation(
                        getBotController().getDesktopScreenRegion().getScreen(),
                        rectangleMiniMap.x,
                        rectangleMiniMap.y - 10)
                );*/
               /* if (makeTp) {
                    getBotController().getKeyboard().type("b");
                }*/


            }
            else {
                int offset = 5;
                int x = getBotController().getDesktopScreenRegion().getCenter().getX() + CommonUtil.getRandom(-offset, offset);
                int y = getBotController().getDesktopScreenRegion().getCenter().getY() + CommonUtil.getRandom(-offset, offset);
                getBotController().getMouse().rightClick(new DefaultScreenLocation(
                        getBotController().getDesktopScreenRegion().getScreen(), x, y)
                );
            }
            //
            double imagesCheckSecPassed = (System.currentTimeMillis() - imagesCheckStartTimeMs) / 1000D;
            log(String.format("%s seconds passed of %s to check images", imagesCheckSecPassed, BotProperties.getBotProperties().getGameImagesCheckTimeoutSec()));
           // if (true) {
            if (imagesCheckSecPassed > BotProperties.getBotProperties().getGameImagesCheckTimeoutSec()) {
                log("Checking images...");
                ScreenRegion r = findImage(targetMiniMapTop);
                if (r != null) {
                    //r = null;
                    gameStarted = true;
                }
               // inGameImageFound = r != null;

                if (r != null) {
                    if (startFollowMs == 0) {
                        startFollowMs = System.currentTimeMillis();
                    }
                    int x = r.getLowerLeftCorner().getX();
                    int y = r.getLowerLeftCorner().getY();

                    int width = r.getLowerRightCorner().getX() - x;
                    int height = width;
                    rectangleMiniMap = new Rectangle(x, y, width, height);

                    log("Mini-map rectangle: " + rectangleMiniMap);
                    /*DesktopCanvas canvas = new DesktopCanvas();
                    canvas.addBox(new DefaultRegion(rectangleMiniMap));
                    getBotController().display(canvas, 3);*/
                }
                else {
                    rectangleMiniMap = null;
                }
                r = findImage(targetInGameAfkDisc);
                if (r != null) {
                    log("Afk disconnected! Restarting...");
                    return getBotController().getRestartLoLAction();
                }
                r = findImage(targetInGameAfkWarning);
                if (r != null) {
                    log("Afk warning. Clicking on it");
                    getBotController().getMouse().click(r.getCenter());
                }
                r = findImage(targetInGameAttemptingToReconnect);
                if (r != null) {
                    log("Attempting to reconnect! Restarting...");
                    return getBotController().getRestartLoLAction();
                }
                // FIND ALLY TOWERS
               /* if (rectangleMiniMap != null) {
                    DesktopScreenRegion desktopScreenRegion = new DesktopScreenRegion(0,
                            rectangleMiniMap.x, rectangleMiniMap.y, rectangleMiniMap.width, rectangleMiniMap.height
                    );
                    targetMiniMapAllyTower.setOrdering(Target.Ordering.TOP_DOWN);


                    regionAllyTowers = desktopScreenRegion.findAll(targetMiniMapAllyTower);
                    DesktopCanvas canvas = new DesktopCanvas();
                    if (regionAllyTowers.isEmpty()) {
                        canvas.addLabel(regionMiniMapTop, "Ally towers has`t been found!");
                    }
                    else {
                        for (ScreenRegion tower : regionAllyTowers) {
                            canvas.addBox(tower);
                        }
                    }
                    getBotController().display(canvas, 5);
                }*/
                r = findImage(targetLearnSkill);
                if (r != null) {
                    getBotController().getMouse().click(r.getCenter());
                    sleep(100);
                    getBotController().getMouse().click(r.getCenter());
                    getBotController().getMouse().press();
                    sleep(100);
                    getBotController().getMouse().release();
                }

                // MAGAZINE SELECT ITEM ONCE
                if (!wasInMagazine && rectangleMiniMap != null) {
                    log("Selecting item in magazine to buy later");
                    ImageTarget shopRecommendedTarget = new ImageTarget(Resources.getUrl(Resources.getShopRecommended()));
                    shopRecommendedTarget.setMinScore(0.8D);
                    getBotController().getKeyboard().keyDown(KeyEvent.VK_P);
                    sleep(200);
                    getBotController().getKeyboard().keyUp(KeyEvent.VK_P);
                    sleep(3000);
                    shopRecommended = findImage(shopRecommendedTarget);
                    if (r != null) {
                        getBotController().getMouse().click(shopRecommended.getCenter());
                        sleep(1000);
                    }
                    regionMagazineClose = findImage(magazineClose);
                    getBotController().getKeyboard().keyDown(KeyEvent.VK_P);
                    sleep(200);
                    getBotController().getKeyboard().keyUp(KeyEvent.VK_P);
                    lastShopping = System.currentTimeMillis();
                    wasInMagazine = true;
                }

                imagesCheckStartTimeMs = System.currentTimeMillis();
            }
            //
            if (rectangleMiniMap == null) {
                double inGameImageTimePassedSec = (System.currentTimeMillis() - inGameImageStartTimeMs) / 1000D;
                log(String.format("%s seconds passed of %s to restart because of mini-map not found!",
                        inGameImageTimePassedSec, BotProperties.getBotProperties().getGameInGameImageNotFoundTimeoutSec()
                ));
                if (inGameImageTimePassedSec > BotProperties.getBotProperties().getGameInGameImageNotFoundTimeoutSec()) {
                    log("Mini-map image not found! Restarting...");
                    DesktopCanvas canvas = new DesktopCanvas();
                    canvas.addLabel(getBotController().getDesktopScreenRegion(), "Mini-map image has not found.\nDoing few more attempts before restarting");
                    int attempts = 5;
                    getBotController().display(canvas, attempts);
                    boolean found = false;
                    for (int i = 0; i < attempts; i++) {
                        ScreenRegion r = findImage(targetMiniMapTop);
                        if (r != null) {
                            found = true;
                            break;
                        }
                        sleep(1000);
                    }
                    if (!found) {
                        log("Mini-map image not found! Restarting...");
                        return getBotController().getRestartLoLAction();
                    }
                }
            }
            sleep(BotProperties.getBotProperties().getGameAntiAfkClickTimeoutSec() * 1000);
            gameMinPassed = (System.currentTimeMillis() - gameStartTimeMs) / 1000D / 60;
            log(String.format("%s minutes passed of %s to restart", gameMinPassed, BotProperties.getBotProperties().getGameTimeoutMin()));
        } while (gameMinPassed < BotProperties.getBotProperties().getGameTimeoutMin());
        return getBotController().getRestartLoLAction();
    }

    private long buyItems(ScreenRegion shopRecommended, ScreenRegion regionMagazineClose, long lastShopping, double lastShoppingPassed) {
        if (regionMagazineClose != null && shopRecommended != null &&
                lastShoppingPassed > BotProperties.getBotProperties().getGameShopTimeoutSec()) { // shopping
            log("It's shopping time!");
            getBotController().getMouse().click(shopRecommended.getCenter());

            int leftX = shopRecommended.getLowerLeftCorner().getX();
            int middleX = shopRecommended.getCenter().getX();
            int rightX = shopRecommended.getLowerRightCorner().getX();

            int yDistance = 100;

            int offsetY = 150;
            List<Point> points = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                offsetY += yDistance;
                points.add(new Point(leftX, offsetY));
                points.add(new Point(middleX, offsetY));
                points.add(new Point(rightX, offsetY));
            }

            Collections.shuffle(points);

            getBotController().getKeyboard().keyDown(KeyEvent.VK_P);
            sleep(100);
            getBotController().getKeyboard().keyUp(KeyEvent.VK_P);
            sleep(500);

            for (Point point : points) {
                DefaultScreenLocation location = new DefaultScreenLocation(getBotController().getDesktopScreenRegion().getScreen(), point.x, point.y);
                getBotController().getMouse().click(location);
                sleep(10);
                getBotController().getMouse().click(location);
            }

            getBotController().getKeyboard().keyDown(KeyEvent.VK_P);
            sleep(100);
            getBotController().getKeyboard().keyUp(KeyEvent.VK_P);
            getBotController().getMouse().click(regionMagazineClose.getCenter());
            lastShopping = System.currentTimeMillis();
        }
        return lastShopping;
    }
}
