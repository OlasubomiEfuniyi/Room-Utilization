/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.utilization;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Olasubomi
 */
public class RoomUtilization {
public static final double SECS_PER_WEEK = 370800;
public static final double SECS_PER_WEEKDAYS = 262800;
public static final double SECS_PER_WEEKENDS = 108000;

private static String query = null;
private static Connection con = null;  
private ParseArray rooms = null;
private String department = null;
       
     /**
     *
     * @param query The instruction to be executed
     * @param con The database connection
     * @param db  The database class
     * @throws SQLException
     */

    public RoomUtilization(String dep) throws ClassNotFoundException, SQLException {
        connect();
        delete();
        
        department = dep;
    }
    public void insert(Cis db) throws SQLException, ClassNotFoundException {
        connect();
        
        setQuery("insert into cis (Section , Days , Room, StartTime, EndTime, ID) values (?, ?, ?, ?, ?, ?)");
        Calendar calendar = Calendar.getInstance();
        Date startDate = new Date(calendar.getTime().getTime());
        PreparedStatement preparedStatement = con.prepareStatement(getQuery());
        preparedStatement.setString(1, db.getSection());
        preparedStatement.setString(2, db.getDays());
        preparedStatement.setString(3, db.getRoom());
        preparedStatement.setString(4, db.getStartTime());
        preparedStatement.setString(5, db.getEndTime());
        preparedStatement.setString(6, Integer.toString(db.getId()));
        

        preparedStatement.execute();
        closeConnection();
    }
    
    public static void connect() throws ClassNotFoundException {
       Class.forName("com.mysql.jdbc.Driver");
       String user = "root";
       String password = "Subomi1999";
               
       try { 
          con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classes?user=root?autoReconnect=true&useSSL=false", user, password);
       }catch(Exception e) {
           System.out.println("Could not connect: " + e.getMessage());
       }
    }
    
    public void delete() throws ClassNotFoundException, SQLException {
       setQuery("DELETE FROM cis");
       
       Statement stmt = con.prepareStatement(getQuery());
       
       stmt.executeUpdate(getQuery());
       
    }
    
    public String[][] utilization(int columns) throws ClassNotFoundException, SQLException {
    ResultSet result = null;
    String[][] res = null;
    Calendar calendar = null;
    Date startDate = null;
    Statement stmt = null;
      /*if(department.equals("Computer Information Systems")) {
           rooms = getRooms(department);
        }
        else {
            rooms = getRooms();
        }*/
       rooms = getRooms(department);
       res = new String[rooms.getNElems()][columns];
       for(int x = 0; x < rooms.getNElems(); x++) {
            setQuery("SELECT Room,'" + SECS_PER_WEEK + "' AS Total_Time_Available, \n" +
                     "SUM(TIME_TO_SEC(TIMEDIFF(EndTime, StartTime))) AS Total_Time, \n" +
                     "SUM((TIME_TO_SEC(TIMEDIFF(EndTime, StartTime)))) /" + SECS_PER_WEEK + "* 100 AS P_Utilization\n" +
                     "FROM cis \n" +
                     "WHERE Room in ('" + rooms.getText(x) + "')");

            calendar = Calendar.getInstance();
            startDate = new Date(calendar.getTime().getTime());

            stmt = con.prepareStatement(getQuery());

            result = stmt.executeQuery(getQuery());

            while(result.next()) {
               res[x][0] = result.getString("Room");
               res[x][1] = Long.toString(Math.round(result.getLong("Total_Time_Available") / 60.0));
               res[x][2] = Long.toString(Math.round(result.getLong("Total_Time") / 60.0));
               res[x][3] = Double.toString(Math.round(result.getDouble("P_Utilization"))); 
            }
    } 
       return res;
    }
    
    public String[][] weekdaysUtilization(int columns) throws SQLException, ClassNotFoundException {
    ResultSet result = null;
    String[][] res = null;
    Calendar calendar = null;
    Date startDate = null;
    Statement stmt = null; 
       
       /*if(department.equals("Computer Information Systems")) {
           rooms = getRooms(department);
        }
        else {
            rooms = getRooms();
        }*/
       rooms = getRooms(department);
       res = new String[rooms.getNElems()][columns];
     
       for(int x = 0; x < rooms.getNElems(); x++) {
          setQuery("SELECT Room,'" + SECS_PER_WEEKDAYS + "' AS Total_Time_Available, \n" +
                     "SUM(TIME_TO_SEC(TIMEDIFF(EndTime, StartTime))) AS Total_Time, \n" +
                     "SUM((TIME_TO_SEC(TIMEDIFF(EndTime, StartTime)))) /" + SECS_PER_WEEKDAYS + "* 100 AS P_Utilization\n" +
                     "FROM cis \n" +
                     "WHERE Days IN ('M', 'Tu', 'W', 'Th', 'F')" + "AND Room IN ('" + rooms.getText(x) + "')"); 
          calendar = Calendar.getInstance();
          startDate = new Date(calendar.getTime().getTime());

          stmt = con.prepareStatement(getQuery());

          result = stmt.executeQuery(getQuery());

          while(result.next()) {
            res[x][0] = result.getString("Room");
            if(res[x][0] == null) {
                res[x][0] = rooms.getText(x);
            }
            res[x][1] = Long.toString(Math.round(result.getLong("Total_Time_Available") / 60.0));
            res[x][2] = Long.toString(Math.round(result.getLong("Total_Time") / 60.0));
            res[x][3] = Double.toString(Math.round(result.getDouble("P_Utilization"))); 
          }
       }
      return res;  
    }
    
    public String[][] weekendUtilization(int columns) throws SQLException, ClassNotFoundException {
    ResultSet result = null;
    String[][] res = null;
    Calendar calendar = null;
    Date startDate = null;
    Statement stmt = null;
       
      /* if(department.equals("Computer Information Systems")) {
           rooms = getRooms(department);
        }
        else {
            rooms = getRooms();
        }*/
       rooms = getRooms(department);
       res = new String[rooms.getNElems()][columns];
       
       for(int x = 0; x < rooms.getNElems(); x++) {
          setQuery("SELECT Room,'" + SECS_PER_WEEKENDS + "' AS Total_Time_Available, \n" +
                     "SUM(TIME_TO_SEC(TIMEDIFF(EndTime, StartTime))) AS Total_Time, \n" +
                     "SUM((TIME_TO_SEC(TIMEDIFF(EndTime, StartTime)))) /" + SECS_PER_WEEKENDS + "* 100 AS P_Utilization\n" +
                     "FROM cis \n" +
                     "WHERE Days IN ('Sat','Sun')" + "AND Room IN ('" + rooms.getText(x) + "')"); 
          calendar = Calendar.getInstance();
          startDate = new Date(calendar.getTime().getTime());

          stmt = con.prepareStatement(getQuery());

          result = stmt.executeQuery(getQuery());

          while(result.next()) {
            res[x][0] = result.getString("Room");
            if(res[x][0] == null) {
                res[x][0] = rooms.getText(x);
            }
            res[x][1] = Long.toString(Math.round(result.getLong("Total_Time_Available") / 60.0));
            res[x][2] = Long.toString(Math.round(result.getLong("Total_Time") / 60.0));
            res[x][3] = Double.toString(Math.round(result.getDouble("P_Utilization"))); 
          }
       }
      return res;    
    }
    
    public ParseArray getRooms(String dep) throws ClassNotFoundException, SQLException {
    ResultSet result = null;
    ParseArray rooms = new ParseArray(80);
    Statement stmt = null;
       setQuery("SELECT Room FROM Rooms WHERE Department = '" + dep + "'");
       
       Calendar calendar = Calendar.getInstance();
       Date startDate = new Date(calendar.getTime().getTime());
       
       stmt = con.prepareStatement(getQuery());
       
       result = stmt.executeQuery(getQuery());
       
       while(result.next()) {
          rooms.insert(result.getString("Room"));
          rooms.delete("-");
       }
      
       return rooms; 
    }
    
    public ParseArray getRooms() throws ClassNotFoundException, SQLException {
    ResultSet result = null;
    ParseArray rooms = new ParseArray(80);
    Statement stmt = null;
       setQuery("SELECT Room FROM cis");
       
       Calendar calendar = Calendar.getInstance();
       Date startDate = new Date(calendar.getTime().getTime());
       
       stmt = con.prepareStatement(getQuery());
       
       result = stmt.executeQuery(getQuery());
       
       while(result.next()) {
          rooms.insert(result.getString("Room"));
          rooms.delete("-");
       }
      
       return rooms; 
    }

    public String findFile(String fileName) throws SQLException {
    ResultSet result = null;
    Statement stmt = null;
    String loc = null;
    
       setQuery("SELECT Location FROM filelocations WHERE FileName = '" + fileName +"'");
       stmt = con.prepareStatement(getQuery());
       result = stmt.executeQuery(getQuery());
       
       while(result.next()) {
          loc = result.getString("Location");
       }
       
       return loc;
    }    
    
    public static void setQuery(String quer) {
       query = quer;
    }
    
    public static String getQuery() {
        return query;
    }
    
    public void closeConnection() throws SQLException {
       con.close();
    }
}