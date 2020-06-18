package org.jefferies.packets.example;

import lombok.Getter;
import org.jefferies.packets.handler.RedisHandler;

@Getter
public class Main {

    @Getter private static Main instance;
    private RedisHandler handler;

    public Main(){
        instance = this;
        handler = new RedisHandler("Test", "127.0.0.1", 6379, "");
        handler.registerPacket(new ExamplePacket());
        handler.setListener(new Listener());
        handler.sendPacket(new ExamplePacket("test"));
    }

    public static void main(String[] args){
        new Main();
    }

}
