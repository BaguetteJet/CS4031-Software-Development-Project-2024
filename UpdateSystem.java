import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

/**
 * The UpdateSystem class manages and triggers scheduled updates
 * based on specific calendar dates and conditions.
 * These updates include payroll generation, clearing claims, and scale updates.
 * @author Luke Scanlon
 * @version 1
 */

public class UpdateSystem {
    /**
     * Initialising the current date based on the systems date
     */
    private LocalDate today = LocalDate.now();

    /**
     * Constructs an instance of UpdateSystem
     */
    public UpdateSystem() {
    }

    /**
     * Runs the system updates based on specific conditions and dates.
     * The updates include:
     * - Generating payslips on the 25th of the month.
     * - Generating payroll on the second Friday of the month.
     * - Clearing pay claims on the second Saturday of the month.
     * - Updating employee scale points on the 1st of October.
     * 
     * This method also tracks execution runs using external CSV files to
     * ensure updates do not run more than once per day.
     */
    public void updateAll() {

        DateChecker dateChecker = new DateChecker();
        runCount runCount = new runCount();
        GeneratePaySlip_PayRoll payee = new GeneratePaySlip_PayRoll();
        PointScaleUpdater scaleUpdater = new PointScaleUpdater();
        ClearPayClaim payClaim = new ClearPayClaim();
        LocalDate savedDate = dateChecker.getDateFromCSV();
        int runs = runCount.getRunCountFromCSV();

        if(savedDate == today) {
            if (runs <= 1) {
                runs++;
                runCount.saveRunCountToCSV(runs);
            }
        } else {
            runCount.resetRunCount();
            dateChecker.saveDateToCSV(today.getDayOfMonth(), today.getMonthValue(), today.getYear());
        }

        if(runs <= 1) {
            if (checkTwentyFifth() == true) {
                payee.generatePayslips(today);
            }

            if (checkSecondFriday() == true) {
                payee.generatePayroll();
                payClaim.clearPayClaims();
            }

            if (checkFirstOctober() == true) {
                scaleUpdater.incrementEmployeeScalePoint();
            }
        }
    }

    /**
     * Checks if the current date is the 25th of the month.
     * 
     * @return true if today is the 25th, false otherwise.
     */
    private boolean checkTwentyFifth() {
        if (today.getDayOfMonth() == 25) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the current date is the second Friday of the month.
     * 
     * @return true if today is the second Friday, false otherwise.
     */
    private boolean checkSecondFriday() {
        if (today.getDayOfWeek() != DayOfWeek.FRIDAY) {
            return false;
        }
        LocalDate dayOfMonth = today.withDayOfMonth(1);
        while (dayOfMonth.getDayOfWeek() != DayOfWeek.FRIDAY) {
            dayOfMonth.plusDays(1);
        }
        dayOfMonth.plusDays(7);
        if (today.equals(dayOfMonth)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the current date is the 1st of October.
     * 
     * @return true if today is October 1st, false otherwise.
     */
    private boolean checkFirstOctober() {
        if (today.getMonth() != Month.OCTOBER) {
            return false;
        } else {
            if (today.getDayOfMonth() == 1) {
                return true;
            } else {
                return false;
            }
        }
    }
}