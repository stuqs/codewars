package Walking_in_the_NASA_facility;

import static java.lang.Integer.min;

public class NASAFacility {
    public static int collision(char[][] room) {

        int result = Integer.MAX_VALUE;
        for (int i1 = 0; i1 < room.length; i1++) {
            for (int j1 = 0; j1 < room[i1].length; j1++) {
                if (room[i1][j1] == '-') {
                    continue;
                }
                result = min(result, processWalker(room, i1, j1));
            }
        }
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    private static int processWalker(char[][] room, int i1, int j1) {
        int candidate = Integer.MAX_VALUE;
        char source = room[i1][j1];
        char target;
        for (int i2 = i1; i2 < room.length; i2++) {
            for (int j2 = 0; j2 < room[i2].length; j2++) {
                target = room[i2][j2];
                if (i2 == i1 && j2 <= j1 || target == '-' || source == target) {
                    continue;
                }
                candidate = min(candidate, processOtherWalker(source, target, i1, j1, i2, j2));
            }
        }
        return candidate;
    }

    private static int processOtherWalker(char source, char target, int i1, int j1, int i2, int j2) {
        int x = i2 - i1;
        int y = j2 - j1;

        switch (source) {
            case 'u' -> {
            }
            case 'd' -> {
                if (target == 'u' && j1 == j2) {
                    return x / 2 + x % 2;
                } else if ((target == 'l' && x == y) || (target == 'r' && x == -y)) {
                    return x;
                }
            }
            case 'r' -> {
                if (target == 'l' && i1 == i2) {
                    return y / 2 + y % 2;
                } else if ((target == 'u' && x == y)) {
                    return x;
                }
            }
            case 'l' -> {
                if (target == 'u' && x == -y) {
                    return x;
                }
            }
            default -> throw new IllegalArgumentException("Input parameter was wrong: " + source);
        }
        return Integer.MAX_VALUE;
    }
}