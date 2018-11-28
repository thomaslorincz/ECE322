package bottom_up;

import data.Entry;
import modules.ModuleF;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class ModuleFTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private ArrayList<Entry> testData;

    @Before
    public void setUp() {
        testData = new ArrayList<>();
        testData.add(new Entry("Jeremy", "1234"));
        testData.add(new Entry("JJJ", "1234"));
        testData.add(new Entry("Frank", "123456789789"));
    }

    /**
     * ID: 2
     * Tests displayData() and setOutputStream() of ModuleF.
     */
    @Test
    public void testModuleF() {
        ModuleF f = new ModuleF();
        f.setOutputStream(new PrintStream(outContent));

        String expectedOutput  = "Current Data:\n1 Jeremy, 1234\n2 JJJ, 1234\n3 Frank, 123456789789\n";

        f.displayData(testData);
        assertEquals(
            expectedOutput,
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }
}
