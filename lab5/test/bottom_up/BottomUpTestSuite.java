package bottom_up;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    ModuleETests.class,
    ModuleFTests.class,
    ModuleGTests.class,
    ModuleBFTests.class,
    ModuleCFTests.class,
    ModuleDFGTests.class,
    ModuleABCDEFGTests.class
})

public class BottomUpTestSuite {
    // This class remains empty
}
