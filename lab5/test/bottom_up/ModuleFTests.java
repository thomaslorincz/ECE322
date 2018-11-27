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
        testData.add(new Entry("Morris", "0623"));
        testData.add(new Entry("Quinn", "3847"));
        testData.add(new Entry("JJJ", "1234"));
        testData.add(new Entry("Thomas", "777222"));
        testData.add(new Entry("Frank", "123456789789"));
    }

    /**
     * Tests displayData() and setOutputStream() of ModuleF.
     */
    @Test
    public void testModuleF() {
        ModuleF f = new ModuleF();
        f.setOutputStream(new PrintStream(outContent));

        String expectedOutput  = "Current Data:\n1 Jeremy, 1234\n2 Morris, 0623\n3 Quinn, 3847\n4 JJJ, 1234\n5 Thomas" +
            ", 777222\n6 Frank, 123456789789\n";

        f.displayData(testData);
        assertEquals(
            expectedOutput.replaceAll("\\r?\\n|\\r", ""),
            outContent.toString().replaceAll("\\r?\\n|\\r", "")
        );
    }
}
