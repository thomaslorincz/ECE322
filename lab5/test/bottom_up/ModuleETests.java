package bottom_up;

import modules.ModuleE;
import org.junit.Test;

public class ModuleETests {

    /**
     * ID: 1
     * Tests exitProgram() from ModuleE.
     */
    @Test(expected = ModuleE.DataBaseExitException.class)
    public void testModuleE() throws ModuleE.DataBaseExitException {
        ModuleE e = new ModuleE();
        e.exitProgram();
    }
}
