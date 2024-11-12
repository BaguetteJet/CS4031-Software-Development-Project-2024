public class MenuEmployee extends Menu {
    // code to get info, payslips, accept promotions
    private String[] details;
    private String optional;
    private Boolean fullTime;

     /**
     * Constructor.
     * 
     * @param UserID ID of user
     */
    public MenuEmployee(String UserID) {
        this.UserID = UserID;
        more = true;
    }

    /**
     * Method to run the employee menu.
     */
    public void run() {

        prefix = UserID + "> "; // message prefix
        defaultMessage = prefix + "Employee Logged in"; // menu default message
        lastMessage = defaultMessage;
        details = employeesCSV.selectEmployee(UserID);
        fullTime = employeesCSV.isFullTime(details);

        // keep running menu
        while (more) {
            employeeMenu();
        }
    }

    // employee main menu
    private void employeeMenu() {

        // menu message
        System.out.printf("%s\n%s\n%s\n", pageBreak, lastMessage, pageBreak);
        lastMessage = defaultMessage;

        if (fullTime && true) { // and promotion
            optional = "A)ccept promotion   ";
        }
        else if (!fullTime && true) { // and form to fill in
            optional = "F)ill Pay Claim   ";
        }
        else 
            optional = "";

        // options
        System.out.print("V)iew Details   P)ay Slips   " + optional + "Q)uit [Log Out]\n-> ");
        String command = in.nextLine().toUpperCase();

        // choice
        switch (command) {
            case "V": // view details
                
                break;

            case "L": // display pay slips
                
                break;

            case "A":
                if (!fullTime || !true) // and promotion
                    break;

                break;
            
            case "F":
                if (fullTime || !true) // and promotion
                    break;

                break;

            case "Q": // quit back to main system menu
                System.out.printf("%s\n   Logged Out\n%s\n", pageBreak, pageBreak);
                more = false;
                return;
        }
    }


}
