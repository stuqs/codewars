package Break_the_pieces;

import org.junit.Test;

import java.util.Arrays;
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
                    })
            ,
            Map.entry(
                    new String[]{
                            "Test 2",
                            "    +---+\n    |   |\n+---+   |\n|       |\n+-------+"
                    },
                    new String[]{
                            "    +---+\n    |   |\n+---+   |\n|       |\n+-------+"
                    }
            )
            ,
            Map.entry(
                    new String[]{
                            "Test 3",
                            "                 +--+\n                 |  |\n                 |  |\n+----------------+  |\n|                   |\n|                   |\n+-------------------+\n"
                    },
                    new String[]{
                            "                 +--+\n                 |  |\n                 |  |\n+----------------+  |\n|                   |\n|                   |\n+-------------------+\n"
                    }
            )
            ,
            Map.entry(
                    new String[]{
                            "Test 4",
                            "+-------------------+\n|                   |\n|                   |\n|  +----------------+\n|  |                 \n|  |                 \n+--+                 "
                    },
                    new String[]{
                            "+-------------------+\n|                   |\n|                   |\n|  +----------------+\n|  |                 \n|  |                 \n+--+                 "
                    }
            )
            ,
            Map.entry(
                    new String[]{
                            "Test 5",
                            "+------------+            +-------------+\n|            |            |             |\n|            |            |             |\n|            |            |             |\n+------+-----+------------+-------------+\n|      |     |            |             |\n|      |     |            |             |\n+------+-----+------------+-------------+"
                    },
                    new String[]{
                            "+------------+\n|            |\n|            |\n|            |\n+------------+",
                            "+------+\n|      |\n|      |\n+------+",
                            "+-----+\n|     |\n|     |\n+-----+",
                            "+------------+\n|            |\n|            |\n+------------+",
                            "+-------------+\n|             |\n|             |\n|             |\n+-------------+",
                            "+-------------+\n|             |\n|             |\n+-------------+"
                    }
            )
            ,
            Map.entry(
                    new String[]{
                            "Test 6",
                            "+------------+          \n|            |    +----+\n|            |    |    |\n|            |    |    |\n+------+-----+----+    |\n|      |     |         |\n|      |     |         |\n+------+-----+---------+"
                    },
                    new String[]{
                            "+------------+\n|            |\n|            |\n|            |\n+------------+",
                            "+------+\n|      |\n|      |\n+------+",
                            "+-----+\n|     |\n|     |\n+-----+",
                            "     +----+\n     |    |\n     |    |\n+----+    |\n|         |\n|         |\n+---------+"
                    }
            )
            ,
            Map.entry(
                    new String[]{
                            "Test 7",
                            "                 \n   +-----+       \n   |     |       \n   |     |       \n   +-----+-----+ \n         |     | \n         |     | \n         +-----+ "
                    },
                    new String[]{
                            "+-----+\n|     |\n|     |\n+-----+",
                            "+-----+\n|     |\n|     |\n+-----+"
                    }
            )
            ,
            Map.entry(
                    new String[]{
                            "Test 8",
                            "           +-+             \n           | |             \n         +-+-+-+           \n         |     |           \n      +--+-----+--+        \n      |           |        \n   +--+-----------+--+     \n   |                 |     \n   +-----------------+     "
                    },
                    new String[]{
                            "+-+\n| |\n+-+",
                            "+-----+\n|     |\n+-----+",
                            "+-----------+\n|           |\n+-----------+",
                            "+-----------------+\n|                 |\n+-----------------+"
                    }
            )
            ,
            Map.entry(
                    new String[]{
                            "Test 9",
                            "+-----------------+\n|                 |\n|   +-------------+\n|   |\n|   |\n|   |\n|   +-------------+\n|                 |\n|                 |\n+-----------------+"
                    },
                    new String[]{
                            "+-----------------+\n|                 |\n|   +-------------+\n|   |\n|   |\n|   |\n|   +-------------+\n|                 |\n|                 |\n+-----------------+"
                    }
            )
            ,
            Map.entry(
                    new String[]{
                            "Test 10",
                            "+-------------------+--+\n|                   |  |\n|                   |  |\n|  +----------------+  |\n|  |                   |\n|  |                   |\n+--+-------------------+"
                    },
                    new String[]{
                            "                 +--+\n                 |  |\n                 |  |\n+----------------+  |\n|                   |\n|                   |\n+-------------------+",
                            "+-------------------+\n|                   |\n|                   |\n|  +----------------+\n|  |\n|  |\n+--+"
                    }
            )
    );

    @Test
    public void simpleTest() {
        for (Map.Entry<String[], String[]> e : DATA.entrySet()) {
            System.out.println("Processing test - " + e.getKey()[0]);
            String[] actual = BreakPieces.process(e.getKey()[1]);
            String[] expected = e.getValue();
            Arrays.sort(actual);
            Arrays.sort(expected);
            assertEquals("Failed on " + e.getKey()[0], expected, actual);
        }
    }
}