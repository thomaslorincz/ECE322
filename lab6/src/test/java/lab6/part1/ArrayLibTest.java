package lab6.part1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ArrayLibTest {

    @Test
    public void testReverse() {
        String[] list = new String[]{"One", "Two", "Three"};
        String[] reversed = ArrayLib.reverse(list, String.class);
        String[] expected = new String[]{"Three", "Two", "One"};

        assertArrayEquals(expected, reversed);
    }

    @Test
    public void testIntersection() {
        String[] list1 = new String[]{"One", "Two", "Three"};
        String[] list2 = new String[]{"Two", "Three", "Four"};
        String[] intersection = ArrayLib.intersection(list1, list2, String.class);
        String[] expected = new String[]{"Two", "Three"};

        assertArrayEquals(expected, intersection);
    }

    @Test
    public void testUnion() {
        String[] list1 = new String[]{"One", "Two", "Three"};
        String[] list2 = new String[]{"Two", "Three", "Four"};
        String[] union = ArrayLib.union(list1, list2, String.class);
        String[] expected = new String[]{"One", "Two", "Three", "Four"};

        assertArrayEquals(expected, union);
    }

    @Test
    public void testIndexOf() {
        String[] list = new String[]{"One", "Two", "Three"};

        assertEquals(1, ArrayLib.indexOf(list, "Two"));
        assertEquals(-1, ArrayLib.indexOf(list, "two"));
    }

    @Test
    public void testWithout() {
        String[] list = new String[]{"One", "Two", "Three", "A", "B", "C"};
        String[] without = ArrayLib.without(list, String.class, "One", "Two", "Three");
        String[] expected = new String[]{"A", "B", "C"};

        assertArrayEquals(expected, without);
    }
}