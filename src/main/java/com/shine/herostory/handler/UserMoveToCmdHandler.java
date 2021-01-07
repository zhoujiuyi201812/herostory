package com.shine.herostory.handler;

import com.shine.herostory.Broadcaster;
import com.shine.herostory.msg.GameMsgProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

/**
 * @program: herostory
 * @description: 用户移动Handler
 * @author: yczjy
 * @create: 2021-01-07 15:52
 **/
public class UserMoveToCmdHandler implements CmdHandler<GameMsgProtocol.UserMoveToCmd> {
    @Override
    public void handle(ChannelHandlerContext channelHandlerContext, GameMsgProtocol.UserMoveToCmd msg) {
        GameMsgProtocol.UserMoveToCmd userMoveToCmd = (GameMsgProtocol.UserMoveToCmd) msg;
        GameMsgProtocol.UserMoveToResult result = GameMsgProtocol.UserMoveToResult
                .newBuilder()
                .setMoveUserId((Integer) channelHandlerContext.channel().attr(AttributeKey.valueOf("userId")).get())
                .setMoveToPosX(userMoveToCmd.getMoveToPosX())
                .setMoveToPosY(userMoveToCmd.getMoveToPosY())
                .build();
        Broadcaster.broadcaster(result);//广播入场
    }
}
