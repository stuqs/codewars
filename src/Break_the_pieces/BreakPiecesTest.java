package Break_the_pieces;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


public class BreakPiecesTest {
    @Test
    public void simpleTest() {
        String shape = String.join("\n", new String[]{"+------------+",
                "|            |",
                "|            |",
                "|            |",
                "+------+-----+",
                "|      |     |",
                "|      |     |",
                "+------+-----+"});
        String expected[] = {String.join("\n", new String[]{"+------------+",
                "|            |",
                "|            |",
                "|            |",
                "+------------+"}),
                String.join("\n", new String[]{"+------+",
                        "|      |",
                        "|      |",
                        "+------+"}),
                String.join("\n", new String[]{"+-----+",
                        "|     |",
                        "|     |",
                        "+-----+"})};
        String actual[] = BreakPieces.process(shape);
        Arrays.sort(expected);
        Arrays.sort(actual);
        assertEquals(expected, actual);
    }
}