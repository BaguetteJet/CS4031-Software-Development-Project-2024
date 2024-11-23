/**
 * Class to run the Login menu of the system, extends Menu.
 * 
 * @author Igor Kochanski 100%
 * @version 1
 */
public class MenuLogin extends Menu {

   /**
    * Constructor.
    */
   public MenuLogin() {
      more = true;
   }

   /**
    * Method to run the login menu.
    */
   public void run() {

      usersCSV.printData(); // display all logins <--- [FOR TESTING PURPOSES]
      lastMessage = defaultMessage;

      // keep running menu
      while (more) {
         mainMenu();
      }
      in.close();
   }

   // main menu
   private void mainMenu() {

      // menu message
      System.out.printf("%s\n%s\n%s\n", pageBreak, lastMessage, pageBreak);
      lastMessage = defaultMessage;

      // options
      System.out.print("L)ogin   H)elp   Q)uit\n-> ");
      String command = in.nextLine().toUpperCase();

      // choice
      switch (command) {
         case "L": // user login
            System.out.print("Username: ");
            String username = in.nextLine();
            System.out.print("Password: ");
            String password = in.nextLine();

            // check if not empty
            if (username.length() == 0 || password.length() == 0) {
               lastMessage = "! Input cannot be empty";
               break;
            }

            // check if correct login
            String UserID = usersCSV.checkLogin(username, password);
            if (UserID == null) {
               lastMessage = "! Invalid login details.";
               break;
            }

            // redirect to user menu
            runUserMenu(UserID);
            break;

         case "H": // display help message
            System.out.println(pageBreak +
                              "\n_____ Quick Guide To The UL Payroll System _____\n" +
                              "   \n" +
                              "SYSTEM USER TYPES\n" +
                              "   The system has 3 user types:\n" +
                              "   - ADMIN \n" +
                              "   - EMPLOYEE \n" +
                              "   - HUMAN RESOURCES [HR]\n\n" +
                              "   Each user type can see and do different things.\n" +
                              "   A \"user\" in this system is just an account,\n" +
                              "   meaning not every user is an employee!\n" +
                              "   Only EMPLOYEE type users are Full-Time or \n" +
                              "   Part-Time employees, meanwhile ADMIN and HR\n" +
                              "   type users are just accounts. (not employees!)\n\n" +
                              "   Individual Admin and HR employees can also have\n" +
                              "   their own EMPLOYEE type account to use the\n" +
                              "   Payroll system.\n" +
                              "   \n" +
                              "SELECTION SYSTEM\n" +
                              "   Different input types:\n" +
                              "   - Prompts with  :   indicate an open field.\n" +
                              "   - Prompts with  ->  indicate a selection.\n\n" +
                              "   Open field inputs can be variable length, are\n" +
                              "   capslock sensitive may require a certain format\n" +
                              "   (number, text, date, etc...),  while a selection\n" +
                              "   is capslock insensitive and a single character.\n" +
                              "   \n" +
                              "HOW TO USE\n" +
                              "   After logging in, each user type has a differnt menu.\n" +
                              "   Read SELECTION SYSTEM on how to navigate menus.\n\n" +
                              "   ADMIN users can create new users of each type and\n" +
                              "   view existing usernames and passwords. When creating\n" +
                              "   a new EMPLOYEE user, extra input data is required.\n\n" +
                              "   EMPLOYEE users can view their personal information and\n" +
                              "   past pay slips. Full-Time employees can accept\n" +
                              "   promotions, while Part-Time employees can fill in\n" +
                              "   pay claims.\n\n" +
                              "   HUMAN RESOURCES users can view personal information\n" +
                              "   of every EMPLOYEE, and have the ability to send\n" +
                              "   promotion offers to a Full-Time employees if\n" +
                              "   possible.\n" +
                              "   \n" +
                              "PROMOTION FUNCTIONALITY\n" +
                              "   When promoting an employee, the scale point will\n" +
                              "   increase by one, unless the highest scale point is\n" +
                              "   already reached. In that case, if a further role\n" +
                              "   is possible, the employee will be promoted to the next\n" +
                              "   role within the job bracket. Every employee gets promoted\n" +
                              "   once a year in October. Part-Time employees do not\n" +
                              "   have a scale point or salary and cannot be promoted.\n" +
                              "   \n" +
                              pageBreak);
            System.out.print("press Enter to exit");
            in.nextLine();
            break;

         case "Q": // quit system
            System.out.printf("%s\n%s\n   Goodbye.\n%s\n", pageSpace, pageBreak, pageBreak);
            more = false;
            break;
      }
   }

   // method to select user menu type
   private void runUserMenu(String userID) {
      // ID number prefix
      char typeOfUser = userID.charAt(0);

      // choose next menu
      switch (typeOfUser) {
         case 'A': // admin
            MenuAdmin admin = new MenuAdmin(userID);
            admin.run();
            break;

         case 'E': // employee
            MenuEmployee employee = new MenuEmployee(userID);
            employee.run();
            break;

         case 'H': // human resources
            MenuHumanResources humanResources = new MenuHumanResources(userID);
            humanResources.run();
            break;

         default: // other
            lastMessage = "! User has invalid ID";
            return;
      }
   }
}
