package network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

/**
 * NetworkSerializer
 *
 * <P>Responsible for registering all classes of objects that will be sent
 * through the network. Either to/from client or server.
 *
 * @author Team 2
 * @version 1.0
 */
public class NetworkSerializer {
    /**
     * Registers network classes to be serialized for transport between
     * the client and server
     *
     * @param endPoint Either the client or server objects. Server will register
     *                 with itself, so will client.
     */
    public static void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(java.util.ArrayList.class);
        kryo.register(ChannelPayload.class);
        kryo.register(ChannelsPayload.class);
        kryo.register(FrequencyPayload.class);
        kryo.register(NumberOfChannelsPayload.class);
        kryo.register(ConnectionRequest.class);
        kryo.register(StatusUpdatePayload.class);
    }
}
