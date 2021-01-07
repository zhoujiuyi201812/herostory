package com.shine.herostory.handler;

import com.google.protobuf.GeneratedMessageV3;
import com.shine.herostory.msg.GameMsgProtocol;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: herostory
 * @description: 指令工厂
 * @author: yczjy
 * @create: 2021-01-07 16:16
 **/
public final class CmdHandlerFactory {

    private CmdHandlerFactory(){}

    static private final Map<Class<?>,CmdHandler<? extends GeneratedMessageV3>> HANDLER_MAP=new HashMap<>();

    static public void init(){
        HANDLER_MAP.put(GameMsgProtocol.UserEntryCmd.class,new  UserEntryCmdHandler());
        HANDLER_MAP.put(GameMsgProtocol.WhoElseIsHereCmd.class,new WhoElseIsHereCmdHandler());
        HANDLER_MAP.put(GameMsgProtocol.UserMoveToCmd.class,new UserMoveToCmdHandler());
    }

    static public CmdHandler<? extends GeneratedMessageV3>  create(Class<?> className){
        if(null==className){
            return null;
        }
        return HANDLER_MAP.get(className);
    }
}
