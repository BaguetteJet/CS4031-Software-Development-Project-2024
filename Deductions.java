import java.util.ArrayList;

public class Deductions extends CSV {

    private CSVPayScale payScale;
    private CSVEmployees employees;
    private ArrayList<String[]> employeeCSV = employees.getData(), payScaleCSV = payScale.getData();
    private double salary;

    public Deductions(String pathOfCSV) {
        super(pathOfCSV);
        readCSV();
    }

    public void getScalePoint(String userID, String scale) {
        for(int i = 1; i < employeeCSV.size(); i++){
            String[] scalePoint = employeeCSV.get(i);
            if (scalePoint[0].equals(userID)) {
                scale = scalePoint[2];
            }
        }
    }

    public void getRoleID(String userID, String roleID) {
        for(int i = 1; i < employeeCSV.size(); i++){
            String[] getRole = employeeCSV.get(i);
            if (getRole[0].equals(userID)) {
                roleID = getRole[1];
            }
        }
    }

    public void getSalary(String roleID, String scale, String salary) {
        for (int i = 1; i < payScaleCSV.size(); i++) {
            String[] findSalary = payScaleCSV.get(i);
            if (findSalary[0].equals(roleID)) {
                salary = findSalary[Integer.parseInt(scale) + 2];
            }
        }
    }

    public double getGrossMonthly() {
        return salary / 12;
    }

    //Calculate PAYE on salary
    public double getPAYE() {
        double MonthlyEarnings = salary / 12;
        if (MonthlyEarnings <= 3500) {
            return MonthlyEarnings * 0.2;
        } else {
            double tax = 700;
            double highRateSalary = MonthlyEarnings - 3500;
            return (highRateSalary * 0.4) + tax;
        }
    }

    // Calculate PRSI paid by employee
    public double getPRSI_EE() {
        double monthlyEarnings = salary / 52;
        int creditPRSI = 12;
        if (monthlyEarnings < 352) {
            return 0;
        } else if (monthlyEarnings == 352.01) {
            return (monthlyEarnings * 0.041) - creditPRSI;
        } else if (monthlyEarnings <= 424) {
            double afterCutOff = monthlyEarnings -352.01;
            double sixthEarnings = afterCutOff / 6;
            double taxCredit = 12 - sixthEarnings;
            return (((monthlyEarnings * 0.041) - taxCredit) * 52) / 12;
        } else {
            return ((monthlyEarnings * 0.041) * 52) / 12;
        }
    }

    //Calculate PRSI paid by employer
    public double getPRSI_ER() {
        double monthlyEarnings = salary /52;
        if (monthlyEarnings <= 38) {
            return 0;
        } else if (monthlyEarnings <= 496) {
            return (monthlyEarnings * 0.089) * 12;
        } else {
            return (monthlyEarnings * 0.1115) * 12;
        }
    }
    //Calculates USC on Salary
    public double getUSC() {
        if (salary <= 13000) {
            return 0;
        } else {
            return (salary * 0.04) /12;
        }
    }
}