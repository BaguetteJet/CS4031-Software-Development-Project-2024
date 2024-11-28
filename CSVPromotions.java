/**
 * Class to operate Promotions.csv file data, extends CSV class.
 * All promotions are saved here.
 *
 * @author Ciaran Whelan 100%
 * @version 1
 */
public class CSVPromotions extends CSV {

    //Reads from Employees.csv, PayScale.csv & PayRoll.csv
    private final CSVEmployees employeesCSV;
    private final CSVPayScale payScaleCSV;
    private final CSVPayRoll payRollCSV;

    /**
     * Constructor.
     *
     * @param pathOfCSV path of Promotions.CSV
     */
    public CSVPromotions(String pathOfCSV) {
        super(pathOfCSV);
        readCSV();

        employeesCSV = new CSVEmployees("data\\Employees.csv");
        payScaleCSV = new CSVPayScale("data\\PayScale.csv");
        payRollCSV = new CSVPayRoll("data\\PayRoll.csv");
    }

    /**
     * Method to find all the employees that are promotable or progress the payScale
     */
    public void findPromotionsAndUpdateScale(){
        // clears the promotions from the last year
        if (getData().size() > 1) 
            clearData();

        String[] promotable = new String[2];
        // runs through all employees
        for (String[] row : employeesCSV.getData()) {
            // check if they are Fulltime & have another role that is avaialable for them
            if (employeesCSV.isFullTime(row) && !payScaleCSV.canProgress(row[1], row[2]) && payScaleCSV.getNextRole(row[1]) != null) {
                promotable[0] = row[0];
                // default is no until updated by HR
                promotable[1] = "No";
                addRow(promotable);
            // if cant progress check if they can go further up the scale
            } else if (employeesCSV.isFullTime(row) && payScaleCSV.canProgress(row[1], row[2])) {
                row[2] = Integer.toString(Integer.parseInt(row[2]) + 1);

                //Updates the data in Employees.csv & PayRoll.csv with new scale
                employeesCSV.updateRow(row[0], 0, row);
            }
        }
    }

    /** 
     * Method to promote an employee
     * 
     * @param userID userID
     */
    public void promoteEmployee(String userID){
        String row[] = employeesCSV.getRowOf(userID),
               payRow[] = payScaleCSV.getRowOf(row[1]),
               empty[] = new String[]{"", ""}; 

        // checks if there is a further role that they can pursue
        if(!payScaleCSV.canProgress(row[1], row[2]) && payScaleCSV.getNextRole(row[1]) != null){
            row[1] = payScaleCSV.getNextRole(row[1]);
            row[2] = payScaleCSV.findAvailableScalePoints(row[1])[0];

  
            updateRow(userID, 0, empty);

            //Updates the data in Employees.csv & PayRoll.csv with new role
            employeesCSV.updateRow(userID, 0, row);

            String newRow[] = {userID, row[1], row[2], payRow[Integer.parseInt(row[2])]};
            payRollCSV.updateRow(userID, 0, newRow);
            return;
        }

        // debugging statement to check for fails in findPromotions
        System.out.println("User is unable to be promoted");
    }
}
