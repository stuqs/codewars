package Mine_Sweeper;

import java.util.Objects;

public class Game {

    private static String[][] answerMap;
    private static String[][] initMap;
    private static int minesN;

    public static void newGame(String map) {
        answerMap = parseMap(map);

        minesN = 0;
        for (String[] strings : answerMap) {
            for (String s : strings) {
                if ("x".equalsIgnoreCase(s)) {
                    minesN++;
                }
            }
        }
    }

    public static void read(String map) {
        initMap = parseMap(map);
    }

    public static int getMinesN() {
        return minesN;
    }

    public static int open(int x, int y) {
        if (Objects.equals(answerMap[x][y], "x")) {
            System.err.println("BOOM!!!!" + x + ' ' + y);
        }
        return Integer.parseInt(answerMap[x][y]);
    }

    private static String[][] parseMap(String board) {
        String[] rows = board.split("\n");
        String[][] array = new String[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            String[] elements = rows[i].split(" ");
            for (int j = 0; j < elements.length; j++) {
                array[i] = elements;
            }
        }
        return array;
    }
}