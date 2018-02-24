package network;

import java.util.ArrayList;

/**
 * Holds all channels sent from server to a single client
 *
 * @author Team2
 * @version 1.0
 */
public class Channels {

    private ArrayList<Channel> channelList;

    /**
     * Create a list of channels to be sent to a single client
     */
    public Channels(){
        channelList = new ArrayList<>();
    }

    /**
     * Add a channel to the channel list
     *
     * @param channel Number of the channel
     * @param data Data to be sent on the channel
     */
    public void addChannel(int channel, int data ){
        Channel channelNum = new Channel( channel, data );
        channelList.add( channelNum );
    }

    /**
     * @return The list of channels to be sent to the client
     */
    public ArrayList<Channel> getChannelList() {
        return channelList;
    }
}
