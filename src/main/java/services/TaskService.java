package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ArrayTaskList;
import model.Task;

import java.util.ArrayList;

public class TaskService {
    public static ObservableList<Task> getObservableList(ArrayTaskList arrayTaskList){
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : arrayTaskList){
            tasks.add(task);
        }
        return FXCollections.observableArrayList(tasks);
    }
}
