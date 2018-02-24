package client;

import java.util.HashMap;

/**
 * Receive data from the server and calculate the lowest 
 * value of each channel
 *
 * @author Team 2
 * @version 1.0
 */
public class ClientLowestStats implements StatsInterface {
    
    private HashMap<Integer, Integer> dataContainer =
        new HashMap<Integer, Integer>();

    /**
     * Update the minimum value for a channel
     *
     * @param channel Channel index to update the lowest value on
     * @param data Data received for channel at the channel index
     */
    @Override
    public void onReceiveData(int channel, int data) {
        int currentLow = getValue(channel);
        currentLow = Math.min(currentLow, data);
        dataContainer.put(channel, currentLow);
    }

    /**
     * Get the lowest value from a channel at channel index
     *
     * @param channelIndex Channel index to get the lowest value from
     * @return the lowest value of the channel
    */
    @Override
    public int getValue(int channelIndex) {
        if (!dataContainer.containsKey(channelIndex)) {
            dataContainer.put(channelIndex, Integer.MAX_VALUE);
        }
        return dataContainer.get(channelIndex);
    }
}