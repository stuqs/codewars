package Mine_Sweeper;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class MineSweeper {
    private static final String MINE = "x";
    private static final String NEW_ROW = "\n";
    private static final String CLOSED_CELL = "?";
    private static final String UNKNOWN_RESULT = "?";

    private final String[][] PARSED_BOARD;

    public MineSweeper(final String board, final int nMines) {
        PARSED_BOARD = parseMap(board);
    }

    public String solve() {
        do {
            while (processMines()) {
            }
            // check if all cells were opened
            if (isGameSolved()) {
                return formatResult();
            }
            if (!processProbabilities()) {
                return UNKNOWN_RESULT;
            }
        } while (true);
    }

    private String[][] parseMap(String board) {
        String[] rows = board.split(NEW_ROW);
        String[][] array = new String[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            String[] elements = rows[i].split(" ");
            for (int j = 0; j < elements.length; j++) {
                array[i] = elements;
            }
        }
        return array;
    }

    private boolean isGameSolved() {
        for (String[] strings : PARSED_BOARD) {
            for (String string : strings) {
                if (string.equals(CLOSED_CELL)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void insertByCoordinate(Coordinate c, int value) {
        PARSED_BOARD[c.x()][c.y()] = String.valueOf(value);
    }

    private void insertByCoordinate(Coordinate c, String value) {
        PARSED_BOARD[c.x()][c.y()] = value;
    }

    private List<Coordinate> findCorrespondingCells(Coordinate c) {
        List<Coordinate> result = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                result.add(new Coordinate(c.x + i, c.y + j));
            }
        }
        result = result.stream()
                .filter(this::validateCell)
                .collect(Collectors.toList());
        result.remove(c);
        return result;
    }

    private boolean processMines() {
        boolean result = false;
        for (int i = 0; i < PARSED_BOARD.length; i++) {
            for (int j = 0; j < PARSED_BOARD[i].length; j++) {
                String cell = PARSED_BOARD[i][j];
                // process only digits
                if (CLOSED_CELL.equals(cell) || MINE.equals(cell)) {
                    continue;
                }
                int cellValue = Integer.parseInt(cell);

                List<Coordinate> cellsAround = findCorrespondingCells(new Coordinate(i, j));
                long mines = cellsAround.stream()
                        .filter(s -> MINE.equals(PARSED_BOARD[s.x][s.y]))
                        .count();
                List<Coordinate> closedCells = cellsAround.stream()
                        .filter(s -> CLOSED_CELL.equals(PARSED_BOARD[s.x][s.y]))
                        .toList();

                //if there is no closed cells around go further
                if (closedCells.size() == 0) {
                    continue;
                }

                // all corresponding mines were found, other cells should be opened
                if (cellValue == mines) {
                    closedCells.forEach(c -> insertByCoordinate(c, Game.open(c.x(), c.y())));
                    result = true;
                }
                // number of closed cells and opened mines equals to the cell value, all closed cells are mines
                if (cellValue == closedCells.size() + mines) {
                    closedCells.forEach(c -> insertByCoordinate(c, MINE));
                    result = true;
                }
            }
        }
        return result;
    }

    private String formatResult() {
        StringBuilder result = new StringBuilder();
        for (String[] strings : PARSED_BOARD) {
            for (String s : strings) {
                result.append(s);
                result.append(" ");
            }
            result.deleteCharAt(result.length() - 1);
            result.append("\n");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    /**
     * todo
     *
     * @return
     */
    private Collection<Probability> calculateProbabilities() {
        Map<Coordinate, Probability> result = new HashMap<>();
        for (int i = 0; i < PARSED_BOARD.length; i++) {
            for (int j = 0; j < PARSED_BOARD[i].length; j++) {
                String cell = PARSED_BOARD[i][j];
                // process only digits
                if (CLOSED_CELL.equals(cell) || MINE.equals(cell)) {
                    continue;
                }
                int cellValue = Integer.parseInt(cell);
                Coordinate currentCoordinate = new Coordinate(i, j);
                List<Coordinate> cellsAround = findCorrespondingCells(currentCoordinate);
                List<Coordinate> closedCells = cellsAround.stream()
                        .filter(s -> CLOSED_CELL.equals(PARSED_BOARD[s.x][s.y]))
                        .toList();
                long mines = cellsAround.stream()
                        .filter(s -> MINE.equals(PARSED_BOARD[s.x][s.y]))
                        .count();
                if (mines == cellValue) {
                    continue;
                }
                BigDecimal probability = BigDecimal.valueOf(cellValue - mines).divide(BigDecimal.valueOf(closedCells.size()), 3, RoundingMode.HALF_UP);
                // probability of one cell from different corespondent cells are averaged
                closedCells.forEach(c -> result.merge(c, new Probability(c, probability),
                        (p1, p2) -> new Probability(c, p1.probability().add(p2.probability()).divide(BigDecimal.valueOf(2), 3, RoundingMode.HALF_UP))));
            }
        }
        return result.values();
    }

    /**
     * todo
     * @return
     */
    private boolean processProbabilities() {
        Collection<Probability> values = calculateProbabilities();
        Probability minProbability = values.stream()
                .min(Comparator.comparing(Probability::probability))
                .orElseThrow(IllegalArgumentException::new);
        Probability maxProbability = values.stream()
                .max(Comparator.comparing(Probability::probability))
                .orElseThrow(IllegalArgumentException::new);

        // if all probabilities are equal there is no 100% solution
        if (minProbability.probability().equals(maxProbability.probability())) {
            return false;
        }
        insertByCoordinate(minProbability.coordinate(), Game.open(minProbability.coordinate().x(), minProbability.coordinate().y()));
        return true;
    }

    private boolean validateCell(Coordinate c) {
        return c.validate() && c.x < PARSED_BOARD.length && c.y < PARSED_BOARD[c.x].length;
    }

    private record Coordinate(int x, int y) {
        public boolean validate() {
            return x >= 0 && y >= 0;
        }
    }

    private record Probability(Coordinate coordinate, BigDecimal probability) {
    }
}