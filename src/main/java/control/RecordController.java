package control;

import javafx.animation.PauseTransition;
import javafx.scene.control.*;
import javafx.util.Duration;
import model.Grade;
import model.ModuleModel;
import model.StudentModel;


import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class RecordController {

    //-----------------
    //
    // Fields
    //
    //-----------------


    private ArrayList<StudentModel> model;
    private RecordView view;

    private Connection connection = null;
    private Statement statement;
    private String url;
    private String username;
    private String password;


    //-----------------
    //
    // Constructor
    //
    //-----------------
    public RecordController(RecordView view) {
        this.model = new ArrayList<StudentModel>();
        this.view = view;
        this.url = "jdbc:mysql://localhost:3306/mtustudentdb";
        this.username = "user";
        this.password = "toor";
        try {
            connection = DriverManager.getConnection(url, username, password);

            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //-----------------
    //
    // Root
    //
    //-----------------


    /**
     * <H1>Save</H1>
     * commits changes in the database
     *
     * @return boolean indicating whether the save operation was a success or failure
     */
    // Creates a string consisting of the current students
    // in model, then writes them to file
    public boolean save() {
//        boolean res;
//        try{
//            connection.commit();
//            res = true;
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            res = false;
//        }
//


        return true;

    }


    /**
     * <H1>Error Message</H1>
     * sends an error message to the error label to be displayed
     *
     * @param _seconds    - the amount of seconds the message will display for
     * @param _message    - the desired message to be displayed
     * @param _errorLabel - the label to be used
     */
    public void errorMessage(double _seconds, String _message, Label _errorLabel) {
        PauseTransition errorPause = new PauseTransition(Duration.seconds(_seconds));
        _errorLabel.setText(_message);
        _errorLabel.setVisible(true);
        errorPause.setOnFinished(e -> _errorLabel.setVisible(false));
        errorPause.play();
    }

    /**
     * <H1>Start Up</H1>
     * sets up application
     * <b>loads data</b>
     * <b>lists the data</b>
     *
     */
    public void startUp() {
        boolean res = true;
        list(view.resultLV);
        listModulesTab4(view.resultLVTab4);
        if (!res) {
            errorMessage(3, "Error loading data", view.errorLbl);
        }
    }

    /**
     * <H1>Exit</H1>
     * Prompts the user if they would like to save after attempting to exit, before closing the application
     */
    // Prompt user if they would like to save after
    // attempting to exit, then closes application
    public void exit() {
        ButtonType saveButton = new ButtonType("Save");
        ButtonType dontSaveButton = new ButtonType("Exit Without Saving");
        Alert closeConfirm = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to save?", saveButton, dontSaveButton);
        closeConfirm.setTitle("Exit Application");
        closeConfirm.setHeaderText("Exiting Application");
        closeConfirm.showAndWait();

        if (closeConfirm.getResult().equals(saveButton)) {
            save();
            System.exit(0);
        } else {
            System.exit(0);
        }

    }

    //-----------------
    //
    // Tab 1
    //
    //-----------------

    /**
     * <H1>Add</H1>
     * Creates a new student object and adds it to the model
     *
     * @param name        - Name of the student
     * @param studentID   - Student ID
     * @param dateOfBirth - Student Date of Birth
     * @return - boolean indicating whether the add operation was a success or failure
     */
    // Checks if any fields are blank, and if not creates a
    // new student object, before adding it to model
    public boolean addStudent(String name, String studentID, String dateOfBirth, int semester) {
        boolean res = true;
        String errorStr = "Please enter a valid ";
        int errorCount = 0;
        LocalDate formattedDate = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        if (name == "") {
            res = false;
            errorStr += "name";
            errorCount++;
        }
        if (!checkSId(studentID)) {
            res = false;
            if (errorCount == 0) {
                errorStr += "student ID";
                errorCount++;
            } else
                errorStr += ", student ID";
        }
        if (!checkDoB(formattedDate)) {
            res = false;

            if (errorCount == 0)
                errorStr += "date";
            else
                errorStr += ", date";
        }
        if (semester < 1) {
            res = false;
        }

        if (res) {
            System.out.println("adding student");

            try {
                Statement AddStatement = connection.createStatement();
                AddStatement.executeUpdate(String.format("INSERT INTO student VALUES (\"%s\",\"%s\",\"%s\",\"%d\")",
                        studentID, name, formattedDate.toString(), semester));
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            errorStr += '.';
        }


        return res;
    }


    /**
     * <H1>Remove</H1>
     * removes the most recent student from the database
     *
     * @param _lv - list view where student details are displayed
     */
    public void removeStudent(ListView<StudentModel> _lv) {
        if(_lv.getSelectionModel().getSelectedItem() != null){
            try {
                Statement removeStatement = connection.createStatement();
                String sNo = _lv.getSelectionModel().getSelectedItem().getStudentID();
                String query = String.format("delete from student where idStudent = '%s';", sNo);
                removeStatement.executeUpdate(query);

                Statement removeGrades = connection.createStatement();
                String rmGradeQuery = String.format("DELETE  from studentgrade where idstudent = '%s';",sNo);
                removeGrades.executeUpdate(rmGradeQuery);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else{
            errorMessage(1.5,"Please select a student", view.errorLbl);
        }
    }

    /**
     * <H1>List</H1>
     * Displays the currently stored users in the display text area
     *
     * @param _lv - List view where student details are displayed
     */
    public void list(ListView _lv) {
        try {
            Statement getStudents = connection.createStatement();
            ResultSet resultSet = getStudents.executeQuery("select * from student;");
            _lv.getItems().clear();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String sId = resultSet.getString("idStudent");
                String doB = resultSet.getString("dateOfBirth");
                int sem = resultSet.getInt("Semester");


                _lv.getItems().add(new StudentModel(name, sId, doB, sem));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <H1>Check Student ID</H1>
     * Validates whether the student ID matches regex
     *
     * @param inputSId - inputted student ID to be validated
     * @return - boolean indicating whether the student ID is valid
     */
    public boolean checkSId(String inputSId) {
        boolean res = true;
        if (!inputSId.matches("[R]+\\d{8}"))
            res = false;

        if (res) {
            try {
                Statement getStatement = connection.createStatement();
                String query = String.format("select * from student WHERE idStudent = '%s';", inputSId);
                ResultSet rset = getStatement.executeQuery(query);
                if (rset.next()) {
                    res = false;
                    errorMessage(1.5, "Student already exists", view.errorLbl);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return res;
    }

    /**
     * <H1>Check DoB</H1>
     * Validates whether the date matches format and is within valid range
     *
     * @param doB - inputed student date of birth to be validated
     * @return - boolean indicating whether the date of birth is valid
     */
    public boolean checkDoB(LocalDate doB) {
        boolean res = false;
        LocalDate currentTime = LocalDate.now();
        try {
            if (doB != null && !(doB.isAfter(currentTime)))
                res = true;

        } catch (Exception e) {
            res = false;
        }

        return res;
    }

    //-----------------
    //
    // Tab 2
    //
    //-----------------

    public boolean addGrade(){
        boolean res = false;
        try{
            String sID = view.studentCBTab2.getValue();
            String code = view.moduleCBTab2.getValue();
            int grade = Integer.parseInt(view.gradeTF.getText());
            if(!(grade>100 || grade < 0)){ // Ensure grade is not above or below 100
                Statement getGrades = connection.createStatement();
                Statement addGrade = connection.createStatement();
                String query = String.format("select * from studentgrade WHERE (idstudent = '%s') " +
                        "AND (modulecode = '%s');", sID,code);
                ResultSet rset = getGrades.executeQuery(query);

                if(!rset.next()){
                    addGrade.executeUpdate(String.format("INSERT INTO studentgrade VALUES('%s','%s',%d);",
                            sID,code,grade));
                }
                else{
                    addGrade.executeUpdate(String.format("UPDATE studentgrade " +
                            "SET grade = %d " +
                            "WHERE (idstudent = '%s') AND (modulecode = '%s');",grade,sID,code));
                }
            } else

            view.gradeTF.clear();
            gradeLVUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }

        return res;
    }

    public void removeGrade(){
        try{
            String sID = view.GradeLV.getSelectionModel().getSelectedItem().getSID();
            String mCode = view.GradeLV.getSelectionModel().getSelectedItem().getmCode();

            Statement removeGrade = connection.createStatement();
            String query = String.format("DELETE FROM studentgrade WHERE  (modulecode = '%s') AND (idstudent = '%s');"
            ,mCode,sID);
            removeGrade.executeUpdate(query);
            gradeLVUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * <H1>List View Update</H1>
     * Updates ListView containing students
     *
     * @param _lv - list view to be updated
     */
    public void studentLVUpdate(ListView _lv) {
        try {
            Statement listModules = connection.createStatement();
            ResultSet rset = listModules.executeQuery("select * from student;");
            _lv.getItems().clear();
            while (rset.next()) {
                _lv.getItems().add(rset.getString("idStudent"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * <H1>ComboBox Update</H1>
     * - Updates combo box containing students
     *
     * @param _cb - comboBox to be updated
     */
    public void studentCBUpdate(ComboBox<String> _cb) {
        try{
            Statement getStudents = connection.createStatement();
            ResultSet resultSet = getStudents.executeQuery("select * from student;");
            _cb.getItems().clear();
            while (resultSet.next()) {
                String sId = resultSet.getString("idStudent");
                _cb.getItems().add(sId);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void moduleCBUpdate(ComboBox<String> _cb) {
        try{
            Statement getModules = connection.createStatement();
            ResultSet resultSet = getModules.executeQuery("select * from module;");
            _cb.getItems().clear();
            while (resultSet.next()) {
                String code = resultSet.getString("modulecode");
                _cb.getItems().add(code);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void gradeLVUpdate(){
        try{
            Statement getModules = connection.createStatement();
            Statement getModule = connection.createStatement();

            String sID = view.studentCBTab2.getSelectionModel().getSelectedItem();
            ListView _lv = view.GradeLV;
            String query = String.format("select * from studentgrade where idstudent = '%s';"
                    ,sID);
            ResultSet resultSet = getModules.executeQuery(query);
            String moduleQuery;

            _lv.getItems().clear();
            while(resultSet.next()){
                int grade = resultSet.getInt("grade");
                String code = resultSet.getString("modulecode");
                moduleQuery = String.format("select * from module where modulecode = '%s';",code);
                ResultSet moduleRSet = getModule.executeQuery(moduleQuery);
                moduleRSet.next();
                String name = moduleRSet.getString("name");
                view.GradeLV.getItems().add(new Grade(sID,code,grade));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //-----------------
    //
    // Tab 3
    //
    //-----------------

    /**
     * <H1>Student Details Update</H1>
     * Updates the student details displayed in tab 3
     *
     * @param studentIndex - index of the student in the database
     * @param _tf1         - name text field
     * @param _tf2         - student ID text field
     */
    public void studentDetailsUpdate(int studentIndex, TextField _tf1, TextField _tf2) {
        _tf1.setText(model.get(studentIndex).getName());
        _tf2.setText(model.get(studentIndex).getStudentID());

    }



    /**
     * <H1>Student Module LV Sort</H1>
     * sorts the student module list view, either by name or by grade
     *
     * @param _lv          - The listView to be sorted
     * @param sortMode     - If true, modules will be sorted by grade. Otherwise, modules will be sorted by name.
     */
    public boolean studentDetailsLVSort(ListView _lv, boolean sortMode) {
        boolean res = true;
            try{
                Statement getGrades = connection.createStatement();
//                Statement getModule = connection.createStatement();
                Statement getStudent= connection.createStatement();
                ResultSet resultSet;
//                ResultSet moduleResultSet;
                ResultSet studentResultSet;
                String query;

                String sID = view.tab3StudentCB.getSelectionModel().getSelectedItem();
                query = String.format("SELECT Semester FROM student WHERE idstudent = '%s';",sID);
                _lv.getItems().clear();

                studentResultSet = getStudent.executeQuery(query);
                if(studentResultSet.next()){
                    int studentSem = studentResultSet.getInt("semester");
                    if (sortMode){
                        query = String.format("SELECT  m.name, m.modulecode, m.semester,g.grade\n" +
                                        "\n" +
                                        "FROM module AS m\n" +
                                        "JOIN studentgrade AS g ON m.modulecode = g.modulecode\n" +
                                        "WHERE g.idstudent = '%s'\n" +
                                        "AND m.semester < %d\n" +
                                        "GROUP BY modulecode; "
                                ,sID,studentSem);
                        resultSet = getGrades.executeQuery(query);
                        while(resultSet.next()){
                            String mCode = resultSet.getString("modulecode");
                            int grade = resultSet.getInt("grade");
                            String moduleName = resultSet.getString("name");

                            if(grade >= 40)
                                _lv.getItems().add(String.format("%s (%s): %d",moduleName,mCode,grade));
                            else
                                _lv.getItems().add(String.format("%s (%s): NP",moduleName,mCode));
                        }
                    }
                    else{
                        query = String.format("SELECT  m.name, m.modulecode, m.semester,g.grade\n" +
                                        "\n" +
                                        "FROM module AS m\n" +
                                        "JOIN studentgrade AS g ON m.modulecode = g.modulecode\n" +
                                        "WHERE g.idstudent = '%s'\n" +
                                        "AND m.semester < %d\n" +
                                        "AND g.grade >= 40\n" +
                                        "GROUP BY modulecode; "
                                ,sID,studentSem);
                        resultSet = getGrades.executeQuery(query);
                        while(resultSet.next()){
                            String mCode = resultSet.getString("modulecode");
                            int grade = resultSet.getInt("grade");
                            String moduleName = resultSet.getString("name");

                                _lv.getItems().add(String.format("%s (%s): %d",moduleName,mCode,grade));
                        }
                    }
                }


            }
            catch (Exception e){
                res = false;
                e.printStackTrace();
            }

        return res;
    }


    //-----------------
    //
    // Tab 4
    //
    //-----------------
    public boolean addModule(String name, String moduleCode, int sem){
        boolean res = true;
        String errorStr = "please enter a valid";
        int errorCount = 0;

        if (name == "") {
            res = false;
            errorStr += "name";
            errorCount++;

        }
        if (moduleCode == "") {
            res = false;
            if(errorCount == 0){
                errorStr += "module code";
                errorCount++;
            }
            else
                errorStr += ", module code";
        }

        // Check it the module already exists in db
        try {
            Statement getModules = connection.createStatement();
            ResultSet resultSet = getModules.executeQuery(String.format("SELECT * FROM module where modulecode = '%s'",
                    moduleCode));
            if(resultSet.next())
                res = false;
        }
        catch (Exception e){
            e.printStackTrace();
        }


        if(res){
            try {
                Statement addModuleStatement = connection.createStatement();
                addModuleStatement.executeUpdate(String.format("INSERT INTO module VALUES('%s','%s',%d);",
                        moduleCode,name,sem));
                view.mNameTf.clear();
                view.mCodeTf.clear();
                listModulesTab4(view.resultLVTab4);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        } else{
            errorStr += '.';
            errorMessage(1.5,errorStr,view.errorLbl);
        }

        return res;
    }

    public void removeModule(ListView<ModuleModel> _lv) {
        if(_lv.getSelectionModel().getSelectedItem() != null){
            try{
                Statement removeStatement = connection.createStatement();
                String modulecode = _lv.getSelectionModel().getSelectedItem().getCode();
                String query = String.format("delete from module where modulecode = '%s';",modulecode);
                removeStatement.executeUpdate(query);

                Statement removeGrades = connection.createStatement();
                String rmGradeQuery = String.format("DELETE  from studentgrade where modulecode = '%s';",modulecode);
                removeGrades.executeUpdate(rmGradeQuery);
                listModulesTab4(_lv);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            errorMessage(1.5,"Please select a module",view.errorLbl);
        }

    }

    public void listModulesTab4(ListView _lv){
        try {
            Statement getModules = connection.createStatement();
            ResultSet resultSet = getModules.executeQuery("select * from module;");
            _lv.getItems().clear();
            while (resultSet.next()){
                                String name = resultSet.getString("name");
                String mCode = resultSet.getString("modulecode");
                int sem = resultSet.getInt("Semester");//

                _lv.getItems().add(new ModuleModel(name,mCode,sem));
            }
        }
        catch (Exception e){

        }
    }

    public void memoryLeakSim(){
        ArrayList<StudentModel> studentLeak = new ArrayList<StudentModel>();
        long endtime;
        long startime = System.currentTimeMillis();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long heapsize = Runtime.getRuntime().totalMemory();
        System.out.println("Free Memory before: " + (freeMemory >> 20)+ "MB");
        System.out.println("Heap Size: " + (heapsize >> 20) + "MB");

        try{
            while (true){
                studentLeak.add(new StudentModel("HA!","HA!","HA!",1));
            }
        }
        catch (OutOfMemoryError ex){
            endtime = System.currentTimeMillis();
            System.out.println("Time to error: "  +  (endtime-startime) + "ms");
            freeMemory = Runtime.getRuntime().freeMemory();
            System.out.println("Free Memory after: " + (freeMemory >> 20) + "MB" );

        }

    }
}







