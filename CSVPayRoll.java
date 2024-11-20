import java.util.ArrayList;

public class CSVPayRoll extends CSV {

    private CSVPayScale payScale;
    private CSVUsers users;
    private final ArrayList<String[]> usersCSV = users.getData(), payScaleCSV = payScale.getData();
    private int payIndex, index;

    public CSVPayRoll(String pathOfCSV) {
        super(pathOfCSV);
        readCSV();
    }

    /** 
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
        
        String[] row = payScaleCSV.get(payIndex),
                 addRow = {userID, roleID, Integer.toString(scale), row[scale]};
 
        addRow(addRow);
    }

    /** 
     * @param userID
     */
    public void updatePayRoll(String userID){
        if(!alreadyOnRoll(userID)){
            System.out.println("User not on PayRoll");
            return;
        }
        String[] row = getData().get(index);
        String getRollID = row[1], getScale = row[2];
        int scale = Integer.parseInt(getScale), updatedScale = scale++;

        if(!roleAndScaleExists(getRollID, updatedScale)){
            System.out.println("User is already at maximum Scale Point");
            return;
        }
        String[] payRow = payScaleCSV.get(payIndex),
                 updatedRow = {userID, getRollID, Integer.toString(updatedScale), payRow[updatedScale]};
        getData().set(index, updatedRow);  
    }

    private boolean userExists(String userID){
        for(int i = 1; i < usersCSV.size(); i++){
            String[] row = usersCSV.get(i);
            if(row[0].equals(userID))
                return true;  
        }
        System.out.println("Invalid User ID");
        return false;
    }

    private boolean roleAndScaleExists(String roleID, int scale){
        for(int i = 0; i < payScaleCSV.size(); i++){
            String[] row = payScaleCSV.get(i);
            if(row[0].equals(roleID)){
                payIndex = i;
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

    private boolean alreadyOnRoll(String userID){
        for(int i = 1; i < getData().size(); i++){
            String[] row = getData().get(i);
            if(row[0].equals(userID)){
                index = i;
                System.out.println("User already on Pay Roll");
                return true;
            }
        }
        return false;
    }
}
