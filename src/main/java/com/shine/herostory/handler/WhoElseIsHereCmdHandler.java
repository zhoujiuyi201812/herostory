package com.shine.herostory.handler;

import com.shine.herostory.entity.User;
import com.shine.herostory.model.UserManager;
import com.shine.herostory.msg.GameMsgProtocol;
import io.netty.channel.ChannelHandlerContext;

/**
 * @program: herostory
 * @description: 当前在场用户
 * @author: yczjy
 * @create: 2021-01-07 15:52
 **/
public class WhoElseIsHereCmdHandler implements CmdHandler<GameMsgProtocol.WhoElseIsHereCmd>{
    @Override
    public void handle(ChannelHandlerContext channelHandlerContext, GameMsgProtocol.WhoElseIsHereCmd msg) {
        GameMsgProtocol.WhoElseIsHereResult.Builder builder = GameMsgProtocol.WhoElseIsHereResult.newBuilder();
        for (User user : UserManager.listUser()) {
            if (null == user) {
                continue;
            }
            GameMsgProtocol.WhoElseIsHereResult.UserInfo build = GameMsgProtocol.WhoElseIsHereResult
                    .UserInfo
                    .newBuilder()
                    .setUserId(user.getUserId())
                    .setHeroAvatar(user.getHeroAvatar())
                    .build();
            builder.addUserInfo(build);
        }
        channelHandlerContext.writeAndFlush(builder.build());
    }
}
