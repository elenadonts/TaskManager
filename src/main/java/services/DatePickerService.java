package services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DatePickerService {

    public static final LocalDate getLocalDateFromDate(Date input){
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return date;
    }
}
