
/**
 * Class to operate PayRoll.csv file data, extends CSV class.
 * All employees on the PayRoll are saved here.
 *
 * @author Ciaran Whelan 100%
 * @version 2
 */
public class CSVPayRoll extends CSV {

    // reads from the PayScale.cvs & Employees.csv
    private final CSVPayScale payScaleCSV;
    private final CSVEmployees employeesCSV;
    
    /**
     * Constructor.
     *
     * @param pathOfCSV path of PayRoll.CSV
     */
    public CSVPayRoll(String pathOfCSV) {
        super(pathOfCSV);
        readCSV();

        payScaleCSV = new CSVPayScale("data\\PayScale.csv");
        employeesCSV = new CSVEmployees("data\\Employees.csv");
    }

    /** 
     * Method to add to PayRoll.
     * 
     * @param userID userID
     * @param roleID roleID
     * @param scale Users scale point
     */
    public void addToPayRoll(String userID){   
        String row[] = employeesCSV.getRowOf(userID); 

        // checks if user is a valid employee
        if(row == null){
            System.out.println("Invalid UserID");
            return;
        }
        // checks if user is already on the PayRoll
        if(getRowOf(userID) != null){
            System.out.println("User already on PayRoll");
            return;
        }

        String payRow[] = payScaleCSV.getRowOf(row[1]);
        String[] addRow = {userID, row[1], row[2], payRow[Integer.parseInt(row[2]) + 1]};
        addRow(addRow);
    }
    
    /**
     * Method to update the payRoll.
     */
    public void updatePayRoll(){
        // clears the data to prevent duplications
        if (getData().size() > 1) clearData();

        String addRow[], payRow[];
        // runs through the list of employees
        for(String[] row : employeesCSV.getData()){
            // checks that its not the header
            if(!row[0].equals("UserID"))
            {
                payRow = payScaleCSV.getRowOf(row[1]);

                // adds their data
                addRow = new String[]{row[0], row[1], row[2], payRow[Integer.parseInt(row[2]) + 1]};

                addRow(addRow);
            } 
        }
    }
}
