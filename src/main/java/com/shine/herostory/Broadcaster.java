package com.shine.herostory;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


/**
 * @program: herostory
 * @description: 广播类
 * @author: yczjy
 * @create: 2021-01-07 15:39
 **/
public final class Broadcaster {

    /**
     * 客户端信道数组, 一定要使用 static, 否则无法实现群发
     */
    static private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private Broadcaster(){}


    public static void addChannel(Channel channel){
        channels.add(channel);
    }


    public static void removeChannel(Channel channel){
        channels.remove(channel);
    }


    public static void broadcaster(Object msg){
        if(null!=msg){
            channels.writeAndFlush(msg);
        }
    }



}
