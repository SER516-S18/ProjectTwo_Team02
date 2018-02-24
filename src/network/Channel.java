package network;

/**
 * Represents a single channel and the associated random data
 * to be sent over the network to the client
 *
 * @author Team2
 * @version 1.0
 */
public class Channel {
    private int channel;
    private int data;

    /** No arg constructor needed to serialize */
    Channel(){}

    /**
     * @param channel Channel number of this channel
     * @param data Data to be sent on this channel
     */
    Channel(int channel, int data ){
        this.channel = channel;
        this.data = data;
    }

    /**
     * @return Number of the channel
     */
    public int getChannel(){
        return channel;
    }

    /**
     * @return Data to be sent with the channel
     */
    public int getData(){
        return data;
    }
}