public class Deductions {

    private double salary;



    public double getSalary() {

        for (int i = 0; i < payRollCSV.getData().size(); i++) {
            if ()
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
            return (monthlyEarnings * 0.041) - 12;
        } else if (monthlyEarnings <= 424) {
            double afterCutOff = monthlyEarnings -352.01;
            double sixthEarnings = afterCutOff / 6;
            double taxCredit = 12 - sixthEarnings;
            return (monthlyEarnings * 0.041) - taxCredit;
        } else {
            return monthlyEarnings * 0.041;
        }
    }

    //Calculate PRSI paid by employer
    public double getPRSI_ER() {
        double monthlyEarnings = salary / 52;
        if (monthlyEarnings <= 38) {
            return 0;
        } else if (monthlyEarnings <= 496) {
            return monthlyEarnings * 0.089;
        } else {
            return monthlyEarnings * 0.1115;
        }
    }
    //Calculates USC on Salary
    public double getUSC() {
        if (salary <= 13000) {
            return 0;
        } else {
            return (salary * 0.04) /52;
        }
    }
}