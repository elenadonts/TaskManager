package controller;

import javafx.application.Platform;
import model.Task;
import org.controlsfx.control.Notifications;


import java.util.Date;

public class Notificator extends Thread {

    private static final int millisecondsInSec = 1000;
    private static final int secondsInMin = 60;


    @Override
    public void run() {
        Date currentDate = new Date();
        while (true) {

            for (Task t : Controller.tasksList) {
                if (t.isActive()) {
                    if (t.isRepeated() && t.getEndTime().after(currentDate)){

                        Date next = t.nextTimeAfter(currentDate);
                        long currentMinute = getTimeInMinutes(currentDate);
                        long taskMinute = getTimeInMinutes(next);
                        if (currentMinute == taskMinute){
                            showNotification(t);
                        }
                    }
                    else {
                        if (!t.isRepeated()){
                            if (getTimeInMinutes(currentDate) == getTimeInMinutes(t.getTime())){
                                showNotification(t);
                            }
                        }

                    }
                }

            }

            try {
                Thread.sleep(millisecondsInSec*secondsInMin);

            } catch (InterruptedException e) {
                e.getMessage();
            }
            currentDate = new Date();
        }
    }
    public static void showNotification(Task task){
        Platform.runLater(() -> {
            Notifications.create().title("Task reminder").text("It's time for " + task.getTitle()).showInformation();
        });
    }
    private static long getTimeInMinutes(Date date){
        return date.getTime()/millisecondsInSec/secondsInMin;
    }
}
