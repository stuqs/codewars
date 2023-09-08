package Walking_in_the_NASA_facility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class NASAFacility_Solution_1 {
    private static final String WALKERS = "udlr";
    private static final Pattern PATTERN_RIGHT_LEFT = Pattern.compile("r(--)*l");
    private static final Pattern PATTERN_DOWN_UP = Pattern.compile("d(--)*u");

    public static int collision(char[][] room) {
        int passingValue = passingCheck(room);
        //as 1 is a minimum result
        if (passingValue == 1) {
            return passingValue;
        }

        String[][] path = copyArrayLength(room);
        calculatePath(room, path);
        int result = Integer.min(processPath(path), passingValue);
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    private static void calculatePath(char[][] room, String[][] path) {
        for (int i = 0; i < room.length; i++) {
            for (int j = 0; j < room[i].length; j++) {
                String s = String.valueOf(room[i][j]);

                if (!WALKERS.contains(s)) {
                    continue;
                }
                int count = 0;
                try {
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
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        }
    }

    private static int passingCheck(char[][] room) {
        return Stream.of(passingHorizontalCheck(room), passingVerticalCheck(room))
                .filter(OptionalInt::isPresent)
                .mapToInt(OptionalInt::getAsInt)
                .min()
                .orElse(Integer.MAX_VALUE);
    }

    private static OptionalInt passingHorizontalCheck(char[][] room) {
        return Arrays.stream(room)
                .map(String::new)
                .flatMap(s -> PATTERN_RIGHT_LEFT.matcher(s).results())
                .mapToInt(r -> r.group().length() / 2)
                .min();
    }

    private static OptionalInt passingVerticalCheck(char[][] room) {
        int maxRowLength = Arrays.stream(room)
                .mapToInt(a -> a.length)
                .max()
                .orElse(0);
        List<String> columns = new ArrayList<>();
        for (int i = 0; i < maxRowLength; i++) {
            StringBuilder sb = new StringBuilder();
            for (char[] chars : room) {
                try {
                    sb.append(chars[i]);
                } catch (IndexOutOfBoundsException e) {
                    sb.append("x");
                }
            }
            columns.add(sb.toString());
        }
        return columns.stream()
                .flatMap(s -> PATTERN_DOWN_UP.matcher(s).results())
                .mapToInt(r -> r.group().length() / 2)
                .min();
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
                            .count() > 1) {
                        int parsedValue = Integer.parseInt(pair);
                        result = Integer.min(result, parsedValue);
                    }
                }
            }
        }
        return result;
    }

    private static String[][] copyArrayLength(char[][] array) {
        String[][] copy = new String[array.length][];
        for (int i = 0; i < array.length; i++) {
            copy[i] = new String[array[i].length];
        }
        return copy;
    }
}
