package org.jefferies.packets.example;

import lombok.Getter;
import org.jefferies.packets.handler.RedisHandler;

@Getter
public class Main {

    public static void main(String[] args){
        RedisHandler handler = new RedisHandler("Test", "127.0.0.1", 6379, "");
        handler.registerPacket(new ExamplePacket());
        handler.setListener(new Listener());
        handler.sendPacket(new ExamplePacket("test"));
        // Prints 'test' in each console instance where this exact code code is running.
    }

}
