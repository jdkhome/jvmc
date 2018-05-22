package com.jdkhome.jvmc.io.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by jdk on 17/7/3.
 * <p>
 * 自己定义的协议
 * 数据包格式
 * +——----——+——-----——+——----——+
 * |  长度   |   数据  |结束标志 |
 * +——----——+——-----——+——----——+
 * 1.协议头4个字节代表长度 int类型
 * 2.中间是要传输的数据,长度不应该超过4096，防止socket流的攻击
 * 3.尾部为结束标志
 */
public class SmartCarEncoder extends MessageToByteEncoder<SmartCarProtocol> {

    @Override
    protected void encode(ChannelHandlerContext tcx, SmartCarProtocol msg,
                          ByteBuf out) throws Exception {

        // 1.写入消息的长度(int 类型)
        out.writeInt(msg.getContentLength());
        // 2.写入消息的内容(byte[]类型)
        out.writeBytes(msg.getContent());
        // 3.写入尾部结束标志信息
        out.writeInt(msg.getEndFlag());
    }
}