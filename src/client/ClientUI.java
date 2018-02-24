package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * The client interface provides following functionalities
 * 1. show stats information (average, lowest, etc) of the 
 *  received data from server
 * 2. plot the data in a graph
 * 3. a console shows system output
 *
 * @author Team 2
 * @version 1.0
 */
public class ClientUI {

    /**  
     * an external user could implement this interface
     *  and set it as a listener to receive channel switch event
     */
    public interface ChannelSwitchListener {
        public void onChannelSwitch(int channel);
    }

    public static final int STATUS_SIZE = 100;
    public static final Color STATUS_OK = Color.GREEN;
    public static final Color STATUS_NOT_OK = Color.RED;
    public static final Color LIGHT_BLUE = new Color(191, 205, 219);

    private ClientReceiveStatusHandler clientHandler;
    private JFrame frame;
    private JPanel basePanel;
    private JComboBox channelSelectionBox;
    private JLabel frequencyValueText;
    private JLabel highestText;
    private JLabel lowestText;
    private JLabel averageText;
    private JLabel channelText;
    private JLabel frequencyText;
    private JLabel highestTextInput;
    private JLabel lowestValueText;
    private JLabel averageValueText;
    private JPanel panelwithbutton;
    private JPanel graphPanel;
    private ChannelSwitchListener channelListerner;
    private ClientPlotPanel clientPlotPanel;

    /**
     * @param numChannel the number of channels need to switch
     */
    public ClientUI(int numChannel) {
        createBaseFrameAndPanel();
        addAverageLabel();
        addChannelSwitch(numChannel);
        addConsole();
        addFrequencyLabel();
        addHighestLabel();
        addLowestLabel();
        addPlotGraph();
        addStartStopButton();
        frame.setVisible(true);
    }

    /**
     * @return The panel containing the graph
     */
    public ClientPlotPanel getClientPlotPanel() {
        return clientPlotPanel;
    }

    /**
     * Set the average value to be displayed
     * @param val Average value to be displayed
     */
    public void setAverageValue(int val) {
        averageValueText.setText(Integer.toString(val));
    }

    /**
     * Set the lowest value to be displayed
     *
     * @param val Lowest value to be displayed
     */
    public void setLowestValue(int val) {
        lowestValueText.setText(Integer.toString(val));
    }

    /**
     * Set the highest value to be displayed
     *
     * @param val Highest value to be displayed
     */
    public void setHighestValue(int val) {
        highestTextInput.setText(Integer.toString(val));
    }

    /**
     * Set the frequency to be displayed
     *
     * @param val frequency to be displayed
     */
    public void setFrequency(int val) {
        frequencyValueText.setText(Integer.toString(val));
    }

    /**
     * @param listener Listener to catch a change of channel numbers
     */
    public void setChannelSwitchListener(ChannelSwitchListener listener) {
        channelListerner = listener;
    }

    private void createBaseFrameAndPanel() {
        frame = new JFrame();
        frame.setTitle("Client");
        frame.setBounds(100, 100, 448, 342);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        basePanel = new JPanel();
        basePanel.setBackground(new Color(220, 220, 220));
        basePanel.setBounds(10, 30, 410, 220);
        frame.getContentPane().add(basePanel);
        basePanel.setLayout(null);
    }

    private void addAverageLabel() {
        averageText = new JLabel();
        averageValueText = new JLabel();
        averageText.setBackground(LIGHT_BLUE);
        averageText.setText("Average:");
        averageText.setBorder(BorderFactory.createEtchedBorder());
        averageText.setOpaque(true);
        averageText.setSize(new Dimension(20, 20));
        averageText.setBounds(252, 93, 74, 38);
        basePanel.add(averageText);

        averageValueText.setBackground(Color.PINK);
        averageValueText.setBorder(BorderFactory.createEtchedBorder());
        averageValueText.setOpaque(true);
        averageValueText.setBounds(328, 93, 74, 38);
        basePanel.add(averageValueText);
    }

    private void addLowestLabel() {
        lowestText = new JLabel("<html>Lowest<br> Value:</html>");
        lowestValueText = new JLabel();

        lowestText.setBorder(BorderFactory.createEtchedBorder());
        lowestText.setOpaque(true);
        lowestText.setSize(new Dimension(20, 20));
        lowestText.setBounds(252, 52, 74, 38);
        lowestText.setBackground(Color.PINK);
        basePanel.add(lowestText);

        lowestValueText.setBackground(LIGHT_BLUE);
        lowestValueText.setBorder(BorderFactory.createEtchedBorder());
        lowestValueText.setOpaque(true);
        lowestValueText.setBounds(328, 52, 74, 38);
        basePanel.add(lowestValueText);
    }

    private void addHighestLabel() {
        highestTextInput = new JLabel();
        highestText = new JLabel("<html>Highest <br>Value:</html> ");
        highestTextInput.setBackground(Color.PINK);
        highestTextInput.setBorder(BorderFactory.createEtchedBorder());
        highestTextInput.setOpaque(true);
        highestTextInput.setBounds(328, 11, 74, 38);
        basePanel.add(highestTextInput);

        highestText.setBackground(LIGHT_BLUE);
        highestText.setBorder(BorderFactory.createEtchedBorder());
        highestText.setOpaque(true);
        highestText.setBounds(252, 11, 74, 38);
        basePanel.add(highestText);
    }

    private void addFrequencyLabel() {
        frequencyText = new JLabel();
        frequencyValueText = new JLabel();

        frequencyText.setBackground(LIGHT_BLUE);
        frequencyText.setText("Frequency(Hz):");
        frequencyText.setBorder(BorderFactory.createEtchedBorder());
        frequencyText.setOpaque(true);
        frequencyText.setSize(new Dimension(20, 20));
        frequencyText.setBounds(252, 173, 74, 38);
        basePanel.add(frequencyText);

        frequencyValueText.setBackground(Color.PINK);
        frequencyValueText.setBorder(BorderFactory.createEtchedBorder());
        frequencyValueText.setOpaque(true);
        frequencyValueText.setBounds(328, 173, 74, 38);
        basePanel.add(frequencyValueText);
    }

    /**
     * @param channelNumber Number of channels selected
     */
    private void addChannelSwitch(int channelNumber) {
        channelText = new JLabel(); 
        channelText.setText("Channel:");
        channelText.setBorder(BorderFactory.createEtchedBorder());
        channelText.setOpaque(true);
        channelText.setSize(new Dimension(20, 20));
        channelText.setBackground(Color.PINK);
        channelText.setBounds(252, 133, 74, 38);
        basePanel.add(channelText);

        panelwithbutton = new JPanel();
        panelwithbutton.setBackground(LIGHT_BLUE);
        panelwithbutton.setBorder(BorderFactory.createEtchedBorder());

        channelSelectionBox = new JComboBox();
        channelSelectionBox.setBackground(LIGHT_BLUE);
        String[] strChannel = new String[channelNumber];
        for (int i = 1; i <= channelNumber; ++i) {
            strChannel[i - 1] = Integer.toString(i);
        }
        channelSelectionBox.setModel(new DefaultComboBoxModel(strChannel));
        channelSelectionBox.setOpaque(true);
        channelSelectionBox.setBounds(328, 133, 74, 38);
        channelSelectionBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onChannelSwitch();
            }
        });

        basePanel.add(channelSelectionBox);
    }

    private void addStartStopButton() {
        clientHandler = ClientReceiveStatusHandler.getInstance();
        JButton startStopButton = new JButton("Start / Stop");
        startStopButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        startStopButton.setBounds(315, 6, 107, 23);
        startStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean currentState = clientHandler.getClientReceiveStatus();
                clientHandler.setClientReceiveStatus(!currentState);
                if (clientHandler.getClientReceiveStatus()) {
                    System.out.println("Client Started Running");
                } else {
                    System.out.println("Client Stopped Running");
                }
            }
        });
        frame.getContentPane().add(startStopButton);
    }

    private void addPlotGraph() {
        graphPanel = new JPanel();
        graphPanel.setBorder(new LineBorder(SystemColor.activeCaption));
        graphPanel.setBackground(Color.pink);
        graphPanel.setBounds(10, 11, 239, 200);
        basePanel.add(graphPanel);
        clientPlotPanel = new ClientPlotPanel();
        graphPanel.setLayout(new BorderLayout(0, 0));
        graphPanel.add(clientPlotPanel.getChartPanel());
        graphPanel.validate();
    }

    private void addConsole() {
        JPanel consolePanel = new JPanel();
        consolePanel.setBorder(new LineBorder(SystemColor.activeCaption));
        consolePanel.setBackground(new Color(220, 220, 220));
        consolePanel.setBounds(10, 255, 412, 60);
        frame.getContentPane().add(consolePanel);
        consolePanel.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel(" Console:");
        lblNewLabel_1.setFont(new Font("Monospaced", Font.PLAIN, 13));
        lblNewLabel_1.setBounds(0, 0, 81, 15);
        consolePanel.add(lblNewLabel_1);

        JPanel consolePanelText = new ClientConsole();
        consolePanelText.setBounds(10, 15, 400, 60);
        consolePanel.add(consolePanelText);
    }

    private void onChannelSwitch() {
        String chosenSelection = (String) channelSelectionBox
            .getSelectedItem();
        int chosenChannel = Integer.parseInt(chosenSelection);
        if (channelListerner != null) {
            channelListerner.onChannelSwitch(chosenChannel);
        }
    }
}
