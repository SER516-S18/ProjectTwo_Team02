package network;

import java.util.ArrayList;

/**
 * Holds all channels sent from server to a single client
 *
 * @author Team2
 * @version 1.0
 */
public class ChannelsPayload {

    private ArrayList<ChannelPayload> channelList;

    /**
     * Create a list of channels to be sent to a single client
     */
    public ChannelsPayload(){
        channelList = new ArrayList<>();
    }

    /**
     * Add a channel to the channel list
     *
     * @param channel Number of the channel
     * @param data    Data to be sent on the channel
     */
    public void addChannel(int channel, int data ){
        ChannelPayload channelNum = new ChannelPayload( channel, data );
        channelList.add( channelNum );
    }

    /**
     * @return The list of channels to be sent to the client
     */
    public ArrayList<ChannelPayload> getChannelList() {
        return channelList;
    }
}
