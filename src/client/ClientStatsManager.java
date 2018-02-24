package client;

import java.util.ArrayList;

/**
 * Client Stats Manager for all stats processing.
 * It receive data from the server and provide querying for information like
 * average, the highest and the lowest value of a channel
 *
 * @author Team 2
 * @version 1.0
 */
public class ClientStatsManager {

    private ArrayList<StatsInterface> statsList;
    private ClientHighestStats highest;
    private ClientLowestStats lowest;
    private ClientAverageStats average;
    
    /** create sub stats controler like average, the highest, etc*/
    public void initialize() {
        statsList = new ArrayList<StatsInterface>();
        highest = new ClientHighestStats();
        lowest = new ClientLowestStats();
        average = new ClientAverageStats();
        statsList.add(highest);
        statsList.add(lowest);
        statsList.add(average);
    }

    /**
     * call this method when the client receives data from the server 
     * 
     * @param channel the channel index
     * @param data the input data associated with the channel
    */
    public void onReceiveData(int channel, int data) {
        for (StatsInterface i : statsList) {
            i.onReceiveData(channel, data);
        }
    }

    public int getHighestValue(int channel) {
        return highest.getValue(channel);
    }

    public int getLowestValue(int channel) {
        return lowest.getValue(channel);
    }

    public int getAverageValue(int channel) {
        return average.getValue(channel);
    }
}