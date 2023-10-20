package Break_the_pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BreakPieces {
    private static final String PLUS = "+";
    private static final String NEW_LINE = "\n";
    private static final String VERTICAL_LINE = "|";
    private static final String HORIZONTAL_LINE = "-";
    private static final String SPACE = " ";
    private static final String HORIZONTAL_REGEX = "\\+-+(:?\\+*-*)*\\+";
    private static final String HORIZONTAL = "h";
    private static final String VERTICAL = "v";

    private static String[] array;
    private static Map<Coordinate, List<Coordinate>> right;
    private static Map<Coordinate, List<Coordinate>> down;
    private static Map<Coordinate, List<Coordinate>> left;
    private static Map<Coordinate, List<Coordinate>> up;

    public static String[] process(String shape) {
        array = shape.split(NEW_LINE);
        List<Coordinate> pluses = findAllPluses();
        initializeMaps(pluses);

        List<List<Coordinate>> figures = new ArrayList<>();
        Set<Coordinate> processed = new HashSet<>(pluses.size());
        Coordinate startPoint = firstUnprocessed(pluses, processed);
        do {
            List<Coordinate> figure = getNextFigure(startPoint);
            if (figure == null) {
                break;
            }

            // mark processed coordinates
            for (Coordinate c : figure) {
                if (right.get(c) == null || down.get(c) == null) {
                    processed.add(c);
                } else {
                    boolean allDotsAroundInFigure = Stream.of(right, down)
                            .map(m -> m.get(c))
                            .filter(Objects::nonNull)
                            .map(l -> l.get(0))
                            .allMatch(figure::contains);
                    if (allDotsAroundInFigure) {
                        processed.add(c);
                    }
                }
            }
            optimiseFigure(figure);

            figures.add(figure);
            startPoint = firstUnprocessed(pluses, processed);
        } while (startPoint != null);

        // the shape cannot be fully divided on multiple figures
        if (figures.size() == 1) {
            return new String[]{shape};
        }

        return figures.stream()
                .map(BreakPieces::drawFigure)
                .toArray(String[]::new);
    }

    private static void putElement(Map<Coordinate, List<Coordinate>> map, Coordinate key, Coordinate value) {
        var list = map.getOrDefault(key, new ArrayList<>());
        list.add(value);
        map.put(key, list);
    }

    private static Coordinate firstUnprocessed(List<Coordinate> pluses, Set<Coordinate> processed) {
        if (pluses.size() == processed.size()) {
            return null;
        }
        List<Coordinate> copy = new ArrayList<>(pluses);
        copy.removeAll(processed);

        return copy.get(0);
    }

    private static List<Coordinate> getNextFigure(Coordinate startPoint) {
        List<Coordinate> currentFigure = new ArrayList<>();
        currentFigure.add(startPoint);
        currentFigure.add(right.get(currentFigure.get(0)).get(0));
        Coordinate next;
        do {
            Coordinate previous = currentFigure.get(currentFigure.size() - 2);
            Coordinate last = currentFigure.get(currentFigure.size() - 1);
            next = getNextPoint(last, previous);
            if (next == null) {
                return null;
            } else if (!next.equals(currentFigure.get(0))) {
                currentFigure.add(next);
            }
        } while (!currentFigure.get(0).equals(next));

        return currentFigure;
    }

    private static void optimiseFigure(List<Coordinate> figure) {
        List<String> directions = computeDirections(figure);
        int removed = 0;
        for (int i = 1; i < directions.size(); i++) {
            if (directions.get(i - 1).equals(directions.get(i))) {
                figure.remove(i - removed);
                removed++;
            }
        }
    }

    private static String drawFigure(List<Coordinate> figure) {
        figure.add(figure.get(0));
        List<String> directions = computeDirections(figure);

        var sourceX = figure.stream()
                .map(Coordinate::x)
                .collect(Collectors.toSet());
        int minX = sourceX.stream().min(Integer::compareTo).orElse(0);
        int maxX = sourceX.stream().max(Integer::compareTo).orElse(0);
        Set<Integer> sourceY = figure.stream()
                .map(Coordinate::y)
                .collect(Collectors.toSet());
        int minY = sourceY.stream().min(Integer::compareTo).orElse(0);
        int maxY = sourceY.stream().max(Integer::compareTo).orElse(0);
        String[][] matrix = new String[maxX - minX + 1][maxY - minY + 1];

        for (int i = 1; i < figure.size(); i++) {
            var a = figure.get(i - 1);
            var b = figure.get(i);
            int aX = a.x() - minX;
            int aY = a.y() - minY;
            int bX = b.x() - minX;
            int bY = b.y() - minY;
            matrix[aX][aY] = PLUS;
            matrix[bX][bY] = PLUS;

            if (HORIZONTAL.equals(directions.get(i - 1))) {
                IntStream.range(Math.min(aY, bY) + 1, Math.max(aY, bY))
                        .forEach(y -> matrix[aX][y] = HORIZONTAL_LINE);
            } else {
                IntStream.range(Math.min(aX, bX) + 1, Math.max(aX, bX))
                        .forEach(x -> matrix[x][aY] = VERTICAL_LINE);
            }
        }

        for (String[] strings : matrix) {
            int i;
            for (i = strings.length - 1; i > 0; i--) {
                if (strings[i] != null) {
                    break;
                }
            }
            for (int j = 0; j < i; j++) {
                if (strings[j] == null) {
                    strings[j] = SPACE;
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < matrix.length - 1; i++) {
            builder.append(String.join("", matrix[i]).replace("null", ""))
                    .append(NEW_LINE);
        }
        builder.append(String.join("", matrix[matrix.length - 1]).replace("null", ""));
        return builder.toString();
    }

    private static List<String> computeDirections(List<Coordinate> figure) {
        List<String> directions = new ArrayList<>();
        for (int i = 1; i < figure.size(); i++) {
            var a1 = figure.get(i - 1);
            var a2 = figure.get(i);

            if (a2.x() - a1.x() == 0) {
                directions.add(HORIZONTAL);
            } else {
                directions.add(VERTICAL);
            }
        }
        return directions;
    }

    private static List<Coordinate> findAllPluses() {
        List<Coordinate> result = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            int index = array[i].indexOf(PLUS);
            while (index >= 0) {
                result.add(new Coordinate(i, index));
                index = array[i].indexOf(PLUS, index + 1);
            }
        }
        Collections.sort(result);
        return result;
    }


    private static Coordinate getNextPoint(Coordinate last, Coordinate previous) {
        int x = last.x() - previous.x();
        int y = last.y() - previous.y();
        if (x == 0) {
            if (y > 0) {
                return Optional.ofNullable(down.getOrDefault(last, right.getOrDefault(last, up.get(last)))).map(List::iterator).map(Iterator::next).orElse(null);
                //right - down/right/up
            } else {
                return Optional.ofNullable(up.getOrDefault(last, left.getOrDefault(last, down.get(last)))).map(List::iterator).map(Iterator::next).orElse(null);
                //left  - up/left/down
            }
        } else {
            if (x > 0) {
                return Optional.ofNullable(left.getOrDefault(last, down.getOrDefault(last, right.get(last)))).map(List::iterator).map(Iterator::next).orElse(null);
                //down - left/down/right
            } else {
                return Optional.ofNullable(right.getOrDefault(last, up.getOrDefault(last, left.get(last)))).map(List::iterator).map(Iterator::next).orElse(null);
                //up   - right/up/left
            }
        }
    }

    private static void initializeMaps(List<Coordinate> pluses) {
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

        right = sameX.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e ->
                        e.getValue().stream()
                                .filter(v -> v.y() > e.getKey().y())
                                .filter(v -> array[e.getKey().x()].substring(e.getKey().y(), v.y() + 1).matches(HORIZONTAL_REGEX))
                                .sorted()
                                .toList()))
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isEmpty()).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        down = sameY.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e ->
                        e.getValue().stream()
                                .filter(v -> v.x() > e.getKey().x())
                                .filter(v -> {
                                    for (int i = e.getKey().x(); i <= v.x(); i++) {
                                        if (array[i].length() < v.y()) {
                                            return false;
                                        }
                                        if (SPACE.equals(array[i].substring(v.y, v.y + 1))) {
                                            return false;
                                        }
                                    }
                                    return true;
                                })
                                .sorted()
                                .toList()))
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        left = sameX.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e ->
                        e.getValue().stream()
                                .filter(v -> v.y() < e.getKey().y())
                                .filter(v -> array[e.getKey().x()].substring(v.y(), e.getKey().y() + 1).matches(HORIZONTAL_REGEX))
                                .sorted(Comparator.reverseOrder())
                                .toList()))
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isEmpty()).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        up = sameY.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e ->
                        e.getValue().stream()
                                .filter(v -> v.x() < e.getKey().x())
                                .filter(v -> {
                                    for (int i = v.x(); i <= e.getKey().x(); i++) {
                                        if (array[i].length() < v.y()) {
                                            return false;
                                        }
                                        if (SPACE.equals(array[i].substring(v.y, v.y + 1))) {
                                            return false;
                                        }
                                    }
                                    return true;
                                })
                                .sorted(Comparator.reverseOrder())
                                .toList()))
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private record Coordinate(int x, int y) implements Comparable<Coordinate> {
        @Override
        public int compareTo(Coordinate o) {
            return 0 == x - o.x() ? y - o.y() : x - o.x();
        }
    }
}