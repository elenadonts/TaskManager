package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Task;
import services.DatePickerService;

import java.text.ParseException;


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
    private DatePicker datePickerEnd;
    @FXML
    private TextField fieldInterval;
    @FXML
    private CheckBox checkBoxActive;
    @FXML
    private CheckBox checkBoxRepeated;
    @FXML
    private VBox rootVBox;

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
        datePickerStart.setValue(DatePickerService.getLocalDateFromDate(currentTask.getStartTime()));
        if (currentTask.isRepeated()){
            checkBoxRepeated.setSelected(true);
            hideRepeatedTaskModule(false);
            datePickerEnd.setValue(DatePickerService.getLocalDateFromDate(currentTask.getEndTime()));
            fieldInterval.setText((currentTask.getFormattedRepeated()));
        }
        if (currentTask.isActive()){
            checkBoxActive.isSelected();
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
    }
    @FXML
    public void saveChanges(){
//        Task collectedFieldsTask = collectFieldsData();
//        if (currentTask == null){
//
//        }
//        else {
//
//        }
    }
    @FXML
    public void closeDialogWindow(){

    }

//    private Task collectFieldsData(){
//
//        if (checkBoxRepeated.isSelected()){
//
//        }
//
//    }


}
