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

    private ArrayList<StatsInterface> mStatsList;
    private ClientHighestStats mHighest;
    private ClientLowestStats mLowest;
    private ClientAverageStats mAverage;

    public void init() {
        mStatsList = new ArrayList<StatsInterface>();
        mHighest = new ClientHighestStats();
        mLowest = new ClientLowestStats();
        mAverage = new ClientAverageStats();
        mStatsList.add(mHighest);
        mStatsList.add(mLowest);
        mStatsList.add(mAverage);
    }

     /**
     * @param channel the channel index
     * @param data the input data associated with the channel
    */
    public void onReceiveData(int channel, int data) {
        for (StatsInterface i : mStatsList) {
            i.onReceiveData(channel, data);
        }
    }

    public int getHighestValue(int channel) {
        return mHighest.getValue(channel);
    }

    public int getLowestValue(int channel) {
        return mLowest.getValue(channel);
    }

    public int getAverageValue(int channel) {
        return mAverage.getValue(channel);
    }
}