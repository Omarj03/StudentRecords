package model;

import java.io.Serializable;

public class ModuleModel implements Serializable {
    //-----------------
    //
    // Fields
    //
    //-----------------
    private String name;
    private String code;
    private int semester;

    //-----------------
    //
    // Constructors
    //
    //-----------------

    public ModuleModel(String _name,String _code, int _semester){
        this.name = _name;
        this.code = _code;
        this.semester = _semester;
    }

    //-----------------
    //
    // Getters and Setters
    //
    //-----------------




    public void setName(String _name){this.name = _name;}
    public String getName(){return name;}

    public void setSemester(int _semester){
        this.semester = _semester;
    }

    public int getSemester(){
        return this.semester;
    }
    public String getCode(){return this.code;}

    //-----------------
    //
    // Other Functionality
    //
    //-----------------

    @Override
    public String toString(){
        return "Semester: " + semester + "\nModule: "+ name + "\nCode:" + code;
    }

}

