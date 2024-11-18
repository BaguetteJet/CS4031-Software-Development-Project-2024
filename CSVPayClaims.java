/** 
 * Class to operate Employees.csv file data, extends CSV class
 * @author Igor Kochanski
 * @version 1
 */
public class CSVPayClaims extends CSV {
    
    /** 
     * Constructor.
     * @param pathOfCSV path of Employees.CSV
     */
    public CSVPayClaims (String pathOfCSV) {
        super(pathOfCSV);
        readCSV();
    }

    /**
     * Method to add a pay claim
     * @param UserID
     * @param Hours
     */
    public void addClaim(String UserID, String Hours) {
        String[] newRow = {UserID, Hours};
        addRow(newRow);
    }
}
