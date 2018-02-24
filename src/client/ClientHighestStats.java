package client;

import java.util.HashMap;

/**
 * Receive data from the server and calculate the highest 
 * value of each channel
 * 
 * @author Team 2
 * @version 1.0
 */
public class ClientHighestStats implements StatsInterface {

    private HashMap<Integer, Integer> dataContainer =
        new HashMap<Integer, Integer>();

    /**
     * Update the maximum value for a channel
     *
     * @param channel Channel index to update the highest value on
     * @param data Data received for channel at the channel index
     */
    @Override
    public void onReceiveData(int channel, int data) {
        int currentHighest = getValue(channel);
        currentHighest = Math.max(currentHighest, data);
        dataContainer.put(channel, currentHighest);
    }

    /**
     * Get the highest value on the channel at channelIndex
     *
     * @param channelIndex The channel index to get the highest value
     * @return the highest value of the channel
    */
    @Override
    public int getValue(int channelIndex) {
        if (!dataContainer.containsKey(channelIndex)) {
            dataContainer.put(channelIndex, Integer.MIN_VALUE);
        }
        return dataContainer.get(channelIndex);
    }
}