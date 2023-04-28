package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test suite for the Grade class
 */
class GradeTest {
    Grade test = new Grade("R00217303","MX210",1);

    /**
     * Checks the getSID method
     */
    @Test
    void getSID() {
        String validSID = "R00217303";
        String invalidSID = "R0000P";
        assertSame(test.getSID(), validSID);
        assertNotSame(test.getSID(), invalidSID);
    }

    /**
     * Checks the getmCode method
     */
    @Test
    void getmCode() {
        String validmCode = "MX210";
        String invalidmCode = "M0000";
        assertSame(test.getmCode(), validmCode);
        assertNotSame(test.getmCode(), invalidmCode);

    }

    /**
     * Checks the getGrade method
     */
    @Test
    void getGrade() {
        int validGrade = 1;
        int invalidGrade = -1;
        assertEquals(test.getGrade(), validGrade);
        assertNotEquals(test.getGrade(), invalidGrade);
    }

}