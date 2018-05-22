package com.jdkhome.jvmc.io.protocol;

import com.jdkhome.jvmc.io.error.DataPackException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

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
public class SmartCarDecoder extends ByteToMessageDecoder {

    /**
     * <pre>
     * 表示数据的长度contentLength，int类型，占据4个字节.
     * 尾部结束标志，int类型，占据4个字节.
     * </pre>
     */
    public static final int BASE_LENGTH = 4 + 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer,
                          List<Object> out) throws Exception {
        // 可读长度必须大于基本长度
        if (buffer.readableBytes() >= SmartCarDecoder.BASE_LENGTH) {

            //标记包头开始
            buffer.markReaderIndex();

            // 防止socket字节流攻击
            // 防止，客户端传来的数据过大
            if (buffer.readableBytes() > 4096) {
                buffer.skipBytes(buffer.readableBytes());
            }

            // 记录包头开始的index
            int beginReader = 4;

            // 首先读消息的长度
            int length = buffer.readInt();

            // 判断请求数据包数据是否到齐
            if (buffer.readableBytes() <= length) {
                //没到齐就重置指针
                buffer.resetReaderIndex();
                return;
            }

            //读这个消息
            byte[] data = new byte[length];
            buffer.readBytes(data);

            // 接着再读一个4字节标志位
            int end_flag = buffer.readInt();

            //如果读的东西并不是那个标志位。。
            if (end_flag != ConstantValue.END_FLAG) {
                throw new DataPackException("读完消息结果没拿到结束标志");
            }

            SmartCarProtocol protocol = new SmartCarProtocol(data.length, data);
            out.add(protocol);
        }
    }

}