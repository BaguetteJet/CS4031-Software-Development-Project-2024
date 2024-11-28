/**
 * Class to operate SystemCheker.csv file data, extends CSV class.
 * todays date and the daily run counter is saved here.
 * @author Luke Scanlon
 * @version 1
 */
public class CSVSystemChecker extends CSV {
    private String today; //today's date as "yyyy-MM-dd" as a string.
    /**
     * Constructor to initialize SystemCheckCSV.
     * 
     * @param pathOfCSV path of the SystemCheck.csv file.
     */
    public CSVSystemChecker(String pathOfCSV, String date) {
        super(pathOfCSV);
        this.today = date;
        readCSV();
    }

    public void updateDateAndRunCounter() {
        try {
            // Gets data from csv.
            String[] currentRow = dataArray.get(1);
            // Gets the current dat saved in csv.
            String storedDate = currentRow[0]; // Retrieve stored date

            if (!storedDate.equals(today)) {
                // Reset run counter if the date has changed.
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

            // Gets data from csv.
            String[] currentRow = dataArray.get(1);
            // Gets the current date saved in the csv.
            String storedDate = currentRow[0];

                // Increment run counter.
                int currentCounter = Integer.parseInt(currentRow[1]);
                currentRow[1] = String.valueOf(currentCounter + 1);
            updateRow(storedDate, 0, currentRow);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getRunCounter() {
        readCSV(); 
        if (!dataArray.isEmpty() && dataArray.size() > 1) {
            try {
                // Gets the run counter from csv.
                return Integer.parseInt(dataArray.get(1)[1]);
            } catch (NumberFormatException e) {
                System.err.println("Run counter is not a valid integer: " + e.getMessage());
            }
        }
        return -1; // Return -1 if there is an error or no data
    }
}