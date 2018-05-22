package com.jdkhome.jvmc.io;

import com.alibaba.fastjson.JSONObject;
import com.jdkhome.jvmc.comment.enums.ResponseError;
import com.jdkhome.jvmc.comment.exception.ServiceException;
import com.jdkhome.jvmc.io.msg.BaseMsg;
import com.jdkhome.jvmc.io.protocol.SmartCarProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author wxb
 * @Created 下午2:42 18/2/24
 * @Description: ChannelHandlerAdapter， 这个类实现了ChannelHandler接口， ChannelHandler提供了许多事件处理的接口方法，
 * 然后你可以覆盖这些方法。 现在仅仅只需要继承ChannelHandlerAdapter类而不是你自己去实现接口方法。
 */
@Component
@ChannelHandler.Sharable
@Log
public class P2PNodeHandLer extends ChannelInboundHandlerAdapter {

    @Autowired
    NodeManager nodeManager;


    /**
     * 这里我们覆盖了chanelRead()事件处理方法。 每当从客户端收到新的数据时， 这个方法会在收到消息时被调用，
     * 这个例子中，收到的消息的类型是ByteBuf
     *
     * @param ctx 通道处理的上下文信息
     * @param msg 接收的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取msg中的code 和 type
        SmartCarProtocol body = (SmartCarProtocol) msg;
        log.info("收到消息 :" + body.toString());

        try {
            // 消息处理器
            //nodeManager.addNode("test", (SocketChannel) ctx.channel());
        } catch (ServiceException se) {
            ctx.channel().writeAndFlush(new SmartCarProtocol(JSONObject.toJSONString(BaseMsg.responseWithServiceError(se))));
        }

    }

    // 连接成功 -> 发送握手消息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        // 获取本机配置...
        BaseMsg baseMsg = new BaseMsg(ResponseError.REQUEST_HAND);

        String msg = JSONObject.toJSONString(baseMsg);

        log.info("发送消息" + msg);

        ctx.channel().writeAndFlush(new SmartCarProtocol(msg));


    }

    /***
     * 这个方法会在发生异常时触发
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }


}
