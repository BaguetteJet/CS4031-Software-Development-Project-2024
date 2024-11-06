/** 
 * Class to run the Login menu of the system, extends Menu.
 * @author Igor Kochanski
 * @version 1
 */
public class LoginMenu extends Menu {

   /**
    * Constructor.
    */
   public LoginMenu() {
      more = true;
   }

   /**
    * Method to run the login menu.
    */
   public void run(){
      usersCSV.printData(); // test

      lastMessage = defaultMessage;
      while (more) {
         mainMenu();
      }
      in.close();
   }

   private void mainMenu() {

      System.out.printf("%s\n%s\n%s\n%s\n", pageSpace, pageBreak, lastMessage, pageBreak);
      lastMessage = defaultMessage;
      System.out.print("L)ogin   H)elp   Q)uit\n-> ");
      String command = in.nextLine().toUpperCase();

      switch (command) {
         case "L":
            System.out.print("Username: ");
            String username = in.nextLine();
            System.out.print("Password: ");
            String password = in.nextLine();

            if (username.length() == 0 || password.length() == 0) {
               lastMessage = "! Input cannot be empty";
               break;
            }

            String UserID = usersCSV.checkLogin(username, password);

            if (UserID == null) {
               lastMessage = "! Invalid login details.";
               break;
            }
            runUserMenu(UserID);
            break;

         case "H":
            System.out.println("help");
            break;

         case "Q":
            System.out.printf("%s\n%s\n   Goodbye.\n%s\n", pageSpace, pageBreak, pageBreak);
            more = false;
            break;
      }
   }

   private void runUserMenu(String userID) {
      char typeOfUser = userID.charAt(0);
      switch(typeOfUser) {
         case 'A':
            AdminMenu admin = new AdminMenu(userID);
            admin.run();
            break;
         case 'E':
            //runEmployee(userID);
            break;
         case 'H':
            //runHumanResources(userID);
            break;

         default:
            lastMessage = "! User has invalid ID";
            return;
      }
   }
}
