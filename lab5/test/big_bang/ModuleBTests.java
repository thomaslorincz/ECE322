package big_bang;

import data.Entry;
import modules.ModuleB;
import modules.ModuleF;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.doThrow;

public class ModuleBTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private ArrayList<Entry> testData;

    @Mock
    private ModuleB moduleBMock;
    @Mock
    private ModuleF moduleFMock;

    @Before
    public void setUp() {
        testData = new ArrayList<>();
        testData.add(new Entry("Jeremy", "1234"));
        testData.add(new Entry("Morris", "0623"));
        testData.add(new Entry("Quinn", "3847"));
        testData.add(new Entry("JJJ", "1234"));
        testData.add(new Entry("Thomas", "777222"));
        testData.add(new Entry("Frank", "123456789789"));
    }

    /**
     *
     */
    @Test
    public void testModuleB() {
        ModuleB b = new ModuleB(moduleFMock);
        b.setF(moduleFMock);
        assertEquals(b.loadFile("test.txt").toString(), testData.toString());
    }

    @Test(expected = FileNotFoundException.class)
    public void testModuleBFileNotFoundException() {
        doThrow(FileNotFoundException.class).when(moduleBMock).loadFile("test.txt");
        moduleBMock.loadFile("test.txt");
    }

    @Test(expected = IOException.class)
    public void testModuleBIOException() {
        doThrow(IOException.class).when(moduleBMock).loadFile("test.txt");
        moduleBMock.loadFile("test.txt");
    }
}
