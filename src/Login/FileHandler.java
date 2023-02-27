package Login;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;


public class FileHandler {

    public static void appendLine(String fileName, String data) {
        // write text to end of the file
        boolean append = true;
        try (PrintWriter pr = new PrintWriter(new FileWriter(fileName, append))) {
            pr.println(data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readLineAt(String fileName, int start) {
        // grab the line from position "start" in the file
        try (RandomAccessFile rf = new RandomAccessFile(fileName, "rws")) {
            rf.seek(start);
            return rf.readLine().trim();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readFirst32(String filename, int start){
        try (RandomAccessFile rf = new RandomAccessFile(filename, "rws")) {
            rf.seek(start);
            String string = "";
            for(int i = 0; i<32;i++){
                string += rf.readChar();
            }
            System.out.println(string);
            return string;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readLast32(String filename, int start){
        try (RandomAccessFile rf = new RandomAccessFile(filename, "rws")) {
            rf.seek(start+32);
            String string = "";
            for(int i = 0; i<32;i++){
                string += rf.readChar();
            }
            return string;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void writeLineAt(String fileName, String data, int start) {
        // overwrite a line from position "start" in the file
        // doesn't check that the start position is actually
        // the beginning of the file. This will not behave well
        // unless every line is the same length
        try (RandomAccessFile rf = new RandomAccessFile(fileName, "rws")) {
            rf.seek(start);
            rf.writeBytes(data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int countLines(String fileName) {
        // return the number of lines in the file
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while (line != null) {
                count++;
                line = br.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}



