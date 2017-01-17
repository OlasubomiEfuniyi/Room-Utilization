/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.utilization;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Olasubomi
 */
public class PieChart extends JFrame {
String[][] data = null; 
String chartTitle = null;

    public PieChart(String title, String[][] d) {
        super(title);
        chartTitle = title;
        data = d;
        
        createPieChart();
    }
    
    private final void createPieChart() {
    Container contentPane = getContentPane();
       contentPane.setBackground(Color.WHITE);
       setSize(700, 500);
       setLocation(500,250);
       setIconImage(new ImageIcon("C:\\Users\\Olasubomi\\Documents\\NetBeansProjects\\Room Utilization\\src\\room\\utilization\\icon.png").getImage());
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       contentPane.add(createPanel());
       setVisible(true);
    }
    
    private PieDataset createDataset() {
    DefaultPieDataset ds = new DefaultPieDataset();
    int len = 0;
        for(int x = 0; x < data.length; x++) {
            len = data[x].length;
            ds.setValue(data[x][0], Double.parseDouble(data[x][len-2]));
        }
        
        return ds;
    }
    
    private JFreeChart createChart(PieDataset dataset) {
    JFreeChart chart = null;
    PieSectionLabelGenerator pl = null;   
    PiePlot3D plot = null;
       chart = ChartFactory.createPieChart3D(chartTitle, dataset, true, true, false);
       pl = new StandardPieSectionLabelGenerator("{0} = {2}");
       plot = (PiePlot3D) chart.getPlot();
       
       plot.setLabelGenerator(pl);
       plot.setLabelFont(new Font("Helvatica", Font.BOLD, 10));
       return chart;
    }
    
    private JPanel createPanel() {
    JFreeChart chart = null;
    ChartPanel panel = null;
       chart = createChart(createDataset());
       panel = new ChartPanel(chart);
       panel.setBackground(Color.WHITE);
       return panel;
    }
    
}
