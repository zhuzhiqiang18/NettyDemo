package com.zzq.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

public class TestServerHandler extends SimpleChannelInboundHandler<HttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpRequest httpObject) throws Exception {
//        System.out.println("method:"+httpObject.method().name());
        URI uri=new URI(httpObject.uri());
        if(uri.getPath().equals("/favicon.ico")) return;
//        System.out.println("URI:"+uri);
        String method=httpObject.method().name();
        String queryStr="";
        if(uri.getQuery()!=null){
            String querys[]=uri.getQuery().split("\\&");
            for (String query:querys){
                queryStr+=query+"\t";
            }
        }



        ByteBuf byteBuf= Unpooled.copiedBuffer(method+"\r"+queryStr, CharsetUtil.UTF_8);
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,byteBuf);
        fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
        fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
        channelHandlerContext.writeAndFlush(fullHttpResponse);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded");
        super.handlerAdded(ctx);
    }
}
