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
    public static String getIntervalInHours(Task task){
        int seconds = task.getRepeatInterval();
        int minutes = seconds/60;
        int hours = minutes/60;
        minutes = minutes%60;
        return formTimeUnit(hours) + ":" + formTimeUnit(minutes);//hh:MM
    }
    public static String formTimeUnit(int timeUnit){
        StringBuilder sb = new StringBuilder();
        if (timeUnit < 10) sb.append("0");
        if (timeUnit == 0) sb.append("0");
        else {
            sb.append(timeUnit);
        }
        return sb.toString();
    }
    public static int parseFromStringToSeconds(String stringTime){//hh:MM
        String[] units = stringTime.split(":");
        int hours = Integer.parseInt(units[0]);
        int minutes = Integer.parseInt(units[1]);
        int result = (hours*60+minutes)*60;
        return result;
    }
}
