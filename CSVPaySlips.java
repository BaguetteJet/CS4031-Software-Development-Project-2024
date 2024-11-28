/**
 * Class to operate PayScale.csv file data, extends CSV class.
 * All pay slips are saved here.
 * 
 * @author Igor Kochanski 100%
 * @version 1
 */
public class CSVPaySlips extends CSV {

    /**
     * Constructor.
     * 
     * @param pathOfCSV path of PaySlips.CSV
     */
    public CSVPaySlips(String pathOfCSV) {
        super(pathOfCSV);
        readCSV();
    }

    /**
     * Method to add new Pay Slip.
     * 
     * @param userID   user ID
     * @param position current position title
     * @param grossPay gross pay
     * @param PAYE     Pay As You Earn
     * @param PRSI     Pay Related Social Insurance
     * @param USC      Universal Social Charge
     * @param netPay   net pay
     * @param date     date issued
     * @param ppsn    PPSN
     */
    public void addPaySlip(String userID, String position, String grossPay, String PAYE, String PRSI, String USC,
            String netPay, String date, String ppsn) {
        String[] newRow = { userID, position, grossPay, PAYE, PRSI, USC, netPay, date, ppsn};
        addRow(newRow);
    }
}
