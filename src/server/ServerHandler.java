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
    private volatile int minValue = 0;
    private volatile int maxValue = 1024;
    private volatile int frequency = FrequencyPayload.DEFAULT_FREQUENCY;
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
        StatusUpdatePayload statusUpdate = new StatusUpdatePayload();
        statusUpdate.isRunning = sendStatus;
        ServerApp.getServerInstance().sendToAllTCP(statusUpdate);
    }

    /**
     * The time interval in milliseconds that frequency is based upon. An
     * example would be seconds, minutes, or hours.
     *
     * A frequency of 1 and a frequency interval of 60000 would mean data is
     * sent once a minute.
     *
     * @param freqInterval Interval at which data should be set based on
     *                     frequency
     */
    public void setFreqInterval(int freqInterval) {
        this.freqInterval = freqInterval;
    }

    /**
     * Get the minimum random number value to generate for sending to the client
     *
     * @return Minimum random number value
     */
    public int getMinValue() {
        return minValue;
    }

    /**
     * Set the minimum random number value to generate for sending to the client
     *
     * @param minValue Minimum random number value
     */
    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    /**
     * Get the maximum random number value to generate for sending to the client
     *
     * @return Maximum random number value
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * Set the maximum random number value to generate for sending to the client
     *
     * @param maxValue Maximum random number value
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Get the frequency per frequency type (ie seconds, minutes or hours)
     * that data should be sent to all clients
     *
     * @return Frequency that data should be sent to all clients
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Set the frequency per frequency type (ie seconds, minutes or hours)
     * that data should be sent to all clients
     *
     * @param frequency Frequency that data should be sent to all clients
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
        FrequencyPayload freqToSend = new FrequencyPayload( frequency );
        // Tell clients about a change in frequency
        ServerApp.getServerInstance().sendToAllTCP( freqToSend );
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
     * Set all server request action listeners. For example, if a client
     * connects or changes a channel value, these listeners will capture the
     * event and handle it
     */
    private void setListeners(){
        ServerApp.getServerInstance().addListener(new Listener() {
            public void received(Connection connection, Object object) {
                // Save client connection and inform client of the set frequency
                ConnectedClient currClient = getClient( connection );
                if (object instanceof ConnectionRequest) {
                    ConnectionRequest newConnection =
                            (ConnectionRequest) object;
                    FrequencyPayload frequency =
                            new FrequencyPayload(ServerHandler.this.frequency);
                    connectedClients.add( new ConnectedClient(
                            connection.getID(),
                            newConnection.getChannelNum().getChannelNum()
                    ) );
                    connection.sendTCP( frequency );
                // Set channel number for the connected client on a channel
                // change
                } else if( object instanceof NumberOfChannelsPayload) {
                    currClient.setChannelNum(
                            ((NumberOfChannelsPayload) object)
                                    .getChannelNum() );
                } else if( object instanceof StatusUpdatePayload){
                    currClient.setSendStatus(
                            ( (StatusUpdatePayload) object ).isRunning );
                    if(( (StatusUpdatePayload) object ).isRunning){
                        System.out.println(
                                "Client ID:" +
                                connection.getID() +
                                " Status Running"
                        );
                    } else {
                        System.out.println(
                                "Client ID:" +
                                connection.getID() +
                                " Status: Not Running"
                        );
                    }
                }
            }
        });
    }

    /**
     * @return A random number between minValue and maxValue, inclusive
     */
    private int getRandomNum(){
        return minValue +
                (int)( Math.random() * ( ( maxValue - minValue) + 1 ) );
    }

    private ConnectedClient getClient( Connection connection ){
        ConnectedClient client = null;
        for( ConnectedClient currentClient : connectedClients ){
            if( currentClient.getConnectionId() == connection.getID() ){
                client = currentClient;
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
        for ( Connection currentConnection : connections ) {
            if ( connection.getConnectionId() == currentConnection.getID() ) {
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
                for( Iterator<ConnectedClient> it = connectedClients.iterator();
                     it.hasNext(); ) {
                    // Only send data to client if it is not stopped and
                    // still connected
                    ConnectedClient currentClient = it.next();
                    if( !isConnected( currentClient ) ){
                        it.remove();
                    } else if( currentClient.getSendStatus() ) {
                        int id = currentClient.getConnectionId();
                        ChannelsPayload channelList = getChannelsToSend(
                                currentClient.getChannelNum());
                        ServerApp.getServerInstance().sendToTCP(
                                id,
                                channelList );
                        System.out.println("Channel data sent to ID: " + id);
                    }
                }
                try {
                    Thread.sleep(freqInterval / frequency);
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
         * @return All channels containing a random number to send to a client
         */
        private ChannelsPayload getChannelsToSend(int channels ){
            ChannelsPayload channelList = new ChannelsPayload();

            for( int i = 1; i <= channels; i++ ){
                channelList.addChannel( i, getRandomNum() );
            }

            return channelList;
        }
    }
}
