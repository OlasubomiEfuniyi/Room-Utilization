/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.utilization;

import java.awt.Color;
import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

/**
 *
 * @author Olasubomi
 */
public class BarGraph extends JFrame {
private final String AXIS_LABEL = "Rooms";
private final String VALUE_AXIS_LABEL = "Utilization Percentage";

private String title = null;
private String[][] data = null;

    public BarGraph(String graphTitle, String[][] graphData) {
       super(graphTitle);
       title = graphTitle;
       data = graphData;
       
       createBarGraph();
    }
    
    private void createBarGraph() {
    Container contentPane = getContentPane();
    
        contentPane.setBackground(Color.WHITE);
        
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(500, 250);
        setIconImage(new ImageIcon("C:\\Users\\Olasubomi\\Documents\\NetBeansProjects\\Room Utilization\\src\\room\\utilization\\icon.png").getImage());
        contentPane.add(createPanel());
        setVisible(true);
    }
    
    private CategoryDataset createDataset() {
    DefaultCategoryDataset ds = new DefaultCategoryDataset();
    int len = 0;
       
       for(int x = 0; x < data.length; x++) {
           len = data[x].length;
           ds.addValue(Double.parseDouble(data[x][len-1]), data[x][0] , data[x][0]);
       }
       
       return ds;
    }
    
    private JFreeChart createGraph(CategoryDataset dataset) {
    JFreeChart graph = null;
    BarRenderer3D br = null; 
       graph = ChartFactory.createBarChart3D(title, AXIS_LABEL, VALUE_AXIS_LABEL, dataset, PlotOrientation.VERTICAL, true, true, false);
      
       return graph;
    } 
    
    private JPanel createPanel() {
    JFreeChart graph = null;
    ChartPanel panel = null;
        
       graph = createGraph(createDataset());
       panel = new ChartPanel(graph);
       panel.setBackground(Color.WHITE);
       
       return panel;
    }
}
