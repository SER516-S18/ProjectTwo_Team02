import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * A JPanel that represents a console and redirects the system
 * output stream to itself
 *
 * @author Team 2
 * @version 1.0
 */
public class ServerConsole extends JPanel{

    private JTextArea textArea;

    ServerConsole( ){
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane( textArea );
        PrintStream ps = new PrintStream( new ServerOutputStream() );
        System.setOut( ps );
        System.setErr( ps );
        scrollPane.setBorder( null );
        this.setLayout( new BorderLayout( 0,0 ) );
        this.setOpaque( false );
        scrollPane.setOpaque( false );
        scrollPane.getViewport().setOpaque( false );
        textArea.setOpaque( false );
        this.add( scrollPane, BorderLayout.CENTER );
    }

    class ServerOutputStream extends OutputStream {
        @Override
        public void write(int i) throws IOException {
            // Append new console character and update the text area
            textArea.setText( textArea.getText() + String.valueOf( (char) i ) );
            textArea.setCaretPosition( textArea.getDocument().getLength() );
            textArea.update( textArea.getGraphics() );
        }
    }
}
