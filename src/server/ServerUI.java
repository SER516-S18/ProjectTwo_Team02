package server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;

/**
 * Server side UI
 *
 * @author Team2
 * @version 1.0
 */

public class ServerUI {

    public final int STATUS_SIZE = 100;
    public final Color STATUS_OK = Color.GREEN;
    public final Color STATUS_NOT_OK = Color.RED;
    public final String FREQ_SECONDS_OPTION = "/second";
    public final String FREQ_MINUTES_OPTION = "/minute";
    public final String FREQ_HOURS_OPTION = "/hour";

    private JFrame serverFrame;
    private JTextField freqTextField;
	private JTextField lowTextField;
	private JTextField highTextField;
	private ServerHandler handler;
	private JPanel statusPanel;
	private JComboBox freqCombo;
	private Color statusColor = STATUS_OK;

    /**
	 * Create the server UI
	 */
	public ServerUI() {
		initialize();
	}

    /**
     * @return The server JFrame containing the server UI
     */
    public JFrame getserverFrame() {
        return serverFrame;
    }

    /**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	    handler = ServerHandler.getInstance();
		serverFrame = new JFrame();
		serverFrame.setTitle("Server");
		serverFrame.setBounds(100, 100, 448, 342);
		serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		serverFrame.getContentPane().setLayout(null);

		JPanel parentPanel = new JPanel();
		parentPanel.setBackground(new Color(220, 220, 220));
		parentPanel.setBounds(10, 41, 412, 196);
		serverFrame.getContentPane().add(parentPanel);
		parentPanel.setLayout(null);

        statusPanel = new JPanel(){
			@Override
            public void paintComponent( Graphics g ){
                super.paintComponent( g );
                Graphics2D g2d = (Graphics2D)g;
                Ellipse2D.Double statusCircle =
                        new Ellipse2D.Double(
                                ( statusPanel.getWidth() / 2 ) -
                                        ( STATUS_SIZE / 2 ),
                                ( statusPanel.getHeight() / 2 ) -
                                        ( STATUS_SIZE / 2 ),
                                STATUS_SIZE, STATUS_SIZE );
                g2d.setColor( statusColor );
                g2d.fill( statusCircle );
            }
        };
        statusPanel.setBorder(new LineBorder(SystemColor.activeCaption));
        statusPanel.setBackground(Color.PINK);
        statusPanel.setBounds(10, 11, 232, 163);
        parentPanel.add(statusPanel);
		
		freqTextField = new JTextField();
		freqTextField.setFont(new Font("Courier New", Font.PLAIN, 13));
		freqTextField.setBackground(Color.PINK);
		freqTextField.setBounds(328, 92, 74, 38);
		freqTextField.setColumns(10);
		freqTextField.addActionListener( textBoxAction() );
		freqTextField.setText( String.valueOf( handler.getFrequency() ) );
		parentPanel.add(freqTextField);

		String[] comboOptions = {
                FREQ_SECONDS_OPTION,
                FREQ_MINUTES_OPTION,
                FREQ_HOURS_OPTION
        };

		freqCombo = new JComboBox(comboOptions);
		freqCombo.setSelectedIndex( 0 );
        freqCombo.setBounds( 328, 135, 74, 20 );
        freqCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               JComboBox cb = (JComboBox) actionEvent.getSource();
               String option = (String) cb.getSelectedItem();
               if( option != null ) {
                   if (option.equals(FREQ_SECONDS_OPTION)) {
                       handler.setFreqInterval(ServerHandler.FREQ_SECONDS);
                   } else if (option.equals(FREQ_MINUTES_OPTION)) {
                       handler.setFreqInterval(ServerHandler.FREQ_MINUTES);
                   } else if (option.equals(FREQ_HOURS_OPTION)) {
                       handler.setFreqInterval(ServerHandler.FREQ_HOURS);
                   }
               }
            }
        });
        freqCombo.setFont(new Font("Courier New", Font.PLAIN, 11));
        parentPanel.add( freqCombo );


        lowTextField = new JTextField();
        lowTextField.setFont(new Font("Courier New", Font.PLAIN, 13));
		lowTextField.setBackground(SystemColor.controlHighlight);
		lowTextField.setBounds(328, 52, 74, 38);
		lowTextField.setColumns(10);
		lowTextField.addActionListener( textBoxAction() );
        lowTextField.setText( String.valueOf( handler.getMinValue() ) );
        parentPanel.add(lowTextField);
		
		highTextField = new JTextField();
		highTextField.setFont(new Font("Courier New", Font.PLAIN, 13));
		highTextField.setBackground(Color.PINK);
		highTextField.setBounds(328, 11, 74, 38);
		highTextField.setColumns(10);
		highTextField.addActionListener( textBoxAction() );
        highTextField.setText( String.valueOf( handler.getMaxValue() ) );
        parentPanel.add(highTextField);

        JLabel txtrHighestValue = new JLabel(
                "<HTML> Highest <br> value: </HTML>");
        txtrHighestValue.setForeground(SystemColor.desktop);
        txtrHighestValue.setFont(new Font("Courier New", Font.PLAIN, 13));
		txtrHighestValue.setBackground(new Color(173, 216, 230));
		txtrHighestValue.setBorder(new EtchedBorder(
		        EtchedBorder.LOWERED, null, null));
		txtrHighestValue.setBounds(245, 11, 80, 38);
		parentPanel.add(txtrHighestValue);

        JLabel txtrLowestValue =
                new JLabel("<HTML>Lowest <br> value:</HTML>");
        txtrLowestValue.setBackground(Color.PINK);
        
        txtrLowestValue.setFont(new Font("Courier New", Font.PLAIN, 13));
        txtrLowestValue.setOpaque(true);
		txtrLowestValue.setBorder(new EtchedBorder(
		        EtchedBorder.LOWERED, null, null));
		txtrLowestValue.setBounds(245, 52, 80, 38);
		parentPanel.add(txtrLowestValue);

        JLabel txtrFrequency =
                new JLabel("<HTML> Frequency <br> (Hz):</HTML>");
        txtrFrequency.setFont(new Font("Courier New", Font.PLAIN, 13));
		txtrFrequency.setBackground(SystemColor.controlHighlight);
		txtrFrequency.setBorder(new EtchedBorder(
		        EtchedBorder.LOWERED, null, null));
		txtrFrequency.setBounds(245, 92, 80, 38);
		parentPanel.add(txtrFrequency);
		
		JPanel consolePanel = new JPanel();
        consolePanel.setBorder(new LineBorder(SystemColor.activeCaption));
        consolePanel.setBackground(new Color(220, 220, 220));
        consolePanel.setBounds(10, 248, 412, 44);
		serverFrame.getContentPane().add(consolePanel);
        consolePanel.setLayout(null);

        JLabel consoleLabel = new JLabel(" Console:");
		consoleLabel.setFont(new Font("Courier New", Font.PLAIN, 13));
		consoleLabel.setBounds(0, 0, 81, 15);
        consolePanel.add(consoleLabel);
		
		final JButton startStopButton = new JButton( "Start / Stop" );
		startStopButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		startStopButton.setBounds(315, 11, 107, 23);
		startStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                boolean currentState = handler.getServerSendStatus();
                handler.setServerSendStatus( !currentState );
                if( handler.getServerSendStatus() ){
                    statusColor = STATUS_OK;
                } else {
                    statusColor = STATUS_NOT_OK;
                }
                statusPanel.updateUI();
            }
        });

		serverFrame.getContentPane().add(startStopButton);
	}

    /**
     * @return Action to be triggered on a press of the enter key for
     * frequency, lowest value and highest value
     */
	private Action textBoxAction(){
	    return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String inputText = "";
                int inputVal = 0;
                try {
                    if ( e.getSource().equals( freqTextField ) ) {
                        inputText = freqTextField.getText();
                        inputVal = Integer.parseInt( inputText );
                        if( inputVal > 0 ) {
                            handler.setFrequency( inputVal );
                            System.out.println(
                                    "Frequency set to: " + inputText );
                        } else {
                            System.out.println(
                                    "Frequency must be greater than zero" );
                        }
                    } else if ( e.getSource().equals( lowTextField ) ) {
                        inputText = lowTextField.getText();
                        inputVal = Integer.parseInt( inputText );
                        if( inputVal <= handler.getMaxValue() ) {
                            handler.setMinValue( inputVal );
                            System.out.println(
                                    "Lowest value set to: " + inputText );
                        } else {
                            System.out.println(
                                    "Lowest value must be less than the max" );
                        }
                    } else if ( e.getSource().equals( highTextField ) ) {
                        inputText = highTextField.getText();
                        inputVal = Integer.parseInt( inputText );
                        if( inputVal >= handler.getMinValue() ) {
                            handler.setMaxValue( inputVal );
                            System.out.println(
                                    "Highest value set to: " + inputText );
                        } else {
                            System.out.println(
                                    "Highest value must be " +
                                    "greater than the max" );
                        }
                    }
                } catch( NumberFormatException err ){
                    System.out.println(
                            "Invalid number entered: " + inputText );
                }
            }
        };
    }
}
