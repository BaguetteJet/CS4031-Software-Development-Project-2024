public class Deductions {

    private CSVPayScale payScale;
    private CSVEmployees employees;
    private final String roleID;
    private final double salary;
    private final int scale;
    private String[] row;

    public Deductions(String userID) {
        this.row = employees.getRowOf(userID);
        this.scale = Integer.parseInt(row[2]);
        this.roleID = row[1];
        this.salary = Double.parseDouble(payScale.salaryAtScalePoint(roleID, scale));
    }

    public double getGrossMonthly() {
        return salary / 12;
    }

    //Calculate PAYE on salary
    public double getPAYE() {
        double MonthlyEarnings = getGrossMonthly();
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

    public void addPaySlip(String UserID, String FullName, String PPSN, String Position, String GrossPay, String PAYE, String PRSI, String USC, String NetPay, String Date) {
        String[] addRow = {UserID, FullName, PPSN, Position, GrossPay, PAYE, PRSI, USC, NetPay, Date};
        for (String x : addRow) {
            if (x.equals(""))
                x = "null";
        }
    }
}