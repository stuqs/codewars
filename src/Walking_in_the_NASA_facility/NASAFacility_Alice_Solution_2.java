package Walking_in_the_NASA_facility;

import java.util.Map;

public class NASAFacility_Alice_Solution_2 {

    private static final String DELIMITER = ":";

    private static final Map<Character, String> DIRECTIONS = Map.of(
            'u', "-1" + DELIMITER + "0",
            'd', "1" + DELIMITER + "0",
            'r', "0" + DELIMITER + "1",
            'l', "0" + DELIMITER + "-1");

    private static final Map<Character, Character> OPPOSITES = Map.of(
            'u', 'd',
            'd', 'u',
            'r', 'l',
            'l', 'r');

    public static int collision(char[][] room) {
        int start = 0;
        int resultStep = -1;

        for (int x = start; x < room.length; x++) {
            for (int y = start; y < room[x].length; y++) {
                char current = room[x][y];
                if (!isStepAvailable(room, x, y) || !DIRECTIONS.containsKey(current)) {
                    continue;
                }

                String[] instructions = DIRECTIONS.get(current).split(DELIMITER);
                int xInc = Integer.parseInt(instructions[0]);
                int yInc = Integer.parseInt(instructions[1]);

                int currentStepX = x;
                int currentStepY = y;

                for (int stepsCount = 1; isStepAvailable(room, currentStepX + xInc, currentStepY + yInc); stepsCount++) {

                    currentStepX = currentStepX + xInc;
                    currentStepY = currentStepY + yInc;

                    char currentStepChar = room[currentStepX][currentStepY];

                    var faced = OPPOSITES.get(currentStepChar);

                    if ((Character) current == faced) { // when faced its antagonist
                        int stepToAntagonist = stepsCount / 2 + stepsCount % 2;
                        resultStep = resultStep == -1 ? stepToAntagonist : Integer.min(stepToAntagonist, resultStep);
                    } else {
                        for (Map.Entry<Character, String> entry : DIRECTIONS.entrySet()) {
                            // need only 90 deg directions
                            // so skip itself and its antagonist, it was processed earlier
                            if (entry.getKey() == OPPOSITES.get(current) || entry.getKey() == current) {
                                continue;
                            }

                            String[] instructionsSide = entry.getValue().split(DELIMITER);
                            int xIncSide = Integer.parseInt(instructionsSide[0]);
                            int yIncSide = Integer.parseInt(instructionsSide[1]);
                            int sideStepX = currentStepX - stepsCount * xIncSide;
                            int sideStepY = currentStepY - stepsCount * yIncSide;
                            if (isStepAvailable(room, sideStepX, sideStepY) && room[sideStepX][sideStepY] == entry.getKey()) {
                                resultStep = resultStep == -1 ? stepsCount : Integer.min(stepsCount, resultStep);
                            }
                        }
                    }

                    if (resultStep == 1) {
                        return resultStep;
                    }
                }
            }
        }
        return resultStep;
    }

    private static boolean isStepAvailable(char[][] mass, int x, int y) {
        return x >= 0 && y >= 0 && mass.length > x && mass[x].length > y;
    }
}
