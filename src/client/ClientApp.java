package client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;
import network.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.management.loading.MLetContent;

/**
 * Client
 *
 * <P>Client side application entry point.
 * <P>This establishes a connection to the server and create an interface to
 *  show status of received data
 *
 * @author Team 2
 * @version 1.0
 */
public class ClientApp {

    public static final String IP = "localhost";
    public static final int PORT = 3000;
    public static final int DEFAULT_CHANNEL_NUM = 3;
    private static final int CONNECTION_TIMEOUT = 5000;

    private static ClientApp instance;

    private Client clientConnection;
    private ClientUI clientInterface;
    private ClientStatsManager statsManager;
    private ClientReceiveStatusHandler clientHandler;
    private StatusUpdate statusUpdate;
    private int curChannel;
    private boolean serverIsRunning; 

    public static void main(String[] args) {
        getInstance().initialize();
    }

    public static ClientApp getInstance() {
        if (instance == null) {
            instance = new ClientApp();
        }
        return instance;
    }

    public void initialize() {
        curChannel = 1;
        clientInterface = new ClientUI(DEFAULT_CHANNEL_NUM);
        clientHandler = ClientReceiveStatusHandler.getInstance();
        statsManager = new ClientStatsManager();
        statsManager.initialize();
        addChannelSwitchListener();

        try {
            connectToServer();
        } catch (IOException e) {
            System.err.println(e.toString());
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /** Send whether the client is receiving data to the server*/
    public void sendClientStatus() {
        statusUpdate.isRunning = clientHandler.getClientReceiveStatus();
        clientConnection.sendTCP(statusUpdate);
    }

    private void connectToServer() throws IOException {
        statusUpdate = new StatusUpdate();
        clientConnection = new Client();
        clientConnection.start();
        clientConnection.connect(CONNECTION_TIMEOUT, IP, PORT);

        Network.register(clientConnection);

        ConnectionOpen newConnection = new ConnectionOpen(DEFAULT_CHANNEL_NUM);
        clientConnection.sendTCP(newConnection);
        clientConnection.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                onDataReceived(object);
            }
        });
    }

    /**
     * Called when the client receives data from the server
     * @param object	received data from the server
     */
    private void onDataReceived(Object object) {
        if (clientHandler.getClientReceiveStatus()) {
            if (object instanceof Frequency) {
                int frequency = ((Frequency) object).getFrequency();
                System.out.println("Frequency set to: " + frequency + " Hz");
                clientInterface.setFrequency(frequency);
            } else if (object instanceof Channels) {
                ArrayList<ChannelNumber> channelList = ((Channels) object).getChannelList();
                for (ChannelNumber channelNum : channelList) {
                    int channel = channelNum.getChannel();
                    int number = channelNum.getNumber();
                    System.out.println("Channel: " + channel + ", Data: " + number);
                    Date currentTime = Calendar.getInstance().getTime(); 
                    clientInterface.getClientPlotPanel().addData(currentTime, channel, number);
                    statsManager.onReceiveData(channel, number);
                }
                UpdateInterfaceStats();
            } else if (object instanceof StatusUpdate) {
                serverIsRunning = ((StatusUpdate) object).isRunning;
                if (serverIsRunning) {
                    System.out.println("Server started running");
                } else {
                    System.out.println("Server stopped running");
                }
            }
        }
    }

    /** Add listener for channel switch event from the client UI*/
    private void addChannelSwitchListener() {
        clientInterface.setChannelSwitchListener(new ClientUI.ChannelSwitchListerner() {
            @Override
            public void onChannelSwitch(int channel) {
                curChannel = channel;
                UpdateInterfaceStats();
            }
        });
    }

    /** Update information on Client UI when new data is received */
    private void UpdateInterfaceStats() {
        clientInterface.setAverageValue(statsManager.getAverageValue(curChannel));
        clientInterface.setLowestValue(statsManager.getLowestValue(curChannel));
        clientInterface.setHighestValue(statsManager.getHighestValue(curChannel));
    }
}
