import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * The GeneratePaySlip_PayRoll class is responsible for generating payslips and payroll
 * records for employees. It retrieves data from CSV files, calculates relevant deductions,
 * and writes the results to appropriate output files.
 * @author Luke Scanlon
 * @version 1
 */
public class GeneratePaySlip_PayRoll {

    /**
     * Path to the Employees CSV file.
     * Path to the PayClaims CSV file.
     * Path to the Payslips CSV file.
     * Path to the PayRoll CSV file
     */
    private String employeesCSV = "data//Employees.csv";
    private String payRollCSV = "data//PayRoll.csv";
    private String paySlipsCSV = "data//Payslips.csv";
    private String payClaimsCSV = "data//PayClaims.csv";

    /**
     * Generates payslips for all employees listed in the "Employees.csv" file
     * who are included in the "PayRoll.csv" file.
     * 
     * @param date The date for which the payslips are generated.
     */
    public void generatePayslips(LocalDate date) {
        Set<Integer> payrollUserIDs = getPayrollUserIDs();

        try (BufferedReader reader = new BufferedReader(new FileReader(employeesCSV))) {
            String line;

            // Skip header line
            if ((line = reader.readLine()) != null && line.startsWith("UserID")) {
                line = reader.readLine();
            }

            while (line != null) {
                String[] data = line.split(",");
                int userID = Integer.parseInt(data[0]);
                String position = data[2];
                int pointScale = Integer.parseInt(data[3]);

                if (payrollUserIDs.contains(userID)) {
                    Deductions deductions = new Deductions(position, pointScale, false, 0);
                    saveToPayslipCSV(date, userID, position, deductions);
                }

                line = reader.readLine();
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading employees.csv: " + e.getMessage());
        }
    }

    /**
     * Retrieves the User IDs of employees who are part of the payroll from "PayRoll.csv".
     * 
     * @return A set of User IDs included in the payroll.
     */
    private Set<Integer> getPayrollUserIDs() {
        Set<Integer> userIDs = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(payRollCSV))) {
            String line;

            // Skip header line
            if ((line = reader.readLine()) != null && line.startsWith("UserID")) {
                line = reader.readLine();
            }

            while (line != null) {
                String[] data = line.split(",");
                userIDs.add(Integer.parseInt(data[0]));
                line = reader.readLine();
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading payroll.csv: " + e.getMessage());
        }
        return userIDs;
    }

    /**
     * Saves a payslip entry to the "Payslips.csv" file.
     * 
     * @param date The date of the payslip.
     * @param userID The user ID of the employee.
     * @param position The position of the employee.
     * @param deductions The Deductions object containing calculated values for the employee.
     */
    private void saveToPayslipCSV(LocalDate date, int userID, String position, Deductions deductions) {
        double grossMonthly = deductions.getGrossMonthly();
        double taxPAYE = deductions.getPAYE();
        double taxPRSI = deductions.getPRSI_EE();
        double taxUSC = deductions.getUSC();
        double netPay = deductions.getNetPay();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(paySlipsCSV, true))) {
            writer.write(userID + "," + position + "," + grossMonthly + "," + taxPAYE + "," + taxPRSI + "," + taxUSC + "," + netPay + "," + date);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving payslip to CSV: " + e.getMessage());
        }
    }

    /**
     * Generates payroll records for all employees listed in "Employees.csv".
     * Full-time and part-time employees are handled differently based on their employment type.
     */
    public void generatePayroll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(employeesCSV))) {
            String line;

            // Skip header line
            if ((line = reader.readLine()) != null && line.startsWith("UserID")) {
                line = reader.readLine();
            }

            while (line != null) {
                String[] data = line.split(",");
                int userID = Integer.parseInt(data[0]);
                String roleID = data[2];
                int roleIDNum = Integer.parseInt(data[2]);
                int pointScale = Integer.parseInt(data[3]);
                String employmentType = data[4];

                if (employmentType.equalsIgnoreCase("FullTime")) {
                    Deductions deductions = new Deductions(roleID, pointScale, false, 0);
                    saveToPayrollCSV(userID, roleIDNum, "FullTime", deductions.getGrossSalary());
                } else if (employmentType.equalsIgnoreCase("PartTime")) {
                    double hoursWorked = getHoursWorked(userID);
                    if (hoursWorked > 0) {
                        Deductions deductions = new Deductions(roleID, pointScale, true, hoursWorked);
                        saveToPayrollCSV(userID, roleIDNum, "PartTime", deductions.getGrossSalary());
                    }
                }

                line = reader.readLine();
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading employees.csv: " + e.getMessage());
        }
    }

    /**
     * Saves a payroll entry to the "PayRoll.csv" file.
     * 
     * @param userID The user ID of the employee.
     * @param roleIDNum The numeric role ID of the employee.
     * @param employmentType The employment type (FullTime/PartTime).
     * @param grossSalary The gross salary of the employee.
     */
    private void saveToPayrollCSV(int userID, int roleIDNum, String employmentType, double grossSalary) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(payRollCSV, true))) {
            writer.write(userID + "," + roleIDNum + "," + employmentType + "," + grossSalary);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving payroll to CSV: " + e.getMessage());
        }
    }

    /**
     * Retrieves the hours worked by an employee from the "PayClaims.csv" file.
     * 
     * @param userID The user ID of the employee.
     * @return The number of hours worked by the employee, or 0.0 if not found.
     */
    private double getHoursWorked(int userID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(payClaimsCSV))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (Integer.parseInt(data[0]) == userID) {
                    return Double.parseDouble(data[1]);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading hours from PayClaims.csv: " + e.getMessage());
        }
        return 0.0;
    }
}