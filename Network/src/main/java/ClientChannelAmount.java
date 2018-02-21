/**
 * The number of channels a client has selected
 *
 * @author Team2
 * @version 1.0
 */
public class ClientChannelAmount {

    public static final int DEFAULT_CHANNEL_NUM = 1;
    private int channelNum;

    // No arg constructor needed to init network
    ClientChannelAmount(){ channelNum = DEFAULT_CHANNEL_NUM; };
    ClientChannelAmount(int num ){
        channelNum = num;
    }

    public int getNum() {
        return channelNum;
    }
}
