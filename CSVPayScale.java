import java.util.ArrayList;
import java.time.LocalDate;
import java.time.Month;
/** 
 * Class to operate PayScale.csv file data, extends CSV class
 * @author Igor Kochanski
 * @version 1
 */
public class CSVPayScale extends CSV {

    /**
     * Constructor.
     * @param pathOfCSV path of Employees.CSV
     */
    public CSVPayScale(String pathOfCSV) {
        super(pathOfCSV);
        readCSV();
    }

    /**
     * Method to print all roles
     */
    public void printRoles() {
        readCSV();
        for (String[] x : dataArray) {
            System.out.printf("%s | %s\n", x[0], x[1]);
        }
    }

    /**
     * Method to find the salary at a scale point
     * @param RoleID role ID of job
     * @param ScalePoint scale point of job
     * @return string of salary
     */
    public String salaryAtScalePoint(String RoleID, int ScalePoint) {
        String[] row = getRowOf(RoleID);
        // check salary if exists
        if (row == null)
            return null;
        // return salary
        return row[ScalePoint + 1];
    }

    /**
     * Method to find scale points available for a role
     * @param roleID role ID of job
     * @return array of possible scale points
     */
    private String[] findAvailableScalePoints(String roleID) {
        String[] row = getRowOf(roleID);
        // check if role exists
        if (row == null)  return null;
        String[] payScale = getData().get(0);
        ArrayList<String> points = new ArrayList<String>();
        // check if next scale point is null
        for (int i = 2; i < row.length; i++) {
            if (!row[i].equals("null")) {
                points.add(payScale[i]);
            }
        }
        // return array of possible scale points
        return points.toArray(new String[points.size()]);
    }

    /**
     * Method to return the correct scale point based on start date
     * @param roleID role ID
     * @param date start date
     * @return scale point
     */
    public String getCorrectScalePoint(String roleID, String date) {
        String[] possible = findAvailableScalePoints(roleID);
        if (possible == null) return null;

        LocalDate startDate = LocalDate.parse(date);
        LocalDate currentDate = LocalDate.now();

        int years = 0;

        // count octobers since start
        for (int year = startDate.getYear(); year <= currentDate.getYear(); year++) {
            LocalDate october = LocalDate.of(year, Month.OCTOBER, 1);
            if (!october.isBefore(startDate) && !october.isAfter(currentDate)) {
                years++;
            }
        }

        int scalePoint = 0;
        if (years < possible.length) scalePoint = years;
        if (years >= possible.length) scalePoint = possible.length - 1;

        return possible[scalePoint];
    }

    /**
     * Method to check if an employee can progress on scale point
     * @param roleID role ID of job
     * @param scalePoint scale point of job
     * @return true if employee promotable
     */
    public boolean canProgress(String roleID, String scalePoint) {
        String[] row = getRowOf(roleID);
        // check if final scale point
        if (Integer.valueOf(scalePoint) + 2 >= row.length)
            return false;
        // check if next scale point exists
        if (row[Integer.valueOf(scalePoint) + 2].equals("null"))
            return false;
        return true;
    }
}
