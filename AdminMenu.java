/** 
 * Class to run the Admin menu of the system, extends Menu.
 * @author Igor Kochanski
 * @version 1
 */
public class AdminMenu extends Menu {
    private String prefix;

    /**
     * Constructor.
     * @param UserID ID of user
     */
    public AdminMenu(String UserID) {
        this.UserID = UserID;
        more = true;
    }

    /**
     * Method to run the admin menu.
     */
    public void run() {

        prefix = UserID + "> ";
        defaultMessage = prefix + "Admin Logged in";
        lastMessage = defaultMessage;

        while (more) {
            adminMenu();
        }
    }

    private void adminMenu() {

        System.out.printf("%s\n%s\n%s\n", pageBreak, lastMessage, pageBreak);
        lastMessage = defaultMessage;
        System.out.print("C)reate User   L)ist Users   Q)uit [Log Out]\n-> ");
        String command = in.nextLine().toUpperCase();

        switch (command) {
            case "C":
                createMenu();
                break;
            
            case "L":
                usersCSV.printData();
                break;

            case "Q":
                System.out.printf("%s\n   Logged Out\n%s\n", pageBreak, pageBreak);
                more = false;
                return;
        }
    }

    private void createMenu() {
        lastMessage = prefix + "Creating user";
        System.out.printf("%s\n%s\n%s\n", pageBreak, lastMessage, pageBreak);
        System.out.print("Select user type to create:\nA)dmin - E)mployee - H)uman Resources - Q)uit\n-> ");
        String command = in.nextLine().toUpperCase();

        if (command.equals("Q")) {
            lastMessage = prefix + "Cancelled user creation.";
            return;
        }
        if (!command.equals("A") && !command.equals("E") && !command.equals("H")) {
            lastMessage = prefix + "! Entered invalid user type.";
            return;
        }

        System.out.print("New Username: ");
        String username = in.nextLine();
        System.out.print("New Password: ");
        String password = in.nextLine();

        if (username == null || password == null) {
            lastMessage = "! Input cannot be empty";
            return;
        }
        if (!username.matches(charRegex) || !password.matches(charRegex)) {
            lastMessage = prefix + "! Entered invalid character.";
            return;
        }
        if (!usersCSV.checkUsernameAvailable(username)) {
            lastMessage = prefix + "! Username already exists.";
            return;
        }

        String newUserID = "";

        switch (command) {
            case "A":
                newUserID = usersCSV.createAdmin(username, password);
                lastMessage = prefix + "Created Admin: " + newUserID;
                break;
            case "E":
                newUserID = usersCSV.createEmployee(username, password);
                lastMessage = prefix + "Created Employee: " + newUserID;
                break;
            case "H":
                newUserID = usersCSV.createHumanResources(username, password);
                lastMessage = prefix + "Created Human Resources: " + newUserID;
                break;
        }
    }
}
