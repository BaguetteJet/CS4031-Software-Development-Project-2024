/**
 *.Deductions class to calulate all deductions from an employees salary.

 @author Luke Scanlon 100%
 @version 4
 */
public class Deductions {
    private CSVEmployees employees;
    private CSVPayScale payScales;
    private CSVPayClaims payClaims;
    private double salary;

    
    /**
     * Constructor.
     * calls an instance of:
     * CSVEmployees.
     * CSVPayScale.
     * CSVPayClaims.
     */
    public Deductions() {
        employees = new CSVEmployees("data\\Employees.csv");
        payScales = new CSVPayScale("data\\PayScale.csv");
        payClaims = new CSVPayClaims("data\\PayClaims.csv");
        
    }

    /**
     * Method to load the salary into the deductions for calculations
     * 
     * @param userID
     */
    public void loadSalary(String userID) {
        String[] row = employees.getRowOf(userID);
        if (row == null) {
            throw new IllegalArgumentException("Employee not found");
        }
        String roleID = row[1];
        int payScale = Integer.valueOf(row[2]);
        String[] payRow = payScales.getRowOf(roleID);

        if (isFullTime(userID)) {
            this.salary = Double.parseDouble(payRow[payScale + 1]) / 12;
        } else {
            this.salary = ((Double.parseDouble(payRow[payScale + 1]) / 52) / 40) * hoursWorked(userID);
        }
    }

    /**
     * Method to get the monthly salary.
     * 
     * @return salary.
     */
    public double getGrossMonthly() {
        return salary;
    }

    /**
     * Method to calculate PAYE(Pay As You Earn) deduction.
     * 
     * @return Amount of PAYE.
     */
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
    
    /**
     * Method to calculate PRSI(Pay Related Social Insurance) deduction.
     * 
     * @return Amount of PRSI.
     */
    public double getPRSI() {
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
    
    /**
     * Method to calculate USC(Universal Social Charge) deduction.
     * 
     * @return Amount of USC.
     */
    public double getUSC() {
        if (salary <= 13000) {
            return 0;
        } else {
            return (salary * 0.04) /12;
        }
    }
    
    /**
     * Method to calculate the total deductions.
     * 
     * @return Amount of PAYE.
     */
    private double getTotalDeductions() {
        return getPAYE() + getPRSI() + getUSC();
    }

    /**
     * Method to calculate the Net pay.
     * 
     * @return NetPay.
     */
    public double getNetPay() {
        return getGrossMonthly() - getTotalDeductions();
    }

    /**
     * Method to check if an employ is full-Time or Part-Time.
     * 
     * @param userID
     * @return True or False.
     */
    private boolean isFullTime(String userID) {
        String[] row = employees.getRowOf(userID);
        String type = row[3];
        if (type.equals("Full-Time")) {
            return true;
        }
        return false;
    }

    /**
     * Method to get the hours worked from the PayClaims.csv file.
     * @param userID
     * @return Hours worked.
     */
    private double hoursWorked(String userID) {
        String[] row = payClaims.getRowOf(userID);
        return Double.parseDouble(row[1]);
    }
}