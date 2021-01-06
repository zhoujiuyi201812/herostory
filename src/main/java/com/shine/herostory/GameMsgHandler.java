package com.shine.herostory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

/**
 * @program: herostory
 * @description: 游戏消息处理器
 * @author: yczjy
 * @create: 2021-01-05 17:22
 **/
public class GameMsgHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        System.out.println("收到客户端消息:"+msg);
        //WebSocket 二进制消息会通过 HttpServerCodec 解码成 BinaryWebSocketFrame
        BinaryWebSocketFrame binaryWebSocketFrame=(BinaryWebSocketFrame)msg;

        ByteBuf buf = binaryWebSocketFrame.content();
        //拿到真实字节数组 并打印
        byte[] data=new byte[buf.readableBytes()];
        buf.readBytes(data);
        System.out.println("收到字节");
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] +" ");
        }
    }
}
