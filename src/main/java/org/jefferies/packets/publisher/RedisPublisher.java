package org.jefferies.packets.publisher;

import com.google.gson.JsonObject;
import redis.clients.jedis.Jedis;

public class RedisPublisher {

    private Jedis jedis;

    private String channel;

    public RedisPublisher(String channel, String address, int port, String password) {
        try {
            this.channel = channel;
            jedis = new Jedis(address, port);
            if (!password.equals("")) {
                jedis.auth(password);
            }
        } catch (Exception ex){
            System.out.println("Redis Publisher failed to connect with provided details.");
        }
    }

    public void write(String msg) {
        jedis.publish(channel, msg);
    }

}
