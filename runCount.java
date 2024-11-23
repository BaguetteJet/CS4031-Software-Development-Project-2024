import java.io.*;

/**
 * The runCount class manages the run count of the application.
 * It provides functionality to read, save, and reset the run count in a CSV file.
 * @author Luke Scanlon
 * @version 1
 */
public class runCount {

    /**
     * Path to the RunCount CSV file.
     */
    private String runCountCSV = "data//RunCount.csv";

    /**
     * Reads the run count from the "RunCount.csv" file.
     * 
     * The CSV file is expected to contain a single line with a single integer value.
     * If the file is not found or the format is invalid, an error message is printed,
     * and the run count defaults to 0.
     * 
     * @return The current run count from the CSV file, or 0 if an error occurs.
     */
    public int getRunCountFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(runCountCSV))) {
            String line = reader.readLine();
            if (line != null) {
                String[] data = line.split(",");
                return Integer.parseInt(data[0]);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("CSV file not found or invalid. Starting from 0.");
        }
        return 0;
    }

    /**
     * Saves the run count to the "RunCount.csv" file.
     * 
     * The run count is saved as a single integer value in the file.
     * If an error occurs while writing to the file, an error message is printed.
     * 
     * @param runCount The run count value to save.
     */
    public void saveRunCountToCSV(int runCount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(runCountCSV))) {
            writer.write(String.valueOf(runCount));
        } catch (IOException e) {
            System.err.println("Error saving run count to CSV.");
        }
    }

    /**
     * Resets the run count to 0 in the "RunCount.csv" file.
     * This method internally calls {@link #saveRunCountToCSV(int)} with a value of 0.
     */
    public void resetRunCount() {
        saveRunCountToCSV(0);
    }
}