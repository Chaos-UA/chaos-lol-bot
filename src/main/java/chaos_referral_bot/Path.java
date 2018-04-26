package chaos_referral_bot;

import java.io.File;
import java.io.IOException;

public class Path {
    public static final String WORKING_DIRECTORY = new File(System.getProperty("user.dir")).getPath() + "/";
    public static final String INCORRECT_USERS_FILE = createFile(WORKING_DIRECTORY + "incorrect_users.txt");
    public static final String USERS_TO_TRAIN_FILE = createFile(WORKING_DIRECTORY + "users_to_train_file.txt");
    public static final String PROPERTIES_FILE = createFile(WORKING_DIRECTORY + "properties.txt");
    public static final String TRAINED_USERS = createFile(WORKING_DIRECTORY + "trained_users.txt");
    public static final String LEAVER_BUSTER_BAN_FILE = createFile(WORKING_DIRECTORY + "leaver_buster_ban.txt");

    public static String createFile(String file) {
        try {
            File f = new File(file);
            f.createNewFile();
            return f.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
