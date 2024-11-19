public class CSVPaySlips extends CSV{

    /** 
     * Constructor.
     * @param pathOfCSV path of PaySlips.CSV
     */
    public CSVPaySlips (String pathOfCSV) {
        super(pathOfCSV);
        readCSV();
    }

    public void addPaySlip(String UserID, String Position, String GrossPay, String PAYE, String PRSI, String USC, String NetPay, String Date) {
        String[] newRow = {UserID,Position,GrossPay,PAYE,PRSI,USC,NetPay,Date};
        addRow(newRow);
    }

}
