/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.utilization;

import java.awt.FontFormatException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import statusbar.StatusBar;

/**
 *
 * @author Olasubomi
 */
public class ParseFile {

private static DisplayResult dr = null;
private static StatusBar sb = null; 
private static Scanner   reader   =   null;
private static File inputFile =  null;
private static ParseArray textArray,timeArray = null;
private static Cis  db        = null;
private static RoomUtilization ru = null;
private static String text = null;
private static String section = "";
private static ParseArray dayArray  = null;
private static ParseArray roomArray = null;
private static double[] totalTimeArray = null;
private static double[] rateArray = null;
private static int id = 1;
private static boolean notComma = false;
private static boolean notEmpty = false;
private static boolean moreDays = false;
private static String  url      = null;
private static int timeCount = 0;
private static String fileLoc = null;
private static String department = null;
  
    public ParseFile(String loc,String dep) throws FileNotFoundException, ClassNotFoundException, SQLException {
       url = loc;
       inputFile = new File(url);
       reader = new Scanner(inputFile);        
       db = new Cis();
       
       ru = new RoomUtilization(dep);
       
       roomArray = new ParseArray(40);
       totalTimeArray = new double[40];
       rateArray = new double[40];
       
       setDepartment(dep);
    }
    
    public ParseFile(String department) throws ClassNotFoundException, SQLException {        
       db = new Cis();
       
       ru = new RoomUtilization(department);
       ru.delete();
       
       
       roomArray = new ParseArray(40);
       totalTimeArray = new double[40];
       rateArray = new double[40];
    }
    
    public static void displayResult(String dep) throws ClassNotFoundException, SQLException, FontFormatException, IOException {
       dr   =   new DisplayResult(dep);    
    }
    
    private void insertIntoDatabase() throws SQLException, ClassNotFoundException {
       if(moreDays) {
          for(int j = 0; j < dayArray.length(); j++ ) {
             if(dayArray.getText(j).equalsIgnoreCase("M") || dayArray.getText(j).equalsIgnoreCase("W") || dayArray.getText(j).equalsIgnoreCase("F")) {
                db.setDays(dayArray.getText(j)); 
             }
             else if(dayArray.getText(j).equalsIgnoreCase("T")) {
                db.setDays(dayArray.getText(j) + dayArray.getText(j + 1));
                j++;
             }
             else if(dayArray.getText(j).equalsIgnoreCase("S")) {
                db.setDays(dayArray.getText(j) + dayArray.getText(j + 1) + dayArray.getText(j + 2));
                j = j + 2;
             }                   
             section = db.getSection();
             db.setId(id++);
             ru.insert(db);                      
          }
          moreDays = false;
       }
       else {
          section = db.getSection();
          db.setId(id++);
          ru.insert(db);
       }
       
       ru.closeConnection();
    }
    
    private void cleanFile() {
       text = reader.nextLine();
       text = text.replaceAll("\t",",");
       text = text.replaceAll("-", "");
       text = text.replaceAll(" ", ",");    
    }
    
    private boolean isSection(int x) {
        return(textArray.getText(x).length() == 4 && x==0 && (textArray.getText(x).startsWith("0") || textArray.getText(x).startsWith("1") || textArray.getText(x).startsWith("2") || textArray.getText(x).startsWith("3") ));
    }
    
    private boolean isSection(String text) {
        return(text.length() == 4 && (text.startsWith("0") || text.startsWith("1") || text.startsWith("2") || text.startsWith("3") ));
    }
    
    private boolean isOneDay(int x) {
       return (textArray.getText(x).equalsIgnoreCase("M") || textArray.getText(x).equalsIgnoreCase("Tu") || textArray.getText(x).equalsIgnoreCase("W") || textArray.getText(x).equalsIgnoreCase("Th") || textArray.getText(x).equalsIgnoreCase("F") || textArray.getText(x).equalsIgnoreCase("Sat") || textArray.getText(x).equalsIgnoreCase("Sun"));
    }
    
    private boolean isComboDays(int x) {
        return (textArray.getText(x).equalsIgnoreCase("MW") || textArray.getText(x).equalsIgnoreCase("MTh") || textArray.getText(x).equalsIgnoreCase("TuTh") || textArray.getText(x).equalsIgnoreCase("TuF") || textArray.getText(x).equalsIgnoreCase("SatSun") || textArray.getText(x).equalsIgnoreCase("MTuTh") || textArray.getText(x).equalsIgnoreCase("TuThF") || textArray.getText(x).equalsIgnoreCase("MWTh"));
    }
    
    private boolean mightBeDay(int x) {
       return (textArray.getText(x).length() <= 5 && (x == 1 || x == 0));   
    }
    
    private boolean isRoom(int x) {
       return ((textArray.getText(x).length() >= 4 && textArray.getText(x).length() <= 7) && (x == 4 || x == 1 || x == 3) && ((textArray.getText(x).contains("FH") || textArray.getText(x).contains("MUM") || textArray.getText(x).contains("M") || textArray.getText(x).contains("OL"))) && !(textArray.getText(x).contains(":")) && (textArray.getText(x).contains("0")|| textArray.getText(x).contains("1") || textArray.getText(x).contains("2") || textArray.getText(x).contains("3") || textArray.getText(x).contains("4") || textArray.getText(x).contains("5") || textArray.getText(x).contains("6") ||textArray.getText(x).contains("7") || textArray.getText(x).contains("8") || textArray.getText(x).contains("9")));
    }
    
    private boolean isTime(int x) {
       return (x == 1 || x == 2) && textArray.getText(x).contains(":") && !(textArray.getText(x-1).contains(":")) && (textArray.getText(x).contains("PM") || textArray.getText(x).contains("AM"));
    }
    
    private void notComma() {
       for(int x = 0; x < text.length(); x++) {
          if(text.charAt(x) != ',') {
             notComma = true;
             break;
          }
          else {
             notComma = false;
          }
       }        
    }
    
    private void notEmpty() {
    textArray = new ParseArray(text.split(""));
    textArray.delete(" ");
        if(textArray.getNElems() == 0) {
           notEmpty = false;
        }
        else {
           notEmpty = true;
        }
    }
    
    private boolean noDays() {
       return (db.getDays() != null && db.getDays().equals("-"));
    }
    
    private void processStartTime(int x) {
    String time = "";
    int intTime = 0;
       timeArray = new ParseArray(textArray.getText(x).split(""));
       if(textArray.getText(x).contains("PM")) {
           intTime = Integer.parseInt(timeArray.getText(0) + timeArray.getText(1));
           
           if(intTime != 12) {
              intTime += 12;
           }
           
           time = Integer.toString(intTime);
           timeArray.delete("M");
           timeArray.delete("P");
           
           for(int i = 2; i < timeArray.getNElems(); i++) {
             time += timeArray.getText(i);
           }
         
       }
       else {
          timeArray.delete("M");
          timeArray.delete("A");

          for(int i = 0; i < timeArray.getNElems(); i++) {
             time += timeArray.getText(i);
           }
       }
                       
       db.setStartTime(time + ":00"); 
    }
    
    private void processEndTime(int x) {
    String time = "";
    int intTime = 0;
       timeArray = new ParseArray(textArray.getText(x + 1).split(""));
       
       if(textArray.getText(x + 1).contains("PM")) {
           intTime = Integer.parseInt(timeArray.getText(0) + timeArray.getText(1));
           
           if(intTime != 12) {
              intTime += 12;
           }
           
           time = Integer.toString(intTime);
           timeArray.delete("M");
           timeArray.delete("P");
           
           for(int i = 2; i < timeArray.getNElems(); i++) {
             time += timeArray.getText(i);
           }
       }
       else {
          timeArray.delete("M");
          timeArray.delete("A");
          for(int i = 0; i < timeArray.getNElems(); i++) {
             time += timeArray.getText(i);
          }
       }

       db.setEndTime(time + ":00");
    }
            
    private void splitLine() {
        textArray = new ParseArray(text.split(","));
        textArray.delete(" ");
        textArray.delete("");
        //textArray.displayContents();
    }
    
    
    private void setUpInsertion() {
    String room = "";
    ParseArray rArray = null;
       for(int x = 0; x < textArray.length(); x++) {
          if(isSection(x)) {
            db.setSection(textArray.getText(x));
          }

          else if(mightBeDay(x)) {
                if(isOneDay(x)) {
                    db.setDays(textArray.getText(x));
                }
                else if(isComboDays(x)) {
                    dayArray = new ParseArray(textArray.getText(x).split(""));
                    moreDays = true;
                }
                else{
                    db.setDays("-");
                }
          }
          else if(isRoom(x)) {
               db.setRoom(textArray.getText(x));
               roomArray.insert(textArray.getText(x));
          }

          if(isTime(x)) {
             processStartTime(x);             
             processEndTime(x);
             x++;
          }
          if(noDays()) {
            db.setRoom("-");
            db.setStartTime("00:00:00");
            db.setEndTime("00:00:00"); 
          }

        }       
    }
    
    public static ParseArray getRoomArray() {
       return roomArray;    
    }
    
    public static String getFileLoc() {
       return fileLoc;
    }
    

    public static void setFileLoc(String loc) throws FileNotFoundException {
        fileLoc = loc;
        inputFile = new File(fileLoc);
        reader = new Scanner(inputFile);
    }
    
    public void readFile() throws SQLException, ClassNotFoundException, ParseException {
    ParseArray checkArray = null;
   
       while(reader.hasNext()) {
            cleanFile();
            notComma();
            notEmpty();
            
            checkArray = new ParseArray(text.split(","));
            if(notComma && notEmpty) {
               if(isSection(checkArray.getText(0)) || checkArray.getText(0).length() == 0) {
                  System.out.println(checkArray.getText(0));
                  splitLine();
                  setUpInsertion();
                  insertIntoDatabase();
               }  
            }
       }
      
    }
    
    public static void createStatusBar() throws FontFormatException, IOException {
         sb = new StatusBar();
         sb.setVisible(false);
    }
    
    public static void closeStatusBar() {
        sb.close();
    }
    
    public static RoomUtilization getRoomUtilization() {
        return ru;
    }
    
    public static void sbVisible(boolean isVisible) {
        sb.setVisible(isVisible);
    } 
    
    public static void setDepartment(String dep) {
        department = dep;
    }
    
    public static String getDepartment() {
        return department;
    }

}
