package com.shine.herostory;

import com.google.protobuf.GeneratedMessageV3;
import com.shine.herostory.entity.User;
import com.shine.herostory.handler.CmdHandler;
import com.shine.herostory.handler.CmdHandlerFactory;
import com.shine.herostory.model.UserManager;
import com.shine.herostory.msg.GameMsgProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;


/**
 * @program: herostory
 * @description: 游戏消息处理器
 * @author: yczjy
 * @create: 2021-01-05 17:22
 **/
public class GameMsgHandler extends SimpleChannelInboundHandler {




    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Broadcaster.addChannel(ctx.channel());
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Integer userId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();
        if(null==userId){
            return;
        }
        UserManager.removeUser(userId);
        super.handlerRemoved(ctx);
        Broadcaster.removeChannel(ctx.channel());
        GameMsgProtocol.UserQuitResult build = GameMsgProtocol.UserQuitResult.newBuilder().setQuitUserId(userId).build();
        Broadcaster.broadcaster(build);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        System.out.println("收到客户端消息 className=" + msg.getClass().getName() + " msg=" + msg);
        CmdHandler<? extends GeneratedMessageV3> cmdHandler = CmdHandlerFactory.create(msg.getClass());
        cmdHandler.handle(channelHandlerContext, cast(msg));
    }

    public <Tcmd extends GeneratedMessageV3> Tcmd cast(Object msg){
        if(null==msg){
            return null;
        }
        return (Tcmd)msg;
    }


}
