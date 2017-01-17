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
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.EmptyBorder;
import statusbar.StatusBar;

/**
 *
 * @author Olasubomi
 */
public class SelectionGUI extends JFrame implements ActionListener, MouseListener {
private static final String ICON_LOC = "C:\\Users\\Olasubomi\\Documents\\NetBeansProjects\\Room Utilization\\src\\room\\utilization\\icon.png";
private Container contentPane = null;
private JComboBox menu = null;

   public SelectionGUI() throws FontFormatException, IOException, ClassNotFoundException, SQLException {
       setupFrame();
       menu = createDropdown();
       setUpContentPane();
       setVisible(true);
   }
   
   public void close() {
      this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
   }
   
   private JComboBox createDropdown() {
   JComboBox dropd = null;
   String[] options = {" ", "Computer Information Systems", "Business", "Science", "Mathematics", "Engineering"};
      dropd = new JComboBox(options);
      dropd.setSelectedIndex(0);
      
      return dropd;
   }
   
   
   private JPanel createLogoPanel() {
   JPanel   panel = null;
   JLabel   label   = null;
   ImageIcon image  = null;
      panel = new JPanel(new BorderLayout());
      panel.setBackground(Color.WHITE);
      image = new ImageIcon("C:\\Users\\Olasubomi\\Documents\\NetBeansProjects\\Room Utilization\\src\\room\\utilization\\logo.png");
      label = new JLabel("", image , JLabel.CENTER);
      panel.add(label, BorderLayout.WEST);
      
      return panel;
   }
   
   private JComboBox getMenu() {
      return menu;
   }
   
   private void setupFrame() {
       setLocation(400 , 250);
       setDefaultCloseOperation(DISPOSE_ON_CLOSE);
       setSize(550 , 350);
       setResizable(false);
       setIconImage(new ImageIcon(ICON_LOC).getImage());
       
   }
   
   private void setUpContentPane() throws FontFormatException, IOException {
      contentPane = getContentPane();
      
      contentPane.setBackground(new Color(0, 153, 204));
      contentPane.add(setUpLabelPanel(new JLabel("Please select a department from the menu below")), BorderLayout.NORTH);
      contentPane.add(setUpDropdownPanel(getMenu()));
      contentPane.add(setupButtonPanel(), BorderLayout.SOUTH);
   }
   
   private JPanel setUpLabelPanel(JLabel label) throws FontFormatException, IOException {
   JPanel panel = null;
   Font font = null;
   
      font = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\Olasubomi\\Documents\\NetBeansProjects\\Room Utilization\\src\\room\\utilization\\Philosopher-Bold.ttf"));
      font = font.deriveFont(Font.BOLD, 20f);
      
      label.setFont(font);
      label.setForeground(new Color(0, 153, 204));
      label.setBorder(new EmptyBorder(0, 40, 0, 0));
      panel = new JPanel(); 
      panel.setLayout(new BorderLayout());
      panel.setBackground(Color.WHITE);
      panel.add(createLogoPanel(), BorderLayout.PAGE_START);
      panel.add(label, BorderLayout.CENTER);
      
      return panel;
      
   }
   
   private JPanel setUpDropdownPanel(JComboBox menu) {
   JPanel panel = null;
   JPanel outerPanel = null; 
  
      panel = new JPanel();
      outerPanel = new JPanel(new BorderLayout());
      
      panel.setBackground(Color.WHITE);
      outerPanel.setBackground(Color.WHITE);
      outerPanel.setBorder(new EmptyBorder(40, 0, 0, 0));
      
      menu.setBackground(Color.WHITE);
      menu.setCursor(new Cursor(Cursor.HAND_CURSOR));
      menu.setForeground(new Color(0, 153, 204));
      panel.add(menu); 
      
      outerPanel.add(panel, BorderLayout.PAGE_START);

      return outerPanel;
   }
   
   private JPanel setupButtonPanel() {
   JPanel panel = null;
   JButton button = null;
      
      panel = new JPanel(new BorderLayout());
      button = new JButton("Continue");
      
      panel.setBackground(Color.WHITE);
      button.setForeground(new Color(0, 153, 204));
      button.setCursor(new Cursor(Cursor.HAND_CURSOR));
      button.addActionListener(this);
      
      panel.setBorder(new EmptyBorder(0, 0, 10, 10));
      panel.add(button, BorderLayout.EAST);
      
      return panel;
   }

    @Override
    public void actionPerformed(ActionEvent e) {
    JButton button = null;
    String menuText = null;
    RoomUtilization ru = null;
        
        try {
            button = (JButton)e.getSource();
            
            menuText = (String)getMenu().getSelectedItem();
            
            if(!(menuText.equals(" "))) {
                ParseFile   parseSchedule   =   new ParseFile(menuText);

                ru = ParseFile.getRoomUtilization();
                ParseFile.setFileLoc(ru.findFile(menuText));
                close();
                RoomUtilizationProgram.load();
                parseSchedule.readFile();
                RoomUtilizationProgram.closeLoad();
                ru.connect();
                ParseFile.displayResult(menuText);
            }
            else {
                JOptionPane.showMessageDialog(null, "Please select a dpearment", "Message", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"Unable to connect to the database", "ERROR!", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Unable to locate the file or disconnect from the database", "ERROR!", JOptionPane.ERROR_MESSAGE);
        } catch (FontFormatException ex) {
            JOptionPane.showMessageDialog(null,"Unable to properly load the interface", "ERROR!", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"Unable to properly load the interface", "ERROR!", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null,"Unable to properly load the interface", "ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}