import java.util.Scanner;

/** 
 * Abstract class of methods to operate menus.
 * @author Igor Kochanski
 * @version 1
 */
public abstract class Menu {
    // Menu 
    protected Scanner in;
    protected boolean more;
    protected String UserID;
    // Menu Style
    protected String defaultMessage;
    protected String lastMessage;
    protected String pageBreak;
    protected String pageSpace;
    protected String charRegex;
    // CSV files
    protected UsersCSV usersCSV;

    /**
     * Constructor.
     */
    public Menu() {
        more = true;
        UserID = null;
        defaultMessage = "   Welcome to UL Payroll System";
        pageBreak = "--------------------------------------";
        pageSpace = "\n\n";
        charRegex = "[0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ$&+:;=?@#|'<>.^*()%!-]+";

        in = new Scanner(System.in);
        // load usersCSV
        loadData();
    }

    /** 
     * Abstract method to run menu.
     */
    public abstract void run();

    private void loadData() {
        usersCSV = new UsersCSV("Users.CSV");
    }
}