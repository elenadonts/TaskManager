package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ArrayTaskList;
import model.TaskIO;

import java.io.File;


public class Main extends Application {
    public static Stage primaryStage;
    private static final int defaultWidth = 820;
    private static final int defaultHeight = 520;

    private static ArrayTaskList savedTasksList = new ArrayTaskList();
    private static File savedTasksFile = new File("src/main/resources/data/tasks.txt");

    public static ArrayTaskList getSavedTasksList() {
        return savedTasksList;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (savedTasksFile.length() != 0) {
            TaskIO.readBinary(savedTasksList, savedTasksFile);
        }

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        primaryStage.setTitle("Task Manager");
        primaryStage.setScene(new Scene(root, defaultWidth, defaultHeight));
        primaryStage.setMinWidth(defaultWidth);
        primaryStage.setMinHeight(defaultHeight);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
