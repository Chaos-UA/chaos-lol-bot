package chaos_referral_bot;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

import java.io.IOException;

public class ChaosExecutor extends DefaultExecutor  {

    public void executeAsynchronously(CommandLine commandLine) throws IOException {
        this.execute(commandLine, ChaosProcessUtil.AVOID_EXECUTE_RESULT);
    }
}
