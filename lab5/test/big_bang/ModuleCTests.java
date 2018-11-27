package big_bang;

import data.Entry;
import modules.ModuleC;
import modules.ModuleF;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class ModuleCTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private ArrayList<Entry> testData;
    private ArrayList<Entry> sortedTestData;

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

        sortedTestData = new ArrayList<>();
        sortedTestData.add(new Entry("Frank", "123456789789"));
        sortedTestData.add(new Entry("JJJ", "1234"));
        sortedTestData.add(new Entry("Jeremy", "1234"));
        sortedTestData.add(new Entry("Morris", "0623"));
        sortedTestData.add(new Entry("Quinn", "3847"));
        sortedTestData.add(new Entry("Thomas", "777222"));
    }

    @Test
    public void testModuleC() {
        ModuleC c = new ModuleC(moduleFMock);
        c.setF(moduleFMock);
        assertEquals(c.sortData(testData).toString(), sortedTestData.toString());
    }
}
