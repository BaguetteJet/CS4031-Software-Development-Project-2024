/**
 * Class to operate PaySlips.csv file data, extends CSV class.
 * All pay claims are saved here.
 * 
 * @author Igor Kochanski 100%
 * @version 1
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
}
