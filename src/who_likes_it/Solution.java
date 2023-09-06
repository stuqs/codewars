
package who_likes_it;

import java.text.MessageFormat;

public class Solution {
    private static final String ZERO = "no one likes this";
    private static final String ONE = "{0} likes this";
    private static final String TWO = "{0} and {1} like this";
    private static final String THREE = "{0}, {1} and {2} like this";
    private static final String FOUR_AND_MORE = "{0}, {1} and {3} others like this";

    public static String whoLikesIt(String... names) {
        if (names == null) {
            throw new IllegalArgumentException("names could not be null");
        }
        return switch (names.length) {
            case 0 -> ZERO;
            case 1 -> MessageFormat.format(ONE, names);
            case 2 -> MessageFormat.format(TWO, names);
            case 3 -> MessageFormat.format(THREE, names);
            default -> MessageFormat.format(FOUR_AND_MORE, names[0], names[1], names.length - 2);
        };
    }
}
