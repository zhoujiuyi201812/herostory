package com.shine.herostory.handler;

import com.google.protobuf.GeneratedMessageV3;
import com.shine.herostory.msg.GameMsgProtocol;
import io.netty.channel.ChannelHandlerContext;

public interface CmdHandler <Tcmd extends GeneratedMessageV3>{
    /**
     * 处理指令
     * @param channelHandlerContext
     * @param msg
     */
    void handle(ChannelHandlerContext channelHandlerContext, Tcmd msg);
}
