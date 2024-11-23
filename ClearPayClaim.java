import java.io.*;

/**
 * The ClearPayClaim class is responsible for clearing pay claim records
 * while retaining the header row in the "PayClaims.csv" file.
 * This ensures the structure of the file is maintained for future data entries.
 * @author Luke Scanlon
 * @version 1
 */

public class ClearPayClaim {

    private String payClaimsCSV = "data//PayClaims.csv";

    /**
     * Clears all pay claims from the "PayClaims.csv" file while retaining the header row.
     * 
     * The method performs the following steps:
     * 1. Reads the first line of the file (header row).
     * 2. Overwrites the file with only the header row.
     * 3. Handles cases where the file is empty or missing a header row by displaying a message.
     * 
     * If any errors occur during file operations, an exception stack trace is printed.
     */
     public void clearPayClaims() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(payClaimsCSV));
            String header = reader.readLine();
            reader.close();
            
            if (header != null) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(payClaimsCSV, false));
                writer.write(header);
                writer.newLine();
                writer.close();
            } else {}
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
}
