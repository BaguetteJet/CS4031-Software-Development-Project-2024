/** 
 * Class to operate Employees.csv file data, extends CSV class
 * @author Igor Kochanski
 * @version 1
 */
public class CSVEmployees extends CSV {

    /** 
     * Constructor.
     * @param pathOfCSV path of Employees.CSV
     */
    public CSVEmployees (String pathOfCSV) {
        super(pathOfCSV);
        readCSV();
    }
    
    /**
     * Method to add new Employee.
     * @param RoleID ID of role must match existing
     * @param UserID ID of user must match existing
     * @param ScalePoint Scale point of career must match
     * @param Type type of employment: Part-Time, Full-Time
     * @param StartDate start date: yyyy-mm-dd
     * @param FullName full name
     * @param PPS PPS number
     */
    public void addEmployee(String UserID, String RoleID, String ScalePoint, String Type, String StartDate, String FullName, String PPSN) {
        String[] addRow = {UserID, RoleID, ScalePoint, Type, StartDate, FullName, PPSN};
        for (String x : addRow) {
            if (x.equals(""))
                x = "null";
        }
        addRow(addRow);
    }

}
