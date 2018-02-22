/**
 * Holds the channel number set by a connecting client
 *
 * @author Team2
 * @version 1.0
 */
class ConnectedClient {
    private int connectionId;
    private int channelNum;
    private boolean sendData = true;

    ConnectedClient( int connectionId, int channelNum ){
        this.connectionId = connectionId;
        this.channelNum = channelNum;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public int getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(int channelNum) {
        this.channelNum = channelNum;
    }

    public boolean getSendStatus(){
        return sendData;
    }

    public void setSendStatus( boolean sendData ){
        this.sendData = sendData;
    }
}
