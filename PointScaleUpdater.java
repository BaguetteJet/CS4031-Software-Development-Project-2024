import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The PointScaleUpdater class is responsible for incrementing the scale points
 * of employees in the "Employees.csv" file if they have not yet reached the maximum
 * scale point for their role as defined in the "PayScale.csv" file.
 * @author Luke Scanlon
 * @version 1
 */
public class PointScaleUpdater {
    /**
     * Path to the Employees CSV file.
     * Path to the PayScale CSV file.
     */
    private String employeesCSV = "data//Employees.csv";
    private String payScaleCSV = "data//PayScale.csv";

    /**
     * Increments the scale point for employees who have not yet reached the maximum scale point.
     * The employee data is updated and written back to the "Employees.csv" file.
     * 
     * The method performs the following steps:
     * 1. Reads the employee data from the CSV file.
     * 2. Checks each employee's current scale point and compares it to the maximum scale point for their role.
     * 3. Increments the scale point if it is below the maximum.
     * 4. Writes the updated data back to the "Employees.csv" file if any changes are made.
     */
    public void incrementEmployeeScalePoint() {
        List<String[]> employees = readCSV(employeesCSV);
        boolean updated = false;

        for (String[] employee : employees) {
            if (employee[0].equalsIgnoreCase("UserID")) {
                String roleID = employee[1];
                int currentScalePoint = Integer.parseInt(employee[2]);
                int maxScalePoint = getMaxScalePoint(roleID);

                if (currentScalePoint < maxScalePoint) {
                    employee[2] = String.valueOf(currentScalePoint + 1);
                    updated = true;
                }
                break;
            }
        }

        if (updated) {
            writeCSV(employeesCSV, employees);
        }
    }

    /**
     * Retrieves the maximum scale point for a given role from the "PayScale.csv" file.
     * 
     * @param roleID The role ID of the employee.
     * @return The maximum scale point for the specified role, or 0 if the role is not found.
     */
    private int getMaxScalePoint(String roleID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(payScaleCSV))) {
            String[] headers = reader.readLine().split(",");
            String line;

            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                if (row[0].equalsIgnoreCase(roleID)) {
                    return headers.length - 2;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading payScale.csv: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Reads data from a CSV file and returns it as a list of string arrays.
     * 
     * @param fileName The path to the CSV file.
     * @return A list of rows, where each row is represented as a string array.
     */
    private List<String[]> readCSV(String fileName) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            System.err.println("Error reading " + fileName + ": " + e.getMessage());
        }
        return data;
    }

    /**
     * Writes data to a CSV file.
     * 
     * @param fileName The path to the CSV file.
     * @param data The data to be written, where each row is represented as a string array.
     */
    private static void writeCSV(String fileName, List<String[]> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String[] row : data) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing " + fileName + ": " + e.getMessage());
        }
    }
}