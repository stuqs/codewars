package Walking_in_the_NASA_facility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class NASAFacility_Alice_Solution_1 {


    private static final String DELIMITER = " ";
    private static final int STEP_FRAGMENTATION = 2;

    private static final Map<Character, String> directions = Map.of(
            'u', "-1" + DELIMITER + "0",
            'd', "1" + DELIMITER + "0",
            'r', "0" + DELIMITER + "1",
            'l', "0" + DELIMITER + "-1");

    public static int collision(char[][] room) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        int start = 0;
        int xMaxLength = room.length;
        int yMaxLength = room[start].length;

        for (int x = start; x < xMaxLength; x++) {
            yMaxLength = Integer.max(yMaxLength, room[x].length);

            for (int y = start; y < yMaxLength; y++) {
                if (!isStepAvailable(room, x, y) || !directions.containsKey(room[x][y])) {
                    continue;
                }

                String[] instructions = directions.get(room[x][y]).split(DELIMITER);
                var xInc = new BigDecimal(instructions[0]);
                var yInc = new BigDecimal(instructions[1]);

                BigDecimal currentStepX = new BigDecimal(x);
                BigDecimal currentStepY = new BigDecimal(y);

                ArrayList<String> steps = new ArrayList<>();

                do {
                    steps.add(currentStepX + DELIMITER + currentStepY);
                    steps.add(calculateHalfStep(currentStepX, xInc) + DELIMITER + calculateHalfStep(currentStepY, yInc));
                    currentStepX = currentStepX.add(xInc);
                    currentStepY = currentStepY.add(yInc);
                }
                while (isStepAvailable(room, currentStepX.intValue(), currentStepY.intValue()));
                result.add(steps);
            }
        }

        // if the steps go out from actual field it doesn't matter, they will never intersect
        int maxPossibleDirectionLength = Integer.max(xMaxLength, yMaxLength) * STEP_FRAGMENTATION;

        // check if there are intersections on each half-step
        // grab all coordinates of it
        // check if there are the same
        for (int step = start; step < maxPossibleDirectionLength; step++) {
            List<String> stepCoordinates = new ArrayList<>();
            for (ArrayList<String> direction : result) {
                if (direction.size() > step) {
                    String s = direction.get(step);
                    stepCoordinates.add(s);
                }
            }
            boolean matched = new HashSet<>(stepCoordinates).size() != stepCoordinates.size();

            if (matched) {
                return new BigDecimal(step).divide(new BigDecimal(STEP_FRAGMENTATION), RoundingMode.UP).intValue();
            }

        }
        return -1;
    }

    private static BigDecimal calculateHalfStep(BigDecimal coordinate, BigDecimal step) {
        return coordinate.add(step.divide(new BigDecimal(STEP_FRAGMENTATION))).stripTrailingZeros();
    }

    private static boolean isStepAvailable(char[][] mass, int x, int y) {
        try {
            char coordinate = mass[x][y];
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
}