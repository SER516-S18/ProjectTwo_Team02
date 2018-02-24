package network;

/**
 * Frequency data to be sent over the network. Set on the server and sent to
 * the client.
 *
 * @author Team2
 * @version 1.0
 */
public class Frequency {

    public static final int DEFAULT_FREQUENCY = 1;
    private int frequency;

    /** No arg constructor needed to serialize */
    Frequency() { frequency = DEFAULT_FREQUENCY; }

    /**
     * Creates a frequency object to be send to the client
     *
     * @param freq Frequency that the server is sending data to clients
     */
    public Frequency( int freq ){
        frequency = freq;
    }

    /**
     * @return Frequency that the server is sending data to clients
     */
    public int getFrequency(){
        return frequency;
    }
}
