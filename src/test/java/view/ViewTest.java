package view;

import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test suite for the View class
 */
class ViewTest {

    // Starting JavaFX application
    public static class StartUp extends Application {
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
                Application.launch(ViewTest.StartUp.class);
            }
        };
        t.setDaemon(true);
        t.start();

    }



    View testView = new View();

    /**
     * Checks the getRoot method
     */
    @Test
    void getRoot() {
        testView.viewStartUp();
        assertNotNull(testView.getRoot());
    }
}