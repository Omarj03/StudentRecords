package model;

import model.ModuleModel;

import java.io.Serializable;
import java.util.ArrayList;

public class StudentModel implements Serializable {
    //-----------------
    //
    // Fields
    //
    //-----------------
    private String name;
    private String studentID;
    private String dateOfBirth;
    private  int semester;

    private ArrayList<ModuleModel> modules;

    //-----------------
    //
    // Constructors
    //
    //-----------------

    public StudentModel(String name, String studentID, String dateOfBirth, int semester){
        this.name = name;
        this.studentID = studentID;
        this.dateOfBirth = dateOfBirth;
        this.semester = semester;
        modules = new ArrayList<ModuleModel>();
    }

    //-----------------
    //
    // Getters
    //
    //-----------------
    public String getName(){
        return name;
    }
    public String getStudentID(){
        return studentID;
    }
    public String getDateOfBirth(){
        return dateOfBirth;
    }
    public ArrayList<ModuleModel> getModules(){return modules;}
    public ModuleModel getModule(int index){
        if(modules.size() > 0){
            return modules.get(index);

        }
        else return new ModuleModel("No modules currently exist", "",0);
    }


    //-----------------
    //
    // Setters
    //
    //-----------------
    public void setName(String _name){
        this.name = _name;
    }
    public void setStudentID(String _studentID){
        this.studentID = _studentID;
    }
    public void setDateOfBirth(String _dateOfBirth){
        this.dateOfBirth= _dateOfBirth;
    }
    public void setSemester(int _semester){this.semester = semester;}

    //-----------------
    //
    // Other Functionality
    //
    //-----------------

    @Override
    public String toString() {
        return "Name: " + name + ", StudentID: " + studentID + ", Date of Birth: " + dateOfBirth
                + ", Semester: " + semester + "." ;
    }
}
