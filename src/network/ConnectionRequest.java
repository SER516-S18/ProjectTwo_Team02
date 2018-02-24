package network;

/**
 * This will be sent from the client when a new connection is made
 * Informs the server of the client channel number.
 *
 * @author Team2
 * @version 1.0
 */
public class ConnectionRequest {
    private NumberOfChannelsPayload channelNum;

    /** No arg constructor needed to serialize */
    ConnectionRequest(){
        channelNum =
                new NumberOfChannelsPayload(
                        NumberOfChannelsPayload.DEFAULT_CHANNEL_NUM );
    }

    /**
     * Creates a new open connection to inform the server
     *
     * @param channelNum Number of channels the client has upon connecting
     */
    public ConnectionRequest(int channelNum ){
        this.channelNum = new NumberOfChannelsPayload( channelNum );
    }

    /**
     * @return Number of channels a client has upon connecting
     */
    public NumberOfChannelsPayload getChannelNum(){
        return channelNum;
    }
}
