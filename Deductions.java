import java.io.*;

/**
 * The Deductions class calculates gross salary, various deductions, and net pay for an employee.
 * It supports both full-time and part-time employees, with salary data read from a CSV file.
 * @author Luke Scanlon
 * @version 3
 */
public class Deductions {

    /**
     * Path to the PayScale CSV file.
     * The gross salary of the employee, the salary comes from the PayScale.csv from the roleID and scale point the employee is on.
     */
    private String payScaleCSV = "data//PayScale.csv";
    private double grossSalary;

    /**
     * Constructs a Deductions object and calculates the gross salary based on the provided parameters.
     * 
     * @param roleID The role ID of the employee.
     * @param pointScale The point scale for the employee's salary.
     * @param isPartTime Indicates whether the employee is part-time.
     * @param hoursWorked The number of hours worked by the employee (only applicable for part-time employees).
     */
    public Deductions(String roleID, int pointScale, boolean isPartTime, double hoursWorked) {
        if (isPartTime) {
            this.grossSalary = calculateHourlyRate(roleID, pointScale) * hoursWorked;
        } else {
            this.grossSalary = getGrossIncomeFromCSV(roleID, pointScale);
        }
    }

    /**
     * Calculates the gross monthly salary.
     * 
     * @return The gross monthly salary.
     */
    public double getGrossMonthly() {
        return grossSalary /12;
    }

    /**
     * Calculates the gross weekly salary.
     * 
     * @return The gross weekly salary.
     */
    public double getGrossWeekly() {
        return grossSalary /52;
    }

    /**
     * Calculates the PAYE (Pay As You Earn) tax based on gross monthly earnings.
     * 
     * @return The PAYE tax amount.
     */
    public double getPAYE() {
        double monthlyEarnings = getGrossMonthly();
        if (monthlyEarnings <= 3500) {
            return monthlyEarnings * 0.2;
        } else {
            double highRateSalary = monthlyEarnings - 3500;
            return (highRateSalary * 0.4) + (monthlyEarnings * 0.2);
        }
    }

    /**
     * Calculates the employee's PRSI (Pay Related Social Insurance).
     * 
     * @return The PRSI amount based on gross weekly earnings.
     */
    public double getPRSI_EE() {
        double weeklyEarnings = getGrossWeekly();
        int creditPRSI = 12;
        if (weeklyEarnings < 352) {
            return 0;
        } else if (weeklyEarnings == 352.01) {
            return (weeklyEarnings * 0.041) - creditPRSI;
        } else if (weeklyEarnings <= 424) {
            double afterCutOff = weeklyEarnings -352.01;
            double sixthEarnings = afterCutOff / 6;
            double taxCredit = 12 - sixthEarnings;
            return (((weeklyEarnings * 0.041) - taxCredit) * 52) / 12;
        } else {
            return ((weeklyEarnings * 0.041) * 52) / 12;
        }
    }
    
    /**
     * Calculates the USC (Universal Social Charge).
     * 
     * @return The USC amount based on the gross salary.
     */
    public double getUSC() {
        if (grossSalary <= 13000) {
            return 0;
        } else {
            return (grossSalary * 0.04) /12;
        }
    }
    
     /**
     * Retrieves the gross salary of the employee.
     * 
     * @return The gross salary.
     */
    public double getGrossSalary() {
        
            return grossSalary;
        
    }

    /**
     * Calculates the total deductions (PAYE, PRSI, and USC).
     * 
     * @return The total deductions.
     */
    public double allDeductions() {
        return getPAYE() + getPRSI_EE() + getUSC();
    }

    /**
     * Calculates the net pay after all deductions.
     * 
     * @return The net monthly pay.
     */
    public double getNetPay() {
        return getGrossMonthly() - allDeductions();
    }

    /**
     * Retrieves the gross income for the given role ID and point scale from a CSV file.
     * 
     * @param roleID The role ID of the employee.
     * @param pointScale The point scale for the employee's salary.
     * @throws IllegalArgumentException if employees scale point is not on the pay scale.
     * @return The gross income for the given role and point scale, or 0.0 if not found.
     */
    private double getGrossIncomeFromCSV(String roleID, int pointScale) {
        try (BufferedReader reader = new BufferedReader(new FileReader(payScaleCSV))) {
            String[] pointScaleRow = reader.readLine().split(",");
    
            int pointScaleIndex = -1;
            for (int i = 1; i < pointScaleRow.length; i++) {
                if (Integer.parseInt(pointScaleRow[i]) == pointScale) {
                    pointScaleIndex = i;
                    break;
                }
            }
            if (pointScaleIndex == -1) {
                throw new IllegalArgumentException("PointScale " + pointScale + " not found in payScale.csv");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                if (rowData[0].equalsIgnoreCase(roleID)) {
                    return Double.parseDouble(rowData[pointScaleIndex]);
                }
            }

            } catch (IOException | NumberFormatException e) {
                System.err.println("Error reading gross income from payScale.csv: " + e.getMessage());
            } 
            return 0.0;
        
    }

    /**
     * Calculates the hourly rate for the given role ID and point scale.
     * 
     * @param roleID The role ID of the employee.
     * @param pointScale The point scale for the employee's salary.
     * @return The calculated hourly rate.
     */
    private double calculateHourlyRate(String roleID, int pointScale) {
        double baseSalary = getGrossIncomeFromCSV(roleID, pointScale) / 12;
        return baseSalary / 160;
    }
}