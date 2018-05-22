package com.jdkhome.jvmc.comment.enums;

/**
 * author link.ji
 * createTime 下午6:47 2018/5/22
 */
public enum MsgEnums {

    PING(0X01),
    PONG(0X02),
    HAND(0X03);

    int code;

    MsgEnums(int code) {
        this.code = code;
    }
}
