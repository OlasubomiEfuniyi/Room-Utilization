/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.utilization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Olasubomi
 */
public class DisplayResult extends JFrame implements ActionListener {
private static final String TITLE = "Room Utilization";   
 
private JTable   allDaysTable   =   null;
private JTable   weekdaysTable = null;
private JTable   weekendTable = null;
private JScrollPane absScroll = null;
private Container contentPane = getContentPane();
private JScrollPane  scrollPane = null;
private RoomUtilization ru = null;
private String[][] allDaysArray = null;
private String[][] weekdaysArray = null;
private String[][] weekendArray = null;
private String department = null;

   public DisplayResult(String dep) throws ClassNotFoundException, SQLException, FontFormatException, IOException {
      ru = ParseFile.getRoomUtilization();
      department = dep;
      display();
   }
   
   private final void display() throws ClassNotFoundException, SQLException, FontFormatException, IOException {
      createFrame();
      contentPane.setBackground(Color.WHITE);
      contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
      contentPane.add(createPanel(createScrollPane(createAllDaysTable()), "Utilization Rate Per Week For " + department, "Weekly Pie Chart", "Weekly Bar Graph"));
      contentPane.add(createPanel(createScrollPane(createWeekdaysTable()), "Utilization Rate From Monday To Friday For " + department, "Weekday Pie Chart", "Weekday Bar Graph"));
      contentPane.add(createPanel(createScrollPane(createWeekendTable()),  "Utilization Rate From Saturday To Sunday For " + department, "Weekend Pie Chart", "Weekend Bar Graph"));
      
      setVisible(true);
   }
   
   
   private final void createFrame() {
        setTitle(TITLE);
        setSize(800 , 800);
        setLocation(250,0);
        setIconImage(new ImageIcon("C:\\Users\\Olasubomi\\Documents\\NetBeansProjects\\Room Utilization\\src\\room\\utilization\\icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
   }
   
   private JPanel createPanel(JScrollPane scrollPane, String labelText, String pieButtonText, String barButtonText) throws ClassNotFoundException, SQLException, FontFormatException, IOException {
   Font font = null;
   JPanel panel = null;
   JPanel buttonPanel = null;
   TitledBorder title = null;
   Border blackLine = BorderFactory.createLineBorder(new Color(0, 153, 204));
   JButton pieChartButton = new JButton(pieButtonText);
   JButton barGraphButton = new JButton(barButtonText);
 
      font = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\Olasubomi\\Documents\\NetBeansProjects\\Room Utilization\\src\\room\\utilization\\Philosopher-Bold.ttf"));
      font = font.deriveFont(Font.BOLD, 20f);
      panel = new JPanel();
      buttonPanel = new JPanel();
      
      panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
      
      title = BorderFactory.createTitledBorder(blackLine, labelText);
      title.setTitleJustification(TitledBorder.CENTER);
      title.setTitleFont(font);
      title.setTitleColor(new Color(0, 153, 204));
     
      pieChartButton.addActionListener(this);
      pieChartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
      barGraphButton.addActionListener(this); 
      pieChartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
      
      panel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(20, 100, 0, 100), title));
      panel.setBackground(Color.WHITE);
      panel.add(scrollPane);
      buttonPanel.setBackground(Color.WHITE);
      
      buttonPanel.add(pieChartButton);
      buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
      buttonPanel.add(barGraphButton);
      panel.add(buttonPanel);
      return panel;
   }
   
   private final JTable createAllDaysTable() throws ClassNotFoundException, SQLException {
   ParseArray roomArray = null;
   String[] result = null;
     
      roomArray = ParseFile.getRoomArray();
      String[] columnNames = {"Room", "Total Time (Minutes)", "Used Time (Minutes)", "Utilization Percentage (%)"};
        
         setAllDaysArray(ru.utilization(columnNames.length));
      
   
         allDaysTable = new JTable(getAllDaysArray(), columnNames);
         DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
         };
         allDaysTable.setEnabled(false);
         allDaysTable.setRowHeight(30);
         allDaysTable.setFont(new Font("Helvatica", Font.PLAIN, 15));
         allDaysTable.setDragEnabled(false);
         return allDaysTable;
   }
   
   private final JTable createWeekdaysTable() throws SQLException, ClassNotFoundException {
   ParseArray roomArray = null;
   String[] result = null;
     
      roomArray = ParseFile.getRoomArray();
      String[] columnNames = {"Room", "Total Time Available(Minutes)", "Total Time Used(Minutes)", "Utilization Percentage(%)"};
        
      setWeekdaysArray(ru.weekdaysUtilization(columnNames.length));
      
   
         weekdaysTable = new JTable(getWeekdaysArray(), columnNames);
         DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
         };
         weekdaysTable.setEnabled(false);
         weekdaysTable.setRowHeight(30);
         weekdaysTable.setFont(new Font("Helvatica", Font.PLAIN, 15));
         weekdaysTable.setDragEnabled(false);
         return weekdaysTable;    
   }
   
   private final JTable createWeekendTable() throws SQLException, ClassNotFoundException {
   ParseArray roomArray = null;
   String[] result = null;
     
      roomArray = ParseFile.getRoomArray();
      String[] columnNames = {"Room", "Total Time Available(Minutes)", "Total Time Used(Minutes)", "Utilization Percentage(%)"};
        
      setWeekendArray(ru.weekendUtilization(columnNames.length));
      
   
         weekendTable = new JTable(getWeekendArray(), columnNames);
         DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
         };
         weekendTable.setEnabled(false);
         weekendTable.setRowHeight(30);
         weekendTable.setFont(new Font("Helvatica", Font.PLAIN, 15));
         weekendTable.setDragEnabled(false);
         return weekendTable;    
   }
   private final JScrollPane createScrollPane(JTable table) { 
       scrollPane = new JScrollPane();
       scrollPane.getViewport().add(table , new BorderLayout().CENTER);
       scrollPane.setBackground(Color.WHITE);
       scrollPane.setBorder(new EmptyBorder(15, 150, 15 , 150));
       return scrollPane;
   } 

   private void setAllDaysArray(String[][] arr) {
       allDaysArray = arr;
   }
   
   private void setWeekdaysArray(String[][] arr) {
       weekdaysArray = arr;
   }
   
   private void setWeekendArray(String[][] arr) {
       weekendArray = arr;
   }
   
   private String[][] getAllDaysArray() {
       return allDaysArray;
   }
   
   private String[][] getWeekdaysArray() {
       return weekdaysArray;
   }
   
   private String[][] getWeekendArray() {
       return weekendArray;
   }
   
    @Override
    public void actionPerformed(ActionEvent e) {
    JButton button = null;
    String buttonText = null;
       
       button = (JButton)e.getSource();
       buttonText = button.getText();
       
       switch(buttonText) {
           case "Weekly Pie Chart":
               new PieChart(buttonText, getAllDaysArray());
               break;
           case "Weekday Pie Chart":
               new PieChart(buttonText, getWeekdaysArray());
               break;
           case "Weekend Pie Chart":
               new PieChart(buttonText, getWeekendArray());
               break;
           case "Weekly Bar Graph":
               new BarGraph(buttonText, getAllDaysArray());
               break;
           case "Weekday Bar Graph":
               new BarGraph(buttonText, getWeekdaysArray());
               break;
           case "Weekend Bar Graph":
               new BarGraph(buttonText, getWeekendArray());
               break;
           
       }
       
    }
}
