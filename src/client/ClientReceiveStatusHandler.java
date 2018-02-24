package client;

/**
 * This class will notify the server when the client 
 * starts/stops receiving data. 
 *
 * @author Team 2
 * @version 1.0
 */
public class ClientReceiveStatusHandler {

    private static ClientReceiveStatusHandler handler;
    private ClientApp clientApp;
    private volatile boolean clientReceiveStatus = true;

    private ClientReceiveStatusHandler(){}

    /**
     * @return A singleton instance of ClientReceiveStatusHandler
     */
    public static ClientReceiveStatusHandler getInstance() {
        if (handler == null) {
            handler = new ClientReceiveStatusHandler();
        }
        return handler;
    }

    /**
     * Get if the client is receiving data or not
     *
     * @return True if the client is receiving data, false if not
    */
    public boolean getClientReceiveStatus() {
        return clientReceiveStatus;
    }

    /**
     * Set if the client is receiving data or not
     *
     * @param sendStatus True if the client is receiving data, false if not
    */
    public void setClientReceiveStatus(boolean sendStatus) {
        clientReceiveStatus = sendStatus;
        clientApp = ClientApp.getInstance();
        clientApp.sendClientStatus();
    }
}
