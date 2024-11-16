/** 
 * Class to run the Login menu of the system, extends Menu.
 * @author Igor Kochanski
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
   public void run(){
      usersCSV.printData(); // FOR TESTING PURPOSES

      lastMessage = defaultMessage;
      // keep running menu
      while (more) {
         mainMenu();
      }
      in.close();
   }

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
            runUserMenu(UserID);
            break;

         case "H": // display help message
            System.out.println("\n1. Log into an account to use.\n2. Admin and HR accounts are not\nemployee accounts");
            break;

         case "Q": // quit system
            System.out.printf("%s\n%s\n   Goodbye.\n%s\n", pageSpace, pageBreak, pageBreak);
            more = false;
            break;
      }
   }

   private void runUserMenu(String userID) {
      char typeOfUser = userID.charAt(0);
      switch(typeOfUser) {
         case 'A':
            MenuAdmin admin = new MenuAdmin(userID);
            admin.run();
            break;
         case 'E':
            MenuEmployee employee = new MenuEmployee(userID);
            employee.run();
            break;
         case 'H':
            MenuHumanResources humanResources = new MenuHumanResources(userID);
            humanResources.run();
            break;

         default:
            lastMessage = "! User has invalid ID";
            return;
      }
   }
}
