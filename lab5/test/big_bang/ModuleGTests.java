package big_bang;

import data.Entry;
import modules.ModuleG;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class ModuleGTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private ArrayList<Entry> testData;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));

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
     * ID: 9
     * Tests updateData() from ModuleG.
     * Compares the data read from test.txt (a copy of the original data file) with what has been set in testData.
     */
    @Test
    public void testModuleG() {
        ModuleG g = new ModuleG();
        String expectedOutput  = "Jeremy,1234\nJJJ,1234\nFrank,123456789789\n";

        g.updateData("test.txt", testData);

        try (BufferedReader br = new BufferedReader(new FileReader("test.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            String everything = sb.toString();

            assertEquals(expectedOutput, everything);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}
