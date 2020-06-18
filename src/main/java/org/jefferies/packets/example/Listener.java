package org.jefferies.packets.example;

import com.google.gson.JsonObject;
import org.jefferies.packets.listener.PacketListener;
import org.jefferies.packets.listener.RedisListener;

public class Listener extends RedisListener {

    @PacketListener(identifier = "ExamplePacket")
    public void onExecute(JsonObject object){
        String message = object.get("message").getAsString();
        System.out.println(message);
    }

}
