import java.util.Arrays;
/**
 * Class to run the Admin menu of the system, extends Menu.
 * 
 * @author Igor Kochanski
 * @version 1
 */
public class MenuAdmin extends Menu {

    /**
     * Constructor.
     * 
     * @param UserID ID of user
     */
    public MenuAdmin(String UserID) {
        this.UserID = UserID;
        more = true;
    }

    /**
     * Method to run the admin menu.
     */
    public void run() {

        prefix = UserID + "> "; // message prefix
        defaultMessage = prefix + "Admin Logged in"; // menu default message
        lastMessage = defaultMessage;
        // keep running menu
        while (more) {
            adminMenu();
        }
    }

    // admin main menu
    private void adminMenu() {

        // menu message
        System.out.printf("%s\n%s\n%s\n", pageBreak, lastMessage, pageBreak);
        lastMessage = defaultMessage;

        // options
        System.out.print("C)reate User   L)ist Users   Q)uit [Log Out]\n-> ");
        String command = in.nextLine().toUpperCase();

        // choice
        switch (command) {
            case "C": // create user menu
                createUserMenu();
                break;

            case "L": // list current users
                usersCSV.printData();
                break;

            case "Q": // quit back to main system menu
                System.out.printf("%s\n   Logged Out\n%s\n", pageBreak, pageBreak);
                more = false;
                return;
        }
    }

    // user creation menu
    private void createUserMenu() {

        // menu message
        lastMessage = prefix + "Creating user";
        System.out.printf("%s\n%s\n%s\n", pageBreak, lastMessage, pageBreak);

        // options
        System.out.print("Select user type to create:\nA)dmin   E)mployee   H)uman Resources   Q)uit\n-> ");
        String command = in.nextLine().toUpperCase();
        String newUserID = "";

        // choice
        switch (command) {
            case "A": // create admin
                newUserID = createAdminPrompt();
                if (newUserID == null)
                    break;
                lastMessage = prefix + "Created Admin: " + newUserID;
                break;

            case "E": // create employee
                newUserID = createEmployeePrompt();
                if (newUserID == null)
                    break;
                lastMessage = prefix + "Created Employee: " + newUserID;
                break;

            case "H": // create human resources
                newUserID = createHumanResourcesPrompt();
                if (newUserID == null)
                    break;
                lastMessage = prefix + "Created Human Resources: " + newUserID;
                break;

            case "Q": // quit
                lastMessage = prefix + "Cancelled user creation.";
                return;

            default: // other
                lastMessage = prefix + "! Entered invalid user type.";
                break;
        }
    }

    // admin creation
    private String createAdminPrompt() {

        // username & password
        System.out.print("New Username: ");
        String username = in.nextLine();
        System.out.print("New Password: ");
        String password = in.nextLine();

        // check username & password details
        lastMessage = checkUserPassword(username, password);
        if (!lastMessage.equals("ok"))
            return null;

        // return new user ID if successful
        return usersCSV.createAdmin(username, password);
    }

    // employee creation
    private String createEmployeePrompt() {

        // username & password
        System.out.print("New Username: ");
        String username = in.nextLine();
        System.out.print("New Password: ");
        String password = in.nextLine();
        // check username & password details
        lastMessage = checkUserPassword(username, password);
        if (!lastMessage.equals("ok"))
            return null;

        payScaleCSV.printRoles();

        // employment roll
        System.out.print("Enter Employment Role ID: ");
        String roleID = in.nextLine();
        if (payScaleCSV.getRowOf(roleID) == null) {
            lastMessage = prefix + "! Invalid role ID.";
            return null;
        }

        System.out.println(payScaleCSV.getRowOf(roleID)[1]);

        // start date
        System.out.print("Start Date (yyyy-mm-dd): ");
        String startDate = in.nextLine();
        if (!startDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            lastMessage = prefix + "! Invalid date format.";
            return null;
        }

        // employment type
        System.out.print("Select Employment Type:\nF)ull-Time   P)art-Time\n-> ");
        String typeOfEmployment = in.nextLine().toUpperCase();
        String scalePoint;
        switch (typeOfEmployment) {
            case "F":
                typeOfEmployment = "Full-Time";
                scalePoint = payScaleCSV.getCorrectScalePoint(roleID, startDate);
                if (scalePoint == null) scalePoint = "null";
                break;

            case "P":
                typeOfEmployment = "Part-Time";
                scalePoint = "null";
                break;
        
            default:
            lastMessage = prefix + "! Invalid employment type.";
                return null;
        }

        // name
        System.out.print("Full Name: ");
        String fullName = in.nextLine();
        if (fullName.contains(",")) {
            lastMessage = prefix + "! Name contains invalid character: ','";
            return null;
        }
        // PPS
        System.out.print("PPSN: ");
        String PPSN = in.nextLine();
        if (PPSN.contains(",")) {
            lastMessage = prefix + "! PPSN contains invalid character: ','";
            return null;
        }

        // return new user ID if successful
        String newUserID = usersCSV.createEmployee(username, password);
        employeesCSV.addEmployee(newUserID, roleID, scalePoint, typeOfEmployment, startDate, fullName, PPSN);
        return newUserID;
    }

    // human resources creation
    private String createHumanResourcesPrompt() {

        // username & password
        System.out.print("New Username: ");
        String username = in.nextLine();
        System.out.print("New Password: ");
        String password = in.nextLine();

        // check username & password details
        lastMessage = checkUserPassword(username, password);
        if (!lastMessage.equals("ok"))
            return null;

        // return new user ID if successful
        return usersCSV.createHumanResources(username, password);
    }

    private String checkUserPassword(String username, String password) {

        // check for null or empty
        String[] illegal = { null, "", "null" };
        if (Arrays.asList(illegal).contains(username) || Arrays.asList(illegal).contains(password))
            return "! Input cannot be empty or null";

        // check for illegal characters
        if (checkForIllegalCharacters(username))
            return "! Username contains invalid character(s).";
        if (checkForIllegalCharacters(password))
            return "! Password contains invalid character(s).";

        // check length
        if (username.length() < 3 || password.length() < 3)
            return "! Username or Passowrd too short";
        if (username.length() > 50 || password.length() > 50)
            return "! Username or Passowrd too long";

        // check if username available
        if (!usersCSV.checkUsernameAvailable(username))
            return "! Username already exists.";

        // else ok
        return "ok";
    }

    private boolean checkForIllegalCharacters(String y) {
        // check for illegal characters
        if (!y.matches(charRegex))
            return true;
        return false;
    }
}
