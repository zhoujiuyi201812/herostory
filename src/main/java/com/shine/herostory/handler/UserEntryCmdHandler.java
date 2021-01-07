package com.shine.herostory.handler;

import com.shine.herostory.Broadcaster;
import com.shine.herostory.entity.User;
import com.shine.herostory.model.UserManager;
import com.shine.herostory.msg.GameMsgProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

/**
 * @program: herostory
 * @description: 用户入场
 * @author: yczjy
 * @create: 2021-01-07 15:51
 **/
public class UserEntryCmdHandler implements CmdHandler<GameMsgProtocol.UserEntryCmd>{
    @Override
    public void handle(ChannelHandlerContext channelHandlerContext, GameMsgProtocol.UserEntryCmd msg) {
        GameMsgProtocol.UserEntryCmd userEntryCmd = (GameMsgProtocol.UserEntryCmd) msg;
        GameMsgProtocol.UserEntryResult build = GameMsgProtocol.UserEntryResult.newBuilder()
                .setUserId(userEntryCmd.getUserId())
                .setHeroAvatar(userEntryCmd.getHeroAvatar())
                .build();
        User user = new User();
        user.setUserId(userEntryCmd.getUserId());
        user.setHeroAvatar(userEntryCmd.getHeroAvatar());
        UserManager.addUser(user);
        channelHandlerContext.channel().attr(AttributeKey.valueOf("userId")).set(user.getUserId());
        Broadcaster.broadcaster(build);//广播入场
    }
}
