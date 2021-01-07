package com.shine.herostory;

import com.shine.herostory.handler.CmdHandlerFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @program: herostory
 * @description: 主服务
 * @author: yczjy
 * @create: 2021-01-05 16:35
 **/
public class ServerMain {

    public static void main(String[] args) {

        CmdHandlerFactory.init();
        GameMsgRecognizer.init();
        /**
         * 接收连接
         */
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        /**
         * 处理请求
         */
        EventLoopGroup workGroup=new NioEventLoopGroup();

        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(bossGroup,workGroup);
        bootstrap.channel(NioServerSocketChannel.class);//服务器通道处理方式
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(
                            new HttpServerCodec(),//Http服务解编码器
                            new HttpObjectAggregator(65535),//内容长度限制
                            new WebSocketServerProtocolHandler("/websocket"),
                            new GameMsgDecoder(),
                            new GameMsgEncoder(),
                            new GameMsgHandler()
                    );
            }
        });
        try {
            ChannelFuture channelFuture = bootstrap.bind(12345).sync();
            if(channelFuture.isSuccess()){
                System.out.println("服务器启动成功");
            }
            /**
             * 等待服务器通道信号关闭
             * 也就是不要退出应用程序
             * 让应用程序可以一直提供服务
             */
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
