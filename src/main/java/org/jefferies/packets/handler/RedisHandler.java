package org.jefferies.packets.handler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import org.jefferies.packets.RedisPacket;
import org.jefferies.packets.listener.RedisListener;
import org.jefferies.packets.publisher.RedisPublisher;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.HashMap;
import java.util.Map;

public class RedisHandler {

    public static RedisHandler i;

    private Map<String, RedisPacket> packets;

    @Getter
    private RedisListener listeners = new ArrayList();

    private String channel;
    private String address;
    private int port;
    private String password;

    private JedisPubSub pubSub;
    private Jedis jedis;

    @Getter
    private RedisPublisher publisher;

    public RedisHandler(String channel, String address, int port, String password) {
        i = this;
        packets = new HashMap<>();
        this.channel = channel;
        this.address = address;
        this.port = port;
        this.password = password;
        startListening();
        publisher = new RedisPublisher(channel, address, port, password);
    }

    public void registerPacket(RedisPacket packet) {
        packets.put(packet.getIdentifier(), packet);
    }

    public void sendPacket(RedisPacket packet){
        try {
            JsonObject object = packet.serialized();
            object.addProperty("identifier", packet.getIdentifier());
            if(object == null){
                System.out.println("[Packet Send Failure] Packet with ID of " + packet.getIdentifier() + " failed to parse correct information.");
            } else {
                publisher.write(object.toString());
            }
        } catch (Exception ex){
            System.out.println("[Packet Send Failure] Packet with ID of " + packet.getIdentifier() + " failed to send.");
            ex.printStackTrace();
        }
    }

    public void addListener(RedisListener listener) {
        listeners.add(listener);
    }

    private void startListening() {
        try {
            pubSub = get();
            jedis = new Jedis(address, port);
            if (!password.equals("")) {
                jedis.auth(password);
            }
            new Thread() {
                public void run() {
                    jedis.subscribe(pubSub, channel);
                }
            }.start();
        } catch (Exception ex) {
            System.out.println("Redis failed to connect with provided details.");
            ex.printStackTrace();
        }
    }

    private JedisPubSub get() {
        return new JedisPubSub() {
            public void onMessage(String channel, String message) {
                if (channel.equals(i.channel)) {
                    JsonObject object = new JsonParser().parse(message).getAsJsonObject();
                    if (packets.containsKey(object.get("identifier").getAsString())) {
                        RedisPacket packet = packets.get(object.get("identifier").getAsString());
                        for(int i = 0; i < i.listeners.length; i++){
                            RedisListener listener = i.listeners[i];
                            listener.receivedPacket(packet, object);
                        }
                    }
                }
            }
        };
    }
}
