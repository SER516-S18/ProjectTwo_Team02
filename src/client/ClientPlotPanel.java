package client;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class ClientPlotPanel
 * Will maintain a plotting area using recieved data
 *
 * @author Team 2
 * @version 1.0
 * @see javax.swing.JPanel
 */
public class ClientPlotPanel {

    private ChartPanel chartPanel;
    private DefaultCategoryDataset dataSet;

    /**
     * Create a graph area to display data received from the server
     */
    ClientPlotPanel() {
        dataSet = new DefaultCategoryDataset();

        JFreeChart lineChart =
                ChartFactory.createLineChart(null, null, null,
                dataSet, PlotOrientation.VERTICAL, true,
                false, false);

        //Setting line chart
        CategoryPlot categoryPlot = lineChart.getCategoryPlot();
        categoryPlot.setRangeGridlinesVisible(false);
        lineChart.setBackgroundPaint(Color.BLACK);
        categoryPlot.setBackgroundPaint(Color.BLACK);
        categoryPlot.setOutlineVisible(false);

        //Setting legend
        LegendTitle legend = lineChart.getLegend();
        legend.setBackgroundPaint(Color.BLACK);
        legend.setItemPaint(Color.WHITE);
        legend.setItemFont(new Font("Tahoma", Font.PLAIN, 11));

        //Removing axis
        org.jfree.chart.axis.CategoryAxis domainAxis =
            categoryPlot.getDomainAxis();
        domainAxis.setVisible(false);
        org.jfree.chart.axis.ValueAxis rangeAxis = categoryPlot.getRangeAxis();
        rangeAxis.setVisible(false);

        chartPanel = new ChartPanel(lineChart);
    }

    /**
     * call this method to add channel data inside dataset when the 
     * client receives data
     *
     * @param date 	time when client received data.
     * @param channelNumber channel number of current data.
     * @param value			data value
     * @see Date
     */
    public void addData(Date date, int channelNumber, int value) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        dataSet.addValue(value, "Ch " + channelNumber, sdf.format(date));
    }

    /**
     * @return A panel containing the chart of data from server
     */
    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}
