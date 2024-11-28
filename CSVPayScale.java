import java.util.ArrayList;
import java.time.LocalDate;
import java.time.Month;

/**
 * Class to operate PayScale.csv file data, extends CSV class.
 * Each role ID number is unique, roles within the same promotable bracket
 * differ by exactly 1.
 * 
 * @author Igor Kochanski 95%
 * @author Ciaran Whelan 5%
 * @version 2
 */
public class CSVPayScale extends CSV {

    /**
     * Constructor.
     * 
     * @param pathOfCSV path of Employees.CSV
     */
    public CSVPayScale(String pathOfCSV) {
        super(pathOfCSV);
        readCSV();
    }

    /**
     * Method to print all job roles.
     */
    public void printRoles() {
        readCSV();

        // loop though all rows
        for (String[] x : dataArray) {

            System.out.printf("%s | %s\n", x[0], x[1]);
        }
    }

    /**
     * Method to find the salary at a scale point.
     * 
     * @param RoleID role ID
     * @param ScalePoint scale point of job
     * @return string of salary, else null
     */
    public String salaryAtScalePoint(String RoleID, int ScalePoint) {
        // get row to job details
        String[] row = getRowOf(RoleID);

        // check salary if exists
        if (row == null)
            return null;

        // return salary
        return row[ScalePoint + 1];
    }

    /**
     * Method to find scale points available for a role.
     * 
     * @param roleID role ID
     * @return array of possible scale points, else null
     */
    public String[] findAvailableScalePoints(String roleID) {
        // get row of job role details
        String[] row = getRowOf(roleID);

        // check if role exists
        if (row == null)
            return null;

        // get column names
        String[] payScale = getData().get(0);

        // create list to store possible scale points
        ArrayList<String> points = new ArrayList<String>();

        // loop though elements of row
        for (int i = 2; i < row.length; i++) {

            // check if next scale point is null
            if (!row[i].equals("null")) {

                // add to list
                points.add(payScale[i]);
            }
        }
        // return list of possible scale points as array
        return points.toArray(new String[points.size()]);
    }

    /**
     * Method to return the correct scale point based on start date.
     * 
     * @param roleID role ID
     * @param date start date
     * @return scale point, else null
     */
    public String getCorrectScalePoint(String roleID, String date) {

        // get possible scale points for role
        String[] possible = findAvailableScalePoints(roleID);

        // check if exists
        if (possible == null)
            return null;

        // get date entered and current date
        LocalDate startDate = LocalDate.parse(date);
        LocalDate currentDate = LocalDate.now();

        // count octobers since start
        int years = 0;
        // loop for years since start
        for (int year = startDate.getYear(); year <= currentDate.getYear(); year++) {

            // get october of that year
            LocalDate october = LocalDate.of(year, Month.OCTOBER, 1);

            // check if october of that year was between start date and current date
            if (!october.isBefore(startDate) && !october.isAfter(currentDate)) {
                years++;
            }
        }

        int scalePoint = 0;
        // set minimum scale point
        if (years < possible.length)
            scalePoint = years;

        // set maximum scale point
        if (years >= possible.length)
            scalePoint = possible.length - 1;

        // return scale point
        return possible[scalePoint];
    }

    /**
     * Method to check if an employee can progress on scale point.
     * 
     * @param roleID role ID
     * @param scalePoint scale point
     * @return true if employee promotable, else false
     */
    public boolean canProgress(String roleID, String scalePoint) {
        // get row of job role details
        String[] row = getRowOf(roleID);

        // check if furthers scale point (18th)
        if (Integer.valueOf(scalePoint) + 2 >= row.length)
            return false;

        // check if next scale point exists (next)
        if (row[Integer.valueOf(scalePoint) + 2].equals("null"))
            return false;

        return true;
    }

    /**
     * Method to get possible next promotion role.
     * 
     * @param roleID role ID of current position
     * @return role ID of next promotion role, else null
     */
    public String getNextRole(String roleID) {
        // advanced role
        String nextRole = Integer.toString(Integer.parseInt(roleID) + 1);

        // check if exists
        if (getRowOf(nextRole) != null)
            return nextRole;

        return null;
    }
}
