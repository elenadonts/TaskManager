package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Task;
import services.TaskIO;
import services.DateService;
import services.TaskService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class NewEditController {

    private static Button clickedButton;

    public static void setClickedButton(Button clickedButton) {
        NewEditController.clickedButton = clickedButton;
    }

    public static void setTableView(TableView tableView) {
        NewEditController.tableView = tableView;
    }

    public static void setCurrentStage(Stage currentStage) {
        NewEditController.currentStage = currentStage;
    }

    private static Stage currentStage;

    private static TableView tableView;

    private static Task currentTask;
    @FXML
    private TextField fieldTitle;
    @FXML
    private DatePicker datePickerStart;
    @FXML
    private TextField txtFieldTimeStart;
    @FXML
    private DatePicker datePickerEnd;
    @FXML
    private TextField txtFieldTimeEnd;
    @FXML
    private TextField fieldInterval;
    @FXML
    private CheckBox checkBoxActive;
    @FXML
    private CheckBox checkBoxRepeated;

    private Controller mainController;


    @FXML
    public void initialize(){
        switch (clickedButton.getId()){
            case  "btnNew" : initNewWindow("New Task");
                break;
            case "btnEdit" : initEditWindow("Edit Task");
                break;
        }

    }
    private void initNewWindow(String title){
        currentStage.setTitle(title);

    }
    private void initEditWindow(String title){
        currentStage.setTitle(title);
        currentTask = (Task)tableView.getSelectionModel().getSelectedItem();
        fieldTitle.setText(currentTask.getTitle());
        datePickerStart.setValue(DateService.getLocalDateValueFromDate(currentTask.getStartTime()));
        txtFieldTimeStart.setText(DateService.getTimeOfTheDayFromDate(currentTask.getStartTime()));/////////////////////////////

        if (currentTask.isRepeated()){
            checkBoxRepeated.setSelected(true);
            hideRepeatedTaskModule(false);
            datePickerEnd.setValue(DateService.getLocalDateValueFromDate(currentTask.getEndTime()));
            fieldInterval.setText(TaskService.getIntervalInHours(currentTask));
            txtFieldTimeEnd.setText(DateService.getTimeOfTheDayFromDate(currentTask.getEndTime()));
        }
        if (currentTask.isActive()){
            checkBoxActive.setSelected(true);

        }
    }
    @FXML
    public void switchRepeatedCheckbox(ActionEvent actionEvent){
        CheckBox source = (CheckBox)actionEvent.getSource();
        if (source.isSelected()){
            hideRepeatedTaskModule(false);
        }
        else if (!source.isSelected()){
            hideRepeatedTaskModule(true);
        }
    }
    private void hideRepeatedTaskModule(boolean toShow){
        datePickerEnd.setDisable(toShow);
        fieldInterval.setDisable(toShow);
        txtFieldTimeEnd.setDisable(toShow);
    }

    @FXML
    public void saveChanges(){
        Task collectedFieldsTask = collectFieldsData();

        if (currentTask == null){//no task was chosen -> add button was pressed
            Controller.tasksList.add(collectedFieldsTask);
            ///////////////////////////////////////////////////////////
        }
        else {
            for (int i = 0; i < Controller.tasksList.size(); i++){
                if (currentTask.equals(Controller.tasksList.get(i))){
                    Controller.tasksList.set(i,collectedFieldsTask);
                }
            }
            currentTask = null;
        }
        TaskIO.rewriteFile(Controller.tasksList);
        Controller.editNewStage.close();
    }
    @FXML
    public void closeDialogWindow(){
        Controller.editNewStage.close();
    }

    private Task collectFieldsData(){
        Task result = null;
        try {
            result = makeTask();
        }
        catch (RuntimeException e){
            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/field-validator.fxml"));
                stage.setScene(new Scene(root, 350, 150));
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            }
            catch (IOException ioe){
                e.getMessage();
            }
        }
        return result;
    }
    private Task makeTask(){
        Task result;
        String newTitle = fieldTitle.getText();
        Date startDateWithNoTime = DateService.getDateValueFromLocalDate(datePickerStart.getValue());//ONLY date!!without time
        Date newStartDate = DateService.getDateMergedWithTime(txtFieldTimeStart.getText(), startDateWithNoTime);
        if (checkBoxRepeated.isSelected()){
            Date endDateWithNoTime = DateService.getDateValueFromLocalDate(datePickerEnd.getValue());
            Date newEndDate = DateService.getDateMergedWithTime(txtFieldTimeEnd.getText(), endDateWithNoTime);
            int newInterval = TaskService.parseFromStringToSeconds(fieldInterval.getText());
            System.out.println(newInterval);
            result = new Task(newTitle, newStartDate,newEndDate, newInterval);
        }
        else {
            result = new Task(newTitle, newStartDate);
        }
        boolean isActive = checkBoxActive.isSelected();
        result.setActive(isActive);
        return result;
    }


}
