package com.jdkhome.jvmc.io;

import com.jdkhome.jvmc.comment.enums.ResponseError;
import com.jdkhome.jvmc.comment.exception.ServiceException;
import com.jdkhome.jvmc.io.protocol.SmartCarProtocol;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author link.ji
 * createTime 下午6:14 2018/5/22
 * 节点管理器
 */
@Component
public class NodeManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 管理连接(握手的连接)
     */
    Map<String, SocketChannel> nodes = new HashMap<>();

    /**
     * 添加一个节点
     *
     * @param nickName
     * @param channel
     */
    public void addNode(String nickName, SocketChannel channel) {
        if (nodes.containsKey(nickName)) {
            logger.info("已存在node: nickname={}", nickName);
            throw new ServiceException(ResponseError.ALREADY_CONNECT);
        }

        if (nodes.containsValue(channel)) {
            logger.info("已存在连接: channel={}", channel);
            throw new ServiceException(ResponseError.ALREADY_CONNECT);
        }

        nodes.put(nickName, channel);
    }

    /**
     * 移除节点
     *
     * @param nickName
     */
    public void rmNode(String nickName) {
        if (!nodes.containsKey(nickName)) {
            logger.info("不存在的node: nickname={}", nickName);
            throw new ServiceException(ResponseError.NODE_NO_FOUND);
        }

        SocketChannel channel = nodes.get(nickName);
        channel.close();
        nodes.remove(nickName);

    }

    /**
     * 向某个节点发送消息
     *
     * @param nickName
     * @param msg
     */
    public void pushMsg(String nickName, String msg) {
        if (nodes.containsKey(nickName)) {
            logger.info("不存在node: nickname={}", nickName);
            throw new ServiceException(ResponseError.NODE_NO_FOUND);
        }

        SocketChannel channel = nodes.get(nickName);
        try {
            SmartCarProtocol response = new SmartCarProtocol(msg);
            nodes.get(nickName).writeAndFlush(response);
        } catch (Exception e) {
            channel.close();
            nodes.remove(nickName);
            logger.error("向某个节点发送消息 -> 未知异常，已移除连接={}", nickName, e);
        }
    }

    /**
     * 向广播消息
     *
     * @param msg
     */
    public void pushAll(String msg) {

        SmartCarProtocol response = new SmartCarProtocol(msg);
        nodes.entrySet().stream().forEach(node -> {
            try {

                node.getValue().writeAndFlush(response);
            } catch (Exception e) {
                node.getValue().close();
                nodes.remove(node.getKey());
                logger.error("向广播消息 -> 未知异常，已移除连接={}", node.getKey(), e);
            }
        });


    }

}
