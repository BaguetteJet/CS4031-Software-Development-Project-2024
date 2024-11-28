import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

/**
 * The UpdateSystem class manages and triggers scheduled updates
 * based on specific calendar dates and conditions.
 * These updates include payroll generation, clearing claims, and scale updates.
 * 
 * @author Luke Scanlon 90%
 * @author Igor Kochanski 10%
 * @version 4
 */

public class UpdateSystem {
    // CSV file managers for handling system data
    CSVPayScale payScale = new CSVPayScale("data\\PayScale.csv");
    CSVPayClaims payClaim = new CSVPayClaims("data\\PayClaims.csv");
    CSVPaySlips paySlip = new CSVPaySlips("data\\PaySlips.csv");
    CSVEmployees employees = new CSVEmployees("data\\Employees.csv");
    CSVPayRoll payRoll = new CSVPayRoll("data\\PayRoll.csv");
    CSVPromotions promotions = new CSVPromotions("data\\Promotions.csv");
    CSVSystemChecker systemChecker = new CSVSystemChecker("data\\SystemChecks.csv");
    Deductions taxes = new Deductions();

    // Current date
    private LocalDate today;

    /**
     * Constructs an UpdateSystem instance with a specified date.
     * If the date format is invalid, the current system date is used instead.
     * 
     * @param dateEntered The date provided by the user in "yyyy-MM-dd" format.
     */
    public UpdateSystem(String dateEntered) {
        if (!dateEntered.matches("\\d{4}-\\d{2}-\\d{2}")) {
            this.today = LocalDate.now();
        } else {
            this.today = LocalDate.parse(dateEntered);
        }
    }

    /**
     * Triggers all scheduled updates based on the current date and run count
     * assuming the program is run everyday.
     * 
     * Operations include:
     * - Clearing pay claims on the second Friday of each month.
     * - Generating payslips on the 25th of each month.
     * - Promoting employees on October 1st.
     */
    public void updateAll() {
        systemChecker.updateDateAndRunCounter();
        systemChecker.incrementRunCounter();
        int runs = systemChecker.getRunCounter();

        if (checkSecondFriday() && runs <= 1) {
            for (String[] employee : employees.getData()) {
                String userID = employee[0];
                addEmployeeToSystem(userID);
            }
            payClaim.clearData();
        }

        if (checkTwentyFifth() && runs <= 1) {
            ArrayList<String[]> employeesData = employees.getData();

            for (String[] employee : employeesData) {
                String userID = employee[0];
                if (isUserInPayRoll(userID)) {
                    addEmployeeToPaySlip(userID);
                }
            }
        }

        if (checkFirstOctober() && runs <= 1) {
            promotions.findPromotionsAndUpdateScale();
        }
    }

    /**
     * Checks if the current date is the 25th of the month.
     * 
     * @return true if today is the 25th, false otherwise.
     */
    private boolean checkTwentyFifth() {
        return today.getDayOfMonth() == 25;
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
        LocalDate firstFriday = today.withDayOfMonth(1);
        while (firstFriday.getDayOfWeek() != DayOfWeek.FRIDAY) {
            firstFriday = firstFriday.plusDays(1);
        }
        LocalDate secondFriday = firstFriday.plusDays(7);
        return today.equals(secondFriday);
    }

    /**
     * Checks if the current date is October 1st.
     * 
     * @return true if today is October 1st, false otherwise.
     */
    private boolean checkFirstOctober() {
        return today.getMonth() == Month.OCTOBER && today.getDayOfMonth() == 1;
    }

    /**
     * Adds an employee to the payroll system.
     * 
     * - Full-time employees are always added.
     * - Part-time employees are added only if they have pay claims.
     * 
     * @param userID The user ID of the employee to add.
     */
    public void addEmployeeToSystem(String userID) {
        if (employees.isFullTime(userID)) {
            payRoll.addToPayRoll(userID);
        } else if (hasPayClaim(userID)) {
            payRoll.addToPayRoll(userID);
        }
    }

    /**
     * Checks if a part-time employee has any pay claims.
     * 
     * @param userID The user ID of the employee to check.
     * @return true if the employee has a pay claim, false otherwise.
     */
    private boolean hasPayClaim(String userID) {
        return !payClaim.getClaimsForUser(userID).isEmpty();
    }

    /**
     * Adds an employee's payslip to the system based on their payroll data.
     * 
     * @param userID The user ID of the employee.
     */
    private void addEmployeeToPaySlip(String userID) {
        if (payRoll.getRowOf(userID) != null) {
            taxes.loadSalary(userID);
            String[] employeeRow = employees.getRowOf(userID);
            String roleID = employeeRow[1];
            String[] payScaleRow = payScale.getRowOf(roleID);
            paySlip.addPaySlip(userID, payScaleRow[1], String.valueOf(taxes.getGrossMonthly()),
                    String.valueOf(taxes.getPAYE()), String.valueOf(taxes.getPRSI()), String.valueOf(taxes.getUSC()),
                    String.valueOf(taxes.getNetPay()), today.toString());
        }
    }

    /**
     * Checks if an employee is in the payroll system.
     * 
     * @param userID The user ID of the employee to check.
     * @return true if the employee is in the payroll system, false otherwise.
     */
    public boolean isUserInPayRoll(String userID) {
        return payRoll.getRowOf(userID) != null;
    }
}