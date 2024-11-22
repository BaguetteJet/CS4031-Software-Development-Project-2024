/**
 * Class to operate Employees.csv file data, extends CSV class.
 * Employee details are saved here.
 * 
 * @author Igor Kochanski 100%
 * @version 2
 */
public class CSVEmployees extends CSV {

    /**
     * Constructor.
     * 
     * @param pathOfCSV path of Employees.CSV
     */
    public CSVEmployees(String pathOfCSV) {
        super(pathOfCSV);
        readCSV();
    }

    /**
     * Method to add new Employee.
     * 
     * @param RoleID     ID of role must match existing
     * @param UserID     ID of user must match existing
     * @param ScalePoint Scale point of career must match
     * @param Type       type of employment: Part-Time, Full-Time
     * @param StartDate  start date: yyyy-mm-dd
     * @param FullName   full name
     * @param PPS        PPS number
     */
    public void addEmployee(String UserID, String RoleID, String ScalePoint, String Type, String StartDate,
            String FullName, String PPSN) {
        String[] newRow = { UserID, RoleID, ScalePoint, Type, StartDate, FullName, PPSN };
        addRow(newRow);
    }

    /**
     * Method to check if employee is Full-Time.
     * 
     * @param row details of employee
     * @return true if Full-Time, else false
     */
    public boolean isFullTime(String[] row) {
        if (row[3].equals("Full-Time"))
            return true;
        return false;
    }

    /**
     * Method to check if employee is Full-Time.
     * 
     * @param UserID user ID of employee
     * @return true if Full-Time, else false
     */
    public boolean isFullTime(String UserID) {
        return isFullTime(getRowOf(UserID));
    }
}
