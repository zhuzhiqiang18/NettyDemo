package com.zzq.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestHttpServer {
    public static void main(String[] args) throws Exception{
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {
            serverBootstrap.group(eventLoopGroup).channel(NioServerSocketChannel.class).childHandler(new TestServerInitializer());

            ChannelFuture channelFuture= serverBootstrap.bind(8888).sync();

            channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }



    }
}
