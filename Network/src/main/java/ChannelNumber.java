/**
 * Represents a single channel and the associated random number
 * to be sent over the network to the client
 *
 * @author Team2
 * @version 1.0
 */
class ChannelNumber {
    private int channel;
    private int number;

    // No arg constructor needed to init network
    ChannelNumber(){}

    ChannelNumber( int channel, int number ){
        this.channel = channel;
        this.number = number;
    }

    public int getChannel(){
        return channel;
    }

    public int getNumber(){
        return number;
    }
}