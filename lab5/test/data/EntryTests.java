package data;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class EntryTests {

    @Test
    public void testEntryData() {
        Entry e = new Entry("test", "0");
        assertEquals("test", e.getName());
        assertEquals("0", e.getNumber());
    }

    @Test
    public void testEntryComparison() {
        Entry e1 = new Entry("same", "1");
        Entry e2 = new Entry("same", "1");
        Entry e3 = new Entry("different", "1");
        assertEquals(0, e1.compareTo(e2));
        assertTrue(0 != e1.compareTo(e3));
    }

    @Test
    public void testEntryToString() {
        Entry e = new Entry("string", "777");
        assertEquals("string, 777", e.toString());
    }
}
