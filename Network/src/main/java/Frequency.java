/**
 * Frequency data to be send over the network. Set on the server.
 *
 * @author Team2
 * @version 1.0
 */
public class Frequency {

    public static final int DEFAULT_FREQUENCY = 1;
    private int frequency;

    // No arg constructor needed to init network
    Frequency() { frequency = DEFAULT_FREQUENCY; }
    Frequency( int freq ){
        frequency = freq;
    }

    public int getFrequency(){
        return frequency;
    }
}
