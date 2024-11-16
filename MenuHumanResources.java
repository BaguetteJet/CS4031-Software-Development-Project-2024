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

            case "Q": // quit back to main system menu
                System.out.printf("%s\n   Logged Out\n%s\n", pageBreak, pageBreak);
                more = false;
                return;
        }
    }

    // promote an employee
    private void promotePrompt() {
        // list available for promotion
        ArrayList<String> canPromote = listForPromotion();
        if (canPromote.size() == 0)
            return;
        // choose employee
        System.out.println("Choose employee to promote.\nUserID: ");
        String userToPromote = in.nextLine().toUpperCase();
        if (canPromote.contains(userToPromote)) {

// ADD promote user function ! ! ! < - - - - 

            System.out.println("Promoted " + userToPromote);
        }
    }

    // find promotable employees
    private ArrayList<String> listForPromotion() {
        employeesCSV.readCSV();
        ArrayList<String> ans = new ArrayList<String>();
        for (String[] row : employeesCSV.dataArray) {
            // check if Full-Time and next scale point possible
            if (payScaleCSV.isPromotable(row[1], row[2]) && row[3].equals("Full-Time")) {
                System.out.printf("%s | %s\n", row[0], row[5]);
                ans.add(row[0]);
            }
        }
        if (ans.size() == 0) {
            System.out.println("NO EMPLOYEES TO PROMOTE");
        }
        return ans;
    }
}
