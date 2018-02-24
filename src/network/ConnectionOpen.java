package network;

/**
 * This will be sent from the client when a new connection is made
 * Informs the server of the client channel number.
 *
 * @author Team2
 * @version 1.0
 */
public class ConnectionOpen {
    private ClientChannelNumber channelNum;

    /** No arg constructor needed to serialize */
    ConnectionOpen(){ channelNum = new ClientChannelNumber( ClientChannelNumber.DEFAULT_CHANNEL_NUM ); }

    /**
     * Creates a new open connection to inform the server
     *
     * @param channelNum Number of channels the client has upon connecting
     */
    public ConnectionOpen( int channelNum ){
        this.channelNum = new ClientChannelNumber( channelNum );
    }

    /**
     * @return Number of channels a client has upon connecting
     */
    public ClientChannelNumber getChannelNum(){
        return channelNum;
    }
}
