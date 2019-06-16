package com.zzq.netty.exchat.chatServer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();
        channels.forEach(ch -> {
            if (ch !=channel){
                ch.writeAndFlush(ch.remoteAddress() +" - "+ s+'\n');
            }else {
                ch.writeAndFlush("[MY] - "+ s+'\n');
            }
        });
    }

    /**
     * 新连接加入
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.writeAndFlush("[服务器] -"+ctx.channel().remoteAddress()+" 加入\n");
        channels.add(ctx.channel());
    }

    /**
     * 连接移除
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //ChannelGroup  会自动移除断开的连接
        channels.writeAndFlush("[服务器] -"+ctx.channel().remoteAddress()+" 离开\n");
    }

    /**
     * 连接处于活动状态
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"  上线");
    }

    /**
     * 连接不可用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+" 下线");
    }
}
