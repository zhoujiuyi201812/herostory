package com.shine.herostory;

import com.google.protobuf.GeneratedMessageV3;
import com.shine.herostory.msg.GameMsgProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: herostory
 * @description: 消息编码
 * @author: yczjy
 * @create: 2021-01-07 10:33
 **/
public class GameMsgEncoder extends ChannelOutboundHandlerAdapter {

    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgEncoder.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        if (null == msg || !(msg instanceof GeneratedMessageV3)) {
            super.write(ctx, msg, promise);
            return;
        }

        Integer msgCode = GameMsgRecognizer.getMsgCodeByClassName(msg.getClass());
        if(0>msgCode){
            LOGGER.error("无法识别的消息:className{}",msg.getClass().getName());
            return;
        }
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeShort((short)0);
        buffer.writeShort(msgCode);
        buffer.writeBytes(((GeneratedMessageV3) msg).toByteArray());
        BinaryWebSocketFrame frame = new BinaryWebSocketFrame(buffer);
        super.write(ctx, frame, promise);
    }
}
