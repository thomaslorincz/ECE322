package lab6.part2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MathPackageTest {

    @Test
    public void testMax() {
        double[] list = new double[]{1.0, 2.0, 3.0, 4.0, 5.0};
        double max = MathPackage.max(list);

        assertEquals(5.0, max);
    }

    @Test
    public void testMin() {
        double[] list = new double[]{1.0, 2.0, 3.0, 4.0, 5.0};
        double min = MathPackage.min(list);

        assertEquals(1.0, min);
    }

    @Test
    public void testRandom() {
        double[] random = MathPackage.random(5, 1.0, 5.0);

        assertEquals(5, random.length);
        for (int i = 0; i < random.length; i++) {
            assertTrue(random[i] >= 1.0);
            assertTrue(random[i] <= 5.0);
        }
    }

    @Test
    public void testNormalize() {
        double[] list = new double[]{0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 10.0};
        double[] normalized = MathPackage.normalize(list);
        double[] expected = new double[]{0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 1.0};

        assertArrayEquals(expected, normalized);
    }

    @Test
    public void testSum() {
        double[] list = new double[]{0.0, 1.0, 2.0, 3.0, 4.0, 5.0};
        double sum = MathPackage.sum(list);
        double expected = 15.0;

        assertEquals(expected, sum);
    }

    @Test
    public void testStddev() {
        double[] list = new double[]{0.0, 1.0, 2.0, 3.0, 4.0, 5.0};
        double stddev = MathPackage.stddev(list);
        double expected = 1.7078251276599330639;

        assertEquals(expected, stddev);
    }

    @Test
    public void testArrayAdd() {
        double[] list1 = new double[]{0.0, 1.0, 2.0, 3.0, 4.0, 5.0};
        double[] list2 = new double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
        double[] added = MathPackage.arrayAdd(list1, list2);
        double[] expected = new double[]{1.0, 3.0, 5.0, 7.0, 9.0, 11.0};

        assertArrayEquals(expected, added);
    }

    @Test
    public void testArrayNegate() {
        double[] list = new double[]{0.0, 1.0, 2.0, -2.0, -1.0, 0.0};
        double[] negated = MathPackage.arrayNegate(list);
        double[] expected = new double[]{0.0, -1.0, -2.0, 2.0, 1.0, 0.0};

        assertArrayEquals(expected, negated);
    }
}
