package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModuleModelTest {
    ModuleModel testModule = new ModuleModel("Maths","MX210",2);
    @Test
    void setName() {
        String validName = "Mathematics";
        int invalidName = 1;
        testModule.setName(validName);
        assertSame(testModule.getName(), validName);
    }

    @Test
    void getName() {
        String validName = "Maths";
        String invalidName = "Science";
        assertSame(testModule.getName(),validName);
        assertNotEquals(testModule.getName(),invalidName);
    }

    @Test
    void setSemester() {
        int validSemester = 1;

        testModule.setSemester(1);
        assertSame(validSemester,testModule.getSemester());
    }

    @Test
    void getSemester() {
        int validSemester = 2;
        int invalidSemester = 1;

        assertSame(validSemester, testModule.getSemester());
        assertNotEquals(invalidSemester,testModule.getSemester());
    }

    @Test
    void getCode() {
        String validCode = "MX210";
        String invalidCode = "MX000";

        assertEquals(validCode, testModule.getCode());
        assertNotEquals(invalidCode,testModule.getCode());
    }
}