import java.io.IOException;
import com.esotericsoftware.kryonet.Server;

/**
 * Server
 *
 * <P>Server side application entry point.
 *
 * @author Team 2
 * @version 1.0
 */
public class ServerApp {

    public static final int PORT = 3000;
    private static Server server;

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

    public static void main( String[] args ) {
        ServerInterface window = new ServerInterface();
        window.getFrmServer().setVisible(true);
        ServerApp.getServerInstance();
        ServerHandler handler = new ServerHandler();
        handler.start();
    }

}
