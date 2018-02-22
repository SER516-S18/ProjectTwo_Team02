

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Team 02
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class ClientInterface {
    public final int STATUS_SIZE = 100;
    public final Color STATUS_OK = Color.GREEN;
    public final Color STATUS_NOT_OK = Color.RED;
    private ClientStatsManager handler;
    private JFrame fromClient;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel HighestValueText;
    private javax.swing.JLabel FrequencyTextInput;
    private javax.swing.JLabel LowestText;
    private javax.swing.JLabel AverageText;
    private javax.swing.JLabel ChannelText;
    private javax.swing.JLabel FrequencyText;
    private javax.swing.JLabel HighestTextInput;
    private javax.swing.JLabel LowestValueInput;
    private javax.swing.JLabel AverageTextInput;
    private javax.swing.JPanel Panelwithbutton;
    private javax.swing.JPanel GraphPanel;

    /**
     * Create the application.
     */
    public ClientInterface() {
        initcomponents();
    }

    public JFrame getFromClient() {
        return fromClient;
    }

    public void initcomponents() {
        //ChartPanel chartPanel = new ClientPlotPanel().getChartPanel();
        //setContentPane(chartPanel);
       // handler = ClientStatsManager.getInstance();
        fromClient = new JFrame();
        fromClient.setTitle("Client");
        fromClient.setBounds(100, 100, 448, 342);
        fromClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fromClient.getContentPane().setLayout(null);

        Color lightblue = new Color(153, 180, 209);
        Border border = BorderFactory.createLineBorder(lightblue);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(220, 220, 220));
        //panel.setBounds(10, 41, 392, 196);
        panel.setBounds(10, 30, 410, 220);
        fromClient.getContentPane().add(panel);
        panel.setLayout(null);

        jCheckBox1 = new JCheckBox();
        Panelwithbutton = new JPanel();
        jComboBox1 = new JComboBox();
        AverageText = new JLabel();
        LowestText = new JLabel("<html>Lowest<br> Value:</html>");
        FrequencyText = new JLabel();
        FrequencyTextInput = new JLabel();
        LowestValueInput = new JLabel();
        ChannelText = new JLabel();
        AverageTextInput = new JLabel();
        HighestTextInput = new JLabel();
        HighestValueText = new JLabel("<html>Highest <br>Value:</html> ");

        GraphPanel = new JPanel();
        jButton1 = new JButton();

        jCheckBox1.setText("1");
        jCheckBox1.getAccessibleContext().setAccessibleDescription("");


        Panelwithbutton.setBackground(new Color(191, 205, 219));
        Panelwithbutton.setBorder(BorderFactory.createEtchedBorder());

        jComboBox1.setBackground(new Color(191, 205, 219));
        jComboBox1.setModel(new DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        jComboBox1.setOpaque(true);
        jComboBox1.setBounds(328,133,74,38);
        jComboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        panel.add(jComboBox1);
        HighestTextInput.setBackground(Color.PINK);
        HighestTextInput.setBorder(BorderFactory.createEtchedBorder());
        HighestTextInput.setOpaque(true);
        HighestTextInput.setBounds(328, 11, 74, 38);
        panel.add(HighestTextInput);

        HighestValueText.setBackground(new Color(191, 205, 219));
        HighestValueText.setBorder(BorderFactory.createEtchedBorder());
        HighestValueText.setOpaque(true);
        HighestValueText.setBounds(252, 11, 74, 38);
        panel.add(HighestValueText);


        LowestText.setBorder(BorderFactory.createEtchedBorder());
        LowestText.setOpaque(true);
        LowestText.setSize(new Dimension(20, 20));
        LowestText.setBounds(252, 52, 74, 38);
        LowestText.setBackground(Color.PINK);
        panel.add(LowestText);

        LowestValueInput.setBackground(new Color(191, 205, 219));
        LowestValueInput.setBorder(BorderFactory.createEtchedBorder());
        LowestValueInput.setOpaque(true);
        LowestValueInput.setBounds(328, 52, 74, 38);
        panel.add(LowestValueInput);

        AverageText.setBackground(new Color(191, 205, 219));
        AverageText.setText("Average:");
        AverageText.setBorder(BorderFactory.createEtchedBorder());
        AverageText.setOpaque(true);
        AverageText.setSize(new Dimension(20, 20));
        AverageText.setBounds(252, 93, 74, 38);
        panel.add(AverageText);

        AverageTextInput.setBackground(Color.PINK);
        AverageTextInput.setBorder(BorderFactory.createEtchedBorder());
        AverageTextInput.setOpaque(true);
        AverageTextInput.setBounds(328, 93, 74, 38);
       // AverageTextInput.setText( String.valueOf( handler.getHighestValue() ) );
        panel.add(AverageTextInput);




        ChannelText.setText("Channel:");
        ChannelText.setBorder(BorderFactory.createEtchedBorder());
        ChannelText.setOpaque(true);
        ChannelText.setSize(new Dimension(20, 20));
        ChannelText.setBackground(Color.PINK);
        ChannelText.setBounds(252,133,74,38);
        panel.add(ChannelText);

        FrequencyText.setBackground(new Color(191, 205, 219));
        FrequencyText.setText("Frequency(Hz):");
        FrequencyText.setBorder(BorderFactory.createEtchedBorder());
        FrequencyText.setOpaque(true);
        FrequencyText.setSize(new Dimension(20, 20));
        FrequencyText.setBounds(252,173,74,38);
        panel.add(FrequencyText);

        FrequencyTextInput.setBackground(Color.PINK);
        FrequencyTextInput.setBorder(BorderFactory.createEtchedBorder());
        FrequencyTextInput.setOpaque(true);
        FrequencyTextInput.setBounds(328,173,74,38);
        panel.add(FrequencyTextInput);

        GraphPanel.setBorder(new LineBorder(SystemColor.activeCaption));
        GraphPanel.setBackground(Color.pink);
        GraphPanel.setBounds(10, 11, 239, 200);
        panel.add(GraphPanel);
        //GraphPanel.add(chartPanel, BorderLayout.CENTER);
        // GraphPanel.validate();

        JPanel consolePanel = new JPanel();
        consolePanel.setBorder(new LineBorder(SystemColor.activeCaption));
        consolePanel.setBackground(new Color(220, 220, 220));
        consolePanel.setBounds(10, 255, 412, 60);
        fromClient.getContentPane().add(consolePanel);
        consolePanel.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel(" Console:");
        lblNewLabel_1.setFont(new Font("Monospaced", Font.PLAIN, 13));
        lblNewLabel_1.setBounds(0, 0, 81, 15);
        consolePanel.add(lblNewLabel_1);

        final JButton startStopButton = new JButton( "Start / Stop" );
        startStopButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        startStopButton.setBounds(315, 6, 107, 23);
        fromClient.getContentPane().add(startStopButton);





}
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed



}
