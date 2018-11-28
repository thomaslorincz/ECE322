package bottom_up;

import data.Entry;
import modules.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class ModuleABCDEFGTests {

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
        testData.add(new Entry("JJJ", "1234"));
        testData.add(new Entry("Frank", "123456789789"));

        sortedTestData = new ArrayList<>();
        sortedTestData.add(new Entry("Frank", "123456789789"));
        sortedTestData.add(new Entry("JJJ", "1234"));
        sortedTestData.add(new Entry("Jeremy", "1234"));

        testDataInserted = "[Jeremy, 1234, JJJ, 1234, Frank, 123456789789, Insert, 100]";

        testDataUpdated = "[Update, 8080, JJJ, 1234, Frank, 123456789789]";

        testDataDeleted = "[JJJ, 1234, Frank, 123456789789]";

        ModuleF f = new ModuleF();
        f.setOutputStream(new PrintStream(outContent));
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
     * ID: 10
     * Tests blank input to ModuleA run() command.
     */
    @Test
    public void testModuleABlank() throws ModuleE.DataBaseExitException {
        String[] command = {""};
        a.run(command);
        assertEquals("", outContent.toString());
    }

    /**
     * ID: 11
     * Tests "help" input to ModuleA run() command.
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
            expectedOutput,
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    /**
     * ID: 12
     * Tests "load" input to ModuleA. Expected to run correctly.
     */
    @Test
    public void testModuleALoad() throws ModuleE.DataBaseExitException {
        String[] command = {"load", "test.txt"};
        a.run(command);
        assertEquals(
            testData.toString(),
            a.getData().toString().replaceAll("\\r?\\n|\\r", "\n")
        );

        String expectedOutput  = "Current Data:\n1 Jeremy, 1234\n2 JJJ, 1234\n3 Frank, 123456789789\n4 Insert, 100\n";

        assertEquals(
            expectedOutput,
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    /**
     * ID: 13
     * Tests "load" input without a pathname.
     */
    @Test
    public void testModuleALoadException() throws ModuleE.DataBaseExitException {
        String[] command = {"load"};
        a.run(command);
        assertEquals(
            "Malformed command!\n",
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    /**
     * ID: 14
     * Tests "add" input to ModuleA run().
     */
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

    /**
     * ID: 15
     * Attempts to run "add" with no loaded file.
     */
    @Test
    public void testModuleAAddNoFile() throws ModuleE.DataBaseExitException {
        String[] command = {"add", "Insert", "100"};
        a.run(command);
        assertEquals(
            "No file loaded!\n",
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    /**
     * ID: 16
     * Attempts to run "add" without specifying the new entry data.
     */
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

    /**
     * ID: 17
     * Tests "sort" input to ModuleA.
     */
    @Test
    public void testModuleASort() throws ModuleE.DataBaseExitException {
        String[] command1 = {"load", "test.txt"};
        a.run(command1);
        String[] command2 = {"sort"};
        a.run(command2);
        assertEquals(
            sortedTestData.toString(),
            a.getData().toString().replaceAll("\\r?\\n|\\r", "\n")
        );

        String expectedOutput  = "Current Data:\n1 Frank, 123456789789\n2 JJJ, 1234\n3 Jeremy, 1234\n";

        assertEquals(
            expectedOutput,
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    /**
     * ID: 18
     * Attempts to run "sort" command with no file loaded.
     */
    @Test
    public void testModuleASortNoFile() throws ModuleE.DataBaseExitException {
        String[] command = {"sort"};
        a.run(command);
        assertEquals(
            "No file loaded!\n",
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    /**
     * ID: 19
     * Tests "update" input to ModuleA.
     */
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

        String expectedOutput  = "Current Data:\n1 Update, 8080\n2 JJJ, 1234\n3 Jeremy, 1234\n";

        assertEquals(
            expectedOutput,
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    /**
     * ID: 20
     * Attempts to run "update" command with no loaded file.
     */
    @Test
    public void testModuleAUpdateNoFile() throws ModuleE.DataBaseExitException {
        String[] command = {"update", "0", "update", "8080"};
        a.run(command);
        assertEquals(
            "No file loaded!\n",
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    /**
     * ID: 21
     * Attempts to run "update" without specifying the entry to update.
     */
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

    /**
     * ID: 22
     * Tests "delete" input to ModuleA.
     */
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

        String expectedOutput  = "Current Data:\n1 JJJ, 1234\n2 Jeremy, 1234\n";

        assertEquals(
            expectedOutput,
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    /**
     * ID: 23
     * Attempts to run "delete" command with no loaded file.
     */
    @Test
    public void testModuleADeleteNoFile() throws ModuleE.DataBaseExitException {
        String[] command = {"delete", "0"};
        a.run(command);
        assertEquals(
            "No file loaded!\n",
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }

    /**
     * ID: 24
     * Attempts to run "delete" command without specifying what data to delete.
     */
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

    /**
     * ID: 25
     * Tests exiting the database via "exit" command.
     */
    @Test(expected = ModuleE.DataBaseExitException.class)
    public void testModuleAExit() throws ModuleE.DataBaseExitException {
        String[] command = {"exit"};
        a.run(command);
    }

    /**
     * ID: 26
     * Tests inputting an unknown command to ModuleA.
     */
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
