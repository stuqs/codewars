package Walking_in_the_hallway;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hallway {
    private static final Pattern PATTERN = Pattern.compile(">[^<>]*<");

    public static int contact(String hallway) {
        Matcher matcher = PATTERN.matcher(hallway);
        Set<String> groups = new TreeSet<>(Comparator.comparingInt(String::length));
        while (matcher.find()) {
            groups.add(matcher.group());
        }

        return groups.stream()
                .map(Hallway::processPath)
                .findFirst()
                .orElse(-1);
    }

    private static int processPath(String path) {
        int length = path.replaceAll("[><]", "").length();
        return length / 2 + 1;
    }
}