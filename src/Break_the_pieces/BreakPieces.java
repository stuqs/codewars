package Break_the_pieces;

import java.util.*;
import java.util.stream.Collectors;

public class BreakPieces {
    private static final String PLUS = "+";
    private static final String NEW_LINE = "\n";
    private static final String VERTICAL_LINE = "|";
    private static final String HORIZONTAL_LINE = "-";
    private static final String SPACE = " ";
    private static final String HORIZONTAL_REGEX = "\\+-+(:?\\+*-*)*\\+";
    private static final String VERTICAL_REGEX = "\\+\\|+(:?\\+*\\|*)*\\+";


    private static int length = 0;

    public static String[] process(String shape) {
        length = shape.indexOf(NEW_LINE);
        String transposedShape = transpose(shape);
        String[] array = shape.split(NEW_LINE);
        String[] transposedArray = transposedShape.split(NEW_LINE);
        Set<Coordinate> pluses = findAllPluses(shape);

        Map<Coordinate, List<Coordinate>> sameX = new HashMap<>();
        Map<Coordinate, List<Coordinate>> sameY = new HashMap<>();
        for (Coordinate p1 : pluses) {
            for (Coordinate p2 : pluses) {
                if (p1 == p2) {
                } else if (p1.x() == p2.x()) {
                    putElement(sameX, p1, p2);
                } else if (p1.y() == p2.y()) {
                    putElement(sameY, p1, p2);
                }
            }
        }

        //todo----------------------------------------------------------------------------------------------------
        // TODO: 13.10.2023 refactor
        Map<Coordinate, List<Coordinate>> right = sameX.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e ->
                        e.getValue().stream()
                                .filter(v -> v.y() > e.getKey().y())
                                .filter(v -> array[e.getKey().x()].length() >= v.y() + 1)
                                .filter(v -> array[e.getKey().x()].substring(e.getKey().y(), v.y() + 1).matches(HORIZONTAL_REGEX))
                                .sorted()
                                .toList()))
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isEmpty()).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<Coordinate, List<Coordinate>> down = sameY.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e ->
                        e.getValue().stream()
                                .filter(v -> v.x() > e.getKey().x())
                                .filter(v -> transposedArray[e.getKey().y()].substring(e.getKey().x(), v.x() + 1).matches(VERTICAL_REGEX))
                                .sorted()
                                .toList()))
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<Coordinate, List<Coordinate>> left = sameX.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e ->
                        e.getValue().stream()
                                .filter(v -> v.y() < e.getKey().y())
                                .filter(v -> array[e.getKey().x()].length() >= e.getKey().y() + 1)
                                .filter(v -> array[e.getKey().x()].substring(v.y(), e.getKey().y() + 1).matches(HORIZONTAL_REGEX))
                                .sorted(Comparator.reverseOrder())
                                .toList()))
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isEmpty()).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<Coordinate, List<Coordinate>> up = sameY.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e ->
                        e.getValue().stream()
                                .filter(v -> v.x() < e.getKey().x())
                                .filter(v -> transposedArray[e.getKey().y()].substring(v.x(), e.getKey().x() + 1).matches(VERTICAL_REGEX))
                                .sorted()
                                .toList()))
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        //todo----------------------------------------------------------------------------------------------------


        Set<Coordinate[]> c0Set = new HashSet<>();
        Set<Coordinate[]> c1Set = new HashSet<>();
        Set<Coordinate[]> c2Set = new HashSet<>();
        List<Coordinate[]> figures = new ArrayList<>();
        right.keySet().forEach(k -> c0Set.add(new Coordinate[]{k, null, null, null}));
        for (Coordinate[] c0 : c0Set) {
            for (Coordinate c1 : right.getOrDefault(c0[0], new ArrayList<>())) {
                Coordinate[] a = Arrays.copyOf(c0, c0.length);
                a[1] = c1;
                c1Set.add(a);
            }
        }
        for (Coordinate[] c1 : c1Set) {
            for (Coordinate c2 : down.getOrDefault(c1[1], new ArrayList<>())) {
                Coordinate[] a = Arrays.copyOf(c1, c1.length);
                a[2] = c2;
                c2Set.add(a);
            }
        }
        for (Coordinate[] c2 : c2Set) {
            for (Coordinate c3 : left.getOrDefault(c2[2], new ArrayList<>())) {
                // validate that it's square
                if (c3.y() != c2[0].y() || !transposedArray[c3.y()].substring(c2[0].x(), c3.x() + 1).matches(VERTICAL_REGEX)) {
                    continue;
                }
                Coordinate[] a = Arrays.copyOf(c2, c2.length);
                a[3] = c3;
                figures.add(a);
            }
        }


        if (figures.isEmpty()) {
            return new String[]{shape};
        }


        Comparator<Coordinate[]> comparatorA0 = Comparator.comparing(a -> a[0]);
        Comparator<Coordinate[]> arrayComparator = comparatorA0.thenComparing(a -> a[1]).thenComparing(a -> a[2]).thenComparing(a -> a[3]);
        figures.sort(arrayComparator);
        Set<Coordinate[]> resultFigures = new HashSet<>();
        Set<Coordinate> processedPoints = new HashSet<>();
        for (Coordinate c : pluses) {
            if (processedPoints.contains(c)) {
                continue;
            }
            boolean found = false;
            figures.removeAll(resultFigures);
            for (Coordinate[] figure : figures) {
                if (c.equals(figure[0])) {
                    found = true;
                    resultFigures.add(figure);

                    // first plus point is always processed if rectangular was found
                    processedPoints.add(c);
                    // if second coordinate is the most right plus point it's also assumed as processed
                    if (right.get(figure[1]) == null) {
                        processedPoints.add(figure[1]);
                    }

                    if (down.get(figure[2]) == null) {
                        processedPoints.add(figure[2]);
                    }
                    if (down.get(figure[3]) == null) {
                        processedPoints.add(figure[3]);
                    }

                    break;
                }
            }
            //if rectangular wasn't found for plus point, thr figure cannot be fully divided on rectangulars
            if (!found) {
                return new String[]{shape};
            }
        }


        var a = resultFigures.stream()
                .map(BreakPieces::createSquare)
                .peek(System.out::println)
                .toArray(String[]::new);

        Arrays.sort(a);
        return a;
    }

    private static Map<Coordinate, List<Coordinate>> putElement(Map<Coordinate, List<Coordinate>> map, Coordinate key, Coordinate value) {
        var list = map.getOrDefault(key, new ArrayList<>());
        list.add(value);
        map.put(key, list);
        return map;
    }

    private static String createSquare(Coordinate[] square) {
        Arrays.sort(square);
        int length = square[1].y() - square[0].y() - 1;
        int height = square[2].x() - square[1].x() - 1;
        StringBuilder builder = new StringBuilder();

        // first/last row
        String firstLastRow = builder.append(PLUS)
                .append(HORIZONTAL_LINE.repeat(length))
                .append(PLUS)
                .append(NEW_LINE)
                .toString();

        String middleLine = VERTICAL_LINE + SPACE.repeat(length) + VERTICAL_LINE + NEW_LINE;
        builder.append(middleLine.repeat(height));
        builder.append(firstLastRow);
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    private static Set<Coordinate> findAllPluses(String shape) {
        Set<Coordinate> result = new HashSet<>();
        shape = shape.replaceAll(NEW_LINE, "");
        int index = shape.indexOf(PLUS);
        while (index >= 0) {
            result.add(new Coordinate(index / length, index % length));
            index = shape.indexOf(PLUS, index + 1);
        }

        return result.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static String transpose(String shape) {
        String[] rows = shape.split(NEW_LINE);
        Optional<Integer> max = Arrays.stream(rows).map(String::length).max(Integer::compare);

        String[][] source = new String[rows.length][max.get()];
        String[][] result = new String[max.get()][rows.length];

        for (int i = 0; i < rows.length; i++) {
            String[] row = rows[i].split("");
            System.arraycopy(row, 0, source[i], 0, row.length);
        }

        for (int i = 0; i < source[0].length; i++) {
            for (int j = 0; j < source.length; j++) {
                result[i][j] = source[j][i];
            }
        }
        StringBuilder sb = new StringBuilder(shape.length());
        for (String[] array : result) {
            sb.append(String.join("", array));
            sb.append("\n");
        }
        return sb.toString();
    }

    private record Coordinate(int x, int y) implements Comparable<Coordinate> {
        @Override
        public int compareTo(Coordinate o) {
            return 0 == x - o.x() ? y - o.y() : x - o.x();
        }
    }
}