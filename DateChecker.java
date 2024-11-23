import java.io.*;
import java.time.LocalDate;

/**
 * The DateChecker class is responsible for managing date-related operations,
 * including reading a date from a CSV file and saving a date to a CSV file.
 * @author Luke Scanlon
 * @version 1
 */
public class DateChecker {

    /**
     * Path to the CSV file that stores the date.
     */
    private String dateCSV = "data//Date.csv";

    /**
     * Reads a date from the CSV file.
     * 
     * The CSV file is expected to contain a single line with the format: `day,month,year`.
     * If the file is not found or the format is invalid, an error message is printed, and `null` is returned.
     * 
     * @return A LocalDate object representing the date from the CSV file, or `null` if an error occurs.
     */
    public LocalDate getDateFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(dateCSV))) {
            String line = reader.readLine();
            if (line != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    int day = Integer.parseInt(data[0]);
                    int month = Integer.parseInt(data[1]);
                    int year = Integer.parseInt(data[2]);
                    return LocalDate.of(year, month, day);
                } else {
                    System.err.println("Invalid CSV format. Expected day, month, and year.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in the CSV file.");
        }
        return null;
    }

    /**
     * Saves a date to the CSV file.
     * 
     * The date is saved in the format: `day,month,year`.
     * If an error occurs while writing to the file, an error message is printed.
     * 
     * @param day The day of the date to save.
     * @param month The month of the date to save.
     * @param year The year of the date to save.
     */
    public void saveDateToCSV(int day, int month, int year) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dateCSV))) {
            writer.write(day + "," + month + "," + year);
        } catch (IOException e) {
            System.err.println("Error saving the date to the CSV file: " + e.getMessage());
        }
    }
}