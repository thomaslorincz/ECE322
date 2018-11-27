package bottom_up;

import data.Entry;
import modules.ModuleC;
import modules.ModuleF;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class ModuleCFTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private ArrayList<Entry> testData;
    private ArrayList<Entry> sortedTestData;

    @Before
    public void setUp() {
        outContent.reset(); // Reset output stream before each test

        testData = new ArrayList<>();
        testData.add(new Entry("Jeremy", "1234"));
        testData.add(new Entry("Morris", "0623"));
        testData.add(new Entry("Quinn", "3847"));
        testData.add(new Entry("JJJ", "1234"));
        testData.add(new Entry("Thomas", "777222"));
        testData.add(new Entry("Frank", "123456789789"));

        sortedTestData = new ArrayList<>();
        sortedTestData.add(new Entry("Frank", "123456789789"));
        sortedTestData.add(new Entry("JJJ", "1234"));
        sortedTestData.add(new Entry("Jeremy", "1234"));
        sortedTestData.add(new Entry("Morris", "0623"));
        sortedTestData.add(new Entry("Quinn", "3847"));
        sortedTestData.add(new Entry("Thomas", "777222"));
    }

    @Test
    public void testModuleC() {
        ModuleF f = new ModuleF();
        f.setOutputStream(new PrintStream(outContent));
        ModuleC c = new ModuleC(f);
        c.setF(f);

        assertEquals(c.sortData(testData).toString(), sortedTestData.toString());

        String expectedOutput  = "Current Data:\n1 Frank, 123456789789\n2 JJJ, 1234\n3 Jeremy, 1234\n4 Morris, 0623\n" +
            "5 Quinn, 3847\n6 Thomas, 777222\n";

        assertEquals(
            expectedOutput,
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }
}
