import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/** 
 * Abstract class of methods to operate reading and writing data to and from CSV file.
 * @author Igor Kochanski
 * @version 2
 */
public abstract class CSV {
    
    /** 
     * ArrayList<> of String[] rows, each element of String[] is different column.
     */
    protected ArrayList<String[]> dataArray;
    /** 
     * Path of CSV file.
     */
    protected String pathOfCSV;

    /** 
     * Constructor.
     * @param pathOfCSV path of CSV file
     */
    protected CSV(String pathOfCSV) {
        this.pathOfCSV = pathOfCSV;
    }

    /** 
     * Method to read a CSV file as ArrayList<String[]> into dataArray field.
     */
    protected void readCSV() {
        dataArray = new ArrayList<String[]>();
        try {
            FileReader fileReader = new FileReader(pathOfCSV);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

            String lineAsString = bufferedReader.readLine();
			while(lineAsString != null)
			{
				String[] lineAsArray = lineAsString.split(",");
                checkForEmpty(lineAsArray);
				dataArray.add(lineAsArray);
				lineAsString = bufferedReader.readLine();
			}
            bufferedReader.close();
        } 
        catch (Exception e) {
            System.out.println(e);
        }
    }

    
    /** 
     * Method to get data of CSV file data.
     * @return ArrayList<String[]> of rows where each String[] is a row
     */
    protected ArrayList<String[]> getData() {
        return dataArray;
    }

    /** 
     * Method to add a row to current data set and save it to the CSV file
     */
    protected void addRow(String[] newRow) {
        try {
            if (newRow.length != dataArray.get(0).length) {
                throw new Exception("Column mismatch between new and previous rows.");
            }
            checkForEmpty(newRow);
            
            FileWriter fileWriter = new FileWriter(pathOfCSV, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (int i = 0; i < newRow.length; i++) {
                bufferedWriter.write(newRow[i]);
                if (i < newRow.length - 1) {
                    bufferedWriter.write(",");
                }
            }
            bufferedWriter.write("\n");
            bufferedWriter.close();
            
            dataArray.add(newRow);
        } 
        catch (Exception e) {
            System.out.println(e);
        }

    }

    /** 
     * Method to print current data held.
     */
    protected void printData() {
        for (int i = 0; i < dataArray.size(); i++) {
            String[] row = dataArray.get(i);
            for (int j = 0; j < dataArray.get(0).length; j++) {
                System.out.print(row[j] + " | ");
            }
            System.out.println();
        }
    }

    private String[] checkForEmpty(String[] s) {
        for (String x : s) {
            if (x.length() == 0)
                x = "null";
        }
        return s;
    }
}
