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
    
    private class InnerData {
        int sum = 0; // sumaration of received data
        int num = 0; // number of received data
    }

    private HashMap<Integer, InnerData> hash = new HashMap<Integer, InnerData>();

    @Override
    public void onReceiveData(int channel, int data) {
        InnerData cur = getInnerData(channel);
        cur.sum += data;
        cur.num += 1;
    }

    /**
     * @param channel the channel index
     * @return the average value of the channel
    */
    @Override
    public int getValue(int channel) {
        InnerData innerData = getInnerData(channel);
        if (innerData.num == 0) {
            return 0;
        } else {
            return innerData.sum / innerData.num;
        }
    }

    private InnerData getInnerData(int channel) {
        if (!hash.containsKey(channel)) {
            hash.put(channel, new InnerData());
        }
        return hash.get(channel);
    }
}