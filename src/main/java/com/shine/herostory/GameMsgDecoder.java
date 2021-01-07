package com.shine.herostory;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import com.shine.herostory.msg.GameMsgProtocol;
import com.shine.herostory.msg.GameMsgProtocol.MsgCode;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: herostory
 * @description: 游戏消息解码器
 * @author: yczjy
 * @create: 2021-01-06 17:33
 **/
public class GameMsgDecoder extends ChannelInboundHandlerAdapter {

    static private final Logger LOGGER=LoggerFactory.getLogger(GameMsgDecoder.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (null == msg || !(msg instanceof BinaryWebSocketFrame)) {
            return;
        }
        //WebSocket  二进制消息会被HttpServerCodec解码成BinaryWebSocketFrame
        BinaryWebSocketFrame frame = (BinaryWebSocketFrame) msg;
        ByteBuf buf = frame.content();
        buf.readShort();
        int msgCode = buf.readShort();
        byte[] msgBody = new byte[buf.readableBytes()];
        buf.readBytes(msgBody);
        Message.Builder builder = GameMsgRecognizer.getMsgByCode(msgCode);
        if(null==builder){
            LOGGER.error("无法识别的消息:{}"+msgCode);
        }
        builder.clear();
        builder.mergeFrom(msgBody);
        Message message = builder.build();
        if (null != message) {
            ctx.fireChannelRead(message);
        }

    }
}
