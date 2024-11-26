import java.util.ArrayList;

/**
 * Class to operate PaySlips.csv file data, extends CSV class.
 * All pay claims are saved here.
 * 
 * @author Igor Kochanski 90%
 * @author Luke Scanlon 10%
 * @version 2
 */
public class CSVPayClaims extends CSV {

    /**
     * Constructor.
     * 
     * @param pathOfCSV path of PaySlips.CSV
     */
    public CSVPayClaims(String pathOfCSV) {
        super(pathOfCSV);
        readCSV();
    }

    /**
     * Method to add a pay claim.
     * 
     * @param UserID user ID
     * @param Hours  hours worked
     */
    public void addClaim(String UserID, String Hours) {
        String[] newRow = { UserID, Hours };
        addRow(newRow);
    }

    /**
     * Method to check if a Part-Time employee has made a payclaim.
     * 
     * @param userID
     * @return userID if they are in the PayClaims.csv file 
     */
    public ArrayList<String[]> getClaimsForUser(String userID) {
        ArrayList<String[]> userClaims = new ArrayList<>();
        for (String[] row : getData()) {
            if (row[0].equals(userID)) {
                userClaims.add(row);
            }
        }
        return userClaims;
    }
}
