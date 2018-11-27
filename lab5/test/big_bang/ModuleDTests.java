package big_bang;

import data.Entry;
import modules.ModuleD;
import modules.ModuleF;
import modules.ModuleG;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.doCallRealMethod;

public class ModuleDTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private ArrayList<Entry> testData;
    private String testDataInserted;
    private String testDataUpdated;
    private String testDataDeleted;

    @Mock
    private ModuleF moduleFMock;
    @Mock
    private ModuleG moduleGMock;

    @Before
    public void setUp() {
        // Reset the test file before each test
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

    /**
     *
     */
    @Test
    public void testModuleDInsertData() {
        ModuleD d = new ModuleD(moduleFMock, moduleGMock);
        doCallRealMethod().when(moduleGMock).updateData("test.txt", testData);
        d.insertData(testData, "Insert", "100", "test.txt");
        assertEquals(testDataInserted, getFileContents("test.txt"));
    }

    /**
     *
     */
    @Test
    public void testModuleDUpdateData() {
        ModuleD d = new ModuleD(moduleFMock, moduleGMock);
        doCallRealMethod().when(moduleGMock).updateData("test.txt", testData);
        d.updateData(testData, 0, "Update", "8080", "test.txt");
        assertEquals(testDataUpdated, getFileContents("test.txt"));
    }

    /**
     *
     */
    @Test
    public void testModuleDDeleteData() {
        ModuleD d = new ModuleD(moduleFMock, moduleGMock);
        doCallRealMethod().when(moduleGMock).updateData("test.txt", testData);
        d.deleteData(testData, 0, "test.txt");
        assertEquals(testDataDeleted, getFileContents("test.txt"));
    }

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