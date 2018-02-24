package client;

import java.util.HashMap;

/**
 * Receive data from the server and calculate the average 
 * value of each channel
 *
 * @author Team 2
 * @version 1.0
 */
public class ClientAverageStats implements StatsInterface {
    
	/**
     * An inner class used to simplify data processing.
     * It records sum and number of input data
     */
    private class InnerData {
        int sum = 0; // sum of received data
        int num = 0; // number of received data
    }

    private HashMap<Integer, InnerData> dataContainer = 
        new HashMap<Integer, InnerData>();
    /**
     * Update the average value for a channel
     *
     * @param channel 	Channel index to update the average value on
     * @param data 		Data received for channel at the channel index
     */
    @Override
    public void onReceiveData(int channel, int data) {
        InnerData currentInnerData = getInnerData(channel);
        currentInnerData.sum += data;
        currentInnerData.num += 1;
    }

    /**
     * Get the average value of a channel
     *
     * @param channelIndex 	The channel index to get the average value from
     * @return 			    The average value of the channel
    */
    @Override
    public int getValue(int channelIndex) {
        InnerData innerData = getInnerData(channelIndex);
        if (innerData.num == 0) {
            return 0;
        } else {
            return innerData.sum / innerData.num;
        }
    }

    /**
     * Get the latest running sum and data from a channel
     *
     * @param channel 	The channel index to get the running sum and data from
     * @return Channel 	data containing a running sum and the latest data
     */
    private InnerData getInnerData(int channel) {
        if (!dataContainer.containsKey(channel)) {
            dataContainer.put(channel, new InnerData());
        }
        return dataContainer.get(channel);
    }
}