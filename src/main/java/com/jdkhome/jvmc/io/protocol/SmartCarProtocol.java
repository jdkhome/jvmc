package com.jdkhome.jvmc.io.protocol;


/**
 * Created by jdk on 17/7/3.
 *
 * 自己定义的协议
 *  数据包格式
 * +——----——+——-----——+——----——+
 * |  长度   |   数据  |结束标志 |
 * +——----——+——-----——+——----——+
 * 1.协议头4个字节代表长度 int类型
 * 2.中间是要传输的数据,长度不应该超过4096，防止socket流的攻击
 * 3.尾部为结束标志
 */
public class SmartCarProtocol {


    /**
     * 消息的长度
     */
    private int contentLength;
    /**
     * 消息的内容
     */
    private byte[] content;

    /**
     * 消息的结束标志
     */
    private int endFlag = ConstantValue.END_FLAG;




    /**
     * 用于初始化，SmartCarProtocol
     *
     * @param contentLength
     *            协议里面，消息数据的长度
     * @param content
     *            协议里面，消息的数据
     */
    public SmartCarProtocol(int contentLength, byte[] content) {
        this.contentLength = contentLength;
        this.content = content;
    }



    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getEndFlag() {
        return endFlag;
    }

    public void setEndFlag(int endFlag) {
        this.endFlag = endFlag;
    }



    @Override
    public String toString() {
        return "SmartCarProtocol [ contentLength="
                + contentLength + ", content=\"" + new String(content) + "\", end_flag=" + endFlag + " ]";
    }

}
