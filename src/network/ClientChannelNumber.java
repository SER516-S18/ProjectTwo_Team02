package network;

/**
 * The number of channels a client has selected
 * This is intended to be sent to the server as a request
 *
 * @author Team2
 * @version 1.0
 */
public class ClientChannelNumber {

    public static final int DEFAULT_CHANNEL_NUM = 1;
    private int channelNum;

    /** No arg constructor needed to serialize */
    ClientChannelNumber(){ channelNum = DEFAULT_CHANNEL_NUM; };

    /**
     * Creates a client channel amount request to be sent to the server
     *
     * @param channelNum
     */
    ClientChannelNumber( int channelNum ){
        this.channelNum = channelNum;
    }

    /**
     * @return The number of channels
     */
    public int getChannelNum() {
        return channelNum;
    }
}
