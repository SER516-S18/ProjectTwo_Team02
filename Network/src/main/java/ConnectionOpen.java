/**
 * This will be sent from the client when a new connection is made
 * Informs the server of the client channel number.
 *
 * @author Team2
 * @version 1.0
 */
public class ConnectionOpen {
    private ClientChannelAmount channelNum;

    // No arg constructor needed to init network
    ConnectionOpen(){ channelNum = new ClientChannelAmount( ClientChannelAmount.DEFAULT_CHANNEL_NUM ); };
    ConnectionOpen( int channelNum ){
        this.channelNum = new ClientChannelAmount( channelNum );
    }

    public ClientChannelAmount getChannelNum(){
        return channelNum;
    }
}
