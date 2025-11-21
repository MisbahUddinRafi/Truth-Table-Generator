package utilities;

import javafx.application.Application;              // import the javafx class Application
import javafx.fxml.FXMLLoader;                      // to load .fxml files
import javafx.scene.Scene;                          // Scene = container for all UI elements (buttons, layouts etc.)
import javafx.stage.Stage;                          // Stage = main window of JavaFX application
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {             // utilities.Main class must extend the built-in Application class and must override the start() method

    public static Stage primaryStage;               // used to switch scenes
    private static List<Scene> sceneStack = new ArrayList<>();
    public static StringBuilder expression = new StringBuilder();

    public static void main(String[] args) throws IOException {        // starting point of the JavaFX app
        launch(args);                               // initialize javafx and call the start() method
    }

    @Override
    public void start(Stage stage) throws Exception {       // main GUI setup method
        primaryStage = stage;
        setRoot("view/mainPage.fxml");                  // Login Page will be shown at the starting (primaryStage will show the LoginPage.fxml as the starting scene)

        primaryStage.setTitle("Truth Table Generator");
        primaryStage.show();
    }


    // setRoot method is used to switch scenes in the stage:
    public static void setRoot(String fxmlFile) {
        try {
            System.out.println("primaryStage: " + primaryStage);                  // added for debugging
            File newFile = new File("src/" + fxmlFile);    // load the FXML file from the view package(folder)
            if (newFile == null) {
                System.err.println("FXML file not found: /view/" + fxmlFile);     // added for debugging
                return;
            }
            System.out.println("Loading FXML from: " + newFile);                  // added for debugging
            FXMLLoader loader = new FXMLLoader(newFile.toURI().toURL());

            Scene scene = new Scene(loader.load());                         // load the fxml file (scene/ UI to be shown)
            sceneStack.addLast(scene);
            primaryStage.setScene(scene);                                   // set the newly loaded scene in the stage
            System.out.println("Scene loaded successfully!");            // added for debugging
        } catch (Exception e) {
            System.err.println("Failed to load FXML: " + fxmlFile);       // added for debugging
            e.printStackTrace();
        }
    }



    // go to the previous page:
    public static void goBack() {
        if(sceneStack.size() > 1) {
            sceneStack.remove(sceneStack.size() - 1);
            primaryStage.setScene(sceneStack.get(sceneStack.size() - 1));
        }
    }


    // delete the previous scene:
    public static void deletePreviousSceneFromStack() {
        sceneStack.remove(sceneStack.size() - 2);

    }


    // update the databases before exiting the program:
    @Override
    public void stop() throws Exception {

    }



    public static void setStageTitle (String title) {
        primaryStage.setTitle(title);
    }


}
