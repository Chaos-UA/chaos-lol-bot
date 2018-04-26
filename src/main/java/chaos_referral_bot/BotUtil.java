package chaos_referral_bot;


import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BotUtil {
    /**
     * @return null if there is no next user
     */
    public static User getFirstUser() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(Path.USERS_TO_TRAIN_FILE))) {
            String line = reader.readLine();
            if (line == null) {
                return null;
            }
            try {
                Pattern pattern = Pattern.compile("[\\s\t]*(\\w+)[\\s\t]*(\\w*)");
                Matcher matcher = pattern.matcher(line);
                matcher.find();
                String name = matcher.group(1).trim();
                String password = matcher.group(2).trim();
                if (name.isEmpty()) {
                    name = "Empty";
                }
                if (password.isEmpty()) {
                    password = BotProperties.getBotProperties().getDefaultPassword();
                }
                return new User(name, password);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return new User("Error occurred", "Error occurred");
            }
        }
    }

    public static void removeFirstUser() throws IOException {
        HashSet<String> uniqueLines = new LinkedHashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(Path.USERS_TO_TRAIN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                uniqueLines.add(line);
            }
        }
        ArrayList<String> lines = new ArrayList<>(uniqueLines);
        if (!lines.isEmpty()) {
            lines.remove(0);
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(Path.USERS_TO_TRAIN_FILE, false))) {
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    writer.println(line);
                }
            }
        }
    }

    public static void markUserAsTrained() throws IOException {
        User user = getFirstUser();
        if (user != null) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(Path.TRAINED_USERS, true))) {
                writer.println();
                writer.print(user.getName() + "\t" + user.getPassword());
            }
            removeFirstUser();
        }
    }

    public static void markUserAsIncorrectLogin() throws IOException {
        User user = getFirstUser();
        if (user != null) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(Path.INCORRECT_USERS_FILE, true))) {
                writer.println();
                writer.print(user.getName() + "\t" + user.getPassword());
            }
            removeFirstUser();
        }
    }

    public static void markUserAsLeaverBusterBan() throws IOException {
        User user = getFirstUser();
        if (user != null) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(Path.LEAVER_BUSTER_BAN_FILE, true))) {
                writer.println();
                writer.print(user.getName() + "\t" + user.getPassword());
            }
            removeFirstUser();
        }
    }
}
