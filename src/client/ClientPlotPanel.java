package client;

import com.sun.javafx.charts.Legend;
import javafx.scene.chart.CategoryAxis;
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
 * @author team02
 * @see javax.swing.JPanel
 */
public class ClientPlotPanel {

    private ChartPanel chartPanel;
    private DefaultCategoryDataset dataset;

    ClientPlotPanel() {
        dataset = new DefaultCategoryDataset();

        JFreeChart lineChart = ChartFactory.createLineChart(null, null, null, 
                dataset, PlotOrientation.VERTICAL, true,
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
        org.jfree.chart.axis.CategoryAxis domainAxis = categoryPlot.getDomainAxis();
        domainAxis.setVisible(false);
        org.jfree.chart.axis.ValueAxis rangeAxis = categoryPlot.getRangeAxis();
        rangeAxis.setVisible(false);

        chartPanel = new ChartPanel(lineChart);
    }

    /**
     * Every time we get data from server, should call this method to add channel data inside dataset.
     *
     * @param Date date: time when client recieved data.
     * @param channelNumber: channel number of current data.
     * @param value: data value
     * @see Date
     */
    public void addData(Date date, int channelNumber, int value) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        dataset.addValue(value, "Ch " + channelNumber, sdf.format(date));
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}
