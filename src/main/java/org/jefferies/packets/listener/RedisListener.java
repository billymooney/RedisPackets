package org.jefferies.packets.listener;

import com.google.gson.JsonObject;
import org.jefferies.packets.RedisPacket;

import java.lang.reflect.Method;

public abstract class RedisListener {
    public RedisListener(){
    }
    public final void receivedPacket(RedisPacket packet, JsonObject object) {
        try {
            for (Method method : getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(PacketListener.class)) {
                    PacketListener annotation = method.getAnnotation(PacketListener.class);
                    if (annotation.identifier().equalsIgnoreCase(packet.getIdentifier())) {
                        method.invoke(this, object);
                    }
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
