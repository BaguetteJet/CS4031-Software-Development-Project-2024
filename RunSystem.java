/**
 * Main class to run the UL Payroll System
 * @author Igor Kochanski - 23358459
 * @author Ciaran Whelan - 23370211
 * @author Luke Scanlon - 23390573
 */
public class RunSystem {
    /**
     * Run main UL Payroll System.
     * @param args
     */
    public static void main(String[] args) {
        MenuLogin loginMenu = new MenuLogin();
        loginMenu.run();
    }
}
