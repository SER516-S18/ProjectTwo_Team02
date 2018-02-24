package server;

/**
 * Represents a client that is currently connected to the server.
 *
 * @author Team2
 * @version 1.0
 */
class ConnectedClient {
    private int connectionId;
    private int channelNum;
    private boolean sendData = true;

    /**
     * Creates a connected client to start sending data to
     *
     * @param connectionId the KryoNet client ID given upon connection to the server
     * @param channelNum The number of channels currently selected by the client
     */
    ConnectedClient( int connectionId, int channelNum ){
        this.connectionId = connectionId;
        this.channelNum = channelNum;
    }

    /**
     * Get the connection id assigned to a client upon connecting
     *
     * @return the KryoNet client ID given upon connection to the server
     */
    public int getConnectionId() {
        return connectionId;
    }

    /**
     * Get the number of data channels specified by the client
     *
     * @return The number of channels currently selected by the client
     */
    public int getChannelNum() {
        return channelNum;
    }

    /**
     * Set the number of channels the client as specified that it wants to receive
     *
     * @param channelNum The number of channels currently selected by the client
     */
    public void setChannelNum(int channelNum) {
        this.channelNum = channelNum;
    }

    /**
     * Get whether or not the client should receive data
     *
     * @return True if okay to send data to client, false if not
     */
    public boolean getSendStatus(){
        return sendData;
    }

    /**
     * Set whether or not the client should receive data
     *
     * @param sendData True if okay to send data to client, false if not
     */
    public void setSendStatus( boolean sendData ){
        this.sendData = sendData;
    }
}
