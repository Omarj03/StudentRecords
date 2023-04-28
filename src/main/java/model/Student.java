package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <H1> Student</H1>
 * Student class that models a student.
 */
public class Student implements Serializable {
    //-----------------
    //
    // Fields
    //
    //-----------------
    private String name;
    private String studentID;
    private String dateOfBirth;
    private int semester;

    private final ArrayList<Module> modules;

    //-----------------
    //
    // Constructors
    //
    //-----------------

    /**
     * @param name - Student Name
     * @param studentID - The student ID in the database
     * @param dateOfBirth - The students date of birth
     * @param semester - The current semester of the student
     */
    public Student(String name, String studentID, String dateOfBirth, int semester) {
        this.name = name;
        this.studentID = studentID;
        this.dateOfBirth = dateOfBirth;
        this.semester = semester;
        modules = new ArrayList<Module>();
    }

    //-----------------
    //
    // Getters
    //
    //-----------------
    public String getName() {
        return name;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public Module getModule(int index) {
        if (modules.size() > 0) {
            return modules.get(index);

        } else return new Module("No modules currently exist", "", 0);
    }


    //-----------------
    //
    // Setters
    //
    //-----------------
    public void setName(String _name) {
        this.name = _name;
    }

    public void setStudentID(String _studentID) {
        this.studentID = _studentID;
    }

    public void setDateOfBirth(String _dateOfBirth) {
        this.dateOfBirth = _dateOfBirth;
    }

    public void setSemester(int _semester) {
        this.semester = semester;
    }

    //-----------------
    //
    // Other Functionality
    //
    //-----------------

    @Override
    public String toString() {
        return "Name: " + name + ", StudentID: " + studentID + ", Date of Birth: " + dateOfBirth
                + ", Semester: " + semester + ".";
    }
}
