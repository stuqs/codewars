package Break_the_pieces;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;


public class BreakPiecesTest {
    private static final Map<String[], String[]> DATA = Map.ofEntries(
            Map.entry(
                    new String[]{
                            "Test 1",
                            "+------------+\n|            |\n|            |\n|            |\n+------+-----+\n|      |     |\n|      |     |\n+------+-----+"
                    },
                    new String[]{
                            "+------------+\n|            |\n|            |\n|            |\n+------------+",
                            "+------+\n|      |\n|      |\n+------+",
                            "+-----+\n|     |\n|     |\n+-----+"
                    }),

            Map.entry(
                    new String[]{
                            "Test 2",
                            "    +---+\n    |   |\n+---+   |\n|       |\n+-------+"
                    },
                    new String[]{
                            "    +---+\n    |   |\n+---+   |\n|       |\n+-------+"
                    }
            )

    );

    @Test
    public void simpleTest() {
        for (Map.Entry<String[], String[]> e : DATA.entrySet()) {
            System.out.println("Processing test - " + e.getKey()[0]);
            String[] actual = BreakPieces.process(e.getKey()[1]);
            String[] expected = e.getValue();
            assertEquals(expected, actual);
        }
    }
}