import java.util.ArrayList;
/**
 * Class to run the Employee menu of the system, extends Menu.
 * 
 * @author Igor Kochanski
 * @version 1
 */
public class MenuHumanResources extends Menu {

     /**
     * Constructor.
     * 
     * @param UserID ID of user
     */
    public MenuHumanResources(String UserID) {
        this.UserID = UserID;
        more = true;
    }

    /**
     * Method to run the human resources menu.
     */
    public void run() {

        prefix = UserID + "> "; // message prefix
        defaultMessage = prefix + "Human Resources Logged in"; // menu default message
        lastMessage = defaultMessage;

        // keep running menu
        while (more) {
            humanResourcesMenu();
        }
    }

    // employee main menu
    private void humanResourcesMenu() {

        // menu message
        System.out.printf("%s\n%s\n%s\n", pageBreak, lastMessage, pageBreak);
        lastMessage = defaultMessage;

        // options
        System.out.print("V)iew Employees   P)romote   Q)uit [Log Out]\n-> ");
        String command = in.nextLine().toUpperCase();

        // choice
        switch (command) {
            case "V": // view details of all employees
                employeesCSV.printData();
                break;

            case "P": // promote an employee
                promotePrompt();
                break;

            case "PromoteTEST": // promote an employee
                promotionsCSV.findPromotions();
                break;

            case "Q": // quit back to main system menu
                System.out.printf("%s\n   Logged Out\n%s\n", pageBreak, pageBreak);
                more = false;
                return;
        }
    }

    // promote an employee
    private void promotePrompt() {
        // list available for promotion
        ArrayList<String[]> canPromote = promotionsCSV.getData();
        if (canPromote.size() <= 1){
            System.out.println("No available employees to promote");
            return;
        }
        // choose employee
        System.out.println("Choose employee to promote.");
        promotionsCSV.printData();
        String userToPromote = in.nextLine().toUpperCase();

        String row[] = promotionsCSV.getRowOf(userToPromote);
        if (row == null) {
            System.out.println("Invalid UserID");
            return;
        }
        row[1] = "Yes";

        promotionsCSV.updateRow(userToPromote, 0, row);
        System.out.println("Promoted " + userToPromote);
    }
}
