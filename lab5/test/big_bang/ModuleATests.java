package big_bang;

import data.Entry;
import modules.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class ModuleATests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private ModuleA a;
    private ArrayList<Entry> testData;
    private ArrayList<Entry> sortedTestData;
    private String testDataInserted;
    private String testDataUpdated;
    private String testDataDeleted;

    @Before
    public void setUp() {
        outContent.reset(); // Reset outContent before each test

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

        testDataInserted = "[Jeremy, 1234, Morris, 0623, Quinn, 3847, JJJ, 1234, Thomas, 777222, Frank, 123456789789," +
            " Insert, 100]";

        testDataUpdated = "[Update, 8080, Morris, 0623, Quinn, 3847, JJJ, 1234, Thomas, 777222, Frank, 123456789789]";

        testDataDeleted = "[Morris, 0623, Quinn, 3847, JJJ, 1234, Thomas, 777222, Frank, 123456789789]";

        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
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

    /**
     * Tests blank input to ModuleA run() command.
     * @throws ModuleE.DataBaseExitException when database closes
     */
    @Test
    public void testModuleABlank() throws ModuleE.DataBaseExitException {
        String[] command = {""};
        a.run(command);
        assertEquals("", outContent.toString());
    }

    /**
     * Tests "help" input to ModuleA run() command.
     * @throws ModuleE.DataBaseExitException when database closes
     */
    @Test
    public void testModuleAHelp() throws ModuleE.DataBaseExitException {
        String[] command = {"help"};
        a.run(command);
        String expectedOutput = "Available Commands: \n" +
            "load <filepath>\n" +
            "add <name> <number>\n" +
            "update <index> <name> <number>\n" +
            "delete <index>\n" +
            "sort\n" +
            "exit\n";
        assertEquals(
            expectedOutput.replaceAll("\\r?\\n|\\r", "\n"),
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    @Test
    public void testModuleALoad() throws ModuleE.DataBaseExitException {
        String[] command = {"load", "test.txt"};
        a.run(command);
        assertEquals(
            testData.toString().replaceAll("\\r?\\n|\\r", "\n"),
            a.getData().toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    @Test
    public void testModuleALoadException() throws ModuleE.DataBaseExitException {
        String[] command = {"load"};
        a.run(command);
        assertEquals(
            "Malformed command!\n",
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    @Test
    public void testModuleAAdd() throws ModuleE.DataBaseExitException {
        String[] command1 = {"load", "test.txt"};
        a.run(command1);
        String[] command2 = {"add", "Insert", "100"};
        a.run(command2);
        assertEquals(
            testDataInserted,
            a.getData().toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    @Test
    public void testModuleAAddNoFile() throws ModuleE.DataBaseExitException {
        String[] command = {"add", "Insert", "100"};
        a.run(command);
        assertEquals(
            "No file loaded!\n",
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    @Test
    public void testModuleAAddException() throws ModuleE.DataBaseExitException {
        String[] command1 = {"load", "test.txt"};
        a.run(command1);
        String[] command2 = {"add"};
        a.run(command2);
        assertEquals(
            "Malformed command!\n",
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    @Test
    public void testModuleASort() throws ModuleE.DataBaseExitException {
        String[] command1 = {"load", "test.txt"};
        a.run(command1);
        String[] command2 = {"sort"};
        a.run(command2);
        assertEquals(
            sortedTestData.toString().replaceAll("\\r?\\n|\\r", "\n"),
            a.getData().toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    @Test
    public void testModuleASortNoFile() throws ModuleE.DataBaseExitException {
        String[] command = {"sort"};
        a.run(command);
        assertEquals(
            "No file loaded!\n",
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    @Test
    public void testModuleAUpdate() throws ModuleE.DataBaseExitException {
        String[] command1 = {"load", "test.txt"};
        a.run(command1);
        String[] command2 = {"update", "0", "Update", "8080"};
        a.run(command2);
        assertEquals(
            testDataUpdated,
            a.getData().toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    @Test
    public void testModuleAUpdateNoFile() throws ModuleE.DataBaseExitException {
        String[] command = {"update", "0", "update", "8080"};
        a.run(command);
        assertEquals(
            "No file loaded!\n",
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    @Test
    public void testModuleAUpdateException() throws ModuleE.DataBaseExitException {
        String[] command1 = {"load", "test.txt"};
        a.run(command1);
        String[] command2 = {"update"};
        a.run(command2);
        assertEquals(
            "Malformed command!\n",
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    @Test
    public void testModuleADelete() throws ModuleE.DataBaseExitException {
        String[] command1 = {"load", "test.txt"};
        a.run(command1);
        String[] command2 = {"delete", "0"};
        a.run(command2);
        assertEquals(
            testDataDeleted,
            a.getData().toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    @Test
    public void testModuleADeleteNoFile() throws ModuleE.DataBaseExitException {
        String[] command = {"delete", "0"};
        a.run(command);
        assertEquals(
            "No file loaded!\n",
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    @Test
    public void testModuleADeleteException() throws ModuleE.DataBaseExitException {
        String[] command1 = {"load", "test.txt"};
        a.run(command1);
        String[] command2 = {"delete"};
        a.run(command2);
        assertEquals(
            "Malformed command!\n",
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    @Test(expected = ModuleE.DataBaseExitException.class)
    public void testModuleAExit() throws ModuleE.DataBaseExitException {
        String[] command = {"exit"};
        a.run(command);
    }

    @Test
    public void testModuleAUnknown() throws ModuleE.DataBaseExitException {
        String[] command = {"unknown"};
        a.run(command);
        assertEquals(
            "Unknown command, type 'help' for command list.\n",
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }
}
