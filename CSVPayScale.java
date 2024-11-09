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
        System.out.println("RoleID  |  Role Name  |  Scale Points");
        for (int i = 1; i < getData().size(); i++) {
            String[] employee = getData().get(i);
            System.out.printf("%s | %s\n", employee[0], employee[1]);
        }
    }

    public String checkRoleID(String roleID) {
        for (int i = 1; i < getData().size(); i++) {
           String[] row = getData().get(i);
           if (row[0].equals(roleID)) {
                return row[0];
           }
        }
        return null;
    }

    public String salaryAtScalePoint(String RoleID, int ScalePoint) {
        for (int i = 1; i < getData().size(); i++) {
            String[] employee = getData().get(i);
            if (employee[0].equals(RoleID)) {
                return employee[ScalePoint + 2];
            }
        }
        return null;
    }

    public String[] findAvailableScalePoints(String RoleID) {
        for (int i = 1; i < getData().size(); i++) {
            String[] employee = getData().get(i);
            if (employee[0].equals(RoleID)) {
                return findAvailableScalePoints(employee);
            }
        }
        return null;
    }

    private String[] findAvailableScalePoints(String[] employee) {
        String[] payScale = getData().get(0);
        ArrayList<String> points = new ArrayList<String>();
        for (int i = 2; i < employee.length; i++) {
            if (!employee[i].equals("null")) {
                points.add(payScale[i]);
            }
        }
        return points.toArray(new String[points.size()]);
    }
}
