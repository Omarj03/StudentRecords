package model;

/**
 * <H1>Grade</H1>
 * Grade class that modules a grade for a student in a particular module
 */
public class Grade {
    private final String sID;
    private final String mCode;
    private final int grade;

    /**
     * @param _sID - The student ID of the student
     * @param _mCode - The Module code for the module
     * @param _grade - The grade the student received in the appropriate module
     */
    public Grade(String _sID, String _mCode, int _grade){
        this.sID = _sID;
        this.mCode = _mCode;
        this.grade = _grade;
    }

    public String getSID(){return this.sID;}
    public String getmCode(){return this.mCode;}
    public int getGrade(){return this.grade;}

    @Override
    public String toString(){
        return String.format("%s (%s): %d",sID,mCode,grade);
    }
}
