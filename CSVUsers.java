import java.util.ArrayList;
/** 
 * Class to operate Users.csv file data, extends CSV class
 * @author Igor Kochanski
 * @version 1
 */
public class CSVUsers extends CSV {

    /** 
     * Constructor.
     * @param pathOfCSV path of Users.CSV
     */
    public CSVUsers (String pathOfCSV) {
        super(pathOfCSV);
        readCSV();
    }

    /**
     * Method to check if the provided details are a valid login
     * @param username username to check
     * @param password password to check
     * @return String of User ID if valid, else return null
     */
    public String checkLogin(String username, String password) {
        readCSV();
        for (int i = 1; i < dataArray.size(); i++) {
           String[] row = dataArray.get(i);
           if (row[1].equals(username) && row[2].equals(password)) {
                return row[0];
           }
        }
        return null;
    }

    /**
     * Method to check if username is not currently in use.
     * @param username
     * @return Boolean return false if username already in use, else true.
     */
    public boolean checkUsernameAvailable(String username) {
        readCSV();
        if (getRowOf(username, 1) == null)
            return true;
        return false;
    }

    /**
     * Method to creat Admin user.
     * @param username new username
     * @param password new passowrd
     * @return String new User ID
     */
    public String createAdmin(String username, String password) {
        return createUser("A", username, password);
    }

    /**
     * Method to creat Employee user.
     * @param username new username
     * @param password new passowrd
     * @return String new User ID
     */
    public String createEmployee(String username, String password) {
        return createUser("E", username, password);
    }

    /**
     * Method to creat Human Resources user.
     * @param username new username
     * @param password new passowrd
     * @return String new User ID
     */
    public String createHumanResources(String username, String password) {
        return createUser("H", username, password);
    }

    private String createUser(String type, String username, String password) {
        String newUserID = generateID(getData(), type);
        String[] addRow = {newUserID, username, password};
        addRow(addRow);
        return newUserID;
    }

    private String generateID(ArrayList<String[]> Users, String type) {
        int numID;
        String newUserID = null;
        boolean unique = false;
        while (!unique) {
            unique = true; // assume then prove wrong
            numID = (int)(Math.random() * 9999999);
            newUserID = String.format("%07d", numID);
            for (int i = 1; i < Users.size(); i++) {
                String[] row = Users.get(i);
                String oldUserID = row[0].substring(1);
                if (oldUserID.equals(newUserID)) {
                    unique = false;
                    break;
                }
            }
        }
        newUserID = type + newUserID;
        return newUserID;
    }
}
