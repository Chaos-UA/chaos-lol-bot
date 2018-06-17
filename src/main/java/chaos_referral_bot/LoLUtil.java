package chaos_referral_bot;


import org.apache.commons.exec.CommandLine;

import java.io.File;
import java.io.IOException;

public class LoLUtil {
    private static final String LOL_LAUNCHER_PROCESS_NAME = "LoLLauncher.exe";
    private static final String LOL_CLIENT_PROCESS_NAME = "LeagueClient.exe";
    private static final String LOL_CLIENT_UX_PROCESS_NAME = "LeagueClientUx.exe";
    private static final String LOL_CLIENT_UX_RENDER_PROCESS_NAME = "LeagueClientUxRender.exe";
    private static final String LOL_GAME_PROCESS_NAME = "League of Legends.exe";
    private static final String LOL_LAUNCHER_FILE = getLolDirectory() + "LeagueClient.exe";
    private static final String LOL_RADS_USER_KERNEL_PROCESS_NAME = "rads_user_kernel.exe";
    private static final String EXPLORER_PROCESS_NAME = "explorer.exe";
    private static final String IEXPLORE_PROCESS_NAME = "iexplore.exe";
    private static final String MICROSOFT_EDGE_PROCESS_NAME = "MicrosoftEdge.exe";

    public static void restartLeagueOfLegends() throws IOException {
        forceKillLeagueOfLegends();
        forceKillIExplore();
        startLeagueOfLegends();
    }

    public static void killLolClientRenderer() throws IOException {
        ChaosProcessUtil.killAllProcesses(LOL_CLIENT_UX_RENDER_PROCESS_NAME);
    }

    public static void killAllExceptLeagueOfLegendsGameClient() throws IOException {
        ChaosProcessUtil.killAllProcesses(LOL_CLIENT_PROCESS_NAME);
        ChaosProcessUtil.killAllProcesses(LOL_LAUNCHER_PROCESS_NAME);
        ChaosProcessUtil.killAllProcesses(LOL_CLIENT_UX_PROCESS_NAME);
        ChaosProcessUtil.killAllProcesses(LOL_CLIENT_UX_RENDER_PROCESS_NAME);
        ChaosProcessUtil.killAllProcesses(LOL_RADS_USER_KERNEL_PROCESS_NAME);
    }

    public static void startLeagueOfLegends() throws IOException {
        ChaosExecutor chaosExecutor = new ChaosExecutor();
        chaosExecutor.setExitValues(null);
        chaosExecutor.setWorkingDirectory(new File(getLolDirectory()));
        chaosExecutor.executeAsynchronously(CommandLine.parse(LOL_LAUNCHER_FILE));
    }

    public static boolean isGameRunning() {
        return ChaosProcessUtil.isProcessRunning(LOL_GAME_PROCESS_NAME);
    }

    public static void forceKillLeagueOfLegends() throws IOException {
        ChaosProcessUtil.killAllProcesses(LOL_GAME_PROCESS_NAME);
        killAllExceptLeagueOfLegendsGameClient();
    }

    public static void forceKillExplorer() throws IOException {
        if (true) {return;}
        ChaosProcessUtil.killAllProcesses(EXPLORER_PROCESS_NAME);
    }

    public static void forceKillIExplore() throws IOException {
        if (true) {
            System.out.println("Killing explorer is disabled");
        }
        ChaosProcessUtil.killAllProcesses(IEXPLORE_PROCESS_NAME);
        ChaosProcessUtil.killAllProcesses(MICROSOFT_EDGE_PROCESS_NAME);
    }

    public static void startExplorerIfNotRunning() throws Exception {
        if (true) {return;}
        if (!ChaosProcessUtil.isProcessRunning(EXPLORER_PROCESS_NAME)) {
            ChaosExecutor chaosExecutor = new ChaosExecutor();
            chaosExecutor.setExitValues(null);
            chaosExecutor.executeAsynchronously(CommandLine.parse(EXPLORER_PROCESS_NAME));
        }
    }

    public static boolean isLeagueOfLegendsGameClientRunning() throws Exception {
        return ChaosProcessUtil.isProcessRunning(LOL_GAME_PROCESS_NAME);
    }

    public static String getLolDirectory() {
        return BotProperties.getBotProperties().getLolDirectory();
    }
}
