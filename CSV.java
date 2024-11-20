
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Abstract class of methods to operate reading and writing data to and from CSV file.
 *
 * @author Igor Kochanski
 * @author Ciaran Whelan
 * @version 4
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
     *
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
            // open file and reader
            FileReader fileReader = new FileReader(pathOfCSV);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            // read next line as string
            String lineAsString = bufferedReader.readLine();
            while (lineAsString != null) {
                // split string by commas into an Array
                String[] lineAsArray = lineAsString.split(",");
                // replace empty with "null"
                checkForEmpty(lineAsArray);
                // add to data array
                dataArray.add(lineAsArray);
                // move onto next line
                lineAsString = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Method to add a row to current data set and save it to the CSV file
     */
    protected void addRow(String[] newRow) {
        try {
            // check if columns matches previous ones (unless empty)
            if (dataArray.get(0).length != 0 && newRow.length != dataArray.get(0).length) {
                throw new Exception("Column mismatch between new and previous rows.");
            }
            checkForEmpty(newRow);
            // open file and writer
            FileWriter fileWriter = new FileWriter(pathOfCSV, true); // append
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            // add each value to next line
            for (int i = 0; i < newRow.length; i++) {
                bufferedWriter.write(newRow[i]);
                // check if more
                if (i < newRow.length - 1) {
                    bufferedWriter.write(",");
                }
            }
            // move onto next line
            bufferedWriter.write("\n");
            bufferedWriter.close();

            readCSV();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Method to update a row
     *
     * @param value value to find row by
     * @param column column to find value in
     * @param newRow row that will replace previous
     */
    protected void updateRow(String value, int column, String[] newRow) {
        // copy data
        ArrayList<String[]> newDataArray = getData();
        // replace row at given index
        newDataArray.set(getIndexOfRow(value, column), newRow);
        // write new data to file
        updateData(newDataArray);
    }

    /**
     * Method to replace the content of CSV file with a new data set
     *
     * @param newData data set
     */
    protected void updateData(ArrayList<String[]> newData) {
        try {
            // check if new data empty
            if (newData.size() < 2) {
                throw new Exception("Data is empty.");
            }
            // check if columns matches previous ones (unless empty)
            if (dataArray.get(0).length != 0 && newData.get(0).length != dataArray.get(0).length) {
                throw new Exception("Column mismatch between new and previous rows.");
            }
            // open file and writer
            FileWriter fileWriter = new FileWriter(pathOfCSV, false); // overwrite
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            boolean blank;
            // write each value into file
            for (int j = 0; j < newData.size(); j++) {
                blank = true;
                String[] newRow = newData.get(j);
                checkForEmpty(newRow);
                for (int i = 0; i < newRow.length; i++) {
                    // skip row if empty
                    if (!newRow[i].equals("")) {
                        bufferedWriter.write(newRow[i]);
                        if (i < newRow.length - 1) {
                            bufferedWriter.write(",");
                        }
                        blank = false;
                    }
                }
                if(!blank)
                    bufferedWriter.write("\n");
            }
            bufferedWriter.close();
            fileWriter.close();

            readCSV();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Method to clear csv file data leaving the first row
     */
    protected void clearData() {
        ArrayList<String[]> array = new ArrayList<String[]>();
        array.add(getData().get(0));
        updateData(array);
    }

    /**
     * Method to get data of CSV file data.
     *
     * @return ArrayList<String[]> of rows where each String[] is a row
     */
    protected ArrayList<String[]> getData() {
        readCSV();
        return dataArray;
    }

    /**
     * Method to print current data held.
     */
    protected void printData() {
        readCSV();
        // print everything
        for (int i = 0; i < dataArray.size(); i++) {
            String[] row = dataArray.get(i);
            for (int j = 0; j < dataArray.get(0).length; j++) {
                System.out.print(row[j] + " | ");
            }
            System.out.println();
        }
    }

    /**
     * Method to print current data set.
     *
     * @param table data set
     */
    protected void printData(ArrayList<String[]> table) {
        for (int i = 0; i < table.size(); i++) {
            String[] row = table.get(i);
            for (int j = 0; j < table.get(0).length; j++) {
                System.out.print(row[j] + " | ");
            }
            System.out.println();
        }
    }

    /**
     * Method to get row of matching first column value
     *
     * @param value value to find in column 0
     * @return row found
     */
    protected String[] getRowOf(String value) {
        return getRowOf(value, 0);
    }

    /**
     * Method to get row of matching column value
     *
     * @param value value to find
     * @param column column to search
     * @return row found
     */
    protected String[] getRowOf(String value, int column) {
        readCSV();
        for (int i = 1; i < dataArray.size(); i++) {
            String[] row = dataArray.get(i);
            if (row[column].equals(value)) {
                return row;
            }
        }
        return null;
    }

    /**
     * Method to get the index of a row
     *
     * @param value value to find
     * @param column column to search
     * @return index
     */
    protected int getIndexOfRow(String value, int column) {
        readCSV();
        for (int i = 1; i < dataArray.size(); i++) {
            String[] row = dataArray.get(i);
            if (row[column].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    private String[] checkForEmpty(String[] s) {
        for (String x : s) {
            if (x.length() == 0) {
                x = "null";
            }
        }
        return s;
    }
}
