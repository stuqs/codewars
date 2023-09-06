package Walking_in_the_NASA_facility;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CheckFacility {
    @Test
    public void testCollision() {
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
}