import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * Server side UI
 *
 * @author Team2
 * @version 1.0
 */

public class ServerInterface {

	private JFrame frmServer;
    private JTextField freqTextField;
	private JTextField lowTextField;
	private JTextField highTextField;
	private ServerHandler handler;

    /**
	 * Create the application.
	 */
	public ServerInterface() {
		initialize();
	}

    public JFrame getFrmServer() {
        return frmServer;
    }

    public JTextField getFreqTextField() {
        return freqTextField;
    }

    public JTextField getHighTextField() {
        return highTextField;
    }

    public JTextField getLowTextField() {
        return lowTextField;
    }

    /**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	    handler = ServerHandler.getInstance();
		frmServer = new JFrame();
		frmServer.setTitle("Server");
		frmServer.setBounds(100, 100, 448, 342);
		frmServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServer.getContentPane().setLayout(null);
		
		Color lightblue = new Color(153,180,209);
		Border border = BorderFactory.createLineBorder(lightblue);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(220, 220, 220));
		panel.setBounds(10, 41, 392, 196);
		frmServer.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(SystemColor.activeCaption));
		panel_2.setBackground(new Color(250, 235, 215));
		panel_2.setBounds(10, 11, 239, 163);
		panel.add(panel_2);
		
		freqTextField = new JTextField();
		freqTextField.setBackground(new Color(250, 235, 215));
		freqTextField.setBounds(328, 92, 74, 38);
		freqTextField.setColumns(10);
		freqTextField.addActionListener( textBoxAction() );
		freqTextField.setText( String.valueOf( handler.getFreq() ) );
		panel.add(freqTextField);
		
		lowTextField = new JTextField();
		lowTextField.setBackground(SystemColor.controlHighlight);
		lowTextField.setBounds(328, 52, 74, 38);
		lowTextField.setColumns(10);
		lowTextField.addActionListener( textBoxAction() );
        lowTextField.setText( String.valueOf( handler.getMin() ) );
        panel.add(lowTextField);
		
		highTextField = new JTextField();
		highTextField.setBackground(new Color(250, 235, 215));
		highTextField.setBounds(328, 11, 74, 38);
		highTextField.setColumns(10);
		highTextField.addActionListener( textBoxAction() );
        highTextField.setText( String.valueOf( handler.getMax() ) );
        panel.add(highTextField);

        JLabel txtrHighestValue = new JLabel("Highest value:");
		txtrHighestValue.setBackground(SystemColor.controlHighlight);
		txtrHighestValue.setBorder(border);
		txtrHighestValue.setBounds(252, 11, 73, 38);
		panel.add(txtrHighestValue);

        JLabel txtrLowestValue = new JLabel("Lowest value:");
		txtrLowestValue.setBackground(new Color(250, 235, 215));
		txtrLowestValue.setBorder(border);
		txtrLowestValue.setBounds(252, 52, 73, 38);
		panel.add(txtrLowestValue);

        JLabel txtrFrequency = new JLabel("Frequency(Hz):");
		txtrFrequency.setBackground(SystemColor.controlHighlight);
		txtrFrequency.setBorder(border);
		txtrFrequency.setBounds(252, 92, 73, 38);
		panel.add(txtrFrequency);
		
		JPanel consolePanel = new JPanel();
        consolePanel.setBorder(new LineBorder(SystemColor.activeCaption));
        consolePanel.setBackground(new Color(220, 220, 220));
        consolePanel.setBounds(10, 248, 412, 80);
		frmServer.getContentPane().add(consolePanel);
        consolePanel.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel(" Console:");
		lblNewLabel_1.setFont(new Font("Monospaced", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(0, 0, 81, 15);
        consolePanel.add(lblNewLabel_1);

        JPanel consoleText = new ServerConsole();
        consoleText.setBounds(10, 15, 400, 60);
        consolePanel.add(consoleText);
		
		final JButton startStopButton = new JButton( "Start / Stop" );
		startStopButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		startStopButton.setBounds(315, 11, 107, 23);
		startStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                boolean currentState = handler.getServerSendStatus();
                handler.setServerSendStatus( !currentState );
            }
        });

		frmServer.getContentPane().add(startStopButton);
	}

	// Action to be triggered on enter press
	private Action textBoxAction(){
	    return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String text = "";
                int val = 0;
                try {
                    if ( e.getSource().equals( freqTextField ) ) {
                        text = freqTextField.getText();
                        val = Integer.parseInt( text );
                        if( val > 0 ) {
                            handler.setFreq( val );
                            System.out.println( "Frequency set to: " + text + " Hz" );
                        } else {
                            System.out.println( "Frequency must be greater than zero" );
                        }
                    } else if ( e.getSource().equals( lowTextField ) ) {
                        text = lowTextField.getText();
                        val = Integer.parseInt( text );
                        if( val <= handler.getMax() ) {
                            handler.setMin( val );
                            System.out.println( "Lowest value set to: " + text );
                        } else {
                            System.out.println( "Lowest value must be less than the max" );
                        }
                    } else if ( e.getSource().equals( highTextField ) ) {
                        text = highTextField.getText();
                        val = Integer.parseInt( text );
                        if( val >= handler.getMin() ) {
                            handler.setMax( val );
                            System.out.println( "Highest value set to: " + text );
                        } else {
                            System.out.println( "Highest value must be greater than the max" );
                        }
                    }
                } catch( NumberFormatException err ){
                    System.out.println( "Invalid number entered: " + text );
                }
            }
        };
    }
}
