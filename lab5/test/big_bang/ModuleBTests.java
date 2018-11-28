package big_bang;

import data.Entry;
import modules.ModuleB;
import modules.ModuleF;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class ModuleBTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private PrintStream originalOut = System.out;
    private ArrayList<Entry> testData;

    @Mock
    private ModuleF moduleFMock;

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
     * ID: 1
     * Tests loadFile() in ModuleB. ModuleF is mocked.
     */
    @Test
    public void testModuleB() {
        ModuleB b = new ModuleB(moduleFMock);
        assertEquals(testData.toString(), b.loadFile("test.txt").toString());
    }

    /**
     * ID: 2
     * Tests ModuleB's ability to catch a FileNotFoundException.
     */
    @Test
    public void testModuleBFileNotFoundException() {
        ModuleB b = new ModuleB(moduleFMock);
        System.setOut(new PrintStream(outContent));
        b.loadFile("nofile.txt");
        String expectedOutput = "File not found!\n";
        assertEquals(
            expectedOutput,
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }
}
