package Walking_in_the_NASA_facility;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CheckFacility {
    @Test
    public void testCollision() {
        assertEquals(2, NASAFacility.collision(new char[][]{
                {'-', '-', '-', '-', '-', 'd', '-', '-', '-', '-', '-'},
                {'-', '-', 'l', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'u', '-', '-', '-', '-'},
                {'-'}}));


        assertEquals(1, NASAFacility.collision(new char[][]{
                "-r--l".toCharArray(),
                "-----".toCharArray(),
                "-u-d-".toCharArray(),
                "----l".toCharArray(),
                "-----".toCharArray(),
        }));
        assertEquals(2, NASAFacility.collision(new char[][]{
                "-----".toCharArray(),
                "--d-l".toCharArray(),
                "-----".toCharArray(),
                "rr--u".toCharArray(),
                "--l--".toCharArray(),
        }));
        assertEquals(2, NASAFacility.collision(new char[][]{
                "-----".toCharArray(),
                "---ur".toCharArray(),
                "rd--l".toCharArray(),
                "d-r--".toCharArray(),
                "-r---".toCharArray(),
        }));
        assertEquals(-1, NASAFacility.collision(new char[][]{
                "-u---".toCharArray(),
                "---uu".toCharArray(),
                "---r-".toCharArray(),
                "--r--".toCharArray(),
                "-d---".toCharArray(),
        }));
    }

    public static void main1(String[] args) {

        var test1 = new char[][]{
                {'-', '-', '-', '-'},
                {'-', 'd', '-', 'u'},
                {'-', 'u', '-', 'l'},
                {'r', '-', '-', '-'},
        };

        assertEquals(1, NASAFacility.collision(test1));


        var test2 = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'r', 'l', '-'}
        };

        assertEquals(1, NASAFacility.collision(test2));

        var test3 = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'u', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
        };

        assertEquals(1, NASAFacility.collision(test3));

        var test4 = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', 'l'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
        };

        assertEquals(2, NASAFacility.collision(test4));

        var test5 = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', 'r'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
        };

        assertEquals(-1, NASAFacility.collision(test5));

        var test6 = new char[][]{
                {'d', '-', '-', '-', 'l'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', 'r', 'l', '-', 'r'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'u', 'r', 'u'},
        };

        assertEquals(1, NASAFacility.collision(test6));

        var test7 = new char[][]{
                "----d-".toCharArray(),
                "--u-----".toCharArray(),
                "-".toCharArray(),
                "u---u".toCharArray(),
                "----u".toCharArray(),
        };
//        assertEquals(-1, NASAFacility.collision(test7));

        var test8 = new char[][]{
                "-r--l".toCharArray(),
                "-----".toCharArray(),
                "-u-d-".toCharArray(),
                "----l".toCharArray(),
                "-----".toCharArray(),
        };
        assertEquals(1, NASAFacility.collision(test8));

        var test9 = new char[][]{
                "-----".toCharArray(),
                "--d-l".toCharArray(),
                "-----".toCharArray(),
                "rr--u".toCharArray(),
                "--l--".toCharArray(),
        };
        assertEquals(2, NASAFacility.collision(test9));

        var test10 = new char[][]{
                "-----".toCharArray(),
                "---ur".toCharArray(),
                "rd--l".toCharArray(),
                "d-r--".toCharArray(),
                "-r---".toCharArray(),
        };
        assertEquals(2, NASAFacility.collision(test10));

        var test11 = new char[][]{
                "-u---".toCharArray(),
                "---uu".toCharArray(),
                "---r-".toCharArray(),
                "--r--".toCharArray(),
                "-d---".toCharArray(),
        };
        assertEquals(-1, NASAFacility.collision(test11));

        var test12 = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'u', '-', '-'},
                {'-', '-', '-', '-', '-'},
        };

        assertEquals(2, NASAFacility.collision(test12));

        var test13 = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', 'd'},
                {'-', '-', '-', '-', 'u'},
        };

        assertEquals(1, NASAFacility.collision(test13));

        var test14 = new char[][]{
                {'-', 'd'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-'},
                {'-', '-', '-', '-', 'l'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-'},
                {'-', '-', '-', '-', 'u'},
        };

        assertEquals(3, NASAFacility.collision(test14));
    }

    @Test
    public void test1() {
        var test1 = new char[][]{
                {'-', '-', '-', '-'},
                {'-', 'd', '-', 'u'},
                {'-', 'u', '-', 'l'},
                {'r', '-', '-', '-'},
        };

        assertEquals(1, collision(test1));
    }

    @Test
    public void test2() {
        var test2 = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'r', 'l', '-'}
        };

        assertEquals(1, collision(test2));
    }

    @Test
    public void test3() {

        var test3 = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'u', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
        };

        assertEquals(1, collision(test3));
    }

    @Test
    public void test4() {

        var test4 = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', 'l'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
        };

        assertEquals(2, collision(test4));
    }

    @Test
    public void test5() {

        var test5 = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', 'r'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
        };

        assertEquals(-1, collision(test5));
    }

    @Test
    public void test6() {

        var test6 = new char[][]{
                {'d', '-', '-', '-', 'l'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', 'r', 'l', '-', 'r'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'u', 'r', 'u'},
        };

        assertEquals(1, collision(test6));
    }

//    @Test
    public void test7() {

        var test7 = new char[][]{
                {'-', '-', '-', '-', 'd', '-'},
                {'-', '-', 'u', '-', '-', '-', '-', '-'},
                {'-'},
                {'u', '-', '-', '-', 'u'},
                {'-', '-', '-', '-', 'u'}
        };
        assertEquals(-1, collision(test7));
    }

    @Test
    public void test8() {

        var test8 = new char[][]{
                {'-', 'r', '-', '-', 'l'},
                {'-', '-', '-', '-', '-'},
                {'-', 'u', '-', 'd', '-'},
                {'-', '-', '-', '-', 'l'},
                {'-', '-', '-', '-', '-'}
        };
        assertEquals(1, collision(test8));
    }

    @Test
    public void test9() {

        var test9 = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'd', '-', 'l'},
                {'-', '-', '-', '-', '-'},
                {'r', 'r', '-', '-', 'u'},
                {'-', '-', 'l', '-', '-'}
        };
        assertEquals(2, collision(test9));
    }

    @Test
    public void test10() {

        var test10 = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', 'u', 'r'},
                {'r', 'd', '-', '-', 'l'},
                {'d', '-', 'r', '-', '-'},
                {'-', 'r', '-', '-', '-'}
        };
        assertEquals(2, collision(test10));
    }

    @Test
    public void test11() {

        var test11 = new char[][]{
                {'-', 'u', '-', '-', '-'},
                {'-', '-', '-', 'u', 'u'},
                {'-', '-', '-', 'r', '-'},
                {'-', '-', 'r', '-', '-'},
                {'-', 'd', '-', '-', '-'}
        };
        assertEquals(-1, collision(test11));
    }

    @Test
    public void test12() {

        var test12 = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'u', '-', '-'},
                {'-', '-', '-', '-', '-'},
        };

        assertEquals(2, collision(test12));
    }

    @Test
    public void test13() {

        var test13 = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', 'd'},
                {'-', '-', '-', '-', 'u'},
        };

        assertEquals(1, collision(test13));
    }

    @Test
    public void test14() {

        var test14 = new char[][]{
                {'-', 'd'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-'},
                {'-', '-', '-', '-', 'l'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-'},
                {'-', '-', '-', '-', 'u'},
        };

        assertEquals(3, collision(test14));
    }

//    @Test
    public void test15() {
        Random random = new Random(0);
        int xLength = Math.abs(500);
        int yLength = Math.abs(500);

        var test15 = new char[xLength][yLength];

        var directions = new char[]{'-', 'l', 'r', 'u', 'd'};

        for (int x = 0; x < xLength; x++) {

            for (int y = 0; y < yLength; y++) {
                test15[x][y] = directions[Math.abs(random.nextInt(5))];
            }
        }

        long start = System.currentTimeMillis();
        assertEquals(1, collision(test15));
        long end = System.currentTimeMillis();
        assertTrue(end - start < 16000);
    }

    @Test
    public void test16() {

        var test16 = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', 'd', '-', '-', '-'},
                {'r', '-', '-', '-', '-'},
                {'-', '-', 'd', 'l', '-'},
                {'-', '-', 'l', '-', '-'},
                {'-', 'u', '-', '-', '-'},
        };

        assertEquals(1, collision(test16));
    }

    @Test
    public void test17() {

        var test = new char[][]{
                {'-', 'r', 'r', 'u', '-'},
                {'-', '-', '-', '-', '-'},
                {'r', 'd', 'd', 'l', 'u'},
                {'r', '-', 'l', '-', '-'},
                {'-', 'd', 'd', 'l', '-'},
                {'-', '-', 'l', '-', '-'},
                {'-', 'u', '-', 'u', '-'},
        };

        assertEquals(1, collision(test));
    }

    @Test
    public void test18() {

        var test = new char[][]{
                {'-', '-', 'd', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'r', '-', '-', '-', 'l'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'u', '-', '-'},
        };

        assertEquals(2, collision(test));
    }

    @Test
    public void test19() {

        var test = new char[][]{
                {'-', '-', 'd', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', 'l'},
                {'-', '-', '-', '-', '-'},
                {'r', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'u', '-', '-'},
        };

        assertEquals(2, collision(test));
    }

    @Test
    public void test20() {

        var test = new char[][]{
                {'-', '-', 'd', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', 'd', '-', '-', '-', '-', '-', 'l'},
        };

        assertEquals(6, collision(test));
    }

    @Test
    public void test21() {

        var test = new char[][]{
                {'-', '-', 'd', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', 'd', '-', '-', '-', '-', '-', 'l'},
        };

        assertEquals(6, collision(test));
    }

    @Test
    public void test22() {

        var test = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', 'l', '-', '-', '-'},
                {'u', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
        };

        assertEquals(1, collision(test));
    }

    @Test
    public void test23() {

        var test = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'd', '-', '-'},
                {'-', '-', '-', 'd', '-'},
                {'-', '-', '-', '-', 'l'},
                {'-', '-', '-', 'u', '-'},
                {'-', '-', 'u', '-', '-'},
        };

        assertEquals(1, collision(test));
    }

    @Test
    public void test24() {

        var test = new char[][]{
                {'-', '-', 'd', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'r', 'u', '-', 'l', '-'},
                {'-', '-', '-', 'l', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', 'u', '-', '-'},
                {'-', '-', '-', '-', '-'},
        };

        assertEquals(2, collision(test));
    }

    @Test
    public void test25() {

        var test = new char[][]{
                {'-', 'r', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', 'u', '-', '-', '-'},
                {'-', '-', '-', 'r', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', 'u'},
        };

        assertEquals(2, collision(test));
    }



    protected int collision(char[][] test) {
        return NASAFacility.collision(test);
    }
}
