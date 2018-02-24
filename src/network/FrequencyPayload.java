package network;

/**
 * FrequencyPayload data to be sent over the network. Set on the server and
 * sent to the client.
 *
 * @author Team2
 * @version 1.0
 */
public class FrequencyPayload {

    public static final int DEFAULT_FREQUENCY = 1;
    private int frequency;

    /** No arg constructor needed to serialize */
    FrequencyPayload() { frequency = DEFAULT_FREQUENCY; }

    /**
     * Creates a frequency object to be send to the client
     *
     * @param freq FrequencyPayload that the server is sending data to clients
     */
    public FrequencyPayload(int freq ){
        frequency = freq;
    }

    /**
     * @return FrequencyPayload that the server is sending data to clients
     */
    public int getFrequency(){
        return frequency;
    }
}
