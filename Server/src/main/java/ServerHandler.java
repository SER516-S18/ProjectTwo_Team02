import java.util.ArrayList;

import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;

/**
 * Handles sending and receiving data for the server.
 *
 * @author Team2
 * @version 1.0
 */
public class ServerHandler {

    private static ServerHandler handler;
    private ArrayList<ConnectedClient> connectedClients;
    private int FREQ_SECONDS = 1000;
    private int min = 1024;
    private int max = 0;
    private int freq = Frequency.DEFAULT_FREQUENCY;
    private boolean serverSendStatus = true;
    DataSender dataSender;

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

    class DataSender extends Thread {
        @Override
        // Send everyone their channels data
        public void run(){
            System.out.println( "Sending started." );
            while( serverSendStatus ) {
                for( ConnectedClient currClient : connectedClients ) {
                    // Only send data to client if it is not stopped
                    if( currClient.getSendStatus() ) {
                        int id = currClient.getConnectionId();
                        Channels channelList = getChannelsToSend(currClient.getChannelNum());
                        ServerApp.getServerInstance().sendToTCP(id, channelList);
                        System.out.println("Channel data sent to ID: " + id);
                    }
                }
                try {
                    Thread.sleep(FREQ_SECONDS / freq );
                } catch ( InterruptedException e ) {
                    System.out.println( "Data sender failed to sleep" );
                    e.printStackTrace();
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
