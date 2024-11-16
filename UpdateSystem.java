import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

public class UpdateSystem {
    private LocalDate today = LocalDate.now(); // Create a date object

    public UpdateSystem() {}

    public void updateAll() {
        // run update on all functions
    }
    
    private boolean checkTwentyFifth() {
        if (today.getDayOfMonth() == 25) {
            return true;
        }
        return false;
    }

    private boolean checkSecondFriday() {
        // check if today is Friday
        if (today.getDayOfWeek() != DayOfWeek.FRIDAY) {
            return false;
        }
        // find 1st Friday of month
        LocalDate dayOfMonth = today.withDayOfMonth(1);
        while (dayOfMonth.getDayOfWeek() != DayOfWeek.FRIDAY) {
            dayOfMonth.plusDays(1);
        }
        // add 7 days
        dayOfMonth.plusDays(7);
        // check if today is 2nd Friday
        if (today.equals(dayOfMonth)) {
            return true;
        }
        return false;
    }

    private boolean checkOctoberAdvance() {
        if (today.getMonth() != Month.OCTOBER) {
            return false;
        }
        // if update was last year
        
        return true;
    }

}
