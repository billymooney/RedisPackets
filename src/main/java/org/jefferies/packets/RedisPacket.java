package org.jefferies.packets;

import com.google.gson.JsonObject;
import lombok.Setter;
import org.jefferies.packets.publisher.RedisPublisher;

public abstract class RedisPacket {
    public abstract String getIdentifier();
    public abstract JsonObject serialized();
}
