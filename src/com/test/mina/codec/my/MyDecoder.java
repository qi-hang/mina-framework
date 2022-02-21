package com.test.mina.codec.my;

import com.test.mina.codec.IMessageDecoder;
import com.test.mina.codec.SerializerHelper;
import com.test.mina.codec.my.v1.MyEncoder;
import com.test.mina.message.Message;
import com.test.mina.message.MessageFactory;

import java.nio.ByteOrder;

import com.test.mina.message.SimpletestMessage;
import com.test.mina.message.TestMessage;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.test.mina.codec.my.MyUtils.fmtLenMap;

/**
 * 自定义解码器，对客户端发给服务端的SEND包进行decode
 */
public class MyDecoder extends CumulativeProtocolDecoder {
    static Logger logger = LoggerFactory.getLogger(MyDecoder.class);

//    @Override
//    protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
//        // 假设不存在粘包和断包情况
//        ioBuffer = ioBuffer.order(ByteOrder.LITTLE_ENDIAN);
//
//        // 从ioBuffer中取出客户端发来的SEND包内容（加密的）
//        byte[] encBs = ioBuffer.array();
//
//        // 对SEND包进行解密
//        byte[] decBs = PacketEnc.decrypt(encBs);
//
//        // 解密后SEND包整体的长度
//        int len = decBs.length;
//
//        // SEND包的整体结构如下：
//        //   2字节长度的协议号 + 数据包具体内容
//        byte[] idBs = new byte[2];
//        idBs[0] = decBs[0];
//        idBs[1] = decBs[1];
//
//        int moduleId = Convert.bytes2ToIntLittle(idBs);
//
//        // 根据协议号获取本数据包对应的Model类
//        Class<?> msgClazz = MessageFactory.INSTANCE.getMessage(moduleId, 0);
//        logger.info("收到数据包==协议号={}={}", moduleId, ioBuffer.getHexDump());
//
//        if (msgClazz == null) {
//            logger.warn("未处理==协议号={}", moduleId);
//        }
//
//        IMessageDecoder msgDecoder = SerializerHelper.getInstance().getDecoder();
//        try {
//            Message msg = msgDecoder.readMessage(moduleId, (short) 0, decBs);
//            protocolDecoderOutput.write(msg);
//        } catch (Exception e) {
//            logger.error("解包出错:==={}", moduleId);
//            logger.error("解包出错:==={}=={}", moduleId, ioBuffer.getHexDump());
//        }
//
//        return true;
//    }

    // 注释内容忽略
    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        logger.info("before:"+ioBuffer.getHexDump());
        // 当前假设：
        //  SEND包只存在粘包现象，不存在断包现象
        ioBuffer = ioBuffer.order(ByteOrder.LITTLE_ENDIAN);
        // 丢包，断包处理
        if (ioBuffer.remaining() >= 2) {
            //ioBuffer.mark();// 标记当前position的快照标记mark，以便后继的reset操作能恢复position位置，开始是0
            byte[] encBs = ioBuffer.array();
            //logger.info("limit:"+String.valueOf(ioBuffer.limit()));
            //byte[] decBs = PacketEnc.decrypt(encBs);//暂时取消加密
            byte[] decBs = encBs;
//            ioBuffer.clear();
//            ioBuffer.put(decBs);
//            ioBuffer.flip();
            //logger.info("after:"+ioBuffer.getHexDump());
            //解密
//            for(int i=0;i<decBs.length;i++){
//                System.out.print(decBs[i]+" ");
//            }
            int readIndex= 0;
            int len = ioBuffer.limit();
            while (true) {
                byte[] idBs = new byte[2];
                idBs[0] = decBs[readIndex];
                readIndex += 1;
                idBs[1] = decBs[readIndex];
                readIndex += 1;

                int moduleId = Convert.bytes2ToIntLittle(idBs);
                //String define = MyUtils.DEFINE_MAP.get(moduleId);
                String define = MessageFactory.INSTANCE.getMessagemodule(moduleId);

//            logger.warn("长度足够==协议号==={}={}",length, ioBuffer.getHexDump());

                Class<?> msgClazz = MessageFactory.INSTANCE.getMessage(moduleId, 0);
                logger.warn("收到封包==协议号={}={}={}", moduleId, ioBuffer.getHexDump(),define);
                if (msgClazz == null) {
                    logger.warn("未处理封包==协议号={}", moduleId);
                }

                // 根据define完成本包读取
                logger.info("readIndex1:"+readIndex);
                byte[] body = getBsByDefine(define, decBs, readIndex);
                readIndex += body.length;
                logger.info("readIndex:"+readIndex+"##"+"len:"+len);
                if(readIndex>len){
                    logger.error("数据不全");
                    return false;
                }
//            HashMap<String, Long> heartbeatMap = SessionManager.INSTANCE.getSessionAttr(ioSession, SessionProperties.HEARTBEAT_MAP, HashMap.class);
//            if (heartbeatMap == null) {/**这个应该只有第一次的时候为空*/
//                heartbeatMap = new HashMap<>();
//            }
//            /**如果是心跳包 把心跳时间写进去*/
//            if (moduleId == Modules.CMD_ECHO) {
//                heartbeatMap.put(Const.LAST_HEART_TIME, time);
//            }
//            heartbeatMap.put(Const.REQ_CURR_TIME, time);
//            ioSession.setAttribute(SessionProperties.HEARTBEAT_MAP, heartbeatMap);

                IMessageDecoder msgDecoder = SerializerHelper.getInstance().getDecoder();
                try {
                    //logger.info("body:"+Convert.bytesToHexStr(body));
                    Message msg = msgDecoder.readMessage(moduleId, (short) 0, body);
                    SimpletestMessage ss = (SimpletestMessage) msg;
                    byte[] b1=new byte[1];
                    b1=ss.getLen1byte();
                    byte[] b2=new byte[1];
                    b2=ss.getLen2byte();
                    logger.info("成功！result:"+/*+ss.getIntVal()+"##"+ss.getLen1str()
                            +"##"+ss.isBoolVal()+"##"+ss.getDoubleVal()
                            +"##"+ss.getLen2str()+*/" b1:"+b1[0]+" b2:"+b2[0]+"##"+ss.getUintval());
//                    TestMessage ss=(TestMessage) msg;
//                    logger.info("result:"+ss.getIntVal()+"##"+ss.getDoubleVal()+"##"+ss.getStr1());
                    //logger.info("解包成功！msg:"+msg.toString());
                    protocolDecoderOutput.write(msg);
                } catch (Exception e) {
                    logger.error("解包出错:==={}", moduleId);
                    logger.error("解包出错:==={}=={}", moduleId, ioBuffer.getHexDump());
                }

                if (readIndex >= len) {
                    return true;
                }

            }
        }
        return false;// 断包，或者执行完，
    }

    private static byte[] getBsByDefine(String define, byte[] decBs, int index) {
        byte[] tmp = new byte[4096];
        int len1 = 2;//body当前总长度，index：当前读取位数，初始为2
        String fmt = define;
        int fmtLen = fmt.length();
        if (fmtLen == 0) {
            return null;
        }
        String[] fmtArr = new String[fmtLen];
        for (int i = 0; i < fmtLen; i++) {
            fmtArr[i] = fmt.substring(i, i + 1);
        }
        for (int i = 0; i < fmtArr.length; i++) {
            String s = fmtArr[i];
            System.out.println("$$$"+s);

            if (!fmtLenMap.containsKey(s)) {
                logger.error("define不存在#1: " + s);
            }
            int len = fmtLenMap.get(s);
            //logger.info("len: " + len);
            switch (s) {
                case "i":
                case "d":
                case "b":
                case "I":
                case "C":
                case "c":
                case "H":
                case "l":
                    len1 += len;//body加上应该长度
                    //logger.info(s+"***"+"neibu:index:"+index+" len1:"+len1+" len:"+len);
                    index += len;
                    break;
                case "s":
                case "u":
                    len1 += len;//总长度加上位数部分
                    byte len1b = decBs[index];
                    //logger.info(decBs[index]+"##"+index);
                    if (len1b < 0) {
                        len1b += 256;
                    }
                    index += len;//index读取位数部分
                    logger.info("neibu:index:"+index+" len1b:"+len1b+" len1:"+len1);
                    index += len1b;//index加上读出的长度
                    logger.info("neibu:index:"+index+" len1b:"+len1b+" len1:"+len1);
                    len1 += len1b;//总长度加上读出的长度
                    break;
                case "S":
                case "U":
                    len1 += len;
                    byte[] len2Bs = new byte[2];
                    len2Bs[0] = decBs[index];
                    index++;
                    len2Bs[1] = decBs[index];
                    index++;
                    int len2 = Convert.bytes2ToIntBig(len2Bs);
                    logger.info("neibu:index:"+index+" len2:"+len2+"**"+len2Bs[0]+"**"+len2Bs[1]);
                    index += len2;
                    len1 += len2;
                    break;
                default:
                    logger.error("define不存在#2：" + s);
                    break;
            }
        }

        byte[] res = new byte[len1-2];
        System.arraycopy(decBs, 2, res, 0, len1-2);
        logger.info("res:"+Convert.bytesToHexStr(res)+" len1:"+len1);
        return res;
    }

}
