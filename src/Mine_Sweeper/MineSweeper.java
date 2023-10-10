package Mine_Sweeper;


import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class MineSweeper {
    private static final String MINE = "x";
    private static final String NEW_ROW = "\n";
    private static final String CLOSED_CELL = "?";
    private static final String UNKNOWN_RESULT = "?";

    private final int NUMBER_OF_MINES;
    private final String[][] PARSED_BOARD;

    public MineSweeper(final String board, final int nMines) {
        PARSED_BOARD = parseMap(board);
        NUMBER_OF_MINES = nMines;
    }

    public String solve() {
        do {
            while (processDummy()) {
            }
            if (isGameSolved()) {
                return formatResult();
            }
            if (!combinatoricsSolution()) {
                return UNKNOWN_RESULT;
            }
            if (isGameSolved()) {
                return formatResult();
            }

        } while (true);
    }

    /**
     * Safely process the field. Opens all 100% sure safe cells.
     *
     * @return true if any cell was open, otherwise false.
     */
    private boolean processDummy() {
        boolean result = false;
        for (int i = 0; i < PARSED_BOARD.length; i++) {
            for (int j = 0; j < PARSED_BOARD[i].length; j++) {
                String cell = PARSED_BOARD[i][j];
                // process only digits
                if (CLOSED_CELL.equals(cell) || MINE.equals(cell)) {
                    continue;
                }
                int cellValue = Integer.parseInt(cell);
                Statistics s = getCorrespondingStatistics(new Coordinate(i, j), PARSED_BOARD);

                //if there is no closed cells around go further
                if (s.closed().size() == 0) {
                    continue;
                }

                // all corresponding mines were found, other cells should be opened
                if (cellValue == s.mines().size()) {
                    s.closed().forEach(c -> PARSED_BOARD[c.x()][c.y] = String.valueOf(Game.open(c.x(), c.y())));
                    result = true;
                }
                // number of closed cells and opened mines equals to the cell value, all closed cells are mines
                if (cellValue == s.closed().size() + s.mines().size()) {
                    s.closed().forEach(c -> PARSED_BOARD[c.x()][c.y] = MINE);
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * Create combinations of possible mine locations from cells that aren't satisfied.
     *
     * @return true if some combination was found.
     */
    private boolean combinatoricsSolution() {
        Coordinate[] cells = findUnsatisfied().stream()
                .map(c -> findCorrespondingCells(c, PARSED_BOARD))
                .flatMap(Collection::stream)
                .filter(c -> CLOSED_CELL.equals(PARSED_BOARD[c.x()][c.y()]))
                .distinct()
                .toArray(Coordinate[]::new);
        int[] fieldStatistics = getStatistics(PARSED_BOARD);
        int missingMines = NUMBER_OF_MINES - fieldStatistics[0];
        int numberOfClosed = fieldStatistics[1];

        // when all cells are satisfied, but there are closed cells left. This could be when mines create hidden island.
        if (cells.length == 0) {
            Set<Coordinate> closedCells = findClosedCells();
            if (missingMines == closedCells.size()) {
                placeMines(PARSED_BOARD, closedCells.toArray(new Coordinate[0]));
                return true;
            } else if (missingMines == 0) {
                for (Coordinate c : closedCells) {
                    PARSED_BOARD[c.x()][c.y()] = String.valueOf(Game.open(c.x(), c.y()));
                }
                return true;
            }
        }

        Set<Coordinate[]> combinations = Arrays.stream(combinations(cells, missingMines, numberOfClosed == cells.length))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Set<Coordinate[]> successfulCombinations = new HashSet<>();
        String[][] testFiled = deepCopy(PARSED_BOARD);
        for (Coordinate[] combination : combinations) {
            placeMines(testFiled, combination);
            if (validateCells(testFiled, combination) && findFirstUnsatisfied(testFiled) == null) {
                successfulCombinations.add(combination);
            }
            revertMines(testFiled, combination);
        }

        if (successfulCombinations.size() == 1) {
            placeMines(PARSED_BOARD, successfulCombinations.iterator().next());
            return true;
        }

        // if there are multiple solutions - find intersects between empty cells and intersections between mines
        var emptyCells = new HashSet<>(Arrays.asList(cells));
        var cellsWithMine = new HashSet<>(Arrays.asList(cells));
        for (Coordinate[] successfulCombination : successfulCombinations) {
            emptyCells.removeAll(new HashSet<>(Arrays.asList(successfulCombination)));
            cellsWithMine.retainAll(new HashSet<>(Arrays.asList(successfulCombination)));
        }

        if (emptyCells.isEmpty() && cellsWithMine.isEmpty()) {
            return false;
        }
        for (Coordinate emptyCell : emptyCells) {
            PARSED_BOARD[emptyCell.x()][emptyCell.y()] = String.valueOf(Game.open(emptyCell.x(), emptyCell.y()));
        }
        for (Coordinate cellWithMine : cellsWithMine) {
            PARSED_BOARD[cellWithMine.x()][cellWithMine.y()] = MINE;
        }
        return true;
    }

    private static String[][] parseMap(String board) {
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

    /**
     * Find all cells around cell that was provided.
     *
     * @param c     coordinate of the cell, neighbours of which should be found
     * @param field game field
     * @return Set of all valid neighbours coordinate
     */
    private static Set<Coordinate> findCorrespondingCells(Coordinate c, String[][] field) {
        Set<Coordinate> result = new HashSet<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                result.add(new Coordinate(c.x + i, c.y + j));
            }
        }
        return result.stream()
                .filter(e -> validateIndex(e, field))
                .filter(e -> !e.equals(c))
                .collect(Collectors.toSet());
    }

    private static Statistics getCorrespondingStatistics(Coordinate c, String[][] field) {
        List<Coordinate> mines = new ArrayList<>();
        List<Coordinate> closed = new ArrayList<>();
        for (Coordinate cell : findCorrespondingCells(c, field)) {
            if (MINE.equals(field[cell.x()][cell.y()])) {
                mines.add(cell);
            } else if (CLOSED_CELL.equals(field[cell.x()][cell.y()])) {
                closed.add(cell);
            }
        }
        return new Statistics(mines, closed);
    }

    private String formatResult() {
        StringBuilder result = new StringBuilder();
        for (String[] strings : PARSED_BOARD) {
            for (String s : strings) {
                result.append(s);
                result.append(" ");
            }
            result.deleteCharAt(result.length() - 1);
            result.append(NEW_ROW);
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    private static boolean validateIndex(Coordinate c, String[][] field) {
        return c.x() >= 0 && c.y() >= 0 && c.x() < field.length && c.y() < field[c.x()].length;
    }

    private static boolean validateCell(Coordinate center, String[][] field) {
        String cell = field[center.x()][center.y()];
        if (CLOSED_CELL.equals(cell) || MINE.equals(cell)) {
            return true;
        }
        int cellValue = Integer.parseInt(cell);
        Statistics s = getCorrespondingStatistics(center, field);
        return cellValue >= s.mines().size() && cellValue - s.mines().size() <= s.closed().size();
    }

    private static boolean validateAreaAround(Coordinate c, String[][] field) {
        Set<Coordinate> result = new HashSet<>();
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                result.add(new Coordinate(c.x + i, c.y + j));
            }
        }
        return result.stream()
                .filter(e -> validateIndex(e, field))
                .allMatch(i -> validateCell(i, field));
    }

    private boolean validateCells(String[][] field, Coordinate[] cells) {
        return Arrays.stream(cells).allMatch(c -> validateAreaAround(c, field));
    }

    /**
     * Find all unsatisfied cells without equity between number in cell and amount of mines around
     *
     * @return coordinate of all unsatisfied cells. If all cells in equity return empty Set
     */
    private Set<Coordinate> findUnsatisfied() {
        Set<Coordinate> result = new HashSet<>();
        for (int x = 0; x < PARSED_BOARD.length; x++) {
            for (int y = 0; y < PARSED_BOARD[x].length; y++) {
                String cell = PARSED_BOARD[x][y];
                if (CLOSED_CELL.equals(cell) || MINE.equals(cell)) {
                    continue;
                }

                int cellValue = Integer.parseInt(cell);
                Coordinate c = new Coordinate(x, y);
                Statistics s = getCorrespondingStatistics(c, PARSED_BOARD);
                if (cellValue != s.mines().size()) {
                    result.add(c);
                }
            }
        }
        return result;
    }

    private Set<Coordinate> findClosedCells() {
        Set<Coordinate> result = new HashSet<>();
        for (int x = 0; x < PARSED_BOARD.length; x++) {
            for (int y = 0; y < PARSED_BOARD[x].length; y++) {
                if (CLOSED_CELL.equals(PARSED_BOARD[x][y])) {
                    result.add(new Coordinate(x, y));
                }
            }
        }
        return result;
    }

    /**
     * Find first unsatisfied equity between number in cell and amount of mines around
     *
     * @param field for which search should be done
     * @return coordinate of unsatisfied cell. If all cells in equity return null
     */
    private Coordinate findFirstUnsatisfied(String[][] field) {
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[x].length; y++) {
                String cell = field[x][y];
                if (CLOSED_CELL.equals(cell) || MINE.equals(cell)) {
                    continue;
                }

                int cellValue = Integer.parseInt(cell);
                Coordinate c = new Coordinate(x, y);
                Statistics s = getCorrespondingStatistics(c, field);
                if (cellValue != s.mines().size()) {
                    return c;
                }
            }
        }
        // all open cells are satisfied(cell value equals amount of mines)
        return null;
    }

    private static <T> T[][] combinations(T[] a, int limit, boolean strict) {
        int len = a.length;
        int numCombinations = (1 << len) - 1;

        @SuppressWarnings("unchecked")
        T[][] combinations = (T[][]) Array.newInstance(a.getClass(), numCombinations);

        // Start i at 1, so that we do not include the empty set in the results
        for (int i = 1; i <= numCombinations; i++) {
            int bitCount = Integer.bitCount(i);
            if (strict && bitCount != limit) {
                continue;
            } else if (limit < bitCount) {
                continue;
            }

            @SuppressWarnings("unchecked")
            T[] combination = (T[]) Array.newInstance(a.getClass().getComponentType(), bitCount);
            for (int j = 0, ofs = 0; j < len; j++) {
                if ((i & (1 << j)) > 0) {
                    combination[ofs++] = a[j];
                }
            }
            combinations[i - 1] = combination;
        }
        return combinations;
    }

    /**
     * Get statistics of provided field.
     *
     * @param field game field to process
     * @return array that contains int 0th position - amount of already used mines, 1st - amount of closed cells
     */
    private int[] getStatistics(String[][] field) {
        int mineCounter = 0;
        int closedCounter = 0;
        for (String[] row : field) {
            for (String cell : row) {
                if (MINE.equals(cell)) {
                    mineCounter++;
                } else if (CLOSED_CELL.equals(cell)) {
                    closedCounter++;
                }
            }
        }
        return new int[]{mineCounter, closedCounter};
    }

    private static void placeMines(String[][] field, Coordinate[] mines) {
        for (Coordinate mine : mines) {
            field[mine.x()][mine.y()] = MINE;
        }
    }

    private static void revertMines(String[][] field, Coordinate[] mines) {
        for (Coordinate mine : mines) {
            field[mine.x()][mine.y()] = CLOSED_CELL;
        }
    }

    private static String[][] deepCopy(String[][] original) {
        if (original == null) {
            return null;
        }
        String[][] result = new String[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    private record Coordinate(int x, int y) {
    }

    private record Statistics(List<Coordinate> mines, List<Coordinate> closed) {
    }
}