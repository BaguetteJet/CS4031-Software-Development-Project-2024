
import java.util.ArrayList;

/**
 * Class to operate Users.csv file data, extends CSV class.
 * Each user has unique a ID number with a letter prefix indicating their user
 * type as well as a unique username and a password.
 * 
 * @author Igor Kochanski 100%
 * @version 1
 */
public class CSVUsers extends CSV {

    /**
     * Constructor.
     * 
     * @param pathOfCSV path of Users.CSV
     */
    public CSVUsers(String pathOfCSV) {
        super(pathOfCSV);
        readCSV();
    }

    /**
     * Method to check if the provided details are a valid login.
     * 
     * @param username username to check
     * @param password password to check
     * @return String of User ID if valid, else return null
     */
    public String checkLogin(String username, String password) {
        readCSV();

        // check each user
        for (int i = 1; i < dataArray.size(); i++) {
            String[] row = dataArray.get(i);

            // check if username -> password combination exists
            if (row[1].equals(username) && row[2].equals(password)) {
                return row[0];
            }
        }
        return null;
    }

    /**
     * Method to check if username is not currently in use.
     * 
     * @param username
     * @return false if username already in use, else true.
     */
    public boolean checkUsernameAvailable(String username) {
        readCSV();

        // check if username exists
        if (getRowOf(username, 1) == null)
            return true;

        return false;
    }

    /**
     * Method to creat Admin user.
     * 
     * @param username new username
     * @param password new passowrd
     * @return new user ID
     */
    public String createAdmin(String username, String password) {
        return createUser("A", username, password);
    }

    /**
     * Method to creat Employee user.
     * 
     * @param username new username
     * @param password new passowrd
     * @return new user ID
     */
    public String createEmployee(String username, String password) {
        return createUser("E", username, password);
    }

    /**
     * Method to creat Human Resources user.
     * 
     * @param username new username
     * @param password new passowrd
     * @return user ID
     */
    public String createHumanResources(String username, String password) {
        return createUser("H", username, password);
    }

    // general user creation method
    private String createUser(String type, String username, String password) {

        // generate new unique ID of certain type
        String newUserID = generateID(getData(), type);

        // add details to Users.csv
        String[] addRow = { newUserID, username, password };
        addRow(addRow);

        // return new user ID
        return newUserID;
    }

    // unique ID generation method
    private String generateID(ArrayList<String[]> Users, String type) {
        int numID; // ID number only
        String newUserID = null;
        boolean unique = false;

        while (!unique) {
            // assume unique
            unique = true;

            // generate a new ID number
            numID = (int) (Math.random() * 9999999);
            newUserID = String.format("%07d", numID);

            // check if already exists
            for (int i = 1; i < Users.size(); i++) {
                String[] row = Users.get(i);

                // get only ID number
                String oldUserID = row[0].substring(1);

                // retry if ID alreay exists
                if (oldUserID.equals(newUserID)) {
                    unique = false;
                    break;
                }
            }
        }

        // add user type as a prefix to the number
        newUserID = type + newUserID;

        // return new ID
        return newUserID;
    }
}
