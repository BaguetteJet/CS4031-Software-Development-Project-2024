import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

/**
 * The UpdateSystem class manages and triggers scheduled updates
 * based on specific calendar dates and conditions.
 * These updates include payroll generation, clearing claims, and scale updates.
 * @author Luke Scanlon
 * @version 2
 */

public class UpdateSystem{

    private String userID;

    CSVPayScale payScale = new CSVPayScale("data\\PayScale.csv");
    CSVPayClaims payClaim = new CSVPayClaims("data\\PayClaims.csv");
    CSVPaySlips paySlip = new CSVPaySlips("data\\PaySlips.csv");
    CSVEmployees employees = new CSVEmployees("data\\Employees,csv");
    CSVPayRoll payRoll = new CSVPayRoll("data\\PayRoll.csv");
    CSVSystemChecker systemChecker = new CSVSystemChecker("data\\SystemChecks.csv");
    Deductions taxes = new Deductions(userID);
    
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
        
        systemChecker.updateDateAndRunCounter();
        systemChecker.incrementRunCounter();
        int runs = systemChecker.getRunCounter();
        
        if (checkSecondFriday() == true) {
            if (runs <= 1) {
                for (String[] employee : employees.getData()) {
                    String userID = employee[0];
                    addEmployeeToSystem(userID);
                }
                payClaim.clearData();
            }
        }

        if (checkTwentyFifth() == true) {
            ArrayList<String[]> data = payRoll.getData();
            ArrayList<String[]> employeesData = employees.getData();
        
            // Loop through all employees
            for (String[] employee : employeesData) {
                String userID = employee[0]; // Assuming userID is in the first column of Employees.csv
        
                // Check if the userID is already in the payroll (this is done once per employee)
                if (isUserInPayRoll(userID)) {
                    addEmployeeToPaySlip(userID);
                }
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

    public void addEmployeeToSystem(String userID) {

        // Full-time employee: Add directly to PayRoll
        if (employees.isFullTime(userID)) {
            payRoll.addToPayRoll(userID);
        }
        // Part-time employee: Add only if they have a PayClaim
        else if (hasPayClaim(userID)) {
                payRoll.addToPayRoll(userID);
            
        }
    }

    /**
     * Check if a part-time employee has any pay claims.
     * 
     * @param userID the user ID of the part-time employee
     * @return true if the employee has a pay claim, false otherwise
     */
    private boolean hasPayClaim(String userID) {
        return !payClaim.getClaimsForUser(userID).isEmpty();
    }

    private void addEmployeeToPaySlip(String userID) {

        if (payRoll.getRowOf(userID) != null) {
            String[] employeeRow = employees.getRowOf(userID);
            String roleID = employeeRow[1];
            String[] payScaleRow = payScale.getRowOf(roleID);
            paySlip.addPaySlip(userID, payScaleRow[1], String.valueOf(taxes.getGrossMonthly()), String.valueOf(taxes.getPAYE()), String.valueOf(taxes.getPRSI_EE()), String.valueOf(taxes.getUSC()), String.valueOf(taxes.getNetPay()), today.toString());
        }
    }

    public boolean isUserInPayRoll(String userID) {
        // Check if the userID exists in the PayRoll CSV
        String[] payRollRow = payRoll.getRowOf(userID);

        // If row is not null, the userID exists in PayRoll.csv
        return payRollRow != null;
    }
}
