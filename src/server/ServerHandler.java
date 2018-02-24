package server;

import java.util.ArrayList;
import java.util.Iterator;

import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;
import network.*;

/**
 * Handles sending and receiving data for the server.
 * This can be considered the logic of the server.
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


    private ServerHandler(){
        connectedClients = new ArrayList<ConnectedClient>();
        setListeners();
    }

    /**
     * @return A singleton instance of ServerHandler
     */
    public static ServerHandler getInstance(){
        if( handler == null ){
            handler = new ServerHandler();
        }
        return handler;
    }

    /**
     * Whether or not the server is started or stopped
     *
     * @return True if server is started and running. False if not
     */
    public boolean getServerSendStatus(){
        return serverSendStatus;
    }

    /**
     * Set whether the server is started or stopped and let all
     * client know about the change
     *
     * @param sendStatus true if started, false if stopped
     */
    public void setServerSendStatus( boolean sendStatus ){
        // If the server status is currently on stop,
        // and the send status is changed to true,
        // lets re-start the server sending process
        if( !serverSendStatus && sendStatus ){
            start();
        }
        serverSendStatus = sendStatus;
        StatusUpdate statusUpdate = new StatusUpdate();
        statusUpdate.isRunning = sendStatus;
        ServerApp.getServerInstance().sendToAllTCP(statusUpdate);
    }

    /**
     * The time interval in milliseconds that frequency is based upon. An example
     * would be seconds, minutes, or hours.
     *
     * A frequency of 1 and a frequency interval of 60000 would mean data is sent
     * once a minute.
     *
     * @param freqInterval Interval at which data should be set based on frequency
     */
    public void setFreqInterval(int freqInterval) {
        this.freqInterval = freqInterval;
    }

    /**
     * Get the minimum random number value to generate for sending to the client
     *
     * @return Minimum random number value
     */
    public int getMin() {
        return min;
    }

    /**
     * Set the minimum random number value to generate for sending to the client
     *
     * @param min Minimum random number value
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Get the maximum random number value to generate for sending to the client
     *
     * @return Maximum random number value
     */
    public int getMax() {
        return max;
    }

    /**
     * Set the maximum random number value to generate for sending to the client
     *
     * @param max Maximum random number value
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Get the frequency per frequency type (ie seconds, minutes or hours)
     * that data should be sent to all clients
     *
     * @return Frequency that data should be sent to all clients
     */
    public int getFreq() {
        return freq;
    }

    /**
     * Set the frequency per frequency type (ie seconds, minutes or hours)
     * that data should be sent to all clients
     *
     * @param freq Frequency that data should be sent to all clients
     */
    public void setFreq(int freq) {
        this.freq = freq;
        Frequency frequency = new Frequency( freq );
        // Tell clients about a change in frequency
        ServerApp.getServerInstance().sendToAllTCP( frequency );
        start();
    }

    /**
     * Starts sending data from the server to the connected clients
     */
    public void start(){
        if( dataSender != null ){
            dataSender.interrupt();
        }
        dataSender = new DataSender();
        dataSender.start();
    }

    /**
     * Set all server request action listeners. For example, if a client connects or
     * changes a channel value, these listeners will capture the event and handle it
     */
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
                    if(( (StatusUpdate) object ).isRunning){
                        System.out.println("Client ID:"+ connection.getID() +" Status Running");
                    } else {
                        System.out.println("Client ID:"+ connection.getID() +" Status: Not Running");
                    }
                }
            }
        });
    }

    /**
     * @return A random number between min and max, inclusive
     */
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

    /**
     * Determines whether or not a client is still connected to the server
     *
     * @param connection A potential connected client
     * @return true if the client is connected, false if not.
     */
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

    /**
     * Contains logic to send data to all of the connected clients
     */
    class DataSender extends Thread {
        /**
         * Send all connected clients their channels data
         */
        @Override
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

        /**
         * Get the channels for a specific client before sending them
         *
         * @param channels number of channels a client has selected
         * @return Channels with random number data to send to a client
         */
        private Channels getChannelsToSend( int channels ){
            Channels channelList = new Channels();

            for( int i = 1; i <= channels; i++ ){
                channelList.addChannelNum( i, getRandomNum() );
            }

            return channelList;
        }
    }
}
