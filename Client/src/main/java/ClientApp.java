import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Client
 *
 * <P>Client side application entry point.
 * <P>This establishes a connection to the server.
 *
 * @author Team 2
 * @version 1.0
 */
public class ClientApp {

    public static final String IP = "localhost";
    public static final int PORT = 3000;
    public static final int DEFAULT_CHANNEL_NUM = 1;

    private static final int CONNECTION_TIMEOUT = 5000;

    private static ClientApp sApp;

    private  Client mClientConnection;
    private ClientInterface mClientInterface;
    private  ClientStatsManager mStatsMgr;

    public void init() {
        try {
            connectToServer();
            mClientInterface = new ClientInterface();
            mClientInterface.getFromClient().setVisible(true);

        } catch (IOException e) {
           System.err.println(e.toString());
       } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    private void connectToServer() throws IOException {
        mClientConnection = new Client();
        mClientConnection.start();
        mClientConnection.connect(CONNECTION_TIMEOUT, IP, PORT);

        Network.register(mClientConnection);

        ConnectionOpen newConnection = new ConnectionOpen( DEFAULT_CHANNEL_NUM );
        mClientConnection.sendTCP( newConnection );

        mStatsMgr = new ClientStatsManager();
        mStatsMgr.init();

        mClientConnection.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof Frequency) {
                    int frequency = ((Frequency) object ).getFrequency();
                    System.out.println( "Frequency set to: " + frequency + " Hz" );
                    // TODO FOR CLIENT TEAM - Save frequency on client side for displaying

                } else if( object instanceof Channels ){
                    ArrayList<ChannelNumber> channelList = ( (Channels) object ).getChannelList();
                    for( ChannelNumber channelNum : channelList ){
                        int channel = channelNum.getChannel();
                        int data = channelNum.getNumber();
                        System.out.println( "Channel: " + channel + ", Data: " + data );
                        mStatsMgr.onReceiveData(channel, data);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        sApp = new ClientApp();
        sApp.init();

    }

}
