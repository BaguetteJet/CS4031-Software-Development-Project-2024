/**
 * Main class to run the UL Payroll System
 * @author Igor Kochanski - 23358459
 * @author Ciaran Whelan - 
 * @author Luke Scanlon - 
 */
public class RunSystem {
    /**
     * Run main UL Payroll System.
     * @param args
     */
    public static void main(String[] args) {
        UpdateSystem updateSystem = new UpdateSystem();
        MenuLogin loginMenu = new MenuLogin();
        updateSystem.updateAll();
        loginMenu.run();
    }
}
