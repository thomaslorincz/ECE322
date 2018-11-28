package bottom_up;

import data.Entry;
import modules.ModuleD;
import modules.ModuleF;
import modules.ModuleG;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class ModuleDFGTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private ArrayList<Entry> testData;
    private String testDataInserted;
    private String testDataUpdated;
    private String testDataDeleted;

    @Before
    public void setUp() {
        outContent.reset(); // Reset output stream before each test

        testData = new ArrayList<>();
        testData.add(new Entry("Jeremy", "1234"));
        testData.add(new Entry("JJJ", "1234"));
        testData.add(new Entry("Frank", "123456789789"));

        testDataInserted = "Jeremy,1234\nJJJ,1234\nFrank,123456789789\nInsert,100\n";

        testDataUpdated = "Update,8080\nJJJ,1234\nFrank,123456789789\n";

        testDataDeleted = "JJJ,1234\nFrank,123456789789\n";
    }

    @After
    public void tearDown() {
        // Reset the test file after each test
        try (PrintWriter writer = new PrintWriter("test.txt", StandardCharsets.UTF_8)) {
            writer.println("Jeremy,1234");
            writer.println("JJJ,1234");
            writer.println("Frank,123456789789");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ID: 7
     * Tests the insertData() method of ModuleD. Uses ModuleF and ModuleG.
     */
    @Test
    public void testModuleDInsertData() {
        ModuleF f = new ModuleF();
        f.setOutputStream(new PrintStream(outContent));
        ModuleG g = new ModuleG();
        ModuleD d = new ModuleD(f, g);
        d.insertData(testData, "Insert", "100", "test.txt");
        assertEquals(testDataInserted, getFileContents("test.txt"));

        String expectedOutput  = "Current Data:\n1 Jeremy, 1234\n2 JJJ, 1234\n3 Frank, 123456789789\n4 Insert, 100\n";

        assertEquals(
            expectedOutput,
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    /**
     * ID: 8
     * Tests the updateData() method of ModuleD. Uses ModuleF and ModuleG.
     */
    @Test
    public void testModuleDUpdateData() {
        ModuleF f = new ModuleF();
        f.setOutputStream(new PrintStream(outContent));
        ModuleG g = new ModuleG();
        ModuleD d = new ModuleD(f, g);
        d.updateData(testData, 0, "Update", "8080", "test.txt");
        assertEquals(testDataUpdated, getFileContents("test.txt"));

        String expectedOutput  = "Current Data:\n1 Update, 8080\n2 JJJ, 1234\n3 Frank, 123456789789\n";

        assertEquals(
            expectedOutput,
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    /**
     * ID: 9
     * Tests the deleteData() method of ModuleD. Uses ModuleF and ModuleG.
     */
    @Test
    public void testModuleDDeleteData() {
        ModuleF f = new ModuleF();
        f.setOutputStream(new PrintStream(outContent));
        ModuleG g = new ModuleG();
        ModuleD d = new ModuleD(f, g);
        d.deleteData(testData, 0, "test.txt");
        assertEquals(testDataDeleted, getFileContents("test.txt"));

        String expectedOutput  = "Current Data:\n1 JJJ, 1234\n2 Frank, 123456789789\n";

        assertEquals(
            expectedOutput,
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    /**
     * A simple utility method for getting the content of a file as a String.
     */
    @NotNull @Contract("_ -> fail")
    private String getFileContents(String pathname) {
        File file = new File(pathname);
        byte[] data = new byte[0];
        try (FileInputStream fis = new FileInputStream(file)) {
            data = new byte[(int) file.length()];
            fis.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(data, StandardCharsets.UTF_8);
    }
}
