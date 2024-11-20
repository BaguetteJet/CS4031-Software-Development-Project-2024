public class CSVPromotions extends CSV {

    private final CSVEmployees employees;
    private final CSVPayScale payScale;
    private final CSVPayRoll payRoll;

    public CSVPromotions(String pathOfCSV) {
        super(pathOfCSV);
        readCSV();

        employees = new CSVEmployees("data\\Employees.csv");
        payScale = new CSVPayScale("data\\PayScale.csv");
        payRoll = new CSVPayRoll("data\\PayRoll.csv");
    }

    public void findPromotions(){
        clearData();

        String[] promotable = new String[2];

        for (String[] row : employees.getData()) {
            if (employees.isFullTime(row) && (payScale.canProgress(row[1], row[2]) || payScale.getNextRole(row[1]) != null)) {
                promotable[0] = row[0];
                promotable[1] = "No";
                addRow(promotable);
            }
        }
    }

    public void promoteEmployee(String userID){
        String row[] = employees.getRowOf(userID),
               payRow[] = payScale.getRowOf(row[1]),
               empty[] = new String[]{"", ""};

        if(payScale.canProgress(row[1], row[2])){
            row[2] = Integer.toString(Integer.parseInt(row[2]) + 1);

            employees.updateRow(row[0], 0, row);
            updateRow(userID, 0, empty);

            String newRow[] = {userID, row[1], row[2], payRow[Integer.parseInt(row[2])]};
            //payRoll.updateRow(userID, 0, newRow);
            return;
        }

        if(payScale.getNextRole(row[1]) != null){
            row[1] = payScale.getNextRole(row[1]);
            row[2] = payScale.findAvailableScalePoints(row[1])[0];

            employees.updateRow(userID, 0, row);
            updateRow(userID, 0, empty);

            String newRow[] = {userID, row[1], row[2], payRow[Integer.parseInt(row[2])]};
            //payRoll.updateRow(userID, 0, newRow);
            return;
        }

        System.out.println("User is unable to be promoted");
    }
}
