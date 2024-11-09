import java.util.ArrayList;

public class PayRollCSV extends CSV {

    private PayScaleCSV payScale;
    private UsersCSV users;
    private ArrayList<String[]> usersCSV = users.getData(),
            payScaleCSV = payScale.getData();
    private int index;

    public PayRollCSV(String pathOfCSV) {
        super(pathOfCSV);
        readCSV();
    }

    
    /** 
     * Method to add a user to the PayRoll
     * @param userID
     * @param roleID
     * @param scale
     */
    public void addToPayRoll(String userID, String roleID, int scale){        
        if(!userExists(userID))
            return;
        if(!roleAndScaleExists(roleID, scale))
            return;
        if(alreadyOnRoll(userID))
            return;
        
        String[] row = payScaleCSV.get(index),
                 addRow = {userID, roleID, Integer.toString(scale), row[scale]};
 
        addRow(addRow);
    }

    public boolean userExists(String userID){
        for(int i = 1; i < usersCSV.size(); i++){
            String[] row = usersCSV.get(i);
            if(row[0].equals(userID))
                return true;  
        }
        System.out.println("Invalid User ID");
        return false;
    }

    public boolean roleAndScaleExists(String roleID, int scale){
        for(int i = 0; i < payScaleCSV.size(); i++){
            String[] row = payScaleCSV.get(i);
            if(row[0].equals(roleID)){
                index = i;
                if(scale < row.length && !row[scale].equals("null")){
                    return true;         
                } else {
                    System.out.println("Invalid Scale Point.");
                    return false;
                }
            }
        }
        System.out.println("Invalid Roll ID.");
        return false;
    }

    public boolean alreadyOnRoll(String userID){
        for(int i = 1; i < getData().size(); i++){
            String[] row = getData().get(i);
            if(row[0].equals(userID)){
                System.out.println("User already on Pay Roll");
                return true;
            }
        }
        return false;
    }
}