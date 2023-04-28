package control;



import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Grade;
import model.ModuleModel;
import model.StudentModel;
import control.RecordController;

public class RecordView {
    // Controller
    RecordController control;


    //-----------------
    //
    // Layouts
    //
    //-----------------

    //-----------------
    // TabPane
    //-----------------


    //-----------------
    // Tab 1
    //-----------------
    protected VBox tab1Root = new VBox();

    protected GridPane inputGPTab1 = new GridPane();

    protected HBox buttonHB = new HBox();

    // Input Labels
    protected Label nameLbl = new Label("Name: ");
    protected Label sIDLbl = new Label("Student ID: ");
    protected Label doBLbl = new Label("Date of Birth: ");
    protected Label spinLbl = new Label("Semester: ");

    //Input Text Fields
    protected TextField nameTf = new TextField();
    protected Spinner<Integer> semSpinT1 = new Spinner<Integer>(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1,8));
    protected TextField sIDTf = new TextField();
    protected DatePicker doBTf = new DatePicker();

    // List Control Buttons
    protected Button addBtn = new Button("Add");
    protected Button removeBtn = new Button("Remove");
    protected Button listBtn = new Button("Refresh List");


    // Display TextArea
    protected ListView<StudentModel> resultLV = new ListView<StudentModel>();

    //-----------------
    // Tab 2
    //-----------------
    protected HBox tab2Root = new HBox();
    protected ListView selectStudentTab2 = new ListView();
    protected GridPane inputGPTab2 = new GridPane();

    protected ComboBox<String> studentCBTab2 = new ComboBox<String>();
    protected ComboBox<String> moduleCBTab2 = new ComboBox<String>();
    protected TextField gradeTF = new TextField();
    protected ListView<Grade> GradeLV = new ListView<Grade>();
    protected Button saveModule = new Button("Add");
    protected Button removeModule = new Button("Remove");


    //-----------------
    // Tab 3
    //-----------------
    protected VBox tab3Root = new VBox();
    protected GridPane tab3GP = new GridPane();
    protected ListView studentModuleLV = new ListView();
    protected ComboBox<String> tab3StudentCB = new ComboBox<String>();
    protected TextField studentIdTF = new TextField();
    //    protected Button sortNameBtn = new Button("Sort by name");
//    protected Button sortGradeBtn = new Button("Sort by grade");
    protected RadioButton sortPassedRB = new RadioButton("Passed");
    protected RadioButton sortAllRB = new RadioButton("All");
    protected ToggleGroup sortModeTG = new ToggleGroup();


    //-----------------
    // Tab 4
    //-----------------
    protected VBox tab4Root = new VBox();

    protected GridPane inputGPTab4 = new GridPane();

    protected HBox buttonHBTab4 = new HBox();
    protected ListView<ModuleModel> resultLVTab4 = new ListView<ModuleModel>();

    // Input Labels
    protected Label moduleNameLbl = new Label("Enter Name: ");
    protected Label moduleCodeLbl= new Label("Enter Module Code: ");
    protected Label moduleSemLbl = new Label("Enter Semester: ");

    //Input Text Fields
    protected TextField mNameTf = new TextField();
    protected TextField mCodeTf = new TextField();
    protected Spinner<Integer> semSpin = new Spinner(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,8));


    // List Control Buttons
    protected Button mAddBtn = new Button("Add");
    protected Button mRemoveBtn = new Button("Remove");
    protected Button mListBtn = new Button("Refresh List");



    //-----------------
    // Layout Root
    //-----------------

    protected BorderPane root = new BorderPane();
    protected TabPane tabPane = new TabPane();
    protected Tab tab1 = new Tab("Add Students");
    protected Tab tab2 = new Tab("Update Student Records");
    protected Tab tab3 = new Tab("View Student Records");
    protected Tab tab4 = new Tab("Add Modules");
    protected HBox bottomHB = new HBox();


    // System Buttons
    protected HBox systemButtonHB = new HBox();

    protected Button breakBtn= new Button("DO NOT TOUCH! (memory leak sim)");
    protected Button quitBtn= new Button("Quit");

    // Error Label
    protected Label errorLbl = new Label();




    //-----------------
    //
    // Constructor
    //
    //-----------------

    public RecordView(){
        this.control = new RecordController(this);

    }

    public void viewStartUp(){
        //-----------------
        // Root Config
        //-----------------
        root.setCenter(tabPane);
        root.setBottom(bottomHB);


        //-----------------
        // BottomHB Config
        //-----------------


        bottomHB.getChildren().addAll(errorLbl,systemButtonHB);
        bottomHB.setSpacing(10);
        bottomHB.setAlignment(Pos.BOTTOM_RIGHT);
        bottomHB.setPadding(new Insets(10));

        systemButtonHB.getChildren().addAll(breakBtn,quitBtn);
        systemButtonHB.setSpacing(10);

        //-----------------
        //
        // TabPane Config
        //
        //-----------------

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(tab1,tab2,tab3, tab4);


        //-----------------
        // Tab 1 Config
        //-----------------
        tab1.setContent(tab1Root);


        resultLV.setMinHeight(400);

        // Tab1Root
        tab1Root.getChildren().addAll(inputGPTab1,buttonHB,resultLV);
        tab1Root.setPadding(new Insets(10));
        tab1Root.setSpacing(15);

        // Configure Input Grid pane
        inputGPTab1.addRow(0, nameLbl,nameTf,spinLbl,semSpinT1);
        inputGPTab1.addRow(1,sIDLbl,sIDTf);
        inputGPTab1.addRow(2,doBLbl,doBTf);
        inputGPTab1.setHgap(5);
        inputGPTab1.setVgap(5);

        // Configure date picker
        doBTf.setEditable(false);


        // Configure List Control Button HBox
        buttonHB.getChildren().addAll(addBtn,removeBtn,listBtn);
        buttonHB.setSpacing(10);

        //-----------------
        // Tab 2 Config
        //-----------------
        tab2.setContent(tab2Root);

        tab2Root.setPadding(new Insets(10));
        tab2Root.setSpacing(10);


        // Configure Input Grid pane
        inputGPTab2.setHgap(5);
        inputGPTab2.setVgap(5);


        inputGPTab2.addRow(0,studentCBTab2);
        inputGPTab2.addRow(1,moduleCBTab2);
        inputGPTab2.addRow(2,new Label("Grade: "),gradeTF);

        inputGPTab2.addRow(3,saveModule,removeModule);

        // Configure Tab 2 root
        tab2Root.getChildren().addAll(inputGPTab2, GradeLV);
        tab1Root.setPadding(new Insets(10));
        tab1Root.setSpacing(15);

        //-----------------
        // Tab 3 Config
        //-----------------
        tab3.setContent(tab3Root);



        // Configure Tab 3 root
        tab3Root.setPadding(new Insets(10));
        tab3Root.setSpacing(10);
        tab3Root.getChildren().addAll(tab3GP,studentModuleLV);

        // Confgiure Student Input GP
        tab3StudentCB.setMinWidth(100);
//        tab3GP.add(tab3GP,0,0);

//
//        studentNameTF.setEditable(false);
//        studentDetailsGP.addRow(2,new Label("Name: "),studentNameTF);

        studentIdTF.setEditable(false);
        tab3GP.addRow(0,new Label("Student Number: "),tab3StudentCB);

        tab3GP.addRow(1,new Label("Sort: "), sortAllRB, sortPassedRB);

        tab3GP.setPadding(new Insets(5));


//        // Configure Tab 3 Left
//        tab3Left.getChildren().addAll(tab3, tab3GP);
//        tab3Left.setSpacing(10);
//        tab3Left.setPadding(new Insets(10));

        // Configure radio buttons
        sortAllRB.setToggleGroup(sortModeTG);
        sortPassedRB.setToggleGroup(sortModeTG);

        //-----------------
        // Tab 4 Config
        //-----------------
        tab4.setContent(tab4Root);



        // Tab4Root
        tab4Root.getChildren().addAll(inputGPTab4,buttonHBTab4,resultLVTab4);
        tab4Root.setPadding(new Insets(10));
        tab4Root.setSpacing(15);

        // Configure Input Grid pane
        inputGPTab4.addRow(0, moduleNameLbl,mNameTf);
        inputGPTab4.addRow(1,moduleCodeLbl,mCodeTf);
        inputGPTab4.addRow(2,moduleSemLbl,semSpin);
        inputGPTab4.setHgap(5);
        inputGPTab4.setVgap(5);


        // Configure List Control Button HBox
        buttonHBTab4.getChildren().addAll(mAddBtn,mRemoveBtn,mListBtn);
        buttonHBTab4.setSpacing(10);


        // Configure spinner



        //-----------------
        //
        // Event Handlers
        //
        //-----------------

        //-----------------
        // Root
        //-----------------



        // Load Button
        breakBtn.setOnAction(e -> {
            control.memoryLeakSim();
        });

        // Quit Button
        quitBtn.setOnAction(e -> control.exit());

        //-----------------
        // Tab 1
        //-----------------

        // Add Button
        addBtn.setOnAction(e -> {
            boolean res = control.addStudent(nameTf.getText(), sIDTf.getText(), doBTf.getEditor().getText(),
                    semSpinT1.getValue());
            nameTf.clear();
            sIDTf.clear();
            doBTf.getEditor().clear();
            if(res){
                control.list(resultLV);
                control.studentLVUpdate(selectStudentTab2);
            }
        });

        // Remove Button
        removeBtn.setOnAction(e -> {
            control.removeStudent(resultLV);
            control.list(resultLV);
            control.studentLVUpdate(selectStudentTab2);
        });


        // List Button
        listBtn.setOnAction(e -> control.list(resultLV));

        //-----------------
        // Tab 2
        //-----------------

        tab2.setOnSelectionChanged(e -> control.studentCBUpdate(studentCBTab2));
        studentCBTab2.setOnAction(e -> {
            control.moduleCBUpdate(moduleCBTab2);
            control.gradeLVUpdate();
        });
        saveModule.setOnAction(e -> {
            boolean res = true;

            res = gradeTF.getText() != "";
            res = studentCBTab2.getSelectionModel().getSelectedItem() != null;
            res = moduleCBTab2.getSelectionModel().getSelectedItem() != null;

            if(res){
                control.addGrade();
            }
            else
                control.errorMessage(1.5,"Please ensure student & module are selected, " +
                        "and grade is entered",errorLbl);

        });
        removeModule.setOnAction(e -> {
            boolean res = true;
            res = (GradeLV.getSelectionModel().getSelectedItem() != null);
            if(res)
                control.removeGrade();
            else {
                control.errorMessage(1.5,"Please select a grade to remove",errorLbl);
            }
        });

        //-----------------
        // Tab 3
        //-----------------
        tab3.setOnSelectionChanged(e -> {
            control.studentCBUpdate(tab3StudentCB);
            sortModeTG.selectToggle(sortAllRB);
        });
        tab3StudentCB.setOnAction(e -> {
            studentModuleLV.getItems().clear();
            boolean sortMode = sortModeTG.getSelectedToggle().equals(sortAllRB);
            boolean res = control.studentDetailsLVSort(studentModuleLV,sortMode);
            if (!res)
                control.errorMessage(1.5,"Please select a student",errorLbl);

        });

        // Radio buttons for sort
        sortAllRB.setOnAction(e ->{
            boolean res = control.studentDetailsLVSort(studentModuleLV,true);
            if (!res)
                control.errorMessage(1.5,"Please select a student",errorLbl);
        });

        sortPassedRB.setOnAction(e ->{
            boolean res = control.studentDetailsLVSort(studentModuleLV,false);
            if (!res)
                control.errorMessage(1.5,"Please select a student",errorLbl);
        });

        //-----------------
        // Tab 4
        //-----------------

        // Add Button
        mAddBtn.setOnAction(e -> {
            control.addModule(mNameTf.getText(),mCodeTf.getText(),semSpin.getValue());
        });

        // Remove Button
        mRemoveBtn.setOnAction(e -> control.removeModule(resultLVTab4));


        // List Button
        mListBtn.setOnAction(e -> control.listModulesTab4(resultLVTab4));



        control.startUp();

    }


    //-----------------
    //
    // Getters & Setters
    //
    //-----------------

    public BorderPane getRoot(){
        return root;
    }
    public Label getErrorLabel() {return errorLbl;}




}
