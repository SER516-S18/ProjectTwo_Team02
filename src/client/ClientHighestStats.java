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
	
	private HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();

	@Override
	public void onReceiveData(int channel, int data) {
		int cur = getValue(channel);
		cur = Math.max(cur, data);
		hash.put(channel, cur);
	}

	/**
     * @param channel the channel index
     * @return the highest value of the channel
    */
	@Override
	public int getValue(int channel) {
		if (!hash.containsKey(channel)) {
			hash.put(channel, Integer.MIN_VALUE);
		}
		return hash.get(channel);
	}
}