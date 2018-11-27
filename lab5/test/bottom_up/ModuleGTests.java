package bottom_up;

import data.Entry;
import modules.ModuleG;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.*;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.doThrow;

public class ModuleGTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private ArrayList<Entry> testData;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));

        testData = new ArrayList<>();
        testData.add(new Entry("Jeremy", "1234"));
        testData.add(new Entry("Morris", "0623"));
        testData.add(new Entry("Quinn", "3847"));
        testData.add(new Entry("JJJ", "1234"));
        testData.add(new Entry("Thomas", "777222"));
        testData.add(new Entry("Frank", "123456789789"));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }


    /**
     * Tests updateData() from ModuleG.
     * Compares the data read from test.txt (a copy of the original data file) with what has been set in testData.
     * This method should read successfully and not throw an IOException
     */
    @Test
    public void testModuleG() {
        ModuleG g = new ModuleG();
        String expectedOutput  = "Jeremy,1234\nMorris,0623\nQuinn,3847\nJJJ,1234\nThomas,777222\nFrank,123456789789\n";

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
