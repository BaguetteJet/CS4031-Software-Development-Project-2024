

public class CSVPromotions extends CSV {

    private final CSVEmployees employees;
    private final CSVPayScale payScale;

    public CSVPromotions(String pathOfCSV) {
        super(pathOfCSV);
        readCSV();

        employees = new CSVEmployees("data\\Employees.csv");
        payScale = new CSVPayScale("data\\PayScale.csv");
    }

    public void isPromotable() {
        clearData();

        String[] promotable = new String[2];

        for (String[] row : employees.getData()) {
            if (employees.isFullTime(row[0]) && (payScale.canProgress(row[1], row[2]) || payScale.getNextRole(row[1]) != null)) {
                promotable[0] = row[0];
                promotable[1] = "False";
                addRow(promotable);
            }
        }
    }

    public void promoteEmployee(String userID){
        String row[] = employees.getRowOf(userID);
        String empty[] = new String[]{"", ""};

        if(payScale.canProgress(row[1], row[2])){
            row[2] = Integer.toString(Integer.parseInt(row[2]) + 1);

            employees.updateRow(row[0], 0, row);
            updateRow(userID, 0, empty);
            return;
        }

        if(payScale.getNextRole(row[1]) != null){
            row[1] = payScale.getNextRole(row[1]);
            row[2] = payScale.findAvailableScalePoints(row[1])[0];

            employees.updateRow(userID, 0, row);
            updateRow(userID, 0, row);
            return;
        }

        System.out.println("User is unable to be promoted");
    }
}
