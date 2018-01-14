package view;

import controller.Notificator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ArrayTaskList;
import services.TaskIO;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;


public class Main extends Application {
    public static Stage primaryStage;
    private static final int defaultWidth = 820;
    private static final int defaultHeight = 520;

    private static final Logger log = Logger.getLogger(Main.class.getName());

    private static ArrayTaskList savedTasksList = new ArrayTaskList();

    private static ClassLoader classLoader = Main.class.getClassLoader();
    public static File savedTasksFile = new File(classLoader.getResource("data/tasks.txt").getFile());

    public static ArrayTaskList getSavedTasksList() {
        return savedTasksList;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("saved data reading");
        if (savedTasksFile.length() != 0) {
            TaskIO.readBinary(savedTasksList, savedTasksFile);
        }
        try {
            log.info("application start");
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            primaryStage.setTitle("Task Manager");
            primaryStage.setScene(new Scene(root, defaultWidth, defaultHeight));
            primaryStage.setMinWidth(defaultWidth);
            primaryStage.setMinHeight(defaultHeight);
            primaryStage.show();
        }
        catch (IOException e){
            log.error("error reading main.fxml");
        }
        primaryStage.setOnCloseRequest(we -> {
                System.exit(0);
            });
        new Notificator().start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
