package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeTest {
    Grade test = new Grade("R00217303","MX210",1);
    @Test
    void getSID() {
        String validSID = "R00217303";
        String invalidSID = "R0000P";
        assertTrue(test.getSID() == validSID);
        assertFalse(test.getSID() == invalidSID);
    }

    @Test
    void getmCode() {
        String validmCode = "MX210";
        String invalidmCode = "M0000";
        assertTrue(test.getmCode() == validmCode);
        assertFalse(test.getmCode() == invalidmCode);

    }

    @Test
    void getGrade() {
        int validGrade = 1;
        int invalidGrade = -1;
        assertTrue(test.getGrade() == validGrade);
        assertFalse(test.getGrade() == invalidGrade);
    }

}