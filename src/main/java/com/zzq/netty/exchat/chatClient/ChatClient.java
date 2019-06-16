package com.zzq.netty.exchat.chatClient;

import com.zzq.netty.exchat.chatServer.ChatServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatClient {
    public static void main(String[] args) throws Exception{
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline channelPipeline= socketChannel.pipeline();
                    channelPipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
                    channelPipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                    channelPipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                    channelPipeline.addLast(new ChatClientHandler());
                }
            });
            Channel channel= bootstrap.connect("localhost",8888).sync().channel();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
            for (;;){
                channel.writeAndFlush(bufferedReader.readLine()+"\r\n");

            }
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
