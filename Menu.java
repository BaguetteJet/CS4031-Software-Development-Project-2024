import java.util.Scanner;

/**
 * Abstract class of methods to operate menus and their style.
 * 
 * @author Igor Kochanski 100%
 * @version 1
 */

public abstract class Menu {
    // Menu
    protected Scanner in;
    protected boolean more;
    protected String UserID;
    // Menu Style
    protected String prefix;
    protected String defaultMessage;
    protected String lastMessage;
    protected String pageBreak;
    protected String pageSpace;
    protected String charRegex;
    // CSV files
    protected CSVUsers usersCSV;
    protected CSVEmployees employeesCSV;
    protected CSVPayScale payScaleCSV;
    protected CSVPayClaims payClaimsCSV;
    protected CSVPaySlips paySlipsCSV;
    protected CSVPromotions promotionsCSV;
    protected CSVSystemChecker systemCheckerCSV; 

    /**
     * Constructor.
     */
    public Menu() {
        more = true;
        UserID = null;
        defaultMessage = "        Welcome to UL Payroll System";
        pageBreak = "------------------------------------------------";
        pageSpace = "\n";
        charRegex = "[0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ$&+:;=?@#|'<>.^*()%!-]+";

        in = new Scanner(System.in);
        // load CSV files
        loadData();
    }

    /**
     * Abstract method to run menu.
     */
    public abstract void run();

    // Method to initiate all csv file classes needed
    private void loadData() {
        usersCSV = new CSVUsers("data\\Users.csv");
        payScaleCSV = new CSVPayScale("data\\PayScale.csv");
        employeesCSV = new CSVEmployees("data\\Employees.csv");
        payClaimsCSV = new CSVPayClaims("data\\PayClaims.csv");
        paySlipsCSV = new CSVPaySlips("data\\PaySlips.csv");
        promotionsCSV = new CSVPromotions("data\\Promotions.csv");
        systemCheckerCSV = new CSVSystemChecker("data\\SystemChecks.csv");
    }
}
