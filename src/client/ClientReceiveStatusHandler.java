package client;

/**
 * This class will notify the server when the client start/stop receive data. 
 *
 * @author Team2
 * @version 1.0
 */
public class ClientReceiveStatusHandler {

    private static ClientReceiveStatusHandler handler;
    private ClientApp clientApp;
    private volatile boolean clientReceiveStatus = true;

    public static ClientReceiveStatusHandler getInstance() {
        if (handler == null) {
            handler = new ClientReceiveStatusHandler();
        }
        return handler;
    }

    public boolean getClientReceiveStatus() {
        return clientReceiveStatus;
    }

    public void setClientReceiveStatus(boolean sendStatus) {
        clientReceiveStatus = sendStatus;
        clientApp = ClientApp.getInstance();
        clientApp.sendClientStatus();
    }
}
