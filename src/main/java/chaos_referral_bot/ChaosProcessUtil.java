package chaos_referral_bot;


import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChaosProcessUtil {
    public static final ExecuteResultHandler AVOID_EXECUTE_RESULT = new ExecuteResultHandler() {
        @Override
        public void onProcessComplete(int exitValue) {

        }

        @Override
        public void onProcessFailed(ExecuteException e) {
            e.printStackTrace();
        }
    };

    public static void killAllProcesses(String processName) throws IOException {
        DefaultExecutor defaultExecutor = new DefaultExecutor();
        defaultExecutor.setExitValues(null);
        defaultExecutor.execute(CommandLine.parse("taskkill /F /IM '" + processName + "'"));
       // defaultExecutor.executeAndGetNext(CommandLine.parse("taskkill /F /IM " + processName), AVOID_EXECUTE_RESULT);

    }

    public static boolean isProcessRunning(String processName) {
        try {
            processName = processName.trim().toLowerCase();
            Process p = Runtime.getRuntime().exec("tasklist");
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.toLowerCase();
                if (line.contains(processName)) {
                    return true;
                }
            }

            return false;
        }
        catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }


}
