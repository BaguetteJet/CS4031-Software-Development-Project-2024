public class CSVPayRoll extends CSV {

    private final CSVPayScale payScale;
    private final CSVEmployees employees;
  
    public CSVPayRoll(String pathOfCSV) {
        super(pathOfCSV);
        readCSV();

        employees = new CSVEmployees("\\Employees.csv");
        payScale = new CSVPayScale("data\\PayScale.csv");
    }

    /** 
     * @param userID
     * @param roleID
     * @param scale
     */
    public void addToPayRoll(String userID){   
        String row[] = employees.getRowOf(userID); 
        if(row == null){
            System.out.println("Invalid UserID");
            return;
        }
        if(getRowOf(userID) != null){
            System.out.println("User already on PayRoll");
            return;
        }

        String payRow[] = payScale.getRowOf(row[1]);
        String[] addRow = {userID, row[1], row[2], payRow[Integer.parseInt(row[2]) + 2]};
        addRow(addRow);
    }
}
