package Break_the_pieces_evil;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class BreakEvilPiecesTest {

    /**
     * DATA formatting: Object[][] of Object[] { name of the test (as String),
     * shape (as String),
     * expected list (as List<String>) }
     */

    private static final Object[][] DATA = new Object[][]{
            // Test 1
            new Object[]{"Simple shape",
                    "+----------+\n|          |\n|          |\n|          |\n+----------+\n|          |\n|          |\n+----------+",
                    Arrays.asList("+----------+\n|          |\n|          |\n+----------+", "+----------+\n|          |\n|          |\n|          |\n+----------+"),
                    ""},

            // Test 2
            new Object[]{"",
                    "              \n +----------+ \n |          | \n |          | \n |          | \n +----------+ \n |          | \n |          | \n +----------+ \n              ",
                    Arrays.asList("+----------+\n|          |\n|          |\n+----------+", "+----------+\n|          |\n|          |\n|          |\n+----------+"),
                    ""},

            // Test 3
            new Object[]{"3 boxes",
                    "+------------+\n|            |\n|            |\n|            |\n+------+-----+\n|      |     |\n|      |     |\n+------+-----+",
                    Arrays.asList("+-----+\n|     |\n|     |\n+-----+", "+------+\n|      |\n|      |\n+------+", "+------------+\n|            |\n|            |\n|            |\n+------------+"),
                    ""},

            // Test 4
            new Object[]{"",
                    "                \n +------------+ \n |            | \n |            | \n |            | \n +------+-----+ \n |      |     | \n |      |     | \n +------+-----+ \n                ",
                    Arrays.asList("+-----+\n|     |\n|     |\n+-----+", "+------+\n|      |\n|      |\n+------+", "+------------+\n|            |\n|            |\n|            |\n+------------+"),
                    ""},

            // Test 5
            new Object[]{"Lego stuff",
                    "+-------------------+--+\n|                   |  |\n|                   |  |\n|  +----------------+  |\n|  |                   |\n|  |                   |\n+--+-------------------+",
                    Arrays.asList("                 +--+\n                 |  |\n                 |  |\n+----------------+  |\n|                   |\n|                   |\n+-------------------+", "+-------------------+\n|                   |\n|                   |\n|  +----------------+\n|  |\n|  |\n+--+"),
                    ""},

            // Test 6
            new Object[]{"",
                    "                          \n +-------------------+--+ \n |                   |  | \n |                   |  | \n |  +----------------+  | \n |  |                   | \n |  |                   | \n +--+-------------------+ \n                          ",
                    Arrays.asList("                 +--+\n                 |  |\n                 |  |\n+----------------+  |\n|                   |\n|                   |\n+-------------------+", "+-------------------+\n|                   |\n|                   |\n|  +----------------+\n|  |\n|  |\n+--+"),
                    ""},

            // Test 7
            new Object[]{"Piece of cake! (check for irrelevant spaces)",
                    "                           \n                           \n           +-+             \n           | |             \n         +-+-+-+           \n         |     |           \n      +--+-----+--+        \n      |           |        \n   +--+-----------+--+     \n   |                 |     \n   +-----------------+     \n                           \n                           ",
                    Arrays.asList("+-+\n| |\n+-+", "+-----+\n|     |\n+-----+", "+-----------+\n|           |\n+-----------+", "+-----------------+\n|                 |\n+-----------------+"),
                    ""},

            // Test 8
            new Object[]{"",
                    "                             \n                             \n                             \n            +-+              \n            | |              \n          +-+-+-+            \n          |     |            \n       +--+-----+--+         \n       |           |         \n    +--+-----------+--+      \n    |                 |      \n    +-----------------+      \n                             \n                             \n                             ",
                    Arrays.asList("+-+\n| |\n+-+", "+-----+\n|     |\n+-----+", "+-----------+\n|           |\n+-----------+", "+-----------------+\n|                 |\n+-----------------+"),
                    ""},

            // Test 9
            new Object[]{"Horseshoe (shapes are not always rectangles!)",
                    "+-----------------+\n|                 |\n|   +-------------+\n|   |\n|   |\n|   |\n|   +-------------+\n|                 |\n+-----------------+",
                    Arrays.asList("+-----------------+\n|                 |\n|   +-------------+\n|   |\n|   |\n|   |\n|   +-------------+\n|                 |\n+-----------------+"),
                    ""},

            // Test 10
            new Object[]{"",
                    "                     \n +-----------------+ \n |                 | \n |   +-------------+ \n |   |               \n |   |               \n |   |               \n |   +-------------+ \n |                 | \n +-----------------+ \n                     ",
                    Arrays.asList("+-----------------+\n|                 |\n|   +-------------+\n|   |\n|   |\n|   |\n|   +-------------+\n|                 |\n+-----------------+"),
                    ""},

            // Test 11
            new Object[]{"Warming up",
                    "+------------+\n|            |\n|            |\n|            |\n+------++----+\n|      ||    |\n|      ||    |\n+------++----+",
                    Arrays.asList("++\n||\n||\n++", "+----+\n|    |\n|    |\n+----+", "+------+\n|      |\n|      |\n+------+", "+------------+\n|            |\n|            |\n|            |\n+------------+"),
                    ""},

            // Test 12
            new Object[]{"",
                    "                \n +------------+ \n |            | \n |            | \n |            | \n +------++----+ \n |      ||    | \n |      ||    | \n +------++----+ \n                ",
                    Arrays.asList("++\n||\n||\n++", "+----+\n|    |\n|    |\n+----+", "+------+\n|      |\n|      |\n+------+", "+------------+\n|            |\n|            |\n|            |\n+------------+"),
                    ""},

            // Test 13
            new Object[]{"Don't forget the eggs! (you'll understand later...)",
                    "++\n++",
                    Arrays.asList("++\n++"),
                    ""},
    };


    @Test
    public void breakEvilPieces_Tests() {
        printDescribe("Sample tests");

        for (int n = 0; n < DATA.length; n++) {

            Object[] params = DATA[n];
            String name = (String) params[0],
                    shape = (String) params[1],
                    addMsg = (String) params[3];

            List<String> expected = (List<String>) params[2],
                    actual = BreakEvilPieces.solve(shape);

            if (!name.isEmpty()) {
                System.out.println("\n\n" + name);
            }
            if (!addMsg.isEmpty()) {
                System.out.println("\n" + addMsg);
            }
            System.out.printf("Test #" + (n + 1) + "%n");

            assertShape(shape, actual, expected);
        }
    }

    private void assertShape(String shape, List<String> actual, List<String> expected) {
        expected.sort(null);
        actual.sort(null);

        if (!actual.equals(expected)) {
            System.out.printf(" \nFAILED TEST !!!\n\nShape:\n%s\n\nExpected:\n%s\n\nBut answer was:\n%s%n",
                    shape,
                    String.join(" \n\n", expected),
                    String.join(" \n\n", actual));
            Set<String> tooMuch = new HashSet<>(actual),
                    lacking = new HashSet<>(expected);
            tooMuch.removeAll(expected);
            lacking.removeAll(actual);

            if (!lacking.isEmpty())
                System.out.printf(" \n\nSubshapes that your answer is missing:\n%s%n", strFormat(String.join("\n\n", lacking)));

            if (!tooMuch.isEmpty())
                System.out.printf(" \n\nWrong subshapes that your answer shouldn't create:\n%s%n", strFormat(String.join("\n\n", tooMuch)));
        }

        assertEquals(expected, actual);
    }


    private String strFormat(String s) {
        return s.replaceAll(" ", ".");
    }

    private void printDescribe(String s) {
        System.out.printf(" \n\n*\n* %s\n*%n", s.toUpperCase());
    }
}