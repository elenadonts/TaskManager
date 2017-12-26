package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Task;


public class TaskInfoController {

    @FXML
    private Label labelTitle;
    @FXML
    private Label labelStart;
    @FXML
    private Label labelEnd;
    @FXML
    private Label labelInterval;
    @FXML
    private Label labelIsActive;

    @FXML
    public void initialize(){
        Task currentTask = (Task)Controller.mainTable.getSelectionModel().getSelectedItem();
        labelTitle.setText("Title: " + currentTask.getTitle());
        labelStart.setText("Start time: " + currentTask.getFormattedDateStart());
        labelEnd.setText("End time: " + currentTask.getFormattedDateEnd());
        labelInterval.setText("Interval: " + currentTask.getFormattedRepeated());
        labelIsActive.setText("Is active: " + (currentTask.isActive() ? "Yes" : "No"));
    }
    @FXML
    public void closeWindow(){

    }

}
