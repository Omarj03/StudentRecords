package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test suite for the Module class
 */
class ModuleTest {
    Module testModule = new Module("Maths","MX210",2);

    /**
     * Checks the setName method
     */
    @Test
    void setName() {
        String validName = "Mathematics";
        int invalidName = 1;
        testModule.setName(validName);
        assertSame(testModule.getName(), validName);
    }

    /**
     * Checks the getName method
     */
    @Test
    void getName() {
        String validName = "Maths";
        String invalidName = "Science";
        assertSame(testModule.getName(),validName);
        assertNotEquals(testModule.getName(),invalidName);
    }

    /**
     * Checks the setSemester method
     */
    @Test
    void setSemester() {
        int validSemester = 1;

        testModule.setSemester(1);
        assertSame(validSemester,testModule.getSemester());
    }

    /**
     * Checks the getSemester method
     */
    @Test
    void getSemester() {
        int validSemester = 2;
        int invalidSemester = 1;

        assertSame(validSemester, testModule.getSemester());
        assertNotEquals(invalidSemester,testModule.getSemester());
    }

    /**
     * Checks the getCode method
     */
    @Test
    void getCode() {
        String validCode = "MX210";
        String invalidCode = "MX000";

        assertEquals(validCode, testModule.getCode());
        assertNotEquals(invalidCode,testModule.getCode());
    }
}