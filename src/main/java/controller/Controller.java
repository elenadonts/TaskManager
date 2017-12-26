package controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ArrayTaskList;
import model.Task;
import services.TaskService;
import view.Main;

import java.io.IOException;


public class Controller {
    private static ArrayTaskList savedTasks = Main.getSavedTasksList();

    public static ObservableList<Task> tasksList = TaskService.getObservableList(savedTasks);
    public static Stage editNewStage;

    public static TableView mainTable;

    @FXML
    public  TableView tasks;
    @FXML
    private TableColumn<Task, String> columnTitle;
    @FXML
    private TableColumn<Task, String> columnTime;
    @FXML
    private TableColumn<Task, String> columnRepeated;
    @FXML
    private Label labelCount;


    @FXML
    public void initialize(){
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("formattedDateStart"));
        columnRepeated.setCellValueFactory(new PropertyValueFactory<>("formattedRepeated"));
        updateCountLabel(tasksList);
        tasks.setItems(tasksList);
        mainTable = tasks;


        tasksList.addListener((ListChangeListener.Change<? extends Task> c) -> {
                updateCountLabel(tasksList);
                tasks.setItems(tasksList);

            }
        );

    }
    private void updateCountLabel(ObservableList<Task> list){
        labelCount.setText(list.size()+ " elements");
    }

    @FXML
    public void showTaskDialog(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        NewEditController.setClickedButton((Button) source);

        try {
            editNewStage = new Stage();
            NewEditController.setCurrentStage(editNewStage);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/new-edit-task.fxml"));
            editNewStage.setScene(new Scene(root, 600, 350));
            editNewStage.setResizable(false);
            editNewStage.initOwner(Main.primaryStage);
            editNewStage.initModality(Modality.APPLICATION_MODAL);//??????
            editNewStage.show();
        }
        catch (IOException e){
            e.getMessage();
        }
    }
    @FXML
    public void deleteTask(){
        Task toDelete = (Task)tasks.getSelectionModel().getSelectedItem();
        tasksList.remove(toDelete);
    }
    @FXML
    public void showDetailedInfo(){
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/task-info.fxml"));
            stage.setScene(new Scene(root, 550, 350));
            stage.setResizable(false);
            stage.setTitle("Info");
            stage.initModality(Modality.APPLICATION_MODAL);//??????
            stage.show();
        }
        catch (IOException e){
            e.getMessage();
        }
    }


}
