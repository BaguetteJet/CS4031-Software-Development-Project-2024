/**
 * Class to run the Employee menu of the system, extends Menu.
 * 
 * @author Igor Kochanski
 * @version 1
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
        details = employeesCSV.getRowOf(UserID);
        fullTime = employeesCSV.isFullTime(details);
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

        if (fullTime && true) // and promotion
            optional = "A)ccept promotion   ";
        
        else if (!fullTime && payClaimsCSV.getRowOf(UserID) == null) // and form to fill in
            optional = "F)ill Pay Claim   ";

        else 
            optional = "";

        // options
        System.out.print("V)iew Details   P)ay Slips   " + optional + "Q)uit [Log Out]\n-> ");
        String command = in.nextLine().toUpperCase();

        // choice
        switch (command) {
            case "V": // view details
                if (fullTime) System.out.printf("User ID: %s\nName: %s\nRole: %s\nRole ID: %s\nType: %s\nStart Date: %s\nScale Point: %s\nSalary: %,d EUR\n", details[0], details[5], job[1], details[1], details[3], details[4], details[2], Integer.valueOf(job[Integer.valueOf(details[2]) + 1]));
                else System.out.printf("User ID: %s\nName: %s\nRole: %s\nRole ID: %s\nType: %s\nStart Date: %s\n", details[0], details[5], job[1], details[1], details[3], details[4]);
                break;

            case "P": // display pay slips
                displayPaySlips();
                break;

            case "A":
                if (!optional.equals("A)ccept promotion   ")) // and promotion
                    break;
                break;
            
            case "F": // fill in pay claim
                if (!optional.equals("F)ill Pay Claim   ")) // and promotion
                    break;
                System.out.print("Enter Hours: ");
                String hours = in.nextLine().toUpperCase();
                try {  
                    Double.parseDouble(hours);
                    payClaimsCSV.addClaim(UserID, hours);  
                    } catch(Exception e) {  
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
    //UserID,Position,GrossPay,PAYE,PRSI,USC,NetPay,Date
    private void displayPaySlips() {
        for (String[] x : paySlipsCSV.getData()) {
            if (x[0].equals(UserID)) {
                System.out.println(pageBreak);
                System.out.printf("University of Limerick\nname: %s\ndate: %s\n", details[5], x[7]);
                System.out.println(pageBreak);
            }
        }
    }
}
