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
    public void addToPayRoll(String userID, String roleID, String scale){   
        String row[] = employees.getRowOf(userID); 
        if(row == null){
            System.out.println("Invalid UserID");
            return;
        }
        if(payScale.getRowOf(roleID) == null){
            System.out.println("Invalid RoleID");
            return;
        }
        if(payScale.getRowOf(scale, Integer.parseInt(scale) + 2) == null){
            System.out.println("Invalid Scale Point");
        }
        if(getRowOf(userID) != null){
            System.out.println("User already on PayRoll");
            return;
        }
          
        String[] addRow = {userID, roleID, scale, row[Integer.parseInt(scale)]};
        addRow(addRow);
    }
}
