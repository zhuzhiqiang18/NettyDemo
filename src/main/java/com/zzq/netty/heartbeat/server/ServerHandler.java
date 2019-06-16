package com.zzq.netty.heartbeat.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //心跳检测事件
        if(evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent =(IdleStateEvent)evt;
            switch (idleStateEvent.state()){
                case ALL_IDLE:
                    System.out.println("读写空闲");
                    break;
                case READER_IDLE:
                    System.out.println("读空闲");
                    break;
                case WRITER_IDLE:
                    System.out.println("写空闲");
                    break;
            }
            ctx.channel().close();
        }
    }
}
