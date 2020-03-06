package com.ljc.review.common.io.z_netty.connpool;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码、粘包、拆包工具
 */
public class SelfDefineEncodeHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf bufferIn, List<Object> out) {
        //规定消息前4个字节为消息长度标记，如果接收到的消息不足4个字节，不予处理
        if (bufferIn.readableBytes() < 4) {
            return;
        }
        //记录beginIndex
        int beginIndex = bufferIn.readerIndex();
        //从当前位置往后读取一个32bit的数字，并将readerIndex增加4
        int length = bufferIn.readInt();
        //如果当前可读byte长度小于length，将readerIndex复位后返回，等待下个包到来
        if (bufferIn.readableBytes() < length) {
            bufferIn.readerIndex(beginIndex);
            return;
        }
        /*
         * 下面这行代码在拆包和粘包（指发送端拆包或粘包发送）场景下有不同的意义：
         *   对于拆包而言，是将readerIndex设置为最大，这个时候需要通知ByteToMessageDecoder类bufferIn中的数据已经读取完毕了,不要再
         * 调用decode方法了。ByteToMessageDecoder类的底层会根据bufferIn.isReadable()方法来判断是否读取完毕。只有将readerIndex设
         * 置为最大,bufferIn.isReadable()方法才会返回false
         *   对于粘包而言，这是将下标后移到一段完整消息的末尾，之后再调用时就读取下一段消息
         */
        bufferIn.readerIndex(beginIndex + 4 + length);
        //截取有效信息，复制到另一个buf（处理粘包的必要操作）
        ByteBuf otherByteBufRef = bufferIn.slice(beginIndex, 4 + length);
        //让otherByteBufRef的引用计数器加1 是否有必要？
        otherByteBufRef.retain();
        //添加到结果集合
        out.add(otherByteBufRef);
    }
}
