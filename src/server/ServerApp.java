package server;

import java.io.IOException;
import com.esotericsoftware.kryonet.Server;
import network.Network;

/**
 * Server
 *
 * <P>Server side application entry point.
 * Starts the UI and the KryoNet server.
 *
 * @author Team 2
 * @version 1.0
 */
public class ServerApp {

    public static final int PORT = 3000;
    private static Server server;

    /**
     * @return A singleton instance of the server
     */
    public static Server getServerInstance() {

        if( server == null ){
            try {
                server = new Server();
                server.start();
                server.bind(PORT);
                Network.register(server);
            } catch ( IOException e ){
                System.out.println("Error creating server");
                e.printStackTrace();
            }
        }

        return server;
    }

    /**
     * @param args No arguments expected
     */
    public static void main( String[] args ) {
        ServerUI window = new ServerUI();
        window.getserverFrame().setVisible(true);
        ServerApp.getServerInstance();
        ServerHandler handler = ServerHandler.getInstance();
        handler.start();
    }

}
