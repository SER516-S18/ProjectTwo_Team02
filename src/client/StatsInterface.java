package client;

/**
 * An interface for unified data processing
 * It receive data from server and calculate a specific value of
 * each channel. The specific value can be average, the highest, etc.
 *
 * @author Team 2
 * @version 1.0
 */
public interface StatsInterface {

    /**
     * @param channel the channel index
     * @param data the input data associated with the channel
    */
    public void onReceiveData(int channel, int data);

    /**
     * @param channel the channel index
     * @return a stats information of a given channel. 
     * It can be average, the highest, etc.
    */
    public int getValue(int channel);
}