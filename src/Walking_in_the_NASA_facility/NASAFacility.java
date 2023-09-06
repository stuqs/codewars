package Walking_in_the_NASA_facility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NASAFacility {
    private static final String WALKERS = "udlr";
    private static final Pattern PATTERN_RIGHT_LEFT = Pattern.compile("r(--)*l");
    private static final Pattern PATTERN_UP_DOWN = Pattern.compile("d(--)*u");

    public static int collision(char[][] room) {
        String[][] path = copyArrayLength(room);
        for (int i = 0; i < room.length; i++) {
            for (int j = 0; j < room[i].length; j++) {
                calculatePath(String.valueOf(room[i][j]), i, j, path);
            }
        }
        int result = Integer.min(processPath(path), passingCheck(room));
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    private static void calculatePath(String s, int i, int j, String[][] path) {
        if (!WALKERS.contains(s)) {
            return;
        }
        int count = 0;
        switch (s) {
            case "u" -> {
                for (int k = i; k >= 0; k--) {
                    path[k][j] = Objects.requireNonNullElse(path[k][j], "") + count + ":";
                    count++;
                }
            }
            case "d" -> {
                for (int k = i; k < path.length; k++) {
                    path[k][j] = Objects.requireNonNullElse(path[k][j], "") + count + ":";
                    count++;
                }
            }
            case "l" -> {
                for (int k = j; k >= 0; k--) {
                    path[i][k] = Objects.requireNonNullElse(path[i][k], "") + count + ":";
                    count++;
                }
            }
            case "r" -> {
                for (int k = j; k < path[i].length; k++) {
                    path[i][k] = Objects.requireNonNullElse(path[i][k], "") + count + ":";
                    count++;
                }
            }
            default -> throw new IllegalArgumentException("Input parameter was wrong: " + s);
        }
    }

    private static int passingCheck(char[][] room) {
        return Stream.of(passingVerticalCheck(room), passingHorizontalCheck(room))
                .flatMap(Collection::stream)
                .mapToInt(Integer::intValue)
                .min()
                .orElse(Integer.MAX_VALUE);
    }

    private static List<Integer> passingHorizontalCheck(char[][] room) {
        List<Integer> result = new ArrayList<>();
        for (char[] row : room) {
            int lengthPath = PATTERN_RIGHT_LEFT.matcher(joinChars(row)).results()
                    .mapToInt(r -> r.group().length() / 2)
                    .min()
                    .orElse(Integer.MAX_VALUE);
            result.add(lengthPath);
        }
        return result;
    }

    private static List<Integer> passingVerticalCheck(char[][] room) {
        char[][] inverted = invertedArray(room);
        for (int i = 0; i < room.length; i++) {
            for (int j = 0; j < room[i].length; j++) {
                inverted[j][i] = room[i][j];
            }
        }

        List<Integer> result = new ArrayList<>();
        for (char[] column : inverted) {
            int lengthPath = PATTERN_UP_DOWN.matcher(joinChars(column)).results()
                    .mapToInt(r -> r.group().length() / 2)
                    .min()
                    .orElse(Integer.MAX_VALUE);
            result.add(lengthPath);
        }
        return result;
    }

    private static int processPath(String[][] path) {
        int result = Integer.MAX_VALUE;
        for (String[] strings : path) {
            for (String string : strings) {
                if (string == null) {
                    continue;
                }
                String[] movements = string.split(":");
                for (String pair : movements) {
                    if (Arrays.stream(movements)
                            .filter(d -> d.equals(pair))
                            .count() > 1
                            && result > Integer.parseInt(pair)) {
                        result = Integer.parseInt(pair);
                    }
                }
            }
        }
        return result;
    }

    private static String joinChars(char[] array) {
        return IntStream.range(0, array.length)
                .mapToObj(i -> array[i])
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    private static String[][] copyArrayLength(char[][] array) {
        String[][] copy = new String[array.length][];
        for (int i = 0; i < array.length; i++) {
            copy[i] = new String[array[i].length];
        }
        return copy;
    }

    private static char[][] invertedArray(char[][] room) {
        int maxRowLength = 0;
        for (char[] ints : room) {
            maxRowLength = Integer.max(ints.length, maxRowLength);
        }
        char[][] inverted = new char[maxRowLength][room.length];
        for (char[] chars : inverted) {
            Arrays.fill(chars, '-');
        }

        for (int i = 0; i < room.length; i++) {
            for (int j = 0; j < room[i].length; j++) {
                inverted[j][i] = room[i][j];
            }
        }
        return inverted;
    }
}
