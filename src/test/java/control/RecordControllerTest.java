package control;


import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecordControllerTest {
    private RecordController control = new RecordController(new RecordView());


    // Starting JavaFX application
    public static class StartUp extends Application{
        @Override
        public void start(Stage primaryStage) throws Exception{

        }
    }

    /**
     * Startup
     */
    @BeforeAll
    public static void jFXStartup(){
        Thread t = new Thread("JavaFX Thread"){
            public void run(){
                Application.launch(StartUp.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();

    }


    @Test
    void checkSId() {
        String validSID = "R00215202";
        String emptySID = "";
        String badFirstCharSID = "A00217303";
        String alreadyExistsSID = "R00217303";
        assertTrue(control.checkSId(validSID));
        assertFalse(control.checkSId(emptySID));
        assertFalse(control.checkSId(badFirstCharSID));
        assertFalse(control.checkSId(alreadyExistsSID));

    }




}