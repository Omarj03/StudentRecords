package model;

import java.io.Serializable;

/**
 * <H1>Module</H1>
 * Module class that models a module
 */
public class Module implements Serializable {
    //-----------------
    //
    // Fields
    //
    //-----------------
    private String name;
    private final String code;
    private int semester;

    //-----------------
    //
    // Constructors
    //
    //-----------------

    /**
     * @param _name - Name of the module
     * @param _code - The module code
     * @param _semester - The semester the module takes place in
     */
    public Module(String _name, String _code, int _semester) {
        this.name = _name;
        this.code = _code;
        this.semester = _semester;
    }

    //-----------------
    //
    // Getters and Setters
    //
    //-----------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }


    //-----------------
    //
    // Other Functionality
    //
    //-----------------

    @Override
    public String toString() {
        return "Semester: " + semester + "\nModule: " + name + "\nCode:" + code;
    }

}

