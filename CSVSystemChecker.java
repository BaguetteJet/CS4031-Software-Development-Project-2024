import java.time.LocalDate;


public class CSVSystemChecker extends CSV {

    /**
     * Constructor to initialize SystemCheckCSV.
     * 
     * @param pathOfCSV path of the SystemCheck.csv file
     */
    public CSVSystemChecker(String pathOfCSV) {
        super(pathOfCSV);
        readCSV(); // Load the current CSV data
    }

    public void updateDateAndRunCounter() {
        try {
            String today = LocalDate.now().toString(); // Get today's date as "yyyy-MM-dd"

            String[] currentRow = dataArray.get(1); // First data row
            String storedDate = currentRow[0]; // Retrieve stored date

            if (!storedDate.equals(today)) {
                // Reset run counter if the date has changed
                currentRow[0] = today;
                currentRow[1] = "0";
                updateRow(storedDate, 0, currentRow);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Increments the run counter, resetting it if the date has changed.
     */
    public void incrementRunCounter() {
        try {
            String today = LocalDate.now().toString(); // Get today's date as "yyyy-MM-dd"

            
            String[] currentRow = dataArray.get(1); // First data row
            String storedDate = currentRow[0]; // Retrieve stored date

            if (!storedDate.equals(today)) {
                // Reset to today's date and initialize run counter to 1
                currentRow[0] = today;
                currentRow[1] = "1";
            } else {
                // Increment run counter
                int currentCounter = Integer.parseInt(currentRow[1]); // Parse the current counter
                currentRow[1] = String.valueOf(currentCounter + 1);
            }
            updateRow(storedDate, 0, currentRow);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getRunCounter() {
        readCSV(); // Ensure the latest data is loaded
        if (!dataArray.isEmpty() && dataArray.size() > 1) {
            try {
                // Retrieve the run counter from the second row, second column
                return Integer.parseInt(dataArray.get(1)[1]);
            } catch (NumberFormatException e) {
                System.err.println("Run counter is not a valid integer: " + e.getMessage());
            }
        }
        return -1; // Return -1 if there is an error or no data
    }
}