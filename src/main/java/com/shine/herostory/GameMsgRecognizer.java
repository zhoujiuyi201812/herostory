package com.shine.herostory;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import com.shine.herostory.msg.GameMsgProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: herostory
 * @description: 消息识别
 * @author: yczjy
 * @create: 2021-01-07 17:03
 **/
public final class GameMsgRecognizer {
    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgRecognizer.class);

    static private final Map<Integer, GeneratedMessageV3> msgCodeAndMsgBodyMap = new HashMap<>();

    static private final Map<Class<?>, Integer> msgClassAndMsgCode = new HashMap<>();

    static public void init() {
        Class<?>[] declaredClasses = GameMsgProtocol.class.getDeclaredClasses();//获取所有内部类
        for (Class<?> innerClass : declaredClasses) {
            if (!GeneratedMessageV3.class.isAssignableFrom(innerClass)) {//是否有继承关系
                continue;
            }
            String className=innerClass.getSimpleName();
            className=className.toLowerCase();
            for (GameMsgProtocol.MsgCode msgCode : GameMsgProtocol.MsgCode.values()) {
                String name = msgCode.name();
                name = name.replace("_", "");
                name = name.toLowerCase();
                if (!className.startsWith(name)) {
                    continue;
                }

                try {
                    msgCodeAndMsgBodyMap.put(msgCode.getNumber(), (GeneratedMessageV3) innerClass.getDeclaredMethod("getDefaultInstance").invoke(innerClass));
                    msgClassAndMsgCode.put(innerClass, msgCode.getNumber());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    static public Message.Builder getMsgByCode(Integer msgCode) {

        if (null == msgCode || 0 > msgCode) {
            return null;
        }

        return msgCodeAndMsgBodyMap.get(msgCode).newBuilderForType();
    }

    static public Integer getMsgCodeByClassName(Class<?> className) {
        if (null == className) {
            return -1;
        }
        Integer msgCode = msgClassAndMsgCode.get(className);
        return null == msgCode ? -1 : msgCode;
    }
}
