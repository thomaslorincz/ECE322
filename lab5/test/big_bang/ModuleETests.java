package big_bang;

import modules.ModuleE;
import org.junit.Test;

public class ModuleETests {

    /**
     * Tests exitProgram() from ModuleE.
     * @throws ModuleE.DataBaseExitException when the database is closed
     */
    @Test(expected = ModuleE.DataBaseExitException.class)
    public void testModuleE() throws ModuleE.DataBaseExitException {
        ModuleE e = new ModuleE();
        e.exitProgram();
    }
}
