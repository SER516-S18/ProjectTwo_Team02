import java.awt.*;

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
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JLabel lblNewLabel_1;

	/**
	 * Create the application.
	 */
	public ServerInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
		
		textField_3 = new JTextField();
		textField_3.setBackground(new Color(250, 235, 215));
		textField_3.setBounds(328, 91, 74, 38);
		textField_3.setColumns(10);
		panel.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setBackground(SystemColor.controlHighlight);
		textField_4.setBounds(328, 52, 74, 38);
		textField_4.setColumns(10);
		panel.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setBackground(new Color(250, 235, 215));
		textField_5.setBounds(328, 11, 74, 38);
		textField_5.setColumns(10);
		panel.add(textField_5);
		
		JTextArea txtrHighestValue = new JTextArea();
		txtrHighestValue.setBackground(SystemColor.controlHighlight);
		txtrHighestValue.setBorder(border);
		txtrHighestValue.setLineWrap(true);
		txtrHighestValue.setWrapStyleWord(true);
		txtrHighestValue.setText("Highest value:");
		txtrHighestValue.setBounds(252, 11, 73, 38);
		panel.add(txtrHighestValue);
		
		JTextArea txtrLowestValue = new JTextArea();
		txtrLowestValue.setBackground(new Color(250, 235, 215));
		txtrLowestValue.setBorder(border);
		txtrLowestValue.setLineWrap(true);
		txtrLowestValue.setWrapStyleWord(true);
		txtrLowestValue.setText("Lowest value:");
		txtrLowestValue.setBounds(252, 52, 73, 38);
		panel.add(txtrLowestValue);
		
		JTextArea txtrFrequency = new JTextArea();
		txtrFrequency.setBackground(SystemColor.controlHighlight);
		txtrFrequency.setBorder(border);
		txtrFrequency.setFont(new Font("Monospaced", Font.PLAIN, 11));
		txtrFrequency.setLineWrap(true);
		txtrFrequency.setWrapStyleWord(true);
		txtrFrequency.setText("Frequency(Hz):");
		txtrFrequency.setBounds(252, 92, 73, 38);
		panel.add(txtrFrequency);
		
		JPanel consolePanel = new JPanel();
        consolePanel.setBorder(new LineBorder(SystemColor.activeCaption));
        consolePanel.setBackground(new Color(220, 220, 220));
        consolePanel.setBounds(10, 248, 412, 80);
		frmServer.getContentPane().add(consolePanel);
        consolePanel.setLayout(null);
		
		lblNewLabel_1 = new JLabel(" Console:");
		lblNewLabel_1.setFont(new Font("Monospaced", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(0, 0, 81, 15);
        consolePanel.add(lblNewLabel_1);

        JPanel consoleText = new ServerConsole();
        consoleText.setBounds(10, 15, 400, 60);
        consolePanel.add(consoleText);
		
		JButton btnNewButton = new JButton("Start / Stop");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton.setBounds(315, 11, 107, 23);
		frmServer.getContentPane().add(btnNewButton);
	}

	public JFrame getFrmServer() {
		return frmServer;
	}
}
