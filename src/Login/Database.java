package Login;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Database {

    private String filename;
    private int rowWidth;
    private int recordCount;
    ArrayList<Integer> deadRecords = new ArrayList<Integer>();

    public Database(String filename, int rowWidth) {
        this.filename = filename;
        this.rowWidth = rowWidth;
        recordCount = getRecordCount();
    }

    public void appendRecord(String data) {
        while (data.length() < rowWidth){
            data += " ";
        }
        FileHandler.appendLine(filename, data);
        recordCount++;


    }


    public boolean validifyPassword(int start, String password){
        //validification for an extra layer of security
        return getRecord(start).substring(32, 63).trim().equals(password.trim());
    }

    public double getBalance(int start){
        return Double.parseDouble(getRecord(start).substring(64, 96).trim());

    }

    public void signup(String username, String password, double balance){
        String data = username;
        while(data.length() < rowWidth/3){
            data += " ";
        }
        data += password;
        while(data.length() < (rowWidth/3)*2){
            data += " ";
        }
        data += balance;
        while(data.length() < rowWidth){
            data += " ";
        }
        FileHandler.appendLine(filename, data);
        recordCount++;
    }



    public void sortArrLi(ArrayList<Integer> arr){

        int sortedCount = 1;
        boolean sorted = true;
        while (!sorted){
            for(int i=0;i<arr.size()-sortedCount;i++){
                if (arr.get(i)>arr.get(i+1)){
                    int temp = arr.get(i);
                    arr.set(i, arr.get(i+1));
                    arr.set(i+1, temp);
                    sorted = false;
                }
            }
            sortedCount++;
        }
    }

    public void deleteRecord(int rowNumber) {
        //replaces row with X's
        String data = "";
        while (data.length() < rowWidth){
            data += "X";
        }
        FileHandler.writeLineAt(filename, data, (rowNumber-1)*rowWidth);
        deadRecords.add(rowNumber);
    }

    public int findUsername(String username) {
        // search for a record matching data
        // return true if found
        for(int i = 0; i<getRecordCount();i++){
            if (getRecord(i).substring(0,31).trim().equals(username.trim())){
                return i;
            }
        }
        return -1;
    }


    public String getRecord(int rowNumber) {
        //rowWidth+2 because of the invisible linebreak character
        return FileHandler.readLineAt(filename, rowNumber * (rowWidth+2));
    }

    public int getRecordCount() {
        return FileHandler.countLines(filename);
    }

    public boolean findRecord(String data) {
        // search for a record matching data
        // return true if found
        for(int i = 0; i<getRecordCount();i++){
            //System.out.println(i);
            //System.out.println(getRecord(i));
            if (getRecord(i).equals(data)){
                return true;
            }
        }
        return false;
    }

}



