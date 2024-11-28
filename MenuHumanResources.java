import java.util.ArrayList;

/**
 * Class to run the Employee menu of the system, extends Menu.
 * 
 * @author Igor Kochanski 85%
 * @author Ciaran Whelan 15%
 * @version 2
 */
public class MenuHumanResources extends Menu {

    /**
     * Constructor.
     * 
     * @param UserID user ID
     */
    public MenuHumanResources(String UserID) {
        this.UserID = UserID;
        more = true;
    }

    /**
     * Method to run the human resources menu.
     */
    public void run() {

        prefix = UserID + "> ";
        defaultMessage = prefix + "Human Resources Logged in";
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

            case "Q": // quit back to main system menu
                System.out.printf("%s\n   Logged Out\n%s\n", pageBreak, pageBreak);
                more = false;
                return;
        }
    }

    // Method to promote an employee
    private void promotePrompt() {
        // list available for promotion
        ArrayList<String[]> canPromote = promotionsCSV.getData();

        // check if promotions to dislay
        if (canPromote.size() <= 1) {
            System.out.println("No available employees to promote");
            return;
        }

        // display possible candidates
        promotionsCSV.printData();

        // choose employee
        System.out.print("Choose employee to promote.\n-> ");
        String userToPromote = in.nextLine().toUpperCase();
        String row[] = promotionsCSV.getRowOf(userToPromote);

        // check if valid user ID
        if (row == null) {
            System.out.println("Invalid UserID");
            return;
        }

        // set promotion offer to get accepted by employee
        row[1] = "Yes";

        // update promotions
        promotionsCSV.updateRow(userToPromote, 0, row);
        System.out.println("Promotion sent to " + userToPromote);
    }
}
