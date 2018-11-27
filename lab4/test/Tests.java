import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class Tests {

    private Bisect b;

    /**
     * Set up method that is run before each test case
     */
    @Before public void setUp() {
        b = new Bisect(new Bisect.polynomial() {
            public double eval(double value) {
                return value - 1;
            }
        });
    }

    // STATEMENT COVERAGE

    /**
     * ID: 1
     * Description: Tests Bisect(polynomial f)
     * Criteria: Statement coverage
     */
    @Test public void testConstructor1() {
        b = new Bisect(new Bisect.polynomial() {
            public double eval(double value) {
                return value - 1;
            }
        });

        assertEquals(0.000001, b.getTolerance());
        assertEquals(50, b.getMaxIterations());
    }

    /**
     * ID: 2
     * Description: Tests Bisect(double tol, polynomial f)
     * Criteria: Statement coverage
     */
    @Test public void testConstructor2() {
        b = new Bisect(0.000002, new Bisect.polynomial() {
            public double eval(double value) {
                return value - 1;
            }
        });

        assertEquals(0.000002, b.getTolerance());
        assertEquals(50, b.getMaxIterations());
    }

    /**
     * ID: 3
     * Description: Tests Bisect(int maxIter, polynomial f)
     * Criteria: Statement coverage
     */
    @Test public void testConstructor3() {
        b = new Bisect(100, new Bisect.polynomial() {
            public double eval(double value) {
                return value - 1;
            }
        });

        assertEquals(0.000001, b.getTolerance());
        assertEquals(100, b.getMaxIterations());
    }

    /**
     * ID: 4
     * Description: Tests Bisect(double tol, int maxIter, polynomial f)
     * Criteria: Statement coverage
     */
    @Test public void testConstructor4() {
        b = new Bisect(0.000002, 100, new Bisect.polynomial() {
            public double eval(double value) {
                return value - 1;
            }
        });

        assertEquals(0.000002, b.getTolerance());
        assertEquals(100, b.getMaxIterations());
    }

    /**
     * ID: 5
     * Description: Tests getTolerance accessor method
     * Criteria: Statement coverage
     */
    @Test public void testGetTolerance() {
        assertEquals(0.000001, b.getTolerance());
    }

    /**
     * ID: 6
     * Description: Tests setTolerance mutator method
     * Criteria: Statement coverage
     */
    @Test public void testSetTolerance() {
        b.setTolerance(0.222);

        assertEquals(0.222, b.getTolerance());
    }

    /**
     * ID: 7
     * Description: Tests getMaxIterations accessor method
     * Criteria: Statement coverage
     */
    @Test public void testGetMaxIterations() {
        assertEquals(50, b.getMaxIterations());
    }

    /**
     * ID: 8
     * Description: Tests setMaxIterations mutator method
     * Criteria: Statement coverage
     */
    @Test public void testSetMaxIterations() {
        b.setMaxIterations(123);

        assertEquals(123, b.getMaxIterations());
    }

    // BRANCH/CONDITION COVERAGE

    /**
     * ID: 9
     * Description: Tests an example for which the interval is invalid.
     * Notable coverage: Branch 3-14 (after 0 loops)
     * Criteria: Branch coverage
     */
    @Test(expected = Bisect.RootNotFound.class)
    public void testRootNotFound1() throws Bisect.RootNotFound {
        b.run(-1, 0);
    }

    /**
     * ID: 10
     * Description: Tests an example for which max iterations are exceeded.
     * Notable coverage: Branches 11-12, 12-14 (after 0 loops)
     * Criteria: Branch coverage, Condition coverage
     */
    @Test(expected = Bisect.RootNotFound.class)
    public void testRootNotFound2() throws Bisect.RootNotFound {
        b.setMaxIterations(1);
        b.run(-1, 2);
    }

    /**
     * ID: 11
     * Description: Tests an example for which a root should be found.
     * Notable coverage: Branches 5-7, 10-12 (after 2 loops)
     * Criteria: Branch coverage, Condition coverage
     */
    @Test public void testValidRoot1() throws Bisect.RootNotFound {
        b.run(-2, 2);
    }

    /**
     * ID: 12
     * Description: Tests an example for which a root should be found.
     * Notable coverage: Branches 5-6, 5-7, 10-12 (after 2 loops)
     * Criteria: Branch coverage, Condition coverage
     */
    @Test public void testValidRoot2() throws Bisect.RootNotFound {
        b.run(0, 4);
    }

    // PATH COVERAGE

    /**
     * ID: 13
     * Description: Tests an example for which a root should be found.
     * Notable coverage: Branches 5-6, 5-7, 10-12 (after 20 loops)
     * Criteria: Path coverage
     */
    @Test public void testValidRoot3() throws Bisect.RootNotFound {
        b.run(0, 3);
    }

    /**
     * ID: 14
     * Description: Tests an example for which a root should be found
     * Notable coverage: Branches 5-7, 9-12 (after 20 loops)
     * Criteria: Path coverage
     */
    @Test public void testValidRoot4() throws Bisect.RootNotFound {
        b.run(-1, 1);
    }

    /**
     * ID: 15
     * Description: Tests an example for which the interval is invalid.
     * Notable coverage: Branch 3-14 (after 1 loop)
     * Criteria: Path coverage
     */
    @Test(expected = Bisect.RootNotFound.class)
    public void testRootNotFound3() throws Bisect.RootNotFound {
        b.run(1, -1);
    }

    /**
     * ID: 16
     * Description: Tests an example for which max iterations are exceeded.
     * Notable coverage: Branch 12-14 (after 50 loops)
     * Criteria: Path coverage
     */
    @Test(expected = Bisect.RootNotFound.class)
    public void testRootNotFound4() throws Bisect.RootNotFound {
        b.run(-100, Integer.MAX_VALUE);
    }
}
