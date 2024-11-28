/**
 * Class to run the Employee menu of the system, extends Menu.
 * 
 * @author Igor Kochanski 95%
 * @author Ciaran Whelan 5%
 * @version 2
 */
public class MenuEmployee extends Menu {
    // code to get info, payslips, accept promotions
    private String[] details;
    private String[] job;
    private String optional;
    private Boolean fullTime;

    /**
     * Constructor.
     * 
     * @param UserID user ID
     */
    public MenuEmployee(String UserID) {
        this.UserID = UserID;
        more = true;
    }

    /**
     * Method to run the employee menu.
     */
    public void run() {

        prefix = UserID + "> ";
        defaultMessage = prefix + "Employee Logged in";
        lastMessage = defaultMessage;
        details = employeesCSV.getRowOf(UserID); // details of user
        fullTime = employeesCSV.isFullTime(details); // check if full-time
        job = payScaleCSV.getRowOf(details[1]);

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

        // check for optional selection
        if (fullTime && promotionsCSV.getRowOf(UserID) != null)
            optional = "A)ccept promotion   ";

        else if (!fullTime && payClaimsCSV.getRowOf(UserID) == null)
            optional = "F)ill Pay Claim   ";

        else
            optional = "";

        // options
        System.out.print("V)iew Details   P)ay Slips   " + optional + "Q)uit [Log Out]\n-> ");
        String command = in.nextLine().toUpperCase();

        // choice
        switch (command) {
            case "V": // view details
                // details of user
                details = employeesCSV.getRowOf(UserID);
                job = payScaleCSV.getRowOf(details[1]);

                // info of Full-Time employee
                if (fullTime)
                    System.out.printf(
                            "User ID: %s\nName: %s\nPPSN: %s\nRole: %s\nRole ID: %s\nType: %s\nStart Date: %s\nScale Point: %s\nSalary: %,d EUR\n",
                            details[0], details[5], details[6], job[1], details[1], details[3], details[4], details[2],
                            Integer.valueOf(job[Integer.valueOf(details[2]) + 1]));

                // info of Part-Time employee
                else
                    System.out.printf("User ID: %s\nName: %s\nPPSN: %s\nRole: %s\nRole ID: %s\nType: %s\nStart Date: %s\n",
                            details[0], details[5], details[6], job[1], details[1], details[3], details[4]);

                break;

            case "P": // display pay slips
                displayPaySlips();
                break;

            case "A": // accept promotion
                if (!optional.equals("A)ccept promotion   ")) // and promotion
                    break;

                // options 
                System.out.print("Accept promotion offer? (y/n)\n-> ");
                String accept = in.nextLine().toUpperCase();

                // choice
                if (accept.equals("Y")) {
                    promotionsCSV.promoteEmployee(UserID);
                    System.out.println("Promotion accepted.");

                } else {
                    System.out.println("Promotion not accepted.");
                }

                break;

            case "F": // fill in pay claim
                if (!optional.equals("F)ill Pay Claim   ")) // and promotion
                    break;
                
                // input
                System.out.print("Enter Hours: ");
                String hours = in.nextLine().toUpperCase();

                // check if double and add to pay claims
                try {
                    Double.parseDouble(hours);
                    payClaimsCSV.addClaim(UserID, hours);
                } catch (Exception e) {
                    lastMessage = prefix + "Invalid Hours Entered";
                    break;
                }
                break;

            case "Q": // quit back to main system menu
                System.out.printf("%s\n   Logged Out\n%s\n", pageBreak, pageBreak);
                more = false;
                return;
        }
    }

    // Method to display pay slips of a given employee
    private void displayPaySlips() {
        // UserID,Position,GrossPay,PAYE,PRSI,USC,NetPay,Date
        
        // loop though all pay slips
        for (String[] x : paySlipsCSV.getData()) {

            // check if ID matches
            if (x[0].equals(UserID)) {
                try {
                    System.out.println(pageBreak);
                    System.out.printf(" > University of Limerick <\n Name: %s\n Date: %s\n Role: %s\n PPSN: %s\n", 
                            details[5], x[7], job[1], details[6]);
                    System.out.printf(
                            " Gross Pay: %,12.2f EUR\n      PAYE: %,12.2f EUR\n      PRSI: %,12.2f EUR\n       USC: %,12.2f EUR\n   Net Pay: %,12.2f EUR\n",
                            Double.parseDouble(x[2]), Double.parseDouble(x[3]), Double.parseDouble(x[4]),
                            Double.parseDouble(x[5]), Double.parseDouble(x[6]));
                    System.out.println(pageBreak);

                } catch (Exception e) {
                    System.out.println("FORMAT ERROR");
                }
            }
        }
    }
}
