package bottom_up;

import modules.*;
import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import static junit.framework.TestCase.assertEquals;

public class ModuleATests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @After
    public void tearDown() {
        outContent.reset(); // Reset outContent after each test

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
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        ModuleA a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
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
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        ModuleA a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
        String[] command = {"help"};
        a.run(command);
        String expectedOutput = "Available Commands: \n" +
            "load <filepath>\n" +
            "add <name> <number>\n" +
            "update <index> <name> <number>\n" +
            "delete <index>\n" +
            "sort\n" +
            "exit";
        assertEquals(
            expectedOutput.replaceAll("\\r?\\n|\\r", ""),
            outContent.toString().replaceAll("\\r?\\n|\\r", "")
        );
    }

    @Test
    public void testModuleALoad() throws ModuleE.DataBaseExitException {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        ModuleA a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
        String[] command = {"load", "test.txt"};
        a.run(command);
        assertEquals(
            "",
            outContent.toString().replaceAll("\\r?\\n|\\r", "")
        );
    }

    @Test
    public void testModuleALoadException() throws ModuleE.DataBaseExitException {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        ModuleA a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
        String[] command = {"load"};
        a.run(command);
        assertEquals(
            "Malformed command!",
            outContent.toString().replaceAll("\\r?\\n|\\r", "")
        );
    }

    @Test
    public void testModuleAAdd() throws ModuleE.DataBaseExitException {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        ModuleA a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
        String[] command1 = {"load", "test.txt"};
        a.run(command1);
        String[] command2 = {"add", "add", "1776"};
        a.run(command2);
        assertEquals(
            "",
            outContent.toString().replaceAll("\\r?\\n|\\r", "")
        );
    }

    @Test
    public void testModuleAAddNoFile() throws ModuleE.DataBaseExitException {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        ModuleA a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
        String[] command = {"add", "add", "1776"};
        a.run(command);
        assertEquals(
            "No file loaded!",
            outContent.toString().replaceAll("\\r?\\n|\\r", "")
        );
    }

    @Test
    public void testModuleAAddException() throws ModuleE.DataBaseExitException {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        ModuleA a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
        String[] command1 = {"load", "test.txt"};
        a.run(command1);
        String[] command2 = {"add"};
        a.run(command2);
        assertEquals(
            "Malformed command!",
            outContent.toString().replaceAll("\\r?\\n|\\r", "")
        );
    }

    @Test
    public void testModuleASort() throws ModuleE.DataBaseExitException {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        ModuleA a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
        String[] command1 = {"load", "test.txt"};
        a.run(command1);
        String[] command2 = {"sort"};
        a.run(command2);
        assertEquals(
            "",
            outContent.toString().replaceAll("\\r?\\n|\\r", "")
        );
    }

    @Test
    public void testModuleASortNoFile() throws ModuleE.DataBaseExitException {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        ModuleA a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
        String[] command = {"sort"};
        a.run(command);
        assertEquals(
            "No file loaded!",
            outContent.toString().replaceAll("\\r?\\n|\\r", "")
        );
    }

    @Test
    public void testModuleAUpdate() throws ModuleE.DataBaseExitException {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        ModuleA a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
        String[] command1 = {"load", "test.txt"};
        a.run(command1);
        String[] command2 = {"update", "0", "update", "8080"};
        a.run(command2);
        assertEquals(
            "",
            outContent.toString().replaceAll("\\r?\\n|\\r", "")
        );
    }

    @Test
    public void testModuleAUpdateNoFile() throws ModuleE.DataBaseExitException {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        ModuleA a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
        String[] command = {"update", "0", "update", "8080"};
        a.run(command);
        assertEquals(
            "No file loaded!",
            outContent.toString().replaceAll("\\r?\\n|\\r", "")
        );
    }

    @Test
    public void testModuleAUpdateException() throws ModuleE.DataBaseExitException {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        ModuleA a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
        String[] command1 = {"load", "test.txt"};
        a.run(command1);
        String[] command2 = {"update"};
        a.run(command2);
        assertEquals(
            "Malformed command!",
            outContent.toString().replaceAll("\\r?\\n|\\r", "")
        );
    }

    @Test
    public void testModuleADelete() throws ModuleE.DataBaseExitException {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        ModuleA a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
        String[] command1 = {"load", "test.txt"};
        a.run(command1);
        String[] command2 = {"delete", "0"};
        a.run(command2);
        assertEquals(
            "",
            outContent.toString().replaceAll("\\r?\\n|\\r", "")
        );
    }

    @Test
    public void testModuleADeleteNoFile() throws ModuleE.DataBaseExitException {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        ModuleA a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
        String[] command = {"delete", "0"};
        a.run(command);
        assertEquals(
            "No file loaded!",
            outContent.toString().replaceAll("\\r?\\n|\\r", "")
        );
    }

    @Test
    public void testModuleADeleteException() throws ModuleE.DataBaseExitException {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        ModuleA a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
        String[] command1 = {"load", "test.txt"};
        a.run(command1);
        String[] command2 = {"delete"};
        a.run(command2);
        assertEquals(
            "Malformed command!",
            outContent.toString().replaceAll("\\r?\\n|\\r", "")
        );
    }

    @Test(expected = ModuleE.DataBaseExitException.class)
    public void testModuleAExit() throws ModuleE.DataBaseExitException {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        ModuleA a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
        String[] command = {"exit"};
        a.run(command);
    }

    @Test
    public void testModuleAUnknown() throws ModuleE.DataBaseExitException {
        ModuleF f = new ModuleF();
        ModuleG g = new ModuleG();
        ModuleB b = new ModuleB(f);
        ModuleC c = new ModuleC(f);
        ModuleD d = new ModuleD(f, g);
        ModuleE e = new ModuleE();
        ModuleA a = new ModuleA(b, c, d, e);
        a.setOutputStream(new PrintStream(outContent));
        String[] command = {"unknown"};
        a.run(command);
        assertEquals(
            "Unknown command, type 'help' for command list.",
            outContent.toString().replaceAll("\\r?\\n|\\r", "")
        );
    }
}
