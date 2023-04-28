package model;

public class Grade {
    private String sID;
    private String mCode;
    private int grade;

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
