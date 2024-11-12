import java.util.ArrayList;

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

    public void printRoles() {
        readCSV();
        for (String[] x : dataArray) {
            System.out.printf("%s | %s\n", x[0], x[1]);
        }
    }

    public String salaryAtScalePoint(String RoleID, int ScalePoint) {
        String[] row = getRowOf(RoleID);
        if (row == null)
            return null;
        return row[ScalePoint + 1];
    }

    public String[] findAvailableScalePoints(String roleID) {
        String[] row = getRowOf(roleID);
        if (row == null)
            return null;
        String[] payScale = getData().get(0);
        ArrayList<String> points = new ArrayList<String>();
        for (int i = 2; i < row.length; i++) {
            if (!row[i].equals("null")) {
                points.add(payScale[i]);
            }
        }
        return points.toArray(new String[points.size()]);
    }
}
