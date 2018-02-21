import java.util.ArrayList;

/**
 * Holds all channels sent from server to client
 *
 * @author Team2
 * @version 1.0
 */
public class Channels {

    private ArrayList<ChannelNumber> channelList;

    Channels(){
        channelList = new ArrayList<ChannelNumber>();
    }

    public void addChannelNum( int channel, int num ){
        ChannelNumber channelNum = new ChannelNumber( channel, num );
        channelList.add( channelNum );
    }

    public ArrayList<ChannelNumber> getChannelList() {
        return channelList;
    }
}
