package BinaryCalculator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class BinaryCalculatorTest {

    @Test
    public void test_1() {
        assertEquals("10", BinaryCalculator.calculate("1", "1", "add"));
    }

    @Test
    public void test_2() {
        assertEquals("0", BinaryCalculator.calculate("1", "1", "subtract"));
    }

    @Test
    public void test_3() {
        assertEquals("1", BinaryCalculator.calculate("1", "1", "multiply"));
    }

    @Test
    public void test_4() {
        assertEquals("0", BinaryCalculator.calculate("1", "0", "multiply"));
    }

    @Test
    public void test_5() {
        try {
            assertEquals("100", BinaryCalculator.calculate("10", "10", "multiply"));
        }
        //Catch exception because of https://github.com/Codewars/codewars.com/issues/21
        catch (Throwable e) {
            fail("No exception expected");
        }
    }

    @Test
    public void test_6() {
        try {
            assertEquals("10", BinaryCalculator.calculate("100", "10", "subtract"));
        }
        //Catch exception because of https://github.com/Codewars/codewars.com/issues/21
        catch (Throwable e) {
            fail("No exception expected");
        }
    }

    @Test
    public void test_7() {
        try {
            final String result = BinaryCalculator.calculate("10", "100", "subtract");
            //Due to kata description, negative values CAN be represented with negative symbol. Thus, both
            //solutions are valid.
            assertTrue("Expected -10 or 11111111111111111111111111111110, but got: " + result,
                    "-10".equals(result) || "11111111111111111111111111111110".equals(result));
        }
        //Catch exception because of https://github.com/Codewars/codewars.com/issues/21
        catch (Throwable e) {
            fail("No exception expected");
        }
    }

    @Test
    public void test_8() {
        try {
            assertEquals("1001010111001001001", BinaryCalculator.calculate("1001010110011111100", "101001101", "add"));
        }
        //Catch exception because of https://github.com/Codewars/codewars.com/issues/21
        catch (Throwable e) {
            fail("No exception expected");
        }
    }

    @Test
    public void test_9() {
        try {
            assertEquals("1011101111010111", BinaryCalculator.calculate("1011110000000101", "101110", "subtract"));
        }
        //Catch exception because of https://github.com/Codewars/codewars.com/issues/21
        catch (Throwable e) {
            fail("No exception expected");
        }
    }

    @Test
    public void test_10() {
        try {
            assertEquals("101001101111111", BinaryCalculator.calculate("101110111", "111001", "multiply"));
        }
        //Catch exception because of https://github.com/Codewars/codewars.com/issues/21
        catch (Throwable e) {
            fail("No exception expected");
        }
    }
}