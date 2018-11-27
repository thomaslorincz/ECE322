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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class ModuleDFGTests {

    private ArrayList<Entry> testData;
    private String testDataInserted;
    private String testDataUpdated;
    private String testDataDeleted;

    @Before
    public void setUp() {
        testData = new ArrayList<>();
        testData.add(new Entry("Jeremy", "1234"));
        testData.add(new Entry("Morris", "0623"));
        testData.add(new Entry("Quinn", "3847"));
        testData.add(new Entry("JJJ", "1234"));
        testData.add(new Entry("Thomas", "777222"));
        testData.add(new Entry("Frank", "123456789789"));

        testDataInserted = "Jeremy,1234\nMorris,0623\nQuinn,3847\nJJJ,1234\nThomas,777222\nFrank,123456789789\nInsert" +
            ",100\n";

        testDataUpdated = "Update,8080\nMorris,0623\nQuinn,3847\nJJJ,1234\nThomas,777222\nFrank,123456789789\n";

        testDataDeleted = "Morris,0623\nQuinn,3847\nJJJ,1234\nThomas,777222\nFrank,123456789789\n";
    }

    @After
    public void tearDown() {
        // Reset the test file after each test
        try (PrintWriter writer = new PrintWriter("test.txt", StandardCharsets.UTF_8)) {
            writer.println("Jeremy,1234");
            writer.println("Morris,0623");
            writer.println("Quinn,3847");
            writer.println("JJJ,1234");
            writer.println("Thomas,777222");
            writer.println("Frank,123456789789");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testModuleDInsertData() {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleD d = new ModuleD(f, g);
        d.insertData(testData, "Insert", "100", "test.txt");
        assertEquals(testDataInserted, getFileContents("test.txt"));
    }

    /**
     *
     */
    @Test
    public void testModuleDUpdateData() {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleD d = new ModuleD(f, g);
        d.updateData(testData, 0, "Update", "8080", "test.txt");
        assertEquals(testDataUpdated, getFileContents("test.txt"));
    }

    /**
     *
     */
    @Test
    public void testModuleDDeleteData() {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleD d = new ModuleD(f, g);
        d.deleteData(testData, 0, "test.txt");
        assertEquals(testDataDeleted, getFileContents("test.txt"));
    }

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
