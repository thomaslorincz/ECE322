package bottom_up;

import data.Entry;
import modules.ModuleB;
import modules.ModuleF;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class ModuleBFTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private PrintStream originalOut = System.out;
    private ArrayList<Entry> testData;

    @Before
    public void setUp() {
        outContent.reset(); // Reset output stream before each test

        testData = new ArrayList<>();
        testData.add(new Entry("Jeremy", "1234"));
        testData.add(new Entry("JJJ", "1234"));
        testData.add(new Entry("Frank", "123456789789"));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    /**
     * ID: 4
     * Tests loadFile() in ModuleB. Tests ModuleF as a dependency to ModuleB.
     */
    @Test
    public void testModuleB() {
        ModuleF f = new ModuleF();
        f.setOutputStream(new PrintStream(outContent));
        ModuleB b = new ModuleB(f);

        assertEquals(testData.toString(), b.loadFile("test.txt").toString());

        String expectedOutput  = "Current Data:\n1 Jeremy, 1234\n2 JJJ, 1234\n3 Frank, 123456789789\n";

        assertEquals(
            expectedOutput,
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    /**
     * ID: 5
     * Tests ModuleB's ability to catch a FileNotFoundException.
     */
    @Test
    public void testModuleBFileNotFoundException() {
        ModuleF f = new ModuleF();
        ModuleB b = new ModuleB(f);
        System.setOut(new PrintStream(outContent));
        b.loadFile("nofile.txt");
        String expectedOutput = "File not found!\n";
        assertEquals(
            expectedOutput,
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }
}
