import java.util.ArrayList;
import java.util.Iterator;

import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;

/**
 * Handles sending and receiving data for the server.
 *
 * @author Team2
 * @version 1.0
 */
public class ServerHandler {

    public static final int FREQ_SECONDS = 1000;
    public static final int FREQ_MINUTES = 60000;
    public static final int FREQ_HOURS = 3600000;

    private static ServerHandler handler;
    private ArrayList<ConnectedClient> connectedClients;
    private volatile int min = 0;
    private volatile int max = 1024;
    private volatile int freq = Frequency.DEFAULT_FREQUENCY;
    private volatile boolean serverSendStatus = true;
    private volatile int freqInterval = FREQ_SECONDS;
    private DataSender dataSender;

    ServerHandler(){
        connectedClients = new ArrayList<ConnectedClient>();
        setListeners();
    }

    public static ServerHandler getInstance(){
        if( handler == null ){
            handler = new ServerHandler();
        }
        return handler;
    }

    public boolean getServerSendStatus(){
        return serverSendStatus;
    }

    public void setServerSendStatus( boolean sendStatus ){
        // If the server status is currently on stop,
        // and the send status is changed to true,
        // lets re-start the server sending process
        if( !serverSendStatus && sendStatus ){
            start();
        }
        serverSendStatus = sendStatus;
    }

    public void setFreqInterval(int freqInterval) {
        this.freqInterval = freqInterval;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
        Frequency frequency = new Frequency( freq );
        // Tell clients about a change in frequency
        ServerApp.getServerInstance().sendToAllTCP( frequency );
        start();
    }

    public void start(){
        if( dataSender != null ){
            dataSender.interrupt();
        }
        dataSender = new DataSender();
        dataSender.start();
    }

    private void setListeners(){
        ServerApp.getServerInstance().addListener(new Listener() {
            public void received(Connection connection, Object object) {
                // Save client connection and inform client of the set frequency
                ConnectedClient currClient = getClient( connection );
                if (object instanceof ConnectionOpen) {
                    ConnectionOpen newConnection = (ConnectionOpen) object;
                    Frequency frequency = new Frequency( freq );
                    connectedClients.add( new ConnectedClient(
                            connection.getID(),
                            newConnection.getChannelNum().getNum()
                    ) );
                    connection.sendTCP( frequency );
                // Set channel number for the connected client on a channel change
                } else if( object instanceof ClientChannelAmount) {
                    currClient.setChannelNum( ((ClientChannelAmount) object).getNum() );
                } else if( object instanceof StatusUpdate ){
                    currClient.setSendStatus( ( (StatusUpdate) object ).isRunning );
                }
            }
        });
    }

    private int getRandomNum(){
        return min + (int)( Math.random() * ( ( max - min ) + 1 ) );
    }

    private ConnectedClient getClient( Connection connection ){
        ConnectedClient client = null;
        for( ConnectedClient currClient : connectedClients ){
            if( currClient.getConnectionId() == connection.getID() ){
                client = currClient;
            }
        }
        return client;
    }

    private boolean isConnected( ConnectedClient connection ){
        boolean connected = false;
        final Connection[] connections =
                ServerApp.getServerInstance().getConnections();
        for ( Connection connection1 : connections ) {
            if ( connection.getConnectionId() == connection1.getID() ) {
                connected = true;
            }
        }
        return connected;
    }

    class DataSender extends Thread {
        @Override
        // Send everyone their channels data
        public void run(){
            System.out.println( "Sending started." );
            while( !Thread.interrupted() && serverSendStatus ) {
                for(Iterator<ConnectedClient> it = connectedClients.iterator(); it.hasNext(); ) {
                    // Only send data to client if it is not stopped and still connected
                    ConnectedClient currClient = it.next();
                    if( !isConnected( currClient ) ){
                        it.remove();
                    } else if( currClient.getSendStatus() ) {
                        int id = currClient.getConnectionId();
                        Channels channelList = getChannelsToSend(currClient.getChannelNum());
                        ServerApp.getServerInstance().sendToTCP(id, channelList);
                        System.out.println("Channel data sent to ID: " + id);
                    }
                }
                try {
                    Thread.sleep(freqInterval / freq );
                } catch ( InterruptedException e ) {
                    return;
                }
            }
            System.out.println( "Sending stopped." );
        }

        private Channels getChannelsToSend( int channels ){
            Channels channelList = new Channels();

            for( int i = 1; i <= channels; i++ ){
                channelList.addChannelNum( i, getRandomNum() );
            }

            return channelList;
        }
    }
}
