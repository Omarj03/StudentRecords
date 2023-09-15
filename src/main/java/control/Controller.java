package control;

import javafx.animation.PauseTransition;
import javafx.scene.control.*;
import javafx.util.Duration;
import model.Grade;
import model.Module;
import model.Student;
import view.View;


import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;


@SuppressWarnings({"rawtypes", "unchecked"})
public class Controller {

    //-----------------
    //
    // Fields
    //
    //-----------------


    private final View view;

    private Connection connection = null;


    //-----------------
    //
    // Constructor
    //
    //-----------------
    public Controller(View view) {
        this.view = view;
        String url = "jdbc:mysql://localhost:3306/mtuStudentDB";
        String username = "user";
        String password = "toor";
        try {
            connection = DriverManager.getConnection(url, username, password);

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
     * Creates a new student object and adds it to the database
     *
     * @param name        - Name of the student
     * @param studentID   - Student ID
     * @param dateOfBirth - Student Date of Birth
     * @param semester - Semester the student is currently in. Independent of year, for example, semester = 4 means Year
     *                 2 semester 2.
     * @return - boolean indicating whether the add operation was a success or failure
     */
    // Checks if any fields are blank, and if not creates a
    // new student object, before adding it to model
    public boolean addStudent(String name, String studentID, String dateOfBirth, int semester) {
        boolean res = true;
        String errorStr = "Please enter a valid ";
        int errorCount = 0;
        LocalDate formattedDate =  LocalDate.now();

        if(Objects.equals(dateOfBirth, "")){
            res = false;
        }
        if (Objects.equals(name, "")) {
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
        if (!checkDoB(formattedDate) || Objects.equals(dateOfBirth, "")) {
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
            errorMessage(1.5, errorStr, view.errorLbl);
        }


        return res;
    }


    /**
     * <H1>Remove</H1>
     * removes the selected student from the database
     *
     * @param _lv - list view where student details are displayed
     */
    public void removeStudent(ListView<Student> _lv) {
        if (_lv.getSelectionModel().getSelectedItem() != null) {
            try {
                Statement removeStatement = connection.createStatement();
                String sNo = _lv.getSelectionModel().getSelectedItem().getStudentID();
                String query = String.format("delete from student where idStudent = '%s';", sNo);
                removeStatement.executeUpdate(query);

                Statement removeGrades = connection.createStatement();
                String rmGradeQuery = String.format("DELETE  from studentgrade where idstudent = '%s';", sNo);
                removeGrades.executeUpdate(rmGradeQuery);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            errorMessage(1.5, "Please select a student", view.errorLbl);
        }
    }

    /**
     * <H1>List</H1>
     * Displays the currently stored users in the display text area
     *
     * @param _lv - List view where student details are displayed
     */
    public void list(ListView<Student> _lv) {
        try {
            Statement getStudents = connection.createStatement();
            ResultSet resultSet = getStudents.executeQuery("select * from student;");
            _lv.getItems().clear();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String sId = resultSet.getString("idStudent");
                String doB = resultSet.getString("dateOfBirth");
                int sem = resultSet.getInt("Semester");


                _lv.getItems().add(new Student(name, sId, doB, sem));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <H1>Check Student ID</H1>
     * Validates whether the student ID matches regex, and ensures it is not already present in the database
     *
     * @param inputSId - inputted student ID to be validated
     * @return - boolean indicating whether the student ID is valid
     */
    public boolean checkSId(String inputSId) {
        boolean res = inputSId.matches("R+\\d{8}");

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
     * @param doB - inputted student date of birth to be validated
     * @return - boolean indicating whether the date of birth is valid
     */
    public boolean checkDoB(LocalDate doB) {
        boolean res = false;
        LocalDate currentTime = LocalDate.now();
        try {
            if (doB != null && !(doB.isAfter(currentTime)))
                res = true;

        } catch (Exception ignored) {
        }

        return res;
    }

    //-----------------
    //
    // Tab 2
    //
    //-----------------

    /**
     * <H1>Add Grade</H1>
     * Adds a grade to the database
     */
    public void addGrade() {
        try {
            String sID = view.studentCBTab2.getValue();
            String code = view.moduleCBTab2.getValue();
            int grade = Integer.parseInt(view.gradeTF.getText());
            if (!(grade > 100 || grade < 0)) { // Ensure grade is not above or below 100
                Statement getGrades = connection.createStatement();
                Statement addGrade = connection.createStatement();
                String query = String.format("select * from studentgrade WHERE (idstudent = '%s') " +
                        "AND (modulecode = '%s');", sID, code);
                ResultSet rset = getGrades.executeQuery(query);

                if (!rset.next()) {
                    addGrade.executeUpdate(String.format("INSERT INTO studentgrade VALUES('%s','%s',%d);",
                            sID, code, grade));
                } else {
                    addGrade.executeUpdate(String.format("UPDATE studentgrade " +
                            "SET grade = %d " +
                            "WHERE (idstudent = '%s') AND (modulecode = '%s');", grade, sID, code));
                }
            } else
                view.gradeTF.clear();
            gradeLVUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * <H1>Remove Grade</H1>
     * Removes the selected grade from the database
     */
    public void removeGrade() {
        try {
            String sID = view.GradeLV.getSelectionModel().getSelectedItem().getSID();
            String mCode = view.GradeLV.getSelectionModel().getSelectedItem().getmCode();

            Statement removeGrade = connection.createStatement();
            String query = String.format("DELETE FROM studentgrade WHERE  (modulecode = '%s') AND (idstudent = '%s');"
                    , mCode, sID);
            removeGrade.executeUpdate(query);
            gradeLVUpdate();
        } catch (Exception e) {
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
     * <H1>Student CB Update</H1>
     * - Updates combo box containing students in the database
     *
     * @param _cb - comboBox to be updated
     */
    public void studentCBUpdate(ComboBox<String> _cb) {
        try {
            Statement getStudents = connection.createStatement();
            ResultSet resultSet = getStudents.executeQuery("select * from student;");
            _cb.getItems().clear();
            while (resultSet.next()) {
                String sId = resultSet.getString("idStudent");
                _cb.getItems().add(sId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <H1>Module CB Update</H1>
     * updates a combobox to include all modules in the database
     * @param _cb - comboBox to be updated
     */
    public void moduleCBUpdate(ComboBox<String> _cb) {
        try {
            Statement getModules = connection.createStatement();
            ResultSet resultSet = getModules.executeQuery("select * from module;");
            _cb.getItems().clear();
            while (resultSet.next()) {
                String code = resultSet.getString("modulecode");
                _cb.getItems().add(code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <H1>Grade LV Update</H1>
     * Updates a list view containing the grades in the database for the appropriate student
     */
    public void gradeLVUpdate() {
        try {
            Statement getModules = connection.createStatement();
            Statement getModule = connection.createStatement();

            String sID = view.studentCBTab2.getSelectionModel().getSelectedItem();
            ListView<Grade> _lv = view.GradeLV;
            String query = String.format("select * from studentgrade where idstudent = '%s';"
                    , sID);
            ResultSet resultSet = getModules.executeQuery(query);
            String moduleQuery;

            _lv.getItems().clear();
            while (resultSet.next()) {
                int grade = resultSet.getInt("grade");
                String code = resultSet.getString("modulecode");
                moduleQuery = String.format("select * from module where modulecode = '%s';", code);
                ResultSet moduleRSet = getModule.executeQuery(moduleQuery);
                moduleRSet.next();
                view.GradeLV.getItems().add(new Grade(sID, code, grade));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-----------------
    //
    // Tab 3
    //
    //-----------------


    /**
     * <H1>Student Module LV Sort</H1>
     * sorts the student module list view, either by name or by grade
     *
     * @param _lv      - The listView to be sorted
     * @param sortMode - If true, modules will be sorted by grade. Otherwise, modules will be sorted by name.
     */
    public boolean studentDetailsLVSort(ListView _lv, boolean sortMode) {
        boolean res = true;
        try {
            Statement getGrades = connection.createStatement();
//                Statement getModule = connection.createStatement();
            Statement getStudent = connection.createStatement();
            ResultSet resultSet;
//                ResultSet moduleResultSet;
            ResultSet studentResultSet;
            String query;

            String sID = view.tab3StudentCB.getSelectionModel().getSelectedItem();
            query = String.format("SELECT Semester FROM student WHERE idstudent = '%s';", sID);
            _lv.getItems().clear();

            studentResultSet = getStudent.executeQuery(query);
            if (studentResultSet.next()) {
                int studentSem = studentResultSet.getInt("semester");
                if (sortMode) {
                    query = String.format("""
                                    SELECT  m.name, m.modulecode, m.semester,g.grade
                                    FROM module AS m
                                    JOIN studentgrade AS g ON m.modulecode = g.modulecode
                                    WHERE g.idstudent = '%s'
                                    AND m.semester < %d
                                    GROUP BY modulecode;\s"""
                            , sID, studentSem);
                    resultSet = getGrades.executeQuery(query);
                    while (resultSet.next()) {
                        String mCode = resultSet.getString("modulecode");
                        int grade = resultSet.getInt("grade");
                        String moduleName = resultSet.getString("name");

                        if (grade >= 40) {
                            _lv.getItems().add(String.format("%s (%s): %d", moduleName, mCode, grade));
                        } else
                            _lv.getItems().add(String.format("%s (%s): NP", moduleName, mCode));
                    }
                } else {
                    query = String.format("SELECT  m.name, m.modulecode, m.semester,g.grade\n" +
                                    "\n" +
                                    "FROM module AS m\n" +
                                    "JOIN studentgrade AS g ON m.modulecode = g.modulecode\n" +
                                    "WHERE g.idstudent = '%s'\n" +
                                    "AND m.semester < %d\n" +
                                    "AND g.grade >= 40\n" +
                                    "GROUP BY modulecode; "
                            , sID, studentSem);
                    resultSet = getGrades.executeQuery(query);
                    while (resultSet.next()) {
                        String mCode = resultSet.getString("modulecode");
                        int grade = resultSet.getInt("grade");
                        String moduleName = resultSet.getString("name");

                        _lv.getItems().add(String.format("%s (%s): %d", moduleName, mCode, grade));
                    }
                }
            }


        } catch (Exception e) {
            res = false;
            e.printStackTrace();
        }

        return res;
    }


    /**<H1>Add Module</H1>
     * Adds a module to the database
     * @param name - Module name
     * @param moduleCode - Module code
     * @param sem - Module semester
     */
    //-----------------
    //
    // Tab 4
    //
    //-----------------
    public void addModule(String name, String moduleCode, int sem) {
        boolean res = true;
        String errorStr = "please enter a valid ";
        int errorCount = 0;

        if (Objects.equals(name, "")) {
            res = false;
            errorStr += "name";
            errorCount++;

        }
        if (Objects.equals(moduleCode, "")) {
            res = false;
            if (errorCount == 0) {
                errorStr += "module code";
            } else
                errorStr += ", module code";
        }

        // Check it the module already exists in db
        try {
            Statement getModules = connection.createStatement();
            ResultSet resultSet = getModules.executeQuery(String.format("SELECT * FROM module where modulecode = '%s'",
                    moduleCode));
            if (resultSet.next())
                res = false;
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (res) {
            try {
                Statement addModuleStatement = connection.createStatement();
                addModuleStatement.executeUpdate(String.format("INSERT INTO module VALUES('%s','%s',%d);",
                        moduleCode, name, sem));
                view.mNameTf.clear();
                view.mCodeTf.clear();
                listModulesTab4(view.resultLVTab4);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            errorStr += '.';
            errorMessage(1.5, errorStr, view.errorLbl);
        }

    }

    /**
     *<H1>Remove Module</H1>
     * Removes the module selected in a list view from the database
     * @param _lv - The listview to be checked for selection
     */
    public void removeModule(ListView<Module> _lv) {
        if (_lv.getSelectionModel().getSelectedItem() != null) {
            try {
                Statement removeStatement = connection.createStatement();
                String modulecode = _lv.getSelectionModel().getSelectedItem().getCode();
                String query = String.format("delete from module where modulecode = '%s';", modulecode);
                removeStatement.executeUpdate(query);

                Statement removeGrades = connection.createStatement();
                String rmGradeQuery = String.format("DELETE  from studentgrade where modulecode = '%s';", modulecode);
                removeGrades.executeUpdate(rmGradeQuery);
                listModulesTab4(_lv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            errorMessage(1.5, "Please select a module", view.errorLbl);
        }

    }

    /**<H1>List Modules Tab 4</H1>
     * Updates the list view of modules on tab 4 to display the modules in the database
     * @param _lv - The list view to be updated
     */
    public void listModulesTab4(ListView<Module> _lv) {
        try {
            Statement getModules = connection.createStatement();
            ResultSet resultSet = getModules.executeQuery("select * from module;");
            _lv.getItems().clear();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String mCode = resultSet.getString("modulecode");
                int sem = resultSet.getInt("Semester");//

                _lv.getItems().add(new Module(name, mCode, sem));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <H1>Memory leak sim</H1>
     * Simulates a memory leak by creating students and adding them to an array in an infinite loop. Prints
     * the Heap size, time to error ,and free memory before and after to the console.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void memoryLeakSim() {
        ArrayList<Student> studentLeak = new ArrayList<>();
        long endTime;
        long startime = System.currentTimeMillis();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long heapSize = Runtime.getRuntime().totalMemory();
        System.out.println("Free Memory before: " + (freeMemory >> 20) + "MB");
        System.out.println("Heap Size: " + (heapSize >> 20) + "MB");

        try {
            while (true) {
                studentLeak.add(new Student("HA!", "HA!", "HA!", 1));
            }
        } catch (OutOfMemoryError ex) {
            endTime = System.currentTimeMillis();
            System.out.println("Time to error: " + (endTime - startime) + "ms");
            freeMemory = Runtime.getRuntime().freeMemory();
            System.out.println("Free Memory after: " + (freeMemory >> 20) + "MB");

        }

    }
}
