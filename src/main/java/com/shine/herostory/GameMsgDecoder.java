package com.shine.herostory;

import com.google.protobuf.GeneratedMessageV3;
import com.shine.herostory.msg.GameMsgProtocol;
import com.shine.herostory.msg.GameMsgProtocol.MsgCode;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

/**
 * @program: herostory
 * @description: 游戏消息解码器
 * @author: yczjy
 * @create: 2021-01-06 17:33
 **/
public class GameMsgDecoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("进入解码器");
        if (null == msg || !(msg instanceof BinaryWebSocketFrame)) {
            return;
        }
        //WebSocket  二进制消息会被HttpServerCodec解码成BinaryWebSocketFrame
        BinaryWebSocketFrame frame = (BinaryWebSocketFrame) msg;
        ByteBuf buf = frame.content();
        byte[] msgBody = new byte[buf.readableBytes()];
        buf.readBytes(msgBody);
        buf.readShort();
        int msgCode = buf.readShort();
        GeneratedMessageV3 cmd=null;
        switch (msgCode) {
            case MsgCode.USER_ENTRY_CMD_VALUE:
                cmd = GameMsgProtocol.UserEntryCmd.parseFrom(msgBody);
                break;
            default:
                throw new IllegalArgumentException("消息编码错误");

        }
        if(null!=cmd){
            ctx.fireChannelRead(cmd);
        }

    }
}
