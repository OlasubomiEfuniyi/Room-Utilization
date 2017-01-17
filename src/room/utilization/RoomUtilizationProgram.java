/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.utilization;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JOptionPane;
import statusbar.StatusBar;

/**
 *
 * @author Olasubomi
 */
public class RoomUtilizationProgram {  
    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) throws SQLException, FontFormatException, IOException, ClassNotFoundException, ParseException {
    String    url   =   null;
    SelectionGUI selection = null;
    
        try {
            selection = new SelectionGUI();
            
         }catch(FileNotFoundException e) {
             JOptionPane.showMessageDialog(null,"Could not find the specified file.", "ERROR!", JOptionPane.ERROR_MESSAGE);
             System.exit(0);
         } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Could not insert into the database.", "ERROR!", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
         } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Problem connecting to the database.", "ERROR!", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
         }catch(Exception e) {
            JOptionPane.showMessageDialog(null,"An unknown error has occured.", "ERROR!", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
         }
    }
    
    public static void load() throws FontFormatException, IOException {
      ParseFile.createStatusBar();
      ParseFile.sbVisible(true);  
    }
    
    public static void closeLoad() {
        ParseFile.closeStatusBar();
    }
}
