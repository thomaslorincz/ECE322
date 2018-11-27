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
     * Tests loadFile() in ModuleB. ModuleF is mocked. It's functionality is not tested or required.
     * Also covers the setF() function in ModuleB.
     */
    @Test
    public void testModuleB() {
        ModuleB b = new ModuleB(moduleFMock);
        b.setF(moduleFMock);
        assertEquals(testData.toString(), b.loadFile("test.txt").toString());
    }

    /**
     * Tests ModuleB's ability to catch a FileNotFoundException.
     */
    @Test
    public void testModuleBFileNotFoundException() {
        ModuleB b = new ModuleB(moduleFMock);
        b.setF(moduleFMock);
        System.setOut(new PrintStream(outContent));
        b.loadFile("nofile.txt");
        String expectedOutput = "File not found!\n";
        assertEquals(
            expectedOutput.replaceAll("\\r?\\n|\\r", "\n"),
            outContent.toString().replaceAll("\\r?\\n|\\r", "\n")
        );
    }
}
