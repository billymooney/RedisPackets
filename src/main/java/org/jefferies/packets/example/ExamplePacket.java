package org.jefferies.packets.example;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jefferies.packets.RedisPacket;

@AllArgsConstructor
@NoArgsConstructor
public class ExamplePacket extends RedisPacket {

    private String message;

    @Override
    public String getIdentifier() {
        return "ExamplePacket";
    }

    @Override
    public JsonObject serialized() {
        JsonObject object = new JsonObject();
        object.addProperty("message", message);
        return object;
    }

}
