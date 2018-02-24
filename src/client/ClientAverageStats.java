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

    @Override
    public void onReceiveData(int channel, int data) {
        InnerData cur = getInnerData(channel);
        cur.sum += data;
        cur.num += 1;
    }

    /**
     * @param channelIndex 	the channel index
     * @return 			    the average value of the channel
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
     *
     * @param channel Channel index
     * @return Channel data
     */
    private InnerData getInnerData(int channel) {
        if (!dataContainer.containsKey(channel)) {
            dataContainer.put(channel, new InnerData());
        }
        return dataContainer.get(channel);
    }
}